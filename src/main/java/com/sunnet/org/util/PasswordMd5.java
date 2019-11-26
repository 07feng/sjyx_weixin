package com.sunnet.org.util;

import com.sunnet.framework.util.EncryptionMd5;

public class PasswordMd5
{
	public static String  getMd5Password(String password)
	{
		EncryptionMd5 encryptionMd5 = new EncryptionMd5();
		return encryptionMd5.stringMD5(password.toString());
	}
	public static void main(String[] args)
	{
		String password="123456";
		System.err.println(getMd5Password(password));
	}
}
