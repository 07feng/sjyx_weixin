package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_group;

/**
 * tb_group 返回数据的加载
 * @author 强强
 *
 */
public class Tb_groupUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_group> list){
		List item= new ArrayList();
		for (Tb_group obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("groupname", obj.getGroupname());
 map.put("notes", obj.getNotes());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if (obj.getEdituserid() != null) {
	 map.put("edituserid", obj.getEdituserid().getFdUserName());
	} else {
		map.put("edituserid", "");
	}
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_group obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("groupname", obj.getGroupname());
 map.put("notes", obj.getNotes());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if (obj.getEdituserid() != null) {
	 map.put("edituserid", obj.getEdituserid().getFdUserName());
	} else {
		map.put("edituserid", "");
	}
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_group> list) {
		List item = new ArrayList();
		for (Tb_group obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("groupname", obj.getGroupname());
 map.put("notes", obj.getNotes());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if (obj.getEdituserid() != null) {
	 map.put("edituserid", obj.getEdituserid().getFdUserName());
	} else {
		map.put("edituserid", "");
	}
			item.add(map);
		}
		return item;
	}

}
