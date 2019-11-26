package com.sunnet.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil
{
  private static HttpClientUtil httpClientUtil;
  
  public static HttpClientUtil getInstance()
  {
    if (httpClientUtil == null) {
      httpClientUtil = new HttpClientUtil();
    }
    return httpClientUtil;
  }
  
  public JSONObject sendHttpGet(String url)
  {
    InputStream inputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    StringBuffer buffer = new StringBuffer();
    HttpURLConnection conn = null;
    if (null != url) {
      try
      {
        URL realUrl = new URL(url);
        conn = (HttpURLConnection)realUrl.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        conn.connect();
        inputStream = conn.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
          buffer.append(str);
        }
        return JSONObject.parseObject(buffer.toString());
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      finally
      {
        try
        {
          bufferedReader.close();
          inputStreamReader.close();
          inputStream.close();
          conn.disconnect();
        }
        catch (IOException e)
        {
          bufferedReader = null;
          inputStreamReader = null;
          inputStream = null;
          conn = null;
          e.printStackTrace();
        }
      }
    }
	return null;
  }
  
  public JSONObject sendHttpPost(String url, String json)
  {
    HttpURLConnection conn = null;
    InputStream inputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    StringBuffer buffer = new StringBuffer();
    PrintWriter out = null;
    try
    {
      URL realUrl = new URL(url);
      conn = (HttpURLConnection)realUrl.openConnection();
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      conn.setDoOutput(true);
      conn.setDoInput(true);
      out = new PrintWriter(conn.getOutputStream());
      out.print(json);
      out.flush();
      inputStream = conn.getInputStream();
      inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      return JSONObject.parseObject(buffer.toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        conn.disconnect();
      }
      catch (IOException e)
      {
        bufferedReader = null;
        inputStreamReader = null;
        inputStream = null;
        conn = null;
        e.printStackTrace();
      }
    }
	return null;
  }
  
  public static String genSign(String salt, String phone)
  {
    EncryptionMd5 encryptionMd5 = new EncryptionMd5();
    StringBuilder sb = new StringBuilder();
    sb.append(salt).append(phone);
    String sign = encryptionMd5.stringMD5(sb.toString());
    return sign;
  }
  
  public static void main(String[] args)
  {
    String server = "http://www.pudaitong.com";
    String port = "80";
    String web = "loan";
    String url = "sms/send";
    String api = server + ":" + port + "/" + web + "/" + url;
    String phone = "18890206906";
    String msg = "����������������";
    String salt = "pbcnForSms";
    String sign = genSign(salt, phone);
    StringBuilder sb = new StringBuilder();
    sb.append(api).append("?phone=").append(phone);
    sb.append("&msg=").append(msg);
    sb.append("&sign=").append(sign);
    HttpClientUtil http = getInstance();
    JSONObject jsonObject = http.sendHttpGet(sb.toString());
    System.err.println(jsonObject.getString("code"));
    System.err.println(jsonObject.getString("message"));
  }
}
