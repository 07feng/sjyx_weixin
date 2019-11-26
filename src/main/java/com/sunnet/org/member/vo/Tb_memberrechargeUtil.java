package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_memberrecharge;

/**
 * tb_memberrecharge 返回数据的加载
 * @author 强强
 *
 */
public class Tb_memberrechargeUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_memberrecharge> list){
		List item= new ArrayList();
		for (Tb_memberrecharge obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("amount", obj.getAmount());
 map.put("way", obj.getWay());
 map.put("memberid", obj.getMemberid());
 map.put("rechargetime", obj.getRechargetime());
 map.put("ipaddress", obj.getIpaddress());
 if (obj.getMemberid() != null) {
		map.put("memberid", obj.getMemberid().getUsersname());
	} else {
		map.put("usersname", "");
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
	public static Map getControllerMap(Tb_memberrecharge obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("amount", obj.getAmount());
 map.put("way", obj.getWay());
 map.put("memberid", obj.getMemberid());
 map.put("rechargetime", obj.getRechargetime());
 map.put("ipaddress", obj.getIpaddress());
 if (obj.getMemberid() != null) {
		map.put("memberid", obj.getMemberid().getUsersname());
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
	public static List getControllerListAll(List<Tb_memberrecharge> list) {
		List item = new ArrayList();
		for (Tb_memberrecharge obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("amount", obj.getAmount());
 map.put("way", obj.getWay());
 map.put("memberid", obj.getMemberid());
 map.put("rechargetime", obj.getRechargetime());
 map.put("ipaddress", obj.getIpaddress());
 if (obj.getMemberid() != null) {
		map.put("memberid", obj.getMemberid().getUsersname());
	} else {
		map.put("usersname", "");
	}
			item.add(map);
		}
		return item;
	}

}
