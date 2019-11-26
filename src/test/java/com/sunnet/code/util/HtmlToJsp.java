package com.sunnet.code.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlToJsp {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

	}

	public static void getString(String path, List<Map<String, Object>> list) {
		File files[] = new File(path).listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {// 只寻找费文件夹的文件
				String filename = files[i].getName();
				String filepath = files[i].getPath();
				if (filename.contains(".html")) {
					String[] a = filename.split(".html");
					FileWriter writer;
					try {
						writer = new FileWriter(path + "/" + a[0] + ".jsp");
						writer.write(getremove(getAdd(readFileByLines(filepath)), list));
						writer.flush();
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName) {
		StringBuffer str = new StringBuffer();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				str.append(tempString + "\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return str.toString();

	}

	public static String getAdd(String str) {
		String add = "<%@ page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%> \n";
		return add + str;
	}

	public static String getremove(String str, List<Map<String, Object>> list) {
		for (Map<String, Object> put : list) {
			str = str.replace(put.get("str").toString(), put.get("replace").toString());
		}
		return str;
	}

}