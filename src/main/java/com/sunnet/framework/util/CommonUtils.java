package com.sunnet.framework.util;
import java.io.*;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
public class CommonUtils {

    public CommonUtils()
    {
    }
    
    public static boolean isnull(Object value)
    {
        return value == null || String.valueOf(value).trim().equals("") || String.valueOf(value).trim().isEmpty() ;
    }

    
    public static boolean UtilisNullOrEmpty(Object value)
    {
        return value == null || String.valueOf(value).trim().length() < 1;
    }

    public static String dateFormat(Date time)
    {
        SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myfmt.format(time).toString();
    }

    public static Date stringToDate(String time)
    {
    	try{
        SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd");
        return myfmt.parse(time);
    }catch(ParseException e){
        e.printStackTrace();
        return new Date();
    }
    }

    public static Date stringToTime(String time)
    {
    	try{
        SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myfmt.parse(time);
    }catch(ParseException e){
        e.printStackTrace();
        return new Date();
    }
    }

    public static String dateFormatOnly(Date time)
    {
        SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd");
        return myfmt.format(time).toString();
    }

    public static String getIPonLinux()
    {
        BufferedReader bufferedreader;
        try{
        String proc = "curl ifconfig.me";
        Process process = Runtime.getRuntime().exec(proc);
        bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return bufferedreader.readLine();
        }
        catch(Exception ex){
        return null;
        }
    }

    public static Date convertTimeToDate(long time)
    {
        Date date = new Date(time);
        System.out.println(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13));
        System.out.println(calendar2.getTime());
        return calendar2.getTime();
    }

    public static void createPropertiesFile(String filename, String pName, String pValue)
    {
        Properties prop = new Properties();
        prop.put(pName, pValue);
        try
        {
            filename = (new StringBuilder(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("/").getPath()))).append(filename).append(".properties").toString();
            FileOutputStream out = new FileOutputStream(filename);
            prop.store(out, "ADRATE");
            out.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String readPropertiesFile(String filename, String propertiesName)
    {
        Properties properties = new Properties();
        try
        {
            filename = (new StringBuilder(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("/").getPath()))).append(filename).append(".properties").toString();
            InputStream inputStream = new FileInputStream(filename);
            properties.load(inputStream);
            inputStream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        String result = properties.getProperty(propertiesName);
        return result;
    }

    public static void writePropertiesFile(String filename, String pName, String pValue)
    {
        Properties properties = new Properties();
        try
        {
            filename = (new StringBuilder(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("/").getPath()))).append(filename).append(".properties").toString();
            InputStream inputStream = new FileInputStream(filename);
            properties.load(inputStream);
            inputStream.close();
            OutputStream outStream = new FileOutputStream(filename);
            properties.setProperty(pName, pValue);
            properties.store(outStream, "");
            outStream.close();
        }
        catch(IOException e)
        {
            createPropertiesFile(filename, pName, pValue);
            e.printStackTrace();
        }
    }

    public static String replaceBase64Code(String code)
    {
        if(code.indexOf(" ") >= 0)
            code = code.replaceAll(" ", "+");
        return code;
    }
    
    /**
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String decode(final byte[] bytes,String charsetName) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(bytes),charsetName);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException 
     * @throws Exception
     */
    public static String encode(final byte[] bytes,String charsetName) throws UnsupportedEncodingException {
        return new String(Base64.encodeBase64(bytes),charsetName);
    }
    
    /** 
     * @param str 
     * @return  
     * @Description: 32位小写MD5 
     */
    public static String parseStrToMd5L32(String str){ 
      String reStr = null; 
      try { 
        MessageDigest md5 = MessageDigest.getInstance("MD5"); 
        byte[] bytes = md5.digest(str.getBytes()); 
        StringBuffer stringBuffer = new StringBuffer(); 
        for (byte b : bytes){ 
          int bt = b&0xff; 
          if (bt < 16){ 
            stringBuffer.append(0); 
          }  
          stringBuffer.append(Integer.toHexString(bt)); 
        } 
        reStr = stringBuffer.toString(); 
      } catch (NoSuchAlgorithmException e) { 
        e.printStackTrace(); 
      } 
      return reStr; 
    } 
        /**
         * 生成md5
         * 
         * @param message
         * @return
         */
        public static String getMD5(String message) {
    	String md5str = "";
    	try {
    	    // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
    	    MessageDigest md = MessageDigest.getInstance("MD5");

    	    // 2 将消息变成byte数组
    	    byte[] input = message.getBytes();

    	    // 3 计算后获得字节数组,这就是那128位了
    	    byte[] buff = md.digest(input);

    	    // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
    	    md5str = bytesToHex(buff);

    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    	return md5str;
        }

        /**
         * 二进制转十六进制
         * 
         * @param bytes
         * @return
         */
        public static String bytesToHex(byte[] bytes) {
    	StringBuffer md5str = new StringBuffer();
    	// 把数组每一字节换成16进制连成md5字符串
    	int digital;
    	for (int i = 0; i < bytes.length; i++) {
    	    digital = bytes[i];

    	    if (digital < 0) {
    		digital += 256;
    	    }
    	    if (digital < 16) {
    		md5str.append("0");
    	    }
    	    md5str.append(Integer.toHexString(digital));
    	}
    	return md5str.toString().toUpperCase();
        }
        
        public final static String getMessageDigest(byte[] buffer) {
    		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    		try {
    			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
    			mdTemp.update(buffer);
    			byte[] md = mdTemp.digest();
    			int j = md.length;
    			char str[] = new char[j * 2];
    			int k = 0;
    			for (int i = 0; i < j; i++) {
    				byte byte0 = md[i];
    				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
    				str[k++] = hexDigits[byte0 & 0xf];
    			}
    			return new String(str);
    		} catch (Exception e) {
    			return null;
    		}
    	}
        
        public String RootPath()
	    {
	    	try {
	    		String strp=this.getClass().getResource("").toURI().getPath();//.substring(0, endIndex).split("/WEB-INF/")[0]+"/customerM";
				return strp.substring(0, strp.lastIndexOf("/WEB-INF/"));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
	    }

}