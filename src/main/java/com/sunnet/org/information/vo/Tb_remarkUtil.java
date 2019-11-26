package com.sunnet.org.information.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.information.model.Tb_remark;

/**
 * tb_remark 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_remarkUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_remark> list) {
		List item = new ArrayList();
		for (Tb_remark obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("title", obj.getTitle());
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time().getTime());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_user_id() != null) {
				map.put("edit_user", obj.getEdit_user_id().getFdUserName());
			} else {
				map.put("edit_user", "");
			}
			if (obj.getLink_url() != null) {
				map.put("link_url", obj.getLink_url());
			} else {
				map.put("link_url", "");
			}
			map.put("remark", obj.getRemark());
			map.put("istype", obj.getIstype());
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
	public static Map getControllerMap(Tb_remark obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("title", obj.getTitle());
		if (obj.getEdit_time() != null) {
			map.put("edit_time", obj.getEdit_time().getTime());
		} else {
			map.put("edit_time", "");
		}
		if (obj.getEdit_user_id() != null) {
			map.put("edit_user", obj.getEdit_user_id().getFdUserName());
		} else {
			map.put("edit_user", "");
		}
		if (obj.getLink_url() != null) {
			map.put("link_url", obj.getLink_url());
		} else {
			map.put("link_url", "");
		}
		map.put("remark", obj.getRemark());
		map.put("istype", obj.getIstype());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_remark> list) {
		List item = new ArrayList();
		for (Tb_remark obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("title", obj.getTitle());
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time().getTime());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_user_id() != null) {
				map.put("edit_user", obj.getEdit_user_id().getFdUserName());
			} else {
				map.put("edit_user", "");
			}
			if (obj.getLink_url() != null) {
				map.put("link_url", obj.getLink_url());
			} else {
				map.put("link_url", "");
			}
			map.put("remark", obj.getRemark());
			map.put("istype", obj.getIstype());
			item.add(map);
		}
		return item;
	}

}
