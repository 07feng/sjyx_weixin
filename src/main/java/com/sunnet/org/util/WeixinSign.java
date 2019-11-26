package com.sunnet.org.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

public class WeixinSign {

	private static String appid = "wxa2c607187b4e51b4";
	private static String secret = "be56a63229716465104ab42869e4a608";
	private static int secCount=0;
//	private static String access_token = "";

	public static void main(String[] args) {
		String jsapi_ticket = getJsapi_ticket();

		// 注意 URL 一定要动态获取，不能 hardcode
		String url = "http://i.91sjyx.com/pc";
		Map<String, String> ret = sign(jsapi_ticket, url);
		for (Map.Entry entry : ret.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	};

	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String getAccess_token(){
		String result = HttpRequestUtil.httpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);
		String access_token = JSONObject.parseObject(result).getString("access_token");
		return access_token;
	}

	public static String getJsapi_ticket() {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?";
		String params = "grant_type=client_credential&appid=" + appid + "&secret=" + secret + "";
		String result = HttpRequestUtil.httpGet(requestUrl + params);
		String access_token = JSONObject.parseObject(result).getString("access_token");
		requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
		params = "access_token=" + access_token + "&type=jsapi";
		result = HttpRequestUtil.httpGet(requestUrl + params);
		String jsapi_ticket = JSONObject.parseObject(result).getString("ticket");
//    int activeTime = Integer.parseInt(JSONObject.parseObject(result).getString("expires_in"));
//    Jssdk jssdk = new Jssdk();
//    jssdk.setActiveTime(activeTime - 600);
//    jssdk.setJsapiTicket(jsapi_ticket);
		// jssdkDao.update(jssdk);
		return jsapi_ticket;
	}

	public static String getOpenId(String code) {
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?";
		String params = "grant_type=authorization_code&appid=" + appid + "&secret=" + secret + "&js_code=" + code;
		String result = HttpRequestUtil.httpPost(requestUrl + params);

		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
