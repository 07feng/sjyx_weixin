package com.sunnet.org.util;

import java.util.Date;
import java.util.Iterator;

/**
 * 一些字符串的空判断
 * 
 * @author
 *
 */
public class StringUtils {

	public static boolean isStringNull(String str) {
		boolean flag = false;
		if (str != null && !(str.trim().equals(""))) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
		// name = name.substring(0, 1).toUpperCase() + name.substring(1);
		// return name;
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
	
	
	/**
	 * APP参数传入 -》对应错误提示
	 * @param r
	 * @param c
	 * @return
	 */
	public static String isObject(Object [] r,String[] c){
		if(r == null){
			return "";
		}else{
			for (int i = 0; i < r.length; i++) {
				if(isObjectNULL(r[i])){
					//如果为真 -》 则不为空 -> 都让过
				}else{
					return c[i];
				}
			}
		}
		return "";
	}

	/**
	 * 判断Object是否为空
	 * @param param
	 * @return
	 */
	public static boolean isObjectNULL(Object param) {
		boolean flag = true;
		if (param instanceof Integer) {
			Integer value = ((Integer) param).intValue();
			if (value == null) {
				flag = false;
			}
		} else if (param instanceof String) {
			String value = (String) param;
			if (value == null || value.trim().equals("")) {
				flag = false;
			}
		} else if (param instanceof Double) {
			Double value = ((Double) param).doubleValue();
			if (value == null) {
				flag = false;
			}
		} else if (param instanceof Float) {
			Float value = ((Float) param).floatValue();
			if (value == null) {
				flag = false;
			}
		} else if (param instanceof Long) {
			Long value = ((Long) param).longValue();
			if (value == null) {
				flag = false;
			}
		}else{
			if(param == null){
				flag = false;
			}
		}
		return flag;
	}

}
