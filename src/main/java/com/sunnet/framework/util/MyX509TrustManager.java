package com.sunnet.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager
  implements X509TrustManager
{
  public void checkClientTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {}
  
  public void checkServerTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {}
  
  public X509Certificate[] getAcceptedIssuers()
  {
    return null;
  }
  
  public static void main(String[] args)
  {
    String requestUrl = "https://xxx";
    try
    {
      URL url = new URL(requestUrl);
      HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
      
      TrustManager[] tm = { new MyX509TrustManager() };
      SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, tm, new SecureRandom());
      SSLSocketFactory ssf = sslContext.getSocketFactory();
      conn.setSSLSocketFactory(ssf);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      
      conn.setRequestMethod("GET");
      
      InputStream inputStream = conn.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      
      StringBuffer buffer = new StringBuffer();
      String str = null;
      while ((str = bufferedReader.readLine()) != null) {
        buffer.append(str);
      }
      bufferedReader.close();
      inputStreamReader.close();
      inputStream.close();
      conn.disconnect();
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
  }
}
