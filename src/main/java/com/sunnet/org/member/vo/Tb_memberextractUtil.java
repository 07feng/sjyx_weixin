package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_memberextract;

/**
 * tb_memberextract 返回数据的加载
 * @author 强强
 *
 */
public class Tb_memberextractUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_memberextract> list){
		List item= new ArrayList();
		for (Tb_memberextract obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("name", obj.getName());
 map.put("account", obj.getAccount());
 map.put("accountname", obj.getAccountname());
/* map.put("memberid", obj.getMemberid());*/
 map.put("extractway", obj.getExtractway());
 map.put("extractamount", obj.getExtractamount());
 map.put("applytime", obj.getApplytime());
 map.put("returntime", obj.getReturntime());
 map.put("extractstatus", obj.getExtractstatus());
 map.put("ipaddress", obj.getIpaddress());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if (obj.getMemberid() != null) {
		map.put("memberid", obj.getMemberid().getId());
		map.put("username", obj.getMemberid().getUsersname());
		map.put("realname", obj.getMemberid().getRealname());
		
	} else {
		map.put("usersname", "");
		map.put("memberid", "");
		map.put("realname","");
	}
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_memberextract obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("name", obj.getName());
 map.put("account", obj.getAccount());
 map.put("accountname", obj.getAccountname());
 map.put("memberid", obj.getMemberid());
 map.put("extractway", obj.getExtractway());
 map.put("extractamount", obj.getExtractamount());
 map.put("applytime", obj.getApplytime());
 map.put("returntime", obj.getReturntime());
 map.put("extractstatus", obj.getExtractstatus());
 map.put("ipaddress", obj.getIpaddress());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if (obj.getMemberid() != null) {
		map.put("member_id", obj.getMemberid().getUsersname());
	} else {
		map.put("usersname", "");
	}
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_memberextract> list) {
		List item = new ArrayList();
		for (Tb_memberextract obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("name", obj.getName());
 map.put("account", obj.getAccount());
 map.put("accountname", obj.getAccountname());
 map.put("memberid", obj.getMemberid());
 map.put("extractway", obj.getExtractway());
 map.put("extractamount", obj.getExtractamount());
 map.put("applytime", obj.getApplytime());
 map.put("returntime", obj.getReturntime());
 map.put("extractstatus", obj.getExtractstatus());
 map.put("ipaddress", obj.getIpaddress());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if (obj.getMemberid() != null) {
		map.put("member_id", obj.getMemberid().getUsersname());
	} else {
		map.put("usersname", "");
	}
			item.add(map);
		}
		return item;
	}

}
