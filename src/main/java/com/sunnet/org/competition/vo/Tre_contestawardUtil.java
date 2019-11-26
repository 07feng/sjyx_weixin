package com.sunnet.org.competition.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.competition.model.Tre_contestaward;

/**
 * tre_contestaward 返回数据的加载
 * 
 * @author
 *
 */
public class Tre_contestawardUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tre_contestaward> list) {
		List item = new ArrayList();
		for (Tre_contestaward obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getContestid() != null) {
				map.put("contestid", obj.getContestid().getId());
				map.put("contestawardinfo", obj.getContestid().getContestawardinfo());//奖项说明
				map.put("contestname", obj.getContestid().getContestname());//赛事名称
				map.put("contestinfo", obj.getContestid().getContestinfo());//赛事摘要
				map.put("conteststarttime", obj.getContestid().getConteststarttime());//开始时间
				map.put("contestendtime", obj.getContestid().getContestendtime());//开始时间
				/*map.put("contestthemeid", obj.getContestid().getContestthemeid().getThemename());//赛事主题名称
				*/
			} else {
				map.put("contestid", "");
			}
			if (obj.getAwardname() != null) {
				map.put("awardname", obj.getAwardname());// 奖项名称
			} else {
				map.put("awardname", "");
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
	public static Map getControllerMap(Tre_contestaward obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("contestid", obj.getContestid());
		map.put("awardname", obj.getAwardname());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tre_contestaward> list) {
		List item = new ArrayList();
		for (Tre_contestaward obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("contestid", obj.getContestid());
			map.put("awardname", obj.getAwardname());
			item.add(map);
		}
		return item;
	}

}
