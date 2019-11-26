package com.sunnet.org.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class AuthCode {

	/*public static void authCode(String phon, String msg) {*/

		/*HttpClient httpClient = new HttpClient();

		String url = "http://210.5.158.31/hy/";
		String uid = "809603";
		String auth = "	307466a05be32552594d3de5b98bb009";
		String mobile = phon;
		String content = null;
		try {
			content = java.net.URLEncoder.encode(msg, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		PostMethod postMethod = new PostMethod(url);

		NameValuePair[] data = { new NameValuePair("uid", uid),
				new NameValuePair("auth", auth),
				new NameValuePair("mobile", mobile),
				new NameValuePair("expid", "0"),
				new NameValuePair("msg", content) };
		postMethod.setRequestBody(data);
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (statusCode == HttpStatus.SC_OK) {
			String sms = null;
			try {
				sms = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("result:" + sms);
		}
		System.out.println("statusCode=" + statusCode);
	}
*/
	/**
	 * 生成随机码
	 */
	public static String create(int len, boolean isNumber) {
		String[] codes = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9" };
		Random random = new SecureRandom();
		String code = "";
		for (int i = 0; i < len; i++) {
			if (isNumber) {
				code += codes[random.nextInt(10)];
			} else {
				code += codes[random.nextInt(62)];
			}
		}
		return code;
	}

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  E ");
		System.out.println(sdf.format(new Date()));

	}
}
