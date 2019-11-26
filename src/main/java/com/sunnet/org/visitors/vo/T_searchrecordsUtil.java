package com.sunnet.org.visitors.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.visitors.model.T_searchrecords;

/**
 * t_searchrecords 返回数据的加载
 * 
 * @author 强强
 *
 */
public class T_searchrecordsUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<T_searchrecords> list) {
		List item = new ArrayList();
		for (T_searchrecords obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("fdId", obj.getFdId());
			map.put("fdMemberId", obj.getFdMemberId().getId());
			map.put("fdMembername", obj.getFdMemberId().getUsersname());
			map.put("fdSearchTerm", obj.getFdsearchterm());
			map.put("fdIpAddress", obj.getFdipaddress());
			map.put("fdSearchtime", obj.getFdsearchtime());
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
	public static Map getControllerMap(T_searchrecords obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("fdId", obj.getFdId());
		map.put("fdMemberId", obj.getFdMemberId().getId());
		map.put("fdSearchTerm", obj.getFdsearchterm());
		map.put("fdIpAddress", obj.getFdipaddress());
		map.put("fdSearchTime", obj.getFdsearchtime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<T_searchrecords> list) {
		List item = new ArrayList();
		for (T_searchrecords obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("fdId", obj.getFdId());
			map.put("fdMemberId", obj.getFdMemberId().getId());
			map.put("fdSearchTerm", obj.getFdsearchterm());
			map.put("fdIpAddress", obj.getFdipaddress());
			map.put("fdSearchTime", obj.getFdsearchtime());
			item.add(map);
		}
		return item;
	}

}
