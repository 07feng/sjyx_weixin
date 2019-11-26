package com.sunnet.org.space.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.space.model.Tb_space;

/**
 * tb_space 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_spaceUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_space> list) {
		List item = new ArrayList();
		for (Tb_space obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("spacesize", obj.getSpacesize());
			map.put("charges", obj.getCharges());
			map.put("note", obj.getNote());
			map.put("sppacestatus", obj.getSppacestatus());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid());

			if (obj.getEdituserid() != null) {
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			} else {
				map.put("fdUserName", "");
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
	public static Map getControllerMap(Tb_space obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("spacesize", obj.getSpacesize());
		map.put("charges", obj.getCharges());
		map.put("note", obj.getNote());
		map.put("sppacestatus", obj.getSppacestatus());
		map.put("edittime", obj.getEdittime());
		map.put("edituserid", obj.getEdituserid());

		if (obj.getEdituserid() != null) {
			map.put("edituserid", obj.getEdituserid().getFdUserName());
		} else {
			map.put("fdUserName", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_space> list) {
		List item = new ArrayList();
		for (Tb_space obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("spacesize", obj.getSpacesize());
			map.put("charges", obj.getCharges());
			map.put("note", obj.getNote());
			map.put("sppacestatus", obj.getSppacestatus());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid());

			if (obj.getEdituserid() != null) {
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			} else {
				map.put("fdUserName", "");
			}
			item.add(map);
		}
		return item;
	}

}
