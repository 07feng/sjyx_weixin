package com.sunnet.org.util;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.xml.crypto.Data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 



import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.sunnet.framework.util.WriteError;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class OSSClientUtil2 {
	static Log log = LogFactory.getLog(OSSClientUtil2.class);

	private static String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
	// accessKey
	private static String ACCESS_KEY_ID = "LTAI67K1f1TOC7KQ";
	private static String ACCESS_KEY_SECRET = "mNJhSMDUTtOvSbpii7SOmHoexI4WvP";
	// 空间
	private static String BACKET_NAME = "sjyximage";
	// 文件存储目录
	private String folder = "";

	private OSSClient ossClient;

	public OSSClientUtil2() {
		this.ossClient = getOSSClient();
//		ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	public OSSClient getOSSClient(){
		try{
		OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		return ossClient;
		}
		catch(Exception e){
			WriteError.writeErrorFile(e);
			return null;
		}
	}
	
//	/**
//	 * 初始化
//	 */
//	public void init() {
//		ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
//	}

	
	/**
	 * 创建模拟文件夹
	 * @param ossClient
	 * @param bucketName
	 * @param folder
	 * @return
	 */
	  public  static String createFolder(OSSClient ossClient,String bucketName,String folder){  
	        //文件夹名   
	        final String keySuffixWithSlash =folder;  
	        //判断文件夹是否存在，不存在则创建  
	        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){  
	            //创建文件夹  
	            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));  
//	            logger.info("创建文件夹成功");  
	            //得到文件夹名  
	            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);  
	            String fileDir=object.getKey();  
	            return fileDir;  
	        }  
	        return keySuffixWithSlash;  
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
		
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String newFolder = f.format(date);
		folder = createFolder(ossClient,BACKET_NAME,newFolder) + "/";
		
		String originalFilename = file.getOriginalFilename();
		String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		Random random = new Random();
		String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
		String url = this.getImgUrl(name);
		try {
			InputStream inputStream = file.getInputStream();
			this.uploadFile2OSS(inputStream, name);
			return url;
		} catch (Exception e) {
			return url;
			// throw new ImgException("图片上传失败");
		}
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
			String str = this.getUrl(this.folder + split[split.length - 1]);
			String url = str.replace(str.split("/")[2], "image.91sjyx.com").split("[?]")[0];
			return url;
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
			
			PutObjectResult putResult = ossClient.putObject(BACKET_NAME, folder + fileName, instream, objectMetadata);
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

	/**
	 * 以时间为间隔创建文件夹
	 * @return
	 */
//	private String getFolder() {
//		String folder = "";
//		Date date = new Date();
//		SimpleDateFormat f = new SimpleDateFormat("YYYY-MM-DD");
//		folder = f.format(date);
//		
//		
//		return folder;
//	}

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
	public String getUrl(String fileName) {
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		// 生成URL
		String url = ossClient.generatePresignedUrl(BACKET_NAME, fileName, expiration).toString();
		System.out.println(url);
		if (url != null) {
			return url.toString();
		}
		return null;
	}
	/**  
	    * 根据key删除OSS服务器上的文件  
	    * @param client OSS客户端 
	    * @param bucketName bucket名称 
	    * @param diskName 文件路径 
	    * @param key Bucket下的文件的路径名+文件名 
	    */  
	 public static void deleteFile(OSSClient client,String key){    
	        client.deleteObject(BACKET_NAME, key);   
	        System.out.println("删除" + BACKET_NAME + "下的文件" +  key + "成功");  
	      }    
    public static void main(String[] args) {
    	OSSClientUtil2 ossClientUtil2 = new OSSClientUtil2();
		OSSClient client = ossClientUtil2.getOSSClient();
    	String str= "http://image.91sjyx.com/201709257/20170914180357525-3728242365.JPG";
    	String a="http://image.91sjyx.com/";
    	System.out.println(a.length());
    	String stra=str.substring(24,str.length());
    
    	 OSSClientUtil2.deleteFile(client,  stra);
    	/*OSSClientUtil2 ossClientUtil2 = new OSSClientUtil2();
    	ossClientUtil2.folder = "2017-08-11/";
     	String url = ossClientUtil2.getImgUrl(str).split("[?]")[0];
    	System.out.println(url);
    	
    	System.out.println(s);
    	for (int i = 0; i < s.length; i++) {
    		
    	}*/
    }
	
	
// 
}
