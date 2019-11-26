package com.sunnet.framework.util;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HttpsRequestUtil
{
  public static JSONObject sendHttpsRequest(String requestUrl, String requestMethod, String outputStr)
  {
    InputStream inputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;
    HttpsURLConnection conn = null;
    StringBuffer buffer = new StringBuffer();
    try
    {
      TrustManager[] tm = { new MyX509TrustManager() };
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new SecureRandom());
      SSLSocketFactory ssf = sslContext.getSocketFactory();
      
      URL url = new URL(requestUrl);
      conn = (HttpsURLConnection)url.openConnection();
      conn.setSSLSocketFactory(ssf);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      
      conn.setRequestMethod(requestMethod);
      if (null != outputStr)
      {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(outputStr.getBytes("UTF-8"));
        outputStream.close();
      }
      inputStream = conn.getInputStream();
      inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      return JSONObject.parseObject(buffer.toString());
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    catch (NoSuchProviderException e)
    {
      e.printStackTrace();
    }
    catch (KeyManagementException e)
    {
      e.printStackTrace();
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
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
}
