package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.Role;

/**
 * role 返回数据的加载
 * 
 * @author 强强
 *
 */
public class RoleUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Role> list) {
		List item = new ArrayList();
		for (Role obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("fdId", obj.getFdId());
			map.put("fdRoleCode", obj.getFdRoleCode());
			map.put("fdRoleName", obj.getFdRoleName());
			map.put("fdRoleDesc", obj.getFdRoleDesc());
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
	public static Map getControllerMap(Role obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("fdId", obj.getFdId());
		map.put("fdRoleCode", obj.getFdRoleCode());
		map.put("fdRoleName", obj.getFdRoleName());
		map.put("fdRoleDesc", obj.getFdRoleDesc());
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
	public static List getControllerListAll(List<Role> list) {
		List item = new ArrayList();
		for (Role obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("fdId", obj.getFdId());
			map.put("fdRoleCode", obj.getFdRoleCode());
			map.put("fdRoleName", obj.getFdRoleName());
			map.put("fdRoleDesc", obj.getFdRoleDesc());
			map.put("fdStatus", obj.getFdStatus());
			map.put("fdCreateTime", obj.getFdCreateTime());

			item.add(map);
		}
		return item;
	}

}
