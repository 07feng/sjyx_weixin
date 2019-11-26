package com.sunnet.org.app.oss;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;






import com.sunnet.org.app.oss.util.getHttppostResult;

import java.io.IOException;
import java.net.URLDecoder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
public class httpClientHelp {
    
 
    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = (HttpResponse) client.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {

            }
        } catch (IOException e) {

        }
        return jsonResult;
    }
    
    public static  String HttpURLConnectionGet(String posturl)
    {
    	URL url=null;
    	HttpURLConnection conn =null;
    	try
    	{
    	url = new URL(posturl);
    	conn = (HttpURLConnection) url.openConnection();
        // 提交模式
        conn.setRequestMethod("GET");// POST GET PUT DELETE
        conn.setRequestProperty("Authorization", "Basic YWRtaW46YWRtaW4=");//YWRtaW46YWRtaW4=");
        // 设置访问提交模式，表单提交
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(15000);// 连接超时 单位毫秒
        conn.setReadTimeout(15000);// 读取超时 单位毫秒 
         byte bytes[]=new byte[1024];
         InputStream inStream=conn.getInputStream();
         inStream.read(bytes, 0, inStream.available());
         return new String(bytes, "utf-8");
    	}
    	catch(Exception ex){
    		return "";
    	}
    	
    }
    public static  Map HttpURLConnectionPost(String posturl,String parm,String cookie )
    {
    	URL url = null;  
    	HttpURLConnection conn = null;  
    	String result="";
    	try {  
    	    url = new URL(posturl);  
    	    conn = (HttpURLConnection) url.openConnection();  
    	    conn.setDoInput(true);  
    	    conn.setDoOutput(true);  
    	    conn.setUseCaches(false);  
    	    conn.setConnectTimeout(50000);//设置连接超时  
    	   //如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。  
    	    conn.setReadTimeout(50000);//设置读取超时  
    	   //如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。            
    	    conn.setRequestMethod("POST");  
    	    conn.setRequestProperty("Accept-Charset","utf-8");  
    	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
    	    if(cookie!=""&&cookie!=null)
    	    	conn.setRequestProperty("Cookie",cookie);
    	    conn.connect();
    	    if(!parm.equals(null)&&!parm.equals(""))
    	    {
    	    	OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
    	    	osw.write(parm);
    	    	osw.flush(); 
    	    	osw.close();  
    	    }
    	    if(cookie==""||cookie==null)
    	      cookie=conn.getHeaderField("Set-Cookie");
    	    if (conn.getResponseCode() == 200) {  
    	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
    	        String inputLine;  
    	        while ((inputLine = in.readLine()) != null) {  
    	            result += inputLine;  
    	        }  
    	        in.close();   
    	    }  
    	}
    	catch (Exception e)
    	{  
    	    System.out.println("err");
    	    getHttppostResult thisresult=new   getHttppostResult();
    	     
    	    result=JSONObject.fromObject(thisresult).toString();
    	}
    	finally
    	{
    		if (conn != null)
    	    	conn.disconnect(); 
    		if(result=="")
    		{
    			getHttppostResult thisresult=new   getHttppostResult();
        	    
        	    result=JSONObject.fromObject(thisresult).toString();
    		}
    	} 
    	Map<String,String> newresult=new HashMap<String,String>();
    	newresult.put("results", result);
    	newresult.put("Cookies", cookie);
    	return newresult;
    } 
}
