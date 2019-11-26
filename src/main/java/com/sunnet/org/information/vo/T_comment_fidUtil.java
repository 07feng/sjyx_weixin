package com.sunnet.org.information.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.information.model.T_comment_fid;

/**
 * t_comment_fid 返回数据的加载
 * 
 * @author 强强
 *
 */
public class T_comment_fidUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<T_comment_fid> list) {
		List item = new ArrayList();
		for (T_comment_fid obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getFd_doccomment_id() > 0) {
				map.put("fd_doccomment", obj.getFd_doccomment_id());
			} else {
				map.put("fd_doccomment", "");
			}
			if (obj.getFd_member_id() != null) {
				map.put("fid_member_id", obj.getFd_member_id().getId());
				map.put("fid_usersname", obj.getFd_member_id().getUsersname());
				if(obj.getFd_member_id().getHeadimg()!=null){
					 map.put("fid_headimg", obj.getFd_member_id().getHeadimg());
				 }else{
					 map.put("fid_headimg","");
				 }
				if (obj.getFd_member_id().getLevelid() != null) {
					map.put("fid_levelid", obj.getFd_member_id().getLevelid()
							.getId());
					map.put("fid_levelname", obj.getFd_member_id().getLevelid()
							.getLevelname());
				} else {
					map.put("fid_levelid", "");
					map.put("fid_levelname", "");
				}
			} else {
				map.put("fid_member_id", "");
				map.put("fid_usersname", "");
				 map.put("fid_headimg","");
			}
			if (obj.getFd_c_member_id() != null) {
				map.put("fd_c_memberid", obj.getFd_c_member_id().getId());
				map.put("fd_c_usersname", obj.getFd_c_member_id().getUsersname());
				if (obj.getFd_c_member_id().getLevelid() != null) {
					map.put("fd_c_levelid", obj.getFd_c_member_id()
							.getLevelid().getId());
					map.put("fd_c_levelname", obj.getFd_c_member_id()
							.getLevelid().getLevelname());
				} else {
					map.put("fd_c_levelid", "");
					map.put("fd_c_levelname", "");
				}
			} else {
				map.put("fd_c_memberid", "");
				map.put("fd_c_usersname", "");
				map.put("fd_c_levelid", "");
				map.put("fd_c_levelname", "");
			}
			if (obj.getFd_comment_time() != null) {
				map.put("fd_comment_time", obj.getFd_comment_time());
			} else {
				map.put("fd_comment_time", "");
			}
			if (obj.getFd_comment_note() != null) {
				map.put("fd_comment", obj.getFd_comment_note());
			} else {
				map.put("fd_comment", "");
			}
			item.add(map);
		}
		return item;
	}
	public static List getcomList(List<T_comment_fid> list) {
		List item = new ArrayList();
		for (T_comment_fid obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			if (obj.getFd_doccomment_id() > 0) {// 评论编号
				map.put("fd_doccomment", obj.getFd_doccomment_id());
			} else {
				map.put("fd_doccomment", "");
			}
			/*if (obj.getFd_member_id() != null) {
				map.put("fid_member_id", obj.getFd_member_id().getId());
				map.put("fid_usersname", obj.getFd_member_id().getUsersname());
			} else {
				map.put("fid_member_id", "");
				map.put("fid_usersname", "");
			}
			if (obj.getFd_c_member_id() != null) {
				map.put("fd_c_memberid", obj.getFd_c_member_id().getId());
				map.put("fd_c_usersname", obj.getFd_c_member_id().getUsersname());
				if (obj.getFd_c_member_id().getLevelid() != null) {
					map.put("fd_c_levelid", obj.getFd_c_member_id()
							.getLevelid().getId());
					map.put("fd_c_levelname", obj.getFd_c_member_id()
							.getLevelid().getLevelname());
				} else {
					map.put("fd_c_levelid", "");
					map.put("fd_c_levelname", "");
				}
			} else {
				map.put("fd_c_memberid", "");
				map.put("fd_c_usersname", "");
				map.put("fd_c_levelid", "");
				map.put("fd_c_levelname", "");
			}*/
			if (obj.getFd_comment_time() != null) {
				map.put("fd_comment_time", obj.getFd_comment_time());
			} else {
				map.put("fd_comment_time", "");
			}
			if (obj.getFd_comment_note() != null) {
				map.put("fd_comment", obj.getFd_comment_note());
			} else {
				map.put("fd_comment", "");
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
	public static Map getControllerMap(T_comment_fid obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		if (obj.getFd_doccomment_id() > 0) {
			map.put("fd_doccomment", obj.getFd_doccomment_id());
		} else {
			map.put("fd_doccomment", "");
		}
		if (obj.getFd_member_id() != null) {
			map.put("member_id", obj.getFd_member_id().getId());
			map.put("usersname", obj.getFd_member_id().getUsersname());
		} else {
			map.put("member_id", "");
			map.put("usersname", "");
		}
		if (obj.getFd_c_member_id() != null) {
			map.put("fd_c", obj.getFd_c_member_id().getId());
			map.put("fd_c_usersname", obj.getFd_member_id().getUsersname());
		} else {
			map.put("fd_c", "");
			map.put("fd_c_usersname", "");
		}
		if (obj.getFd_comment_time() != null) {
			map.put("fd_comment", obj.getFd_comment_time());
		} else {
			map.put("fd_comment", "");
		}
		if (obj.getFd_comment_note() != null) {
			map.put("fd_comment", obj.getFd_comment_note());
		} else {
			map.put("fd_comment", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<T_comment_fid> list) {
		List item = new ArrayList();
		for (T_comment_fid obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getFd_doccomment_id() > 0) {
				map.put("fd_doccomment", obj.getFd_doccomment_id());
			} else {
				map.put("fd_doccomment", "");
			}
			if (obj.getFd_member_id() != null) {
				map.put("fd_member", obj.getFd_member_id().getId());
			} else {
				map.put("fd_member", "");
			}
			if (obj.getFd_c_member_id() != null) {
				map.put("fd_c", obj.getFd_c_member_id().getId());
			} else {
				map.put("fd_c", "");
			}
			if (obj.getFd_comment_time() != null) {
				map.put("fd_comment", obj.getFd_comment_time());
			} else {
				map.put("fd_comment", "");
			}
			if (obj.getFd_comment_note() != null) {
				map.put("fd_comment", obj.getFd_comment_note());
			} else {
				map.put("fd_comment", "");
			}
			item.add(map);
		}
		return item;
	}

}
