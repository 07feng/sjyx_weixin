package com.sunnet.framework.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.sunnet.org.util.DateUtil;

public class WriteError {
	private static final String HEADER = "-------------header----------------   time:%s";
	private static final String FOOTER = "-------------footer----------------   time:%s";
	public static void writeErrorFile(Exception e) {
		try {
			Date date = new Date();
			String fileName =new CommonUtils().RootPath()+"/log/errorlog/"+DateUtil.DateToString(date, "yyyyMMdd HH:mm:ss")+ ".txt";
			File file = new File(fileName.replaceAll("%20", " "));
			//判断目标文件所在的目录是否存在  
			 if(!file.getParentFile().exists()) {  
			 //如果目标文件所在的目录不存在，则创建父目录 
				 System.out.println("目标文件所在目录不存在，准备创建它！");  
			if(!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");  
			}  
			} 
			if (!file.exists()) {
				file.createNewFile();
			}
			String dateTime=DateUtil.DateToString(date, "yyyyMMdd");
			String dateStr=dateTime;
			// true = append file
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(String.format(HEADER, dateStr));
			bufferWritter.newLine();
			bufferWritter.write("date time: "+dateTime);
			bufferWritter.newLine();
			bufferWritter.write("error Source: System");//这里如何判断尚不明了
			bufferWritter.newLine();
			bufferWritter.write("error Message: "+e.getMessage());
			bufferWritter.newLine();
			bufferWritter.write("stack Trace: ");
			StackTraceElement[] stackTrace=e.getStackTrace();
			int i=0;
			for (StackTraceElement stackTraceElement : stackTrace) {
				String stack=stackTraceElement.toString();
				if(stack.indexOf("com.Actions")!=-1||stack.indexOf("com.Services")!=-1||stack.indexOf("com.Daos")!=-1){
					bufferWritter.write(stack);
					bufferWritter.newLine();
					i++;
				}
				if(i>3)break;
			}
			bufferWritter.newLine();
			bufferWritter.write(String.format(FOOTER, dateStr));
			bufferWritter.newLine();
			bufferWritter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static void writeLogFile(String title,String content) {
		try {
			Date date = new Date();
			String fileName =new CommonUtils().RootPath()+"/log/opreatelog/"+DateUtil.DateToString(date, "yyyyMMdd")+ ".txt";
			File file = new File(fileName.replaceAll("%20", " "));
			 //判断目标文件所在的目录是否存在  
			 if(!file.getParentFile().exists()) {  
			 //如果目标文件所在的目录不存在，则创建父目录 
				 System.out.println("目标文件所在目录不存在，准备创建它！");  
			if(!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");  
			}  
			}  

			if (!file.exists()) {
				file.createNewFile();
			}
			String dateTime=DateUtil.DateToString(date, "yyyyMMdd");
			String dateStr=dateTime;
			FileWriter fileWritter = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(String.format(HEADER, dateStr));
			bufferWritter.newLine();
			bufferWritter.write("date time: "+dateTime);
			bufferWritter.newLine();
			bufferWritter.write("Title: "+title);
			bufferWritter.newLine();
			bufferWritter.write("content: "+content);
			bufferWritter.newLine();
			bufferWritter.write("-----------------------------------------------------------------------------");
			bufferWritter.newLine();
			bufferWritter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
