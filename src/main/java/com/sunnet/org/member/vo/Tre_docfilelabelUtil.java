package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tre_docfilelabel;

/**
 * tre_docfilelabel 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tre_docfilelabelUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tre_docfilelabel> list) {
		List item = new ArrayList();
		for (Tre_docfilelabel obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getLabelid() != null) {
				map.put("labelid", obj.getLabelid().getId());
				map.put("name", obj.getLabelid().getName());
			} else {
				map.put("labelid", "");
				map.put("name", "");
			}
			if (obj.getDocid() != null) {
				map.put("docid", obj.getDocid().getId());
			} else {
				map.put("docid", "");
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
	public static Map getControllerMap(Tre_docfilelabel obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("labelid", obj.getLabelid());
		map.put("docid", obj.getDocid());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tre_docfilelabel> list) {
		List item = new ArrayList();
		for (Tre_docfilelabel obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("labelid", obj.getLabelid());
			map.put("docid", obj.getDocid());
			item.add(map);
		}
		return item;
	}

}
