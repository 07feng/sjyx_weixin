// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 2016/9/4 10:50:51
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Util.java

package com.sunnet.framework.util;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package com.push.util:
//            Definition

public class Util
{

    public Util()
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
}