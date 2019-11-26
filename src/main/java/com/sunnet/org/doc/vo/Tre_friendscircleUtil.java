package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tre_friendscircle;

/**
 * tre_friendscircle 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tre_friendscircleUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tre_friendscircle> list) {
		List item = new ArrayList();
		for (Tre_friendscircle obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getCirclememberid() != null) {
				map.put("circlememberid", obj.getCirclememberid().getId());
				map.put("circleusersname", obj.getCirclememberid().getUsersname());
				
				if(obj.getCirclememberid().getLevelid()!=null){
					map.put("circlelevelid", obj.getCirclememberid().getLevelid().getId());
					map.put("circlelevelname", obj.getCirclememberid().getLevelid().getLevelname());
				}else{
					map.put("circlelevelid", "");
					map.put("circlelevelname","");
				}
				map.put("circlelevelname", obj.getCirclememberid().getLevelid().getLevelname());
				if (obj.getCirclememberid().getHeadimg() != null) {
					map.put("circleheadimg", obj.getCirclememberid()
							.getHeadimg());
				} else {
					map.put("circleheadimg", "");
				}
				if (obj.getCirclememberid().getIntroduction() != null) {// 个人简介
					map.put("circleintroduction", obj.getCirclememberid()
							.getIntroduction());
				} else {
					map.put("circleintroduction", "");
				}
			} else {
				map.put("circlememberid", "");
				map.put("circleusersname", "");
				map.put("circleheadimg", "");
				map.put("circleintroduction", "");
				map.put("circlelevelid","");
				map.put("circlelevelname", "");
			}
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				
				if(obj.getMemberid().getLevelid()!=null){
					map.put("levelid", obj.getMemberid().getLevelid().getId());
					map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
				}else{
					map.put("levelid", "");
					map.put("levelname","");
				}
				
				if (obj.getMemberid().getHeadimg() != null) {
					map.put("headimg", obj.getMemberid().getHeadimg());
				} else {
					map.put("headimg", "");
				}
				if (obj.getCirclememberid().getIntroduction() != null) { // 个人简介
					map.put("introduction", obj.getMemberid().getIntroduction());
				} else {
					map.put("introduction", "");
				}
			} else {
				map.put("memberid", "");
				map.put("usersname", "");
				map.put("headimg", "");
				map.put("introduction", "");
				map.put("levelid","");
				map.put("levelname", "");
			}

			map.put("addtime", obj.getAddtime());
			map.put("isfriends",obj.getIsfriends());
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
	public static Map getControllerMap(Tre_friendscircle obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("circlememberid", obj.getCirclememberid());
		map.put("memberid", obj.getMemberid());
		map.put("addtime", obj.getAddtime());
		map.put("isfriends",obj.getIsfriends());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tre_friendscircle> list) {
		List item = new ArrayList();
		for (Tre_friendscircle obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("circlememberid", obj.getCirclememberid());
			map.put("memberid", obj.getMemberid());
			map.put("addtime", obj.getAddtime());
			item.add(map);
		}
		return item;
	}

}
