package com.sunnet.org.competition.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.competition.model.T_contest_theme;

/**
 * t_contest_theme 返回数据的加载
 * 
 * @author 强强
 *
 */
public class T_contest_themeUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<T_contest_theme> list) {
		List item = new ArrayList();
		for (T_contest_theme obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getContesttheme_id() != null) {
				map.put("contesttheme_id", obj.getContesttheme_id().getId());
				map.put("themename", obj.getContesttheme_id().getThemename());
				map.put("themenametype", obj.getContesttheme_id().getThemenametype().getId());
			} else {
				map.put("contesttheme_id", "");
				map.put("themename", "");
				map.put("themenametype", "");
			}
			 
			if (obj.getContest_id() != null) {
				map.put("contest_id", obj.getContest_id().getId());
			} else {
				map.put("contest_id", "");
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
	public static Map getControllerMap(T_contest_theme obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		if (obj.getContesttheme_id() != null) {
			map.put("contesttheme_id", obj.getContesttheme_id().getId());
			map.put("themename", obj.getContesttheme_id().getThemename());
			map.put("themenametype", obj.getContesttheme_id().getThemenametype().getId());
		} else {
			map.put("contesttheme_id", "");
			map.put("themename", "");
			map.put("themenametype", "");
		}
	 
		if (obj.getContest_id() != null) {
			map.put("contest_fdId", obj.getContest_id().getId());
		} else {
			map.put("contest_fdId", "");
		}
		map.put("contest_id", obj.getContest_id());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<T_contest_theme> list) {
		List item = new ArrayList();
		for (T_contest_theme obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getContesttheme_id() != null) {
				map.put("contesttheme_id", obj.getContesttheme_id().getId());
				map.put("themename", obj.getContesttheme_id().getThemename());
				map.put("themenametype", obj.getContesttheme_id().getThemenametype().getId());
			} else {
				map.put("contesttheme_id", "");
				map.put("themename", "");
				map.put("themenametype", "");
			}
			map.put("contesttheme_id", obj.getContesttheme_id());
			if (obj.getContest_id() != null) {
				map.put("contest_fdId", obj.getContest_id().getId());
			} else {
				map.put("contest_fdId", "");
			}
			map.put("contest_id", obj.getContest_id());
			item.add(map);
		}
		return item;
	}

}
