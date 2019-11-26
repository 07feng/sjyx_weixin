package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.Menu;

/**
 * menu 返回数据的加载
 * @author 强强
 *
 */
public class MenuUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Menu> list){
		List item= new ArrayList();
		for (Menu obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("fdId", obj.getFdId());
 map.put("fdGm", obj.getFdGm());
 map.put("fdName", obj.getFdName());
 map.put("fdUrl", obj.getFdUrl());
 map.put("fdPermission", obj.getFdPermission());
 map.put("sort", obj.getSort());
 if (obj.getMenu() != null) {
 map.put("menu_fdName", obj.getMenu().getFdName());
 } else {
 map.put("menu_fdName", "");
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
	public static Map getControllerMap(Menu obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("fdId", obj.getFdId());
 map.put("fdGm", obj.getFdGm());
 map.put("fdName", obj.getFdName());
 map.put("fdUrl", obj.getFdUrl());
 map.put("fdPermission", obj.getFdPermission());
 map.put("sort", obj.getSort());
 if (obj.getMenu() != null) {
 map.put("menu_fdName", obj.getMenu().getFdName());
 } else {
 map.put("menu_fdName", "");
 }
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Menu> list) {
		List item = new ArrayList();
		for (Menu obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("fdId", obj.getFdId());
 map.put("fdGm", obj.getFdGm());
 map.put("fdName", obj.getFdName());
 map.put("fdUrl", obj.getFdUrl());
 map.put("fdPermission", obj.getFdPermission());
 map.put("sort", obj.getSort());
 if (obj.getMenu() != null) {
 map.put("menu_fdName", obj.getMenu().getFdName());
 } else {
 map.put("menu_fdName", "");
 }
			item.add(map);
		}
		return item;
	}

}
