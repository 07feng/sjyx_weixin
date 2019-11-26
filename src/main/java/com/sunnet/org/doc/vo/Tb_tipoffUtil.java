package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tb_tipoff;

/**
 * tb_tipoff 返回数据的加载
 * 
 * @author  
 *
 */
public class Tb_tipoffUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_tipoff> list) {
		List item = new ArrayList();
		for (Tb_tipoff obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id().getUsersname());
			} else {
				map.put("member_fdId", "");
			}
			 
			if (obj.getDoc_id() != null) {
				map.put("doc_Id", obj.getDoc_id().getId());
				map.put("doc_fdId", obj.getDoc_id().getDoctitle());
				map.put("doc_filepath", obj.getDoc_id().getFilepath());
				if(obj.getDoc_id().getMemberid()!=null){
					map.put("doc_memberid", obj.getDoc_id().getMemberid().getUsersname());
				}
				
			} else {
				map.put("doc_fdId", "");
				map.put("doc_filepath","");
			}
			 
			map.put("cause", obj.getCause());
			map.put("causetime", obj.getCausetime());
			map.put("status", obj.getStatus());
			if (obj.getUser_id() != null) {
				map.put("user_fdId", obj.getUser_id().getFdUserName());
			} else {
				map.put("user_fdId", "");
			}
		 
			map.put("usertime", obj.getUsertime());
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
	public static Map getControllerMap(Tb_tipoff obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		if (obj.getMember_id() != null) {
			map.put("member_fdId", obj.getMember_id().getUsersname());
		} else {
			map.put("member_fdId", "");
		}
		map.put("member_id", obj.getMember_id());
		if (obj.getDoc_id() != null) {
			map.put("doc_fdId", obj.getDoc_id().getDoctitle());
			map.put("doc_filepath", obj.getDoc_id().getFilepath());
		} else {
			map.put("doc_fdId", "");
			map.put("doc_filepath","");
		}
		 
		map.put("cause", obj.getCause());
		map.put("causetime", obj.getCausetime());
		map.put("status", obj.getStatus());
		if (obj.getUser_id() != null) {
			map.put("user_fdId", obj.getUser_id().getFdUserName());
		} else {
			map.put("user_fdId", "");
		}
		map.put("user_id", obj.getUser_id());
		map.put("usertime", obj.getUsertime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_tipoff> list) {
		List item = new ArrayList();
		for (Tb_tipoff obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id().getUsersname());
			} else {
				map.put("member_fdId", "");
			}
			map.put("member_id", obj.getMember_id());
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getDoctitle());
				map.put("doc_filepath", obj.getDoc_id().getFilepath());
			} else {
				map.put("doc_fdId", "");
				map.put("doc_filepath","");
			}
			 
			map.put("cause", obj.getCause());
			map.put("causetime", obj.getCausetime());
			map.put("status", obj.getStatus());
			if (obj.getUser_id() != null) {
				map.put("user_fdId", obj.getUser_id().getFdUserName());
			} else {
				map.put("user_fdId", "");
			}
			 
			map.put("usertime", obj.getUsertime());
			item.add(map);
		}
		return item;
	}

}
