package com.sunnet.org.util;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

/**
 * 二维码工具类
 * 
 */
public class QRCodeUtil {

	private static final String CHARSET = "utf-8";
	private static final String FORMAT_NAME = "JPG";
	// 二维码尺寸
	private static final int QRCODE_SIZE = 300;
	// LOGO宽度
	private static final int WIDTH = 60;
	// LOGO高度
	private static final int HEIGHT = 60;

	private static BufferedImage createImage(String content, String imgPath,boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
//		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (imgPath == null || "".equals(imgPath)) {
			return image;
		}
		// 插入图片
		QRCodeUtil.insertImage(image, imgPath, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath,
			boolean needCompress) throws Exception {
		File file = new File(imgPath);
		if (!file.exists()) {
			System.err.println(""+imgPath+"   该文件不存在！");
			return;
		}
		Image src = ImageIO.read(new File(imgPath));
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存放目录
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, String destPath,
			boolean needCompress) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content, imgPath,needCompress);
		mkdirs(destPath);
		String file = new Random().nextInt(99999999)+".jpg";
		ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
	}
	
	
	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存放目录
	 * @throws Exception
	 */
	public static String encodeString(String content, String destPath) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content, "",true);
		mkdirs(destPath);
		String file = new Random().nextInt(99999999)+".jpg";
		ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));
		return file;
	}

	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
	 * @author lanyuan
	 * Email: mmm333zzz520@163.com
	 * @date 2013-12-11 上午10:16:36
	 * @param destPath 存放目录
	 */
	public static void mkdirs(String destPath) {
		File file =new File(destPath);    
		//当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param destPath
	 *            存储地址
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath, String destPath)
			throws Exception {
		QRCodeUtil.encode(content, imgPath, destPath, false);
	}
	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存储地址
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String destPath,
			boolean needCompress) throws Exception {
		QRCodeUtil.encode(content, null, destPath, needCompress);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param destPath
	 *            存储地址
	 * @throws Exception
	 */
	public static void encode(String content, String destPath) throws Exception {
		QRCodeUtil.encode(content, null, destPath, false);
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 *            内容
	 * @param imgPath
	 *            LOGO地址
	 * @param output
	 *            输出流
	 * @param needCompress
	 *            是否压缩LOGO
	 * @throws Exception
	 */
	public static void encode(String content, String imgPath,
			OutputStream output, boolean needCompress) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content, imgPath,
				needCompress);
		ImageIO.write(image, FORMAT_NAME, output);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param output
	 *            输出流
	 * @throws Exception
	 */
	public static void encode(String content, OutputStream output)
			throws Exception {
		QRCodeUtil.encode(content, null, output, false);
	}

	/**
	 * 解析二维码
	 * 
	 * @param file
	 *            二维码图片
	 * @return
	 * @throws Exception
	 */
	public static String decode(File file) throws Exception {
		/*BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;*/
		return "";
	}

	/**
	 * 解析二维码
	 * 
	 * @param path
	 *            二维码图片地址
	 * @return
	 * @throws Exception
	 */
	public static String decode(String path) throws Exception {
		return QRCodeUtil.decode(new File(path));
	}

	public static void main(String[] args) throws Exception {
		//String text = "http://www.baidu.com";
		String text = "http://120.24.234.123:8080/admin/login";
		//String text = "http://120.24.234.123:8080/pc/QRcodetoAddMember";
		//QRCodeUtil.encode(text, "c:/a/68479953.jsp", "c:/a/", true); //镶嵌型
		String code=QRCodeUtil.encodeString(text,"c:/a/");
		System.out.println(code);
		//System.out.println(QRCodeUtil.encodeString(text,"c:/a/")); //单张
	}

	//微信小程序生成二维码
	public static Image getminiqrQr(String page,String sceneStr, String url) {
		RestTemplate rest = new RestTemplate();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			Map<String,Object> param = new HashMap<>();
			param.put("scene", sceneStr);
			param.put("page", page);
			param.put("width", 300);
			param.put("auto_color", false);
			Map<String,Object> line_color = new HashMap<>();
			line_color.put("r", 0);
			line_color.put("g", 0);
			line_color.put("b", 0);
			param.put("line_color", line_color);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			HttpEntity<String> requestEntity = new HttpEntity<String>(JSONObject.fromObject(param).toString(), headers);
			ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
			byte[] result = entity.getBody();
			inputStream = new ByteArrayInputStream(result);
			if(inputStream!=null){
				Image img = ImageIO.read(inputStream);
				if(img==null){
					return null;
				}
				return img;
			}
		} catch (Exception e) {
			System.out.println("调用小程序生成微信永久小程序码URL接口异常"+e);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getQrCode(String scene, String page,String access_token){
		Map<String, Object> params = new HashMap<>();
		params.put("scene", scene);  //参数  这个参数是自己来识别的  可以随意  但我们写的程序他是唯一识别
		params.put("page", page); //位置
		params.put("width",300);

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+access_token);  // 接口
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		String body = JSON.toJSONString(params);
		System.err.println("参数==="+body);//必须是json模式的 post
		StringEntity entity;
		HttpResponse response;

		InputStream inputStream = null;
		try {
			entity = new StringEntity(body);
			entity.setContentType("image/png");

			httpPost.setEntity(entity);

			response = httpClient.execute(httpPost);
			inputStream = response.getEntity().getContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		try {
			while ((ch = inputStream.read()) != -1) {
				bytestream.write(ch);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		byte[] program = bytestream.toByteArray();
		BASE64Encoder encoder = new BASE64Encoder();
		String  binary = encoder.encodeBuffer(program).trim();
		System.err.println("binary===="+binary);
		return binary;
	}

}
