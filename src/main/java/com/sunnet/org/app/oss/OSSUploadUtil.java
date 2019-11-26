package com.sunnet.org.app.oss;

import java.util.ArrayList;
import java.util.List;

public class OSSUploadUtil {
	private static OSSConfig config = null;

	/**
	 * 
	 * @MethodName: contentType
	 * @Description: 获取文件类型
	 * @return String
	 */
	private static String contentType(String fileType) {
		fileType = fileType.toLowerCase();
		String contentType = "";
		switch (fileType) {
		case "bmp":
			contentType = "image/bmp";
			break;
		case "gif":
			contentType = "image/gif";
			break;
		case "png":
		case "jpeg":
		case "jpg":
			contentType = "image/jpeg";
			break;
		case "html":
			contentType = "text/html";
			break;
		case "txt":
			contentType = "text/plain";
			break;
		case "vsd":
			contentType = "application/vnd.visio";
			break;
		case "ppt":
		case "pptx":
			contentType = "application/vnd.ms-powerpoint";
			break;
		case "doc":
		case "docx":
			contentType = "application/msword";
			break;
		case "xml":
			contentType = "text/xml";
			break;
		case "mp4":
			contentType = "video/mp4";
			break;
		default:
			contentType = "application/octet-stream";
			break;
		}
		return contentType;
	}

	/**
	 * 
	 * @MethodName: getBucketName
	 * @Description: 根据url获取bucketName
	 * @param fileUrl
	 *            文件url
	 * @return String bucketName
	 */
	private static String getBucketName(String fileUrl) {
		String http = "http://";
		String https = "https://";
		int httpIndex = fileUrl.indexOf(http);
		int httpsIndex = fileUrl.indexOf(https);
		int startIndex = 0;
		if (httpIndex == -1) {
			if (httpsIndex == -1) {
				return null;
			} else {
				startIndex = httpsIndex + https.length();
			}
		} else {
			startIndex = httpIndex + http.length();
		}
		int endIndex = fileUrl.indexOf(".oss-");
		return fileUrl.substring(startIndex, endIndex);
	}

	/**
	 * 
	 * @MethodName: getFileName
	 * @Description: 根据url获取fileName
	 * @param fileUrl
	 *            文件url
	 * @return String fileName
	 */
	private static String getFileName(String fileUrl) {
		String str = "aliyuncs.com/";
		int beginIndex = fileUrl.indexOf(str);
		if (beginIndex == -1)
			return null;
		return fileUrl.substring(beginIndex + str.length());
	}

	/**
	 * 
	 * @MethodName: getFileName
	 * @Description: 根据url获取fileNames集合
	 *            文件url
	 * @return List<String> fileName集合
	 */
	private static List<String> getFileName(List<String> fileUrls) {
		List<String> names = new ArrayList<>();
		for (String url : fileUrls) {
			names.add(getFileName(url));
		}
		return names;
	}
}
