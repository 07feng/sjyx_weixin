package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.Permission;

/**
 * permission 返回数据的加载
 * 
 * @author 强强
 *
 */
public class PermissionUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Permission> list) {
		List item = new ArrayList();
		for (Permission obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("fdId", obj.getFdId());
			map.put("fdPermissionCode", obj.getFdPermissionCode());
			map.put("fdPermissionName", obj.getFdPermissionName());
			map.put("fdPermissionDesc", obj.getFdPermissionDesc());
			map.put("fdStatus", obj.getFdStatus());
			map.put("fdCreateTime", obj.getFdCreateTime());

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
	public static Map getControllerMap(Permission obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("fdId", obj.getFdId());
		map.put("fdPermissionCode", obj.getFdPermissionCode());
		map.put("fdPermissionName", obj.getFdPermissionName());
		map.put("fdPermissionDesc", obj.getFdPermissionDesc());
		map.put("fdStatus", obj.getFdStatus());
		map.put("fdCreateTime", obj.getFdCreateTime());

		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Permission> list) {
		List item = new ArrayList();
		for (Permission obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("fdId", obj.getFdId());
			map.put("fdPermissionCode", obj.getFdPermissionCode());
			map.put("fdPermissionName", obj.getFdPermissionName());
			map.put("fdPermissionDesc", obj.getFdPermissionDesc());
			map.put("fdStatus", obj.getFdStatus());
			map.put("fdCreateTime", obj.getFdCreateTime());

			item.add(map);
		}
		return item;
	}

}
