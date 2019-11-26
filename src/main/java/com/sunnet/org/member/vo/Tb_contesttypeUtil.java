package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_contesttype;

/**
 * tb_contesttype 返回数据的加载
 * @author 强强
 *
 */
public class Tb_contesttypeUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_contesttype> list){
		List item= new ArrayList();
		for (Tb_contesttype obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("fId", obj.getFid());
 map.put("name", obj.getName());
 map.put("notes", obj.getNotes());
 map.put("editTime", obj.getEdittime());
 map.put("editUserId", obj.getEdituserid());
 
 if (obj.getEdituserid() != null) {
	 map.put("editUserId", obj.getEdituserid().getFdUserName());
	} else {
		map.put("fdUserName", "");
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
	public static Map getControllerMap(Tb_contesttype obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		 map.put("id", obj.getId());
		 map.put("fId", obj.getFid());
		 map.put("name", obj.getName());
		 map.put("notes", obj.getNotes());
		 map.put("editTime", obj.getEdittime());
		 map.put("editUserId", obj.getEdituserid());
		 
		 if (obj.getEdituserid() != null) {
			 map.put("editUserId", obj.getEdituserid().getFdUserName());
			} else {
				map.put("fdUserName", "");
			}
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_contesttype> list) {
		List item = new ArrayList();
		for (Tb_contesttype obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
			 map.put("id", obj.getId());
			 map.put("fId", obj.getFid());
			 map.put("name", obj.getName());
			 map.put("notes", obj.getNotes());
			 map.put("editTime", obj.getEdittime());
			 map.put("editUserId", obj.getEdituserid());
			 
			 if (obj.getEdituserid() != null) {
				 map.put("editUserId", obj.getEdituserid().getFdUserName());
				} else {
					map.put("fdUserName", "");
				}
			item.add(map);
		}
		return item;
	}

}
