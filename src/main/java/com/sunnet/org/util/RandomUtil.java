package com.sunnet.org.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class RandomUtil {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}
	
	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(String num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	// public static String getNoLenth(int leng) {
	// int n = 0;
	// n = (int) (Math.random() * 100000);
	// while (n < 10000 || !handle(n)) {
	// n = (int) (Math.random() * 100000);
	// }
	// return n+"";
	// }
	//
	// public static boolean handle(int n) {
	// int[] list = new int[5];
	// for (int i = 0; i < 5; i++) {
	// list[i] = n % 10;
	// n = n / 10;
	// }
	// for (int i = 0; i < 5; i++) {
	// for (int j = i + 1; j < 5; j++) {
	// if (list[i] == list[j])
	// return false;
	// }
	// }
	// return true;
	// }

	/**
	 * 每次生成的len位数都不相同
	 * 
	 * @param param
	 * @return 定长的数字
	 */
	public static int getNotSimple(int[] param, int len) {
		Random rand = new Random();
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result;
	}

	/**
	 * 随机生成字符长度的数字
	 * 
	 * @param i
	 * @return
	 */
	public static String getNoLenth(int i) {
		int[] in = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		return getNotSimple(in, i) + "";
	}

	/**
	 * Double保留1位
	 * 
	 * @param dou
	 * @return
	 */
	public static Double getDouble(Double dou) {
		DecimalFormat df = new DecimalFormat(".#");
		String st = df.format(dou);
		return Double.valueOf(st);
	}
	
	
	/**
	 * Double保留2位
	 * 
	 * @param dou
	 * @return
	 */
	public static Double getDoubletwo(Double dou) {
		DecimalFormat df = new DecimalFormat(".##");
		String st = df.format(dou);
		return Double.valueOf(st);
	}

	/**
	 * Double去除小位数
	 * 
	 * @param dou
	 * @return
	 */
	public static String getInterget(int i) {
		int[] in = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		return getNotSimple(in, i) + "";
	}
	
	/**
	 * 生成定随机数
	 * @param i
	 * @return
	 */
	public static String getNoLength(int i){
		return toFixdLengthString(getNoLenth(i),i);
	}
	
	/**
	 * 获取时间搓订单编号
	 * 
	 * @param date
	 *            日期字符串
	 * @return 时间
	 */
	public static String getOrder() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(date);
		/* 唯一编号 */
		String ab = getNoLength(4);
		str = str +ab;
		return str;
	}
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}  
	

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		long  a =Math.round(1.24564564);
		System.out.println((int)a);
		// System.out.println(getNoLenth(5));

		// System.out.println(getDouble(Double.valueOf("-0.255")));
		// DecimalFormat df=new DecimalFormat(".##");
		// double d=20145252.1111412;
		// String st=df.format(d);
		// System.out.println(st);

		// System.out.println("返回一个定长的随机字符串(只包含大小写字母、数字):" +
		// generateString(10));
		// System.out
		// .println("返回一个定长的随机纯字母字符串(只包含大小写字母):" + generateMixString(10));
		// System.out.println("返回一个定长的随机纯大写字母字符串(只包含大小写字母):"
		// + generateLowerString(10));
		// System.out.println("返回一个定长的随机纯小写字母字符串(只包含大小写字母):"
		// + generateUpperString(10));
		// System.out.println("生成一个定长的纯0字符串:" + generateZeroString(10));
		// System.out.println("根据数字生成一个定长的字符串，长度不够前面补0:"
		// + toFixdLengthString(123, 5));
		// int[] in = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		// System.out.println("每次生成的len位数都不相同:" + getNotSimple(in, 4));
	}
}