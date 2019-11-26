package com.sunnet.org.filmfestival.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.filmfestival.model.Filmfestivalvipopentime;

/**
 * filmfestivalvipopentime 返回数据的加载
 * 
 * @author 强强
 *
 */
public class FilmfestivalvipopentimeUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Filmfestivalvipopentime> list) {
		List item = new ArrayList();
		for (Filmfestivalvipopentime obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			map.put("time_length_name", obj.getTime_length_name());
			map.put("time_length", obj.getTime_length());
			map.put("pay_money", obj.getPay_money());
			item.add(map);
		}
		return item;
	}

	/**
	 * 返回单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Filmfestivalvipopentime obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("time_length_name", obj.getTime_length_name());
		map.put("time_length", obj.getTime_length());
		map.put("pay_money", obj.getPay_money());

		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Filmfestivalvipopentime> list) {
		List item = new ArrayList();
		for (Filmfestivalvipopentime obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("time_length_name", obj.getTime_length_name());
			map.put("time_length", obj.getTime_length());
			map.put("pay_money", obj.getPay_money());
			item.add(map);

		}
		return item;
	}

}
