package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_memberparameter;

/**
 * tb_memberparameter 返回数据的加载
 * 
 * @author
 *
 */
public class Tb_memberparameterUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_memberparameter> list) {
		List item = new ArrayList();
		for (Tb_memberparameter obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("isopenregmember", obj.getIsopenregmember());
			map.put("membercapacity", obj.getMembercapacity());
			map.put("isopenmobileauth", obj.getIsopenmobileauth());
			map.put("isrepeatname", obj.getIsrepeatname());
			map.put("isopenaudit", obj.getIsopenaudit());
			map.put("noregnickname", obj.getNoregnickname());
			map.put("regagreement", obj.getRegagreement());
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
	public static Map getControllerMap(Tb_memberparameter obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("isopenregmember", obj.getIsopenregmember());
		map.put("membercapacity", obj.getMembercapacity());
		map.put("isopenmobileauth", obj.getIsopenmobileauth());
		map.put("isrepeatname", obj.getIsrepeatname());
		map.put("isopenaudit", obj.getIsopenaudit());
		map.put("noregnickname", obj.getNoregnickname());
		map.put("regagreement", obj.getRegagreement());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tb_memberparameter> list) {
		List item = new ArrayList();
		for (Tb_memberparameter obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("isopenregmember", obj.getIsopenregmember());
			map.put("membercapacity", obj.getMembercapacity());
			map.put("isopenmobileauth", obj.getIsopenmobileauth());
			map.put("isrepeatname", obj.getIsrepeatname());
			map.put("isopenaudit", obj.getIsopenaudit());
			map.put("noregnickname", obj.getNoregnickname());
			map.put("regagreement", obj.getRegagreement());
			item.add(map);
		}
		return item;
	}

}
