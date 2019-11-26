package com.sunnet.framework.util;

import java.security.MessageDigest;

public class EncryptionMd5
{
  public String stringMD5(String password)
  {
    StringBuffer hexValue = new StringBuffer();
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] byteArray = password.getBytes("UTF-8");
      byte[] md5Bytes = md.digest(byteArray);
      for (int i = 0; i < md5Bytes.length; i++)
      {
        int val = md5Bytes[i] & 0xFF;
        if (val < 16) {
          hexValue.append("0");
        }
        hexValue.append(Integer.toHexString(val));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return hexValue.toString();
  }
  
  public static void main(String[] args)
  {
    EncryptionMd5 e = new EncryptionMd5();
    System.err.println(e.stringMD5("abc123456"));
  }
}
