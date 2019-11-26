package com.sunnet.org.util;

import java.util.*;

import com.sunnet.framework.util.EncryptionMd5;
import com.sunnet.org.app.oss.httpClientHelp;
import com.sunnet.org.app.pay.common.Configure;

public class SignUtil
{
	private static final String SALT = "ZINYUN";

	public static String getSaltSign(TreeMap<String, Object> map)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext())
		{
			sb.append(map.get(iterator.next()).toString());
		}
		EncryptionMd5 encryptionMd5 = new EncryptionMd5();
		sb.append(SALT);
		return encryptionMd5.stringMD5(sb.toString());
	}

	public static String getSign(TreeMap<String, Object> map)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext())
		{
			sb.append(map.get(iterator.next()).toString());
		}
		EncryptionMd5 encryptionMd5 = new EncryptionMd5();
		return encryptionMd5.stringMD5(sb.toString());
	}

	public static String creatXml(Map<String, String> map)
	{
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");

		Set<String> set = map.keySet();
		for(Iterator<String> it=set.iterator(); it.hasNext();){
			String key = it.next();
			Object value = map.get(key);
			sb.append("<").append(key).append(">");
			sb.append(value);
			sb.append("</").append(key).append(">");
		}
		sb.append("</xml>");
		return sb.toString();

	}

	/**
	 * 得到加密值
	 * @param map
	 * @return
	 */
	public static String getSignMap(Map<String, String> map){
		String[] keys = map.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		StringBuffer reqStr = new StringBuffer();
		for(String key : keys){
			String v = map.get(key);
			if(v != null && !v.equals("")){
				reqStr.append(key).append("=").append(v).append("&");
			}
		}
		reqStr.append("key").append("=").append(Configure.getSecret());

		//MD5加密
		EncryptionMd5 encryptionMd5 = new EncryptionMd5();
		return encryptionMd5.stringMD5(reqStr.toString()).toUpperCase();
	}


	public static void main(String[] args)
	{
//		TreeMap<String, Object> map=new TreeMap<String,Object>();
//		map.put("fdId", "123456789");
//		map.put("arderNum", "NO20161129112345");
//		System.err.println(SignUtil.getSign(map));
//		System.err.println(SignUtil.getSaltSign(map));
//		String http="http://120.78.170.60/reachPay/getorederno";
//		Map jso =  httpClientHelp.HttpURLConnectionPost(http, "", "");
//		String orderno=net.sf.json.JSONObject.fromObject(jso.get("results")).get("result").toString();
//		System.out.println("orderno="+orderno);
		Integer temp = null;
		System.out.println(temp+1);
	}
}
