package com.sunnet.code.util;

/**
 * 芬哥专用 工具类
 * 
 * @author Administrator
 *
 */
public class TestUtil {

	/*
	 * ********************************* jdbc 配置********************************
	 */
	public static String URL = "";
	public static String USERNAME = "";
	public static String PASSWORD = "";
	public static String BASEDATA = "";

	/**
	 * 首字母大写
	 * 
	 * @param tableName
	 * @return
	 */
	public static String captureName(String name) {
		return getToClassName(name);

	}

	/**
	 * 首字母大写
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getToClassName(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
		// String[] split = tableName.split("_");
		// if (split.length > 1) {
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < split.length; ++i) {
		// String tempTableName = split[i].substring(0, 1).toUpperCase() +
		// split[i].substring(1, split[i].length());
		// sb.append(tempTableName);
		// }
		//
		// return sb.toString();
		// }
		// String tempTables = split[0].substring(0, 1).toUpperCase() +
		// split[0].substring(1, split[0].length());
		// return tempTables;
	}

	/**
	 * 首字母小写
	 * 
	 * @param strto
	 * @return
	 */
	public static String getStr(String strto) {
		char[] chars = new char[1];
		String str = strto;
		chars[0] = str.charAt(0);
		String temp = new String(chars);
		if (chars[0] >= 'A' && chars[0] <= 'Z') {// 当为字母时，则转换为小写
			return str.replaceFirst(temp, temp.toLowerCase());
		}
		return strto;
	}

	/**
	 * 去除下划线变成点
	 * 
	 * @param strto
	 * @return
	 */
	public static String getDelStr(String strto) {
		String[] str = strto.split("/");
		String strUtil = "";
		for (int i = 0; i < str.length; i++) {
			if (i != str.length - 1) {
				strUtil += str[i] + ".";
			} else {
				strUtil += str[i];
			}
		}
		return strUtil;
	}

	public static void main(String[] args) {
		System.out.println(TestUtil.captureName("platform_id"));
		// System.out.println(getDelStr("com/sunnet/org/"));
	}

	/**
	 * 项目目录 //E:/workList/stswork2/zinyun.globaltake/
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}
}
