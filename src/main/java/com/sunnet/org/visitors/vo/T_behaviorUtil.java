package com.sunnet.org.visitors.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.visitors.model.T_behavior;

/**
 * t_behavior 返回数据的加载
 * 
 * @author 强强
 *
 */
public class T_behaviorUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<T_behavior> list) {
		List item = new ArrayList();
		for (T_behavior obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("fdId", obj.getFdId());
			if (obj.getFd_member_id() != null) {
				map.put("fdMemberId", obj.getFd_member_id().getId());
				map.put("fdMembername", obj.getFd_member_id().getUsersname());
			} else {
				map.put("fdMemberId", "");
			}
			map.put("fdIpAddress", obj.getFd_ipaddress());
			map.put("fdSystem", obj.getFd_system());
			map.put("fdbrowser", obj.getFd_browser());
			map.put("fdRegion", obj.getFd_region());
			map.put("fdNetworkType", obj.getFd_networktype());
			map.put("fdOperatingTime", obj.getFd_operatingtime());
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
	public static Map getControllerMap(T_behavior obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("fdId", obj.getFdId());
		if (obj.getFd_member_id() != null) {
			map.put("fdMemberId", obj.getFd_member_id().getId());
			map.put("fdMembername", obj.getFd_member_id().getUsersname());
		} else {
			map.put("fdMemberId", "");
		}
		map.put("fdIpAddress", obj.getFd_ipaddress());
		map.put("fdSystem", obj.getFd_system());
		map.put("fdbrowser", obj.getFd_browser());
		map.put("fdRegion", obj.getFd_region());
		map.put("fdNetworkType", obj.getFd_networktype());
		map.put("fdOperatingTime", obj.getFd_operatingtime());
		 
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<T_behavior> list) {
		List item = new ArrayList();
		for (T_behavior obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("fdId", obj.getFdId());
			if (obj.getFd_member_id() != null) {
				map.put("fdMemberId", obj.getFd_member_id().getId());
				map.put("fdMembername", obj.getFd_member_id().getUsersname());
			} else {
				map.put("fdMemberId", "");
			}
			map.put("fdIpAddress", obj.getFd_ipaddress());
			map.put("fdSystem", obj.getFd_system());
			map.put("fdbrowser", obj.getFd_browser());
			map.put("fdRegion", obj.getFd_region());
			map.put("fdNetworkType", obj.getFd_networktype());
			map.put("fdOperatingTime", obj.getFd_operatingtime());
			item.add(map);
		}
		return item;
	}

}
