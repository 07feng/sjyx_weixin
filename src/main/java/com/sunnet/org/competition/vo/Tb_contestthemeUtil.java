package com.sunnet.org.competition.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.competition.model.Tb_contesttheme;

/**
 * tb_contesttheme 返回数据的加载
 * 
 * @author
 *
 */
public class Tb_contestthemeUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_contesttheme> list) {
		List item = new ArrayList();
		for (Tb_contesttheme obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("themename", obj.getThemename());
			map.put("edittime", obj.getEdittime());
			if (obj.getEdituserid() != null) {
				map.put("edituserid", obj.getEdituserid().getFdId());
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			}

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
	public static Map getControllerMap(Tb_contesttheme obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("themename", obj.getThemename());
		map.put("edittime", obj.getEdittime());
		if (obj.getEdituserid() != null) {
			map.put("edituserid", obj.getEdituserid().getFdId());
			map.put("edituserid", obj.getEdituserid().getFdUserName());
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_contesttheme> list) {
		List item = new ArrayList();
		for (Tb_contesttheme obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("themename", obj.getThemename());
			map.put("edittime", obj.getEdittime());
			if (obj.getEdituserid() != null) {
				map.put("edituserid", obj.getEdituserid().getFdId());
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			}
			item.add(map);
		}
		return item;
	}

}
