package com.sunnet.org.util;

import com.alibaba.fastjson.JSONObject;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SenInfoCheckUtil
 * @Description: 微信小程序上传图片安全检测
 * @Author: Bryce
 * @Date 2019/9/12 0012上午 11:47
 * @Version 1.0
 */
public class SenInfoCheckUtil {
    private static final Logger log = LoggerFactory.getLogger(SenInfoCheckUtil.class);
    /**
     * 图片违规检测
     * @param accessToken
     * @param file
     * @return
     */
    public static Boolean imgFilter(String accessToken, MultipartFile file){
        String contentType = file.getContentType();
        return checkPic(file, accessToken,contentType);
    }

    /**
     * 文本违规检测
     * @param accessToken
     * @param content
     * @return
     */
    public static Boolean cotentFilter(String accessToken, String content){
        return checkContent(accessToken,content);
    }


    /**
     *  恶意图片过滤
     * @param multipartFile
     * @return
     */
    private static Boolean checkPic(MultipartFile multipartFile, String accessToken, String contentType) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            CloseableHttpResponse response = null;

            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + accessToken);
            request.addHeader("Content-Type", "application/octet-stream");

            InputStream inputStream = multipartFile.getInputStream();
            int available = inputStream.available();
            if (available > 1048576){
                inputStream = compressImage(inputStream, inputStream.available());
            }
            int available1 = inputStream.available();
            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            request.setEntity(new ByteArrayEntity(byt, ContentType.create(contentType)));

            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            JSONObject jso = JSONObject.parseObject(result);
            return getResult(jso);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("----------------调用腾讯内容过滤系统出错------------------");
            return true;
        }
    }

    private static Boolean checkContent(String accessToken,String content) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            CloseableHttpResponse response = null;

            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + accessToken);
            request.addHeader("Content-Type", "application/json");
            Map<String, String> map = new HashMap<>();
            map.put("content",content);
            String body = JSONObject.toJSONString(map);
            request.setEntity(new StringEntity(body, ContentType.create("text/json", "UTF-8")));
            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            JSONObject jso = JSONObject.parseObject(result);
            return getResult(jso);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("----------------调用腾讯内容过滤系统出错------------------");
            return true;
        }
    }

    private static Boolean  getResult(JSONObject jso){
        Object errcode = jso.get("errcode");
        int errCode = (int) errcode;
        if (errCode == 0) {
            return true;
        } else if (errCode == 87014) {
            log.info("图片内容违规-----------");
            return false;
        }
        return true;
    }

    private static InputStream compressImage(URLConnection connection, long num) throws IOException {
        if(num == 0 || connection == null){
            return null;
        }
        double multiple = 1.0 / (num / (1024 * 1024) + 1.0);
        InputStream inputStream = connection.getInputStream();
        BufferedImage image = Thumbnails.of(connection.getInputStream()).scale(multiple).asBufferedImage();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

    private static InputStream compressImage(MultipartFile file, long num) throws IOException {
        double multiple = 1.0 / (num / (1024 * 1024) + 1.0);
        InputStream inputStream = file.getInputStream();
        BufferedImage image = Thumbnails.of(inputStream).scale(multiple).asBufferedImage();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

    private static InputStream compressImage(InputStream inputStream, long num) throws IOException {
        double multiple = 1.0 / (num / (1024 * 1024) + 1.0);
        BufferedImage image = Thumbnails.of(inputStream).scale(multiple).asBufferedImage();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }
}
