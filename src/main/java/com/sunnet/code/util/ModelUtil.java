package com.sunnet.code.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.code.model.EntityModule;

/**
 * 生成 设置规则
 * 
 * @author 强强
 *
 *         时间: 2017年2月20日
 */
public class ModelUtil {

	// 列表参数
	public static List<String> parameter = new ArrayList<String>();

	// 外键参数
	public static List<String> foreignparameter = new ArrayList<String>();

	// 赛选参数
	public static List<String> screen = new ArrayList<String>();

	// 添加参数
	public static List<String> add = new ArrayList<String>();

	// 添加参数
	public static List<String> select = new ArrayList<String>();

	// 反馈参数 VO
	public static List<String> voutil = new ArrayList<String>();

	// 反馈参数 VO ->自动查询
	public static List<String> voutilSelecyt = new ArrayList<String>();

	// 全部参数
	public static Map<String, Object> total = new HashMap<String, Object>();

	// 实体类
	public static List<EntityModule> list = new ArrayList<EntityModule>();

	// 图片
	public static List<String> img = new ArrayList<String>();

	/**
	 * 清除
	 */
	public static void clear() {
		parameter.clear();
		foreignparameter.clear();
		screen.clear();
		add.clear();
		total.clear();
		select.clear();
		voutilSelecyt.clear();
		img.clear();
	}

	/**
	 * 获取所有的参数 [去除外键]
	 * 
	 * @param tr
	 * @param is_add
	 *            去除fdId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> toClass(Class tr, boolean is_add) {
		List<String> list = new ArrayList<String>();
		Field[] fields = tr.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.getName() != null
					&& !(field.getName().equals("serialVersionUID"))) {
				// 去除fdId
				if (is_add && field.getName().equals("fdId")) {
					continue;
				}
				// 检查是否包含
				if (isList(field.getName())) {
					list.add(field.getName());
				}
			}
		}
		return list;
	}

	/**
	 * 获取所有的参数 [去除外键]
	 * 
	 * @param tr
	 * @param is_add
	 *            去除fdId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> toClass(String str, boolean is_add)
			throws Exception {
		List<String> list = new ArrayList<String>();
		Class tr = null;
		try {
			tr = Class.forName(str);
		} catch (ClassNotFoundException e) {
			System.err.println("注意：entity需要先执行一次刷新项目！");
			return list;
		}
		Field[] fields = tr.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.getName() != null) {
				// 去除fdId
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}
				if (is_add && field.getName().equals("fdId")) {
					continue;
				}
				if (isList(field.getName())) {
					list.add(field.getName());
				}
			}
		}
		/* 這里搞定/换个地方了 */
//		Object userInfo = null;
//		try {
//			userInfo = tr.newInstance();
//			Method m2 = tr.getMethod("getMap");
//			m2.invoke(userInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static List<String> toClassTool(String str, boolean is_add)
			throws Exception {
		List<String> list = new ArrayList<String>();
		Class tr = null;
		try {
			tr = Class.forName(str);
			Object userInfo = null;
			userInfo = tr.newInstance();
			Method m2 = tr.getMethod("getMap");
			m2.invoke(userInfo);
		} catch (Exception e) {
			System.err.println("注意：entity需要先执行一次刷新项目！");
			return list;
		}
		return list;
	}

	/**
	 * 获取所有的参数
	 * 
	 * @param tr
	 * @return
	 */
	public static Map<String, Object> toTotal() {
		List<String> list = new ArrayList<String>();
		list.addAll(ModelUtil.parameter);
		list.addAll(ModelUtil.foreignparameter);
		Map<String, Object> map = new HashMap<String, Object>();
		for (String str : list) {
			map.put(str, str);
		}
		return map;
	}

	/**
	 * 是否存在
	 * 
	 * @param strName
	 * @return
	 */
	public static boolean isList(String strName) {
		for (String str : foreignparameter) {
			if (strName.equals(str)) {
				return false;
			}
		}
		return true;
	}

}
