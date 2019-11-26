package com.sunnet.framework.util;  

import java.io.File;

import com.sunnet.org.util.DownloadFile;
   
public class Dwon extends Thread {  
    
	/**
	 * 下载路径
	 */
	public String dwonPath;
	
	/**
	 * 保存路径
	 */
	public String savePath;
	
	/**
	 * 保存文件路径
	 */
	public String saveFilePath;
       
	/**
	 * 构造方法
	 * @param dwonPath 下载路径
	 * @param savePath 保存路径 tomcat 
	 * @param saveFilePath 文件名称
	 */
	public Dwon(String dwonPath,String savePath,String saveFilePath){
		this.dwonPath = dwonPath;
		this.savePath = savePath;
		this.saveFilePath = saveFilePath;
	}
	
      
    //重写run方法， 在这里实现下载
    @SuppressWarnings("deprecation")
	@Override  
    public void run() {  
    	File Allfile = new File(savePath); //获取某个文件夹
		if(!Allfile.exists()){
			Allfile.mkdirs();
		}
		 File file = new File(savePath+saveFilePath); 
		 if(!file.exists()){
			 //当图片没有找到的时候，我们再去服务器下载，然后加入文件里面
			 DownloadFile.downloadImgByNet(dwonPath,savePath,saveFilePath);
			 System.out.println("下载完成->");
		 }
    }  
}  