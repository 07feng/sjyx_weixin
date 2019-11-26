package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_level;

/**
 * tb_level 返回数据的加载
 * @author 强强
 *
 */
public class Tb_levelUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_level> list){
		List item= new ArrayList();
		for (Tb_level obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("levelname", obj.getLevelname());
 map.put("minexp", obj.getMinexp());
 map.put("maxexp", obj.getMaxexp());
 map.put("iconimg", obj.getIconimg());
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
	public static Map getControllerMap(Tb_level obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("levelname", obj.getLevelname());
 map.put("minexp", obj.getMinexp());
 map.put("maxexp", obj.getMaxexp());
 map.put("iconimg", obj.getIconimg());
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
	public static List getControllerListAll(List<Tb_level> list) {
		List item = new ArrayList();
		for (Tb_level obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("levelname", obj.getLevelname());
 map.put("minexp", obj.getMinexp());
 map.put("maxexp", obj.getMaxexp());
 map.put("iconimg", obj.getIconimg());
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
