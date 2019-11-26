package com.sunnet.org.filmfestival.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment;

/**
 * filmfestivalvip_comment 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Filmfestivalvip_commentUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Filmfestivalvip_comment> list) {
		List item = new ArrayList();
		for (Filmfestivalvip_comment obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("fid", obj.getFid());
			if(obj.getVipid()!=null){
				map.put("vipid", obj.getVipid().getTitel());
			}else{
				map.put("vipid", "");
			}
			map.put("comments", obj.getComments());
			if (obj.getComment_time() != null) {
				map.put("comment_time", obj.getComment_time());
			} else {
				map.put("comment_time", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_userid() != null) {
				map.put("edit_userid", obj.getEdit_userid().getFdUserName());
			} else {
				map.put("edit_userid", "");
			}
			if (obj.getIs_public() != null) {
				map.put("is_public", obj.getIs_public());
			} else {
				map.put("is_public", "");
			}
			if (obj.getMember_id() != null) {
				 map.put("memberid", obj.getMember_id().getId());
				 map.put("usersname", obj.getMember_id().getUsersname());
			 }else{
				 map.put("memberid", "");
				 map.put("usersname","");
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
	public static Map getControllerMap(Filmfestivalvip_comment obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("fid", obj.getFid());
		map.put("vipid", obj.getVipid());
		map.put("comments", obj.getComments());
		if (obj.getComment_time() != null) {
			map.put("comment_time", obj.getComment_time());
		} else {
			map.put("comment_time", "");
		}
		if (obj.getEdit_time() != null) {
			map.put("edit_time", obj.getEdit_time());
		} else {
			map.put("edit_time", "");
		}
		if (obj.getEdit_userid() != null) {
			map.put("edit_userid", obj.getEdit_userid());
		} else {
			map.put("edit_userid", "");
		}
		if (obj.getIs_public() != null) {
			map.put("is_public", obj.getIs_public());
		} else {
			map.put("is_public", "");
		}
		if (obj.getMember_id() != null) {
			map.put("member_fdId", obj.getMember_id());
		} else {
			map.put("member_fdId", "");
		}
		map.put("member_id", obj.getMember_id());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Filmfestivalvip_comment> list) {
		List item = new ArrayList();
		for (Filmfestivalvip_comment obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("fid", obj.getFid());
			map.put("vipid", obj.getVipid());
			map.put("comments", obj.getComments());
			if (obj.getComment_time() != null) {
				map.put("comment_time", obj.getComment_time());
			} else {
				map.put("comment_time", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_userid() != null) {
				map.put("edit_userid", obj.getEdit_userid());
			} else {
				map.put("edit_userid", "");
			}
			if (obj.getIs_public() != null) {
				map.put("is_public", obj.getIs_public());
			} else {
				map.put("is_public", "");
			}
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id());
			} else {
				map.put("member_fdId", "");
			}
			map.put("member_id", obj.getMember_id());
			item.add(map);
		}
		return item;
	}

}
