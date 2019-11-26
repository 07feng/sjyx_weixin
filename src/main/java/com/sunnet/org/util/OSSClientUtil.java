package com.sunnet.org.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.sunnet.framework.util.RandomStringGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class OSSClientUtil {
	Log log = LogFactory.getLog(OSSClientUtil.class);

	private static String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
	// accessKey
	private static String ACCESS_KEY_ID = "LTAI67K1f1TOC7KQ";
	private static String ACCESS_KEY_SECRET = "mNJhSMDUTtOvSbpii7SOmHoexI4WvP";
	// 空间
	private static String BACKET_NAME = "sjyximage";

	// 文件存储目录
	private  String FOLDER = DateUtil.DateToString(DateUtil.getDate(),"YYYY-MM-dd")+"/";

	private String replace = "http://image.91sjyx.com/";

	public String getReplace() {
		return replace;
	}

	private static OSSClient ossClient;


	public String getFOLDER() {
		return FOLDER;
	}

	public void setFOLDER(String FOLDER) {
		this.FOLDER = FOLDER;
	}

	public OSSClientUtil() {
		ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	/**
	 * 初始化
	 */
	public void init() {
		ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	/**
	 * 销毁
	 */
	public void destory() {
		ossClient.shutdown();
	}

	/**
	 * 上传图片
	 *
	 * @param url
	 */
	public void uploadImg2Oss(String url) {
		File fileOnServer = new File(url);
		FileInputStream fin;
		try {
			fin = new FileInputStream(fileOnServer);
			String[] split = url.split("/");
			this.uploadFile2OSS(fin, split[split.length - 1]);
		} catch (FileNotFoundException e) {
			// throw new ImgException("图片上传失败");
		}
	}

	public String uploadImg2Oss(MultipartFile file) {
		if (file.getSize() > 1024 * 1024) {
			// throw new ImgException("上传图片大小不能超过1M！");
		}
		String originalFilename = file.getOriginalFilename();
		String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		Random random = new Random();
		String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
		try {
			InputStream inputStream = file.getInputStream();
			this.uploadFile2OSS(inputStream, name);
			return name;
		} catch (Exception e) {
			return name;
			// throw new ImgException("图片上传失败");
		}
	}

	public String storeUrl(String url) {
		StringBuffer sb = new StringBuffer(url);
		sb.substring(sb.lastIndexOf("?"));
		System.out.println("sb==="+sb);

		return  "";
	}
	/**
	 * 获得图片路径
	 *
	 * @param fileUrl
	 * @return
	 */
	public String getImgUrl(String fileUrl) {
		if (!StringUtils.isEmpty(fileUrl)) {
			String[] split = fileUrl.split("/");
			return this.getUrl(DateUtil.DateToString(DateUtil.getDate(),"yyyy-mm-dd") + split[split.length - 1]);
		}
		return null;
	}

	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(InputStream instream, String fileName) {
		String ret = "";
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			PutObjectResult putResult = ossClient.putObject(BACKET_NAME, this.getFOLDER() + fileName, instream, objectMetadata);
			ret = putResult.getETag();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	//自动生成文件名
	public String fileName(String fileName){
		String substring = fileName.substring(
				fileName.lastIndexOf(".")).toLowerCase();
		StringBuffer sb= new StringBuffer(new Date().getTime() + RandomStringGenerator.getRandomStringByLength(5)+"w");
		System.out.println(sb.toString());
		sb.append(substring);
		return sb.toString();
	}

	/**
	 * Description: 判断OSS服务文件上传时文件的contentType
	 *
	 * @param FilenameExtension
	 *            文件后缀
	 * @return String
	 */
	public static String getcontentType(String FilenameExtension) {
		if (FilenameExtension.equalsIgnoreCase("bmp")) {
			return "image/bmp";
		}
		if (FilenameExtension.equalsIgnoreCase("gif")) {
			return "image/gif";
		}
		if (FilenameExtension.equalsIgnoreCase("jpeg") || FilenameExtension.equalsIgnoreCase("jpg")
				|| FilenameExtension.equalsIgnoreCase("png")) {
			return "image/jpeg";
		}
		if (FilenameExtension.equalsIgnoreCase("html")) {
			return "text/html";
		}
		if (FilenameExtension.equalsIgnoreCase("txt")) {
			return "text/plain";
		}
		if (FilenameExtension.equalsIgnoreCase("vsd")) {
			return "application/vnd.visio";
		}
		if (FilenameExtension.equalsIgnoreCase("pptx") || FilenameExtension.equalsIgnoreCase("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (FilenameExtension.equalsIgnoreCase("docx") || FilenameExtension.equalsIgnoreCase("doc")) {
			return "application/msword";
		}
		if (FilenameExtension.equalsIgnoreCase("xml")) {
			return "text/xml";
		}
		return "image/jpeg";
	}

	/**
	 * 获得url链接
	 *
	 * @param key
	 * @return
	 */
	public String getUrl(String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		// 生成URL
		URL url = ossClient.generatePresignedUrl(BACKET_NAME, key, expiration);
		if (url != null) {
			return url.toString();
		}
		return null;
	}

}
