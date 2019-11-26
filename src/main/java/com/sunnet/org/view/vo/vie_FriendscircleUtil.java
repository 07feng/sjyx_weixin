package com.sunnet.org.view.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.view.model.vie_Friendscircle;

/**
 * vie_Friendscircle 返回数据的加载
 * 
 * @author 强强
 *
 */
public class vie_FriendscircleUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<vie_Friendscircle> list) {
		List item = new ArrayList();
		for (vie_Friendscircle obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("titel", obj.getTitel());
			if (obj.getOpen_time() != null) {
				map.put("open_time", obj.getOpen_time());
			} else {
				map.put("open_time", "");
			}
			if (obj.getTime_length() >0) {
				map.put("time_length", obj.getTime_length());
			} else {
				map.put("time_length", "");
			}
			map.put("sort", obj.getSort());
			map.put("titelnote", obj.getTitelnote());
			map.put("isfriends", obj.getIsfriends());
			if (obj.getMember_id() != null) {
				map.put("member_id", obj.getMember_id().getId());
			} else {
				map.put("member_id", "");
			}
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
	public static Map getControllerMap(vie_Friendscircle obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("titel", obj.getTitel());
		if (obj.getOpen_time() != null) {
			map.put("open_time", obj.getOpen_time());
		} else {
			map.put("open_time", "");
		}
		if (obj.getTime_length() >0) {
			map.put("time_length", obj.getTime_length());
		} else {
			map.put("time_length", "");
		}
		map.put("sort", obj.getSort());
		map.put("titelnote", obj.getTitelnote());
		map.put("isfriends", obj.getIsfriends());
		if (obj.getMember_id() != null) {
			map.put("member_id", obj.getMember_id().getId());
		} else {
			map.put("member_id", "");
		}
	 
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<vie_Friendscircle> list) {
		List item = new ArrayList();
		for (vie_Friendscircle obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("titel", obj.getTitel());
			if (obj.getOpen_time() != null) {
				map.put("open_time", obj.getOpen_time());
			} else {
				map.put("open_time", "");
			}
			if (obj.getTime_length() >0) {
				map.put("time_length", obj.getTime_length());
			} else {
				map.put("time_length", "");
			}
			map.put("sort", obj.getSort());
			map.put("titelnote", obj.getTitelnote());
			map.put("isfriends", obj.getIsfriends());
			if (obj.getMember_id() != null) {
				map.put("member_id", obj.getMember_id().getId());
			} else {
				map.put("member_id", "");
			}
		 
			item.add(map);
		}
		return item;
	}

}
