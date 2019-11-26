package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.Tb_package;

/**
 * tb_package 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_packageUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_package> list) {
		List item = new ArrayList();
		for (Tb_package obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getPackage_name() != null) {
				map.put("package_name", obj.getPackage_name());
			} else {
				map.put("package_name", "");
			}
			if (obj.getPackage_url() != null) {
				map.put("package_url", obj.getPackage_url());
			} else {
				map.put("package_url", "");
			}
			if (obj.getPackage_version() != null) {
				map.put("package_version", obj.getPackage_version());
			} else {
				map.put("package_version", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_user_id() != null) {
				map.put("edit_user", obj.getEdit_user_id().getFdUserName());
			} else {
				map.put("edit_user", "");
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
	public static Map getControllerMap(Tb_package obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		if (obj.getPackage_name() != null) {
			map.put("package_name", obj.getPackage_name());
		} else {
			map.put("package_name", "");
		}
		if (obj.getPackage_url() != null) {
			map.put("package_url", obj.getPackage_url());
		} else {
			map.put("package_url", "");
		}
		if (obj.getPackage_version() != null) {
			map.put("package_version", obj.getPackage_version());
		} else {
			map.put("package_version", "");
		}
		if (obj.getEdit_time() != null) {
			map.put("edit_time", obj.getEdit_time());
		} else {
			map.put("edit_time", "");
		}
		if (obj.getEdit_user_id() != null) {
			map.put("edit_user", obj.getEdit_user_id().getFdUserName());
		} else {
			map.put("edit_user", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_package> list) {
		List item = new ArrayList();
		for (Tb_package obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getPackage_name() != null) {
				map.put("package_name", obj.getPackage_name());
			} else {
				map.put("package_name", "");
			}
			if (obj.getPackage_url() != null) {
				map.put("package_url", obj.getPackage_url());
			} else {
				map.put("package_url", "");
			}
			if (obj.getPackage_version() != null) {
				map.put("package_version", obj.getPackage_version());
			} else {
				map.put("package_version", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_user_id() != null) {
				map.put("edit_user", obj.getEdit_user_id().getFdUserName());
			} else {
				map.put("edit_user", "");
			}
			item.add(map);
		}
		return item;
	}

}
