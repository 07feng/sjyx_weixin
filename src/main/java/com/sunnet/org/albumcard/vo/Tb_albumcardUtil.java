package com.sunnet.org.albumcard.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.albumcard.model.Tb_albumcard;

/**
 * tb_albumcard 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_albumcardUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_albumcard> list) {
		List item = new ArrayList();
		for (Tb_albumcard obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			if (obj.getFd_id() != null) {
				map.put("fd_fdId", obj.getFd_id());
			} else {
				map.put("fd_fdId", "");
			}
			if (obj.getPay_money() != null) {
				map.put("pay_money", obj.getPay_money());
			} else {
				map.put("pay_money", "");
			}
			if (obj.getFd_name() != null) {
				map.put("fd_name", obj.getFd_name());
			} else {
				map.put("fd_name", "");
			}
			if (obj.getFd_type() != null) {
				map.put("fd_type", obj.getFd_type());
			} else {
				map.put("fd_type", "");
			}
			if (obj.getFd_urlimg() != null) {
				map.put("fd_urlimg", obj.getFd_urlimg());
			} else {
				map.put("fd_urlimg", "");
			}
			if (obj.getFd_number() >0) {
				map.put("fd_number", obj.getFd_number());
			} else {
				map.put("fd_number", 0);
			}
			if (obj.getFd_userid() != null) {
				map.put("fd_userid", obj.getFd_userid().getFdId());
				map.put("fd_usersname", obj.getFd_userid().getFdUserName());
			} else {
				map.put("fd_userid", "");
				map.put("fd_usersname","");
			}
			map.put("fd_usertime", obj.getFd_usertime());
			map.put("fd_isedit", obj.getFd_isedit());
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
	public static Map getControllerMap(Tb_albumcard obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		if (obj.getFd_id() != null) {
			map.put("fd_fdId", obj.getFd_id());
		} else {
			map.put("fd_fdId", "");
		}
		if (obj.getPay_money() != null) {
			map.put("pay_money", obj.getPay_money());
		} else {
			map.put("pay_money", "");
		}
		if (obj.getFd_name() != null) {
			map.put("fd_name", obj.getFd_name());
		} else {
			map.put("fd_name", "");
		}
		if (obj.getFd_type() != null) {
			map.put("fd_type", obj.getFd_type());
		} else {
			map.put("fd_type", "");
		}
		if (obj.getFd_urlimg() != null) {
			map.put("fd_urlimg", obj.getFd_urlimg());
		} else {
			map.put("fd_urlimg", "");
		}
		if (obj.getFd_number() >0) {
			map.put("fd_number", obj.getFd_number());
		} else {
			map.put("fd_number", 0);
		}
		if (obj.getFd_userid() != null) {
			map.put("fd_userid", obj.getFd_userid().getFdId());
			map.put("fd_usersname", obj.getFd_userid().getFdUserName());
		} else {
			map.put("fd_userid", "");
		}
		map.put("fd_usertime", obj.getFd_usertime());
		map.put("fd_isedit", obj.getFd_isedit());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tb_albumcard> list) {
		List item = new ArrayList();
		for (Tb_albumcard obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			if (obj.getFd_id() != null) {
				map.put("fd_fdId", obj.getFd_id());
			} else {
				map.put("fd_fdId", "");
			}
			if (obj.getPay_money() != null) {
				map.put("pay_money", obj.getPay_money());
			} else {
				map.put("pay_money", "");
			}
			if (obj.getFd_name() != null) {
				map.put("fd_name", obj.getFd_name());
			} else {
				map.put("fd_name", "");
			}
			if (obj.getFd_type() != null) {
				map.put("fd_type", obj.getFd_type());
			} else {
				map.put("fd_type", "");
			}
			if (obj.getFd_urlimg() != null) {
				map.put("fd_urlimg", obj.getFd_urlimg());
			} else {
				map.put("fd_urlimg", "");
			}
			if (obj.getFd_number() >0) {
				map.put("fd_number", obj.getFd_number());
			} else {
				map.put("fd_number", 0);
			}
			if (obj.getFd_userid() != null) {
				map.put("fd_userid", obj.getFd_userid().getFdId());
				map.put("fd_usersname", obj.getFd_userid().getFdUserName());
			} else {
				map.put("fd_userid", "");
			}
			map.put("fd_usertime", obj.getFd_usertime());
			map.put("fd_isedit", obj.getFd_isedit());
			item.add(map);
		}
		return item;
	}

}
