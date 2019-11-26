package com.sunnet.org.app.pay.common;

public class Configure {
	private static String key = "hrWxeKNCqjglUTbfhLY0BlvPUQIDAQAB";

	//小程序ID	
	private static String appID = "wxa2c607187b4e51b4";
	//商户号
	private static String mch_id = "1488185482";
	//
	private static String secret = "be56a63229716465104ab42869e4a608";

	public static String getSecret() {
		return secret;
	}

	public static void setSecret(String secret) {
		Configure.secret = secret;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static String getAppID() {
		return appID;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static String getMch_id() {
		return mch_id;
	}

	public static void setMch_id(String mch_id) {
		Configure.mch_id = mch_id;
	}

}
