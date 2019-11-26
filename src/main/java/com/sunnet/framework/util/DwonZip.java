package com.sunnet.framework.util;  

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;

import com.sunnet.org.util.DownLoad;
import com.sunnet.org.util.DownloadFile;

public class DwonZip extends Thread {  
    
	/**
	 * 解压路径
	 */
	public String dwonPath;
	
	/**
	 * 保存路径
	 */
	public List<File> files;
	
	public String path;
	
	public	List<String> flpath;
	
	public	List<String> jpglist;
	
	
       
	public DwonZip(String dwonPath, List<File> files,List<String> flpath,List<String> jpglist, String path){
		this.dwonPath = dwonPath;
		this.files = files;
		this.flpath=flpath;
		this.jpglist=jpglist;
		this.path=path;
	}
      
	@Override  
    public void run() {  
		try {
			    List<File> list = new ArrayList<File>();
				for (int i = 0; i < flpath.size(); i++) {
					String fl = flpath.get(i);
					String jpg = jpglist.get(i);
					 File file = new File(path+jpg); 
					 if(!file.exists()){
						 //当图片没有找到的时候，我们再去服务器下载，然后加入文件里面
						 File fe = DownloadFile.downloadImgBy(fl,path,jpg);
						 list.add(fe);
						 System.out.println("下载完成->");
					 }
				}
				
				File fileZip = new File(dwonPath);
				FileOutputStream outStream = new FileOutputStream(fileZip);
				ZipOutputStream toClient = new ZipOutputStream(new FileOutputStream(fileZip));
				DownLoad.zipFile(list, toClient);
				toClient.close();
				outStream.close();
//				this.stop();
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
		}
	
    }  
}  