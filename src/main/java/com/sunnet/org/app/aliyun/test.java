package com.sunnet.org.app.aliyun;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class test {
    //APP KEY
    private final static String APP_KEY = "24551271";
    // APP密钥
    private final static String APP_SECRET = "cfee9fca03a8ed0b10e19755afb392e8";
    //API域名
    private final static String HOST ="http://txsbjh.market.alicloudapi.com";//"http://txsbjh.market.alicloudapi.com";// "https://dtplus-cn-shanghai.data.aliyuncs.com";
    //自定义参与签名Header前缀（可选,默认只有"X-Ca-"开头的参与到Header签名）
    private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();
	 
    
	public static void main(String[] args){
		Map<String, String> headers = new HashMap<String, String>();
		// 请求path
		String path = "/image/porn";// 鉴黄
		//String pathdetect = "/face/detect";//人像识别
		// String method = "POST";
		// Body内容
		String body = "{\"type\":0,\"image_url\":\"http://sjyximage.oss-cn-shenzhen.aliyuncs.com/201708215/20170803055818922-2707370397.jpeg\"}";
		// （必填）根据期望的Response内容类型设置
		headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
		// （可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
		headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5,
				MessageDigestUtil.base64AndMD5(body));
		// （POST/PUT请求必选）请求Body内容格式
		headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, "application/json");
		/*
		 * CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
		 * CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
		 * CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");
		 */
		headers.put(HttpHeader.HTTP_HEADER_DATE,
				HttpUtil.toGMTString(new Date()));
		Request request1 = new Request(Method.POST_STRING, HOST, path,
				APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
		//Request request2 = new Request(Method.POST_STRING, HOST, pathdetect,
		//		APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
		request1.setHeaders(headers);
		//request2.setHeaders(headers);
		// request1.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
		// 请求的query
		Map<String, String> querys = new HashMap<String, String>();
		request1.setQuerys(querys);
		request1.setStringBody(body);
	//	Map<String, String> querys2 = new HashMap<String, String>();
		//request2.setQuerys(querys);
		//request2.setStringBody(body);
		// 调用服务端
		Response response1;
		Response response2;

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			response1 = Client.execute(request1);
			//response2 = Client.execute(request2);
			map.put("porn", response1);
		//	map.put("detect", response2);
			System.out.println(JSON.toJSONString(response1));
		}catch (Exception e1)  {
			e1.printStackTrace();
		}
		
	  /*  Map<String, String> headers = new HashMap<String, String>();
	    //请求path
	    String path ="/image/porn";// "/image/porn";
	    //String method = "POST";
        //Body内容
        String body ="{\"type\":0,\"image_url\":\" http://sjyximage.oss-cn-shenzhen.aliyuncs.com/201708215/20170803055818922-2707370397.jpeg\"}";
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);
        //headers.put(HttpHeader.HTTP_HEADER_DATE,HttpUtil.toGMTString (new Date()));
        Request request = new Request(Method.POST_STRING,HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        request.setQuerys(querys);
        request.setStringBody(body);
        //调用服务端
        Response response;
		*/
		/*try {
			 response = Client.execute(request); 
			System.out.println(JSON.toJSONString(response));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
	}
}
