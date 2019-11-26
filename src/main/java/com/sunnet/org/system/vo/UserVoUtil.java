package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.User;

/**
 * user 返回数据的加载
 * 
 * @author 强强
 *
 */
public class UserVoUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<User> list) {
		List item = new ArrayList();
		for (User obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("fdId", obj.getFdId());
			map.put("fdUserName", obj.getFdUserName());
			map.put("fdPassword", obj.getFdPassword());
			map.put("fdPhone", obj.getFdPhone());
			map.put("fdName", obj.getFdName());
			map.put("fdAge", obj.getFdAge());
			map.put("fdStatus", obj.getFdStatus());
			map.put("fdCreateTime", obj.getFdCreateTime());
			map.put("fdisdelete", obj.getIsdelete());
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
	public static Map getControllerMap(User obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("fdId", obj.getFdId());
		map.put("fdUserName", obj.getFdUserName());
		map.put("fdPassword", obj.getFdPassword());
		map.put("fdPhone", obj.getFdPhone());
		map.put("fdName", obj.getFdName());
		map.put("fdAge", obj.getFdAge());
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
	public static List getControllerListAll(List<User> list) {
		List item = new ArrayList();
		for (User obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("fdId", obj.getFdId());
			map.put("fdUserName", obj.getFdUserName());
			map.put("fdPassword", obj.getFdPassword());
			map.put("fdPhone", obj.getFdPhone());
			map.put("fdName", obj.getFdName());
			map.put("fdAge", obj.getFdAge());
			map.put("fdStatus", obj.getFdStatus());
			map.put("fdCreateTime", obj.getFdCreateTime());
			map.put("fdisdelete", obj.getIsdelete());
			item.add(map);
		}
		return item;
	}

}
