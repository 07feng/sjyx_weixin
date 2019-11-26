package com.sunnet.org.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.app.aliyun.Client;
import com.sunnet.org.app.aliyun.Constants;
import com.sunnet.org.app.aliyun.HttpHeader;
import com.sunnet.org.app.aliyun.HttpUtil;
import com.sunnet.org.app.aliyun.MessageDigestUtil;
import com.sunnet.org.app.aliyun.Method;
import com.sunnet.org.app.aliyun.Request;
import com.sunnet.org.app.aliyun.Response;


public class AppAliyunImg extends BaseController {

	// APP KEY
	private final static String APP_KEY = "24551271";
	// APP密钥
	private final static String APP_SECRET = "cfee9fca03a8ed0b10e19755afb392e8";
	// API域名https://dtplus-cn-shanghai.data.aliyuncs.com
	private final static String HOST = "http://txsbjh.market.alicloudapi.com";// "https://dtplus-cn-shanghai.data.aliyuncs.com";////
																						// "https://dtplus-cn-shanghai.data.aliyuncs.com";
	// 自定义参与签名Header前缀（可选,默认只有"X-Ca-"开头的参与到Header签名）
	private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();

	/*@RequestMapping(value = "/ali/checkimage")*/
	public static String CheckImage(String imgurl) {
		JsonResult jsonResult = new JsonResult();
		try {
			Map<String, String> headers = new HashMap<String, String>();
			// 请求path
			String path = "/image/porn";// 鉴黄
			//String pathdetect = "/face/detect";//人像识别
			// String method = "POST";
			// Body内容
			String body = "{\"type\":0,\"image_url\":\""+imgurl+"\"}";
			// （必填）根据期望的Response内容类型设置
			headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
			// （可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
		    headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5,
					MessageDigestUtil.base64AndMD5(body));
			// （POST/PUT请求必选）请求Body内容格式
			headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, "application/json");
			 
			headers.put(HttpHeader.HTTP_HEADER_DATE,
					HttpUtil.toGMTString(new Date()));
			Request request1 = new Request(Method.POST_STRING, HOST, path,
					APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
		//	Request request2 = new Request(Method.POST_STRING, HOST, pathdetect,
			//		APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
			request1.setHeaders(headers);
			//request2.setHeaders(headers);
			// request1.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
			// 请求的query
			Map<String, String> querys = new HashMap<String, String>();
			request1.setQuerys(querys);
			request1.setStringBody(body);
			//Map<String, String> querys2 = new HashMap<String, String>();
		//	request2.setQuerys(querys);
			//request2.setStringBody(body);
			// 调用服务端
			Response response1;
		//	Response response2;

			try {
				Map<String, Object> map = new HashMap<String, Object>();
				response1 = Client.execute(request1);
			//	response2 = Client.execute(request2);
				map.put("porn", response1);
			//	map.put("detect", response2);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				jsonResult.setResult(map);
				//System.out.println(JSON.toJSONString(response1));
			} catch (Exception e1) {
				e1.printStackTrace();
				jsonResult.setCode("500");
				jsonResult.setMessage("执行错误！");
				log.debug("异常:" + e1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return JSONObject.toJSONString(jsonResult);
	}

}
