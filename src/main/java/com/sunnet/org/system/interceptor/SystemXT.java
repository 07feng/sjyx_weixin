package com.sunnet.org.system.interceptor;

import com.sunnet.org.system.model.SystemItem;

/**
 * 系统初始化
 * 
 * @author Administrator
 *
 */
public class SystemXT {

	public static String systemImg = "resources/img/home.mp4"; // 系统图片

	public static String systemImgLog = "resources/img/icons-lg.png"; // 后台log设置

	public static String systemTitle = "后台管理系统"; // 系统标题

	public static String systemVersion = "2.0"; // 版本号

	public static String color = "#FCFCFC"; // 颜色

	public static String beian = "粤ICP备13043785号-1";

	/**
	 * 赋值
	 * @param systemItem2
	 */
	public static void getSystem(SystemItem systemItem2) {
		SystemXT.systemImg = systemItem2.getSystemImg();
		SystemXT.systemImgLog = systemItem2.getSystemImgLog();
		SystemXT.systemTitle = systemItem2.getSystemTitle();
		SystemXT.systemVersion = systemItem2.getSystemVersion();
		SystemXT.color = systemItem2.getColor();
		SystemXT.beian = systemItem2.getBeian();
	}

}
