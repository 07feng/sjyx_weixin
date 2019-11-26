package com.sunnet.org.visitors.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.visitors.model.T_topsearchterm;

/**
 * t_topsearchterm 返回数据的加载
 * 
 * @author 强强
 *
 */
public class T_topsearchtermUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<T_topsearchterm> list) {
		List item = new ArrayList();
		for (T_topsearchterm obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("fdSearchterm", obj.getFdSearchterm());
			map.put("fdsearchnumber", obj.getFd_searchnumber());
			map.put("fdLastsearchTime", obj.getFd_lastsearchtime());
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
	public static Map getControllerMap(T_topsearchterm obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("fdSearchterm", obj.getFdSearchterm());
		map.put("fdsearchnumber", obj.getFd_searchnumber());
		map.put("fdLastsearchTime", obj.getFd_lastsearchtime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<T_topsearchterm> list) {
		List item = new ArrayList();
		for (T_topsearchterm obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("fdSearchterm", obj.getFdSearchterm());
			map.put("fdsearchnumber", obj.getFd_searchnumber());
			map.put("fdLastsearchTime", obj.getFd_lastsearchtime());
			item.add(map);
		}
		return item;
	}

}
