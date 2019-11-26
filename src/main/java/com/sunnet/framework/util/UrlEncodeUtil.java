package com.sunnet.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncodeUtil
{
  public static String urlEncodeUTF8(String source)
  {
    String result = source;
    try
    {
      result = URLEncoder.encode(source, "utf-8");
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return result;
  }
}
