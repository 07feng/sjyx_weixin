package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_levelintegralsource;

/**
 * tb_levelintegralsource 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_levelintegralsourceUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_levelintegralsource> list) {
		List item = new ArrayList();
		for (Tb_levelintegralsource obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getLevelid() != null) {
				map.put("levelid", obj.getLevelid().getId());
				map.put("levelname", obj.getLevelid().getLevelname());
			} else {
				map.put("levelid", "");
				map.put("levelname", "");
			}

			map.put("login", obj.getLogin());
			map.put("upload", obj.getUpload());
			map.put("comments", obj.getComments());
			map.put("uncomments", obj.getUncomments());
			map.put("good", obj.getGood());
			map.put("ungood", obj.getUngood());
			map.put("keep", obj.getKeep());
			map.put("unkeep", obj.getUnkeep());
			map.put("unfocus", obj.getUnfocus());
			map.put("chargemoney", obj.getChargemoney());
			map.put("pay", obj.getPay());
			map.put("unpay", obj.getUnpay());
			map.put("shortlisted", obj.getShortlisted());
			map.put("game", obj.getGame());
			map.put("personshow", obj.getPersonshow());
			map.put("winning", obj.getWinning());
			map.put("edittime", obj.getEdittime());
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
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_levelintegralsource obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("levelid", obj.getLevelid());
		map.put("login", obj.getLogin());
		map.put("upload", obj.getUpload());
		map.put("comments", obj.getComments());
		map.put("uncomments", obj.getUncomments());
		map.put("good", obj.getGood());
		map.put("ungood", obj.getUngood());
		map.put("keep", obj.getKeep());
		map.put("unkeep", obj.getUnkeep());
		map.put("unfocus", obj.getUnfocus());
		map.put("chargemoney", obj.getChargemoney());
		map.put("pay", obj.getPay());
		map.put("unpay", obj.getUnpay());
		map.put("shortlisted", obj.getShortlisted());
		map.put("game", obj.getGame());
		map.put("personshow", obj.getPersonshow());
		map.put("winning", obj.getWinning());
		map.put("edittime", obj.getEdittime());
		map.put("edituserid", obj.getEdituserid());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_levelintegralsource> list) {
		List item = new ArrayList();
		for (Tb_levelintegralsource obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("levelid", obj.getLevelid());
			map.put("login", obj.getLogin());
			map.put("upload", obj.getUpload());
			map.put("comments", obj.getComments());
			map.put("uncomments", obj.getUncomments());
			map.put("good", obj.getGood());
			map.put("ungood", obj.getUngood());
			map.put("keep", obj.getKeep());
			map.put("unkeep", obj.getUnkeep());
			map.put("unfocus", obj.getUnfocus());
			map.put("chargemoney", obj.getChargemoney());
			map.put("pay", obj.getPay());
			map.put("unpay", obj.getUnpay());
			map.put("shortlisted", obj.getShortlisted());
			map.put("game", obj.getGame());
			map.put("personshow", obj.getPersonshow());
			map.put("winning", obj.getWinning());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid());
			item.add(map);
		}
		return item;
	}

}
