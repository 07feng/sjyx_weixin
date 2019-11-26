package com.sunnet.org.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ChangeImgTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] imgUrl = {"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\1.jpg",
				"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\2.jpg",
				"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\3.jpg",
				"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\4.jpg",
				"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\5.jpg",
				"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\6.jpg",
				"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\7.jpg",
				"C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\图片\1111\\九寨沟\\8.jpg",
		};
		try {
			boolean flag = renaHTMLAndChange("",imgUrl,"九寨沟123","",1,"");
			System.out.println("1111"+flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static boolean renaHTMLAndChange(String filePath, String[] imgUrl, String textStr, String resultPath, Integer type,
			String userName) throws IOException {

		filePath = "C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\xj\\1111111111\\photo_five\\index.html";
		resultPath = "C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\xj\\1111111111\\photo_five\\test.html";
		File f = new File(filePath);
		File f2 = new File(resultPath);
		if(f2.exists()){
			f2.delete();
		}
		Element name = null;
		String text = "";
		if (!("".equals(textStr)) && textStr != null) {
			text = textStr;
		}
		try {
			
			Document doc = Jsoup.parse(f, "utf-8");
			if (type == 2) {
				// 影展
				name = doc.select(".picture-title").first();
				name.html(text);
			} else if (type == 0) {
				// 贺卡
				name = doc.select("p").first();// 编辑文字
				name.html(textStr);
//				if (name == null) {
//				
//					name.html(text);
//				} else {
//					name.html(userName);
//				}
				for (int i = 0; i < imgUrl.length; i++) {
					Element img = doc.select("img[name='" + (i + 1) + "']").first();// 替换图片
					img.attr("src", imgUrl[i]);
				}
				FileOutputStream fos = new FileOutputStream(resultPath, true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
				osw.write(doc.html());
				osw.close();
			} else {
				// 相册
				Element pStrElement = doc.select("p").first();// 编辑文字
				pStrElement.html(textStr);
				
				Document doc2 = rePlayAlbum(doc, imgUrl);
				FileOutputStream fos = new FileOutputStream(resultPath, true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
				osw.write(doc2.html());
				osw.close();
			}
		 
		} catch (Exception e) {
//			writeEror_to_txt(path,"ddddd");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static Document rePlayAlbum(Document doc, String[] imgUrl) {
		int length = imgUrl.length;

		Elements divs = null;
		divs = doc.select(".main");
		if (divs == null || divs.size() <= 0) {
			// class为swiper的修改
			divs = doc.select(".swiper-slide");
			String[] swiperArray = new String[divs.size()];
			Integer[] imgLength = new Integer[divs.size()];
			Elements imgs = null;
			int imgNum = 0;// 一轮可替换的图片个数
			for (int i = 0; i < divs.size(); i++) {
				swiperArray[i] = divs.get(i).toString();// 获取每页的swiper div
				imgs = divs.get(i).select("img");
				imgLength[i] = divs.get(i).select("img").size();// 每个div中可替换的图片个数
				imgNum = imgNum + imgs.size();
			}
			// int count = 0;
			int changeImg = 0;
			String str = "";
			if (imgUrl.length <= imgNum) {
				// 图片个数小于模板图片个数
					for (int i = 0; i < divs.size(); i++) {
						changeImg = changeImg + imgLength[i];
						if (imgUrl.length <= changeImg) {
							for (int j = 0; j < imgUrl.length; j++) {
								Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
								img.attr("src", imgUrl[j]);
							}
							if (imgUrl.length < changeImg) {
								int a = changeImg - imgUrl.length;
								// 当传递图片少于已有图片时删除多余图片
								for (int j = imgUrl.length; j < changeImg; j++) {
									doc.select("img[name='" + (j + 1) + "']").remove();
								}
							}
							for (int k = i + 1; k < divs.size(); k++) {
								divs.get(k).remove();
							}
							break;
						}
					}
				} else {
					// 图片个数大于模板图片个数
					int count = 0;
					// 先替换所有模板中图片
					for (int j = 0; j < imgNum; j++) {
						Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
						img.attr("src", imgUrl[j]);
						count++;
					}
					// 循环拼接多余图片
					int num = imgUrl.length - imgNum;// 多余的图片
					int temp = 0; 
					int temp2 = 0;
//					changeImg = imgLength[temp];
					do{
						str = swiperArray[temp];
						Element mainDiv = doc.select(".swiper-wrapper").first();// 最外层div
						mainDiv.append(str);// 拼接div
						Elements imgs2 = doc.select(".swiper-slide").get(divs.size()+temp2).select("img");
						for(int j=0;j<imgs2.size();j++){
							if(count>=imgUrl.length){
								imgs2.get(j).remove();
							}else{
								imgs2.get(j).attr("src", imgUrl[count]);
							}
							count++;
							changeImg ++;
						}
//						changeImg += imgLength[temp];
						temp++;
						temp2++;
						if(temp >= divs.size()){
							temp = 0;
						}
					} while(changeImg <= num);
			}
		 }else{
		 //class为main的修改
//			Integer[] imgLength = new Integer[divs.size()];
			Elements imgs = null;
			Elements divs2 = divs.select("div");
			int imgNum = divs2.size()-2;// 一轮可替换的图片个数
			String[] pageArray = new String[imgNum];
			for (int i = 2; i < divs2.size(); i++) {
				pageArray[i-2] = divs2.get(i).toString();// 获取每页的 div
			}
			String str = "";
			int delecTemp = 0;
			
			if (imgUrl.length <= imgNum) {
				for (int j = 0; j < imgUrl.length; j++) {
					Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
					img.attr("src", imgUrl[j]);
					delecTemp ++;
				}
				for (int k = delecTemp; k < imgNum; k++) {
					divs2.get(k+2).remove();
				}
			}else{
				int count = 0;
				for (int j = 0; j < imgNum; j++) {
					Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
					img.attr("src", imgUrl[count]);
					count++;
				}
				int num = imgUrl.length - imgNum;// 多余的图片
				int startTemp = 0;
				for(int i = 0; i < num; i++){//循环拼接可替换div
					str = pageArray[startTemp];
					Element mainDiv = doc.select(".main").first();// 最外层div
					mainDiv.append(str);// 拼接div
					//替换图片
					Element img = doc.select(".main").select("div").get(divs2.size()+i).select("img").first();
					img.attr("src",imgUrl[count]);
					count++;
					startTemp ++ ;
					if(startTemp==imgNum){
						startTemp = 0;
					}
				}
			}
		}
		return doc;
	}
	
	
}
