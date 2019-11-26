package com.sunnet.org.pay.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.org.pay.model.Tb_rechargerecord;

/**
 * tb_rechargerecord 返回数据的加载
 * @author 强强
 *
 */
public class Tb_rechargerecordUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_rechargerecord> list){
		List item= new ArrayList();
		for (Tb_rechargerecord obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("rrid", obj.getRrid());
			map.put("orno", obj.getOrno());
			map.put("alino", obj.getAlino());
			map.put("rramount", obj.getRramount());
			map.put("rrstatus", obj.getRrstatus());
			map.put("way", obj.getWay());
			if(obj.getMemberid()!=null){
				map.put("memberid",obj.getMemberid().getId());
				map.put("memberusername",obj.getMemberid().getUsersname());
			}else{
				map.put("memberid","");
				map.put("memberusername","");
			}
			map.put("rechargetime", obj.getRechargetime());
			map.put("ipaddress",obj.getIpaddress());
			map.put("reamount", obj.getReamount());
			map.put("successtime", obj.getSuccesstime());
			map.put("finishtime", obj.getFinishtime());
			map.put("seller", obj.getSeller());
			
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_rechargerecord obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("rrid", obj.getRrid());
		map.put("orno", obj.getOrno());
		map.put("alino", obj.getAlino());
		map.put("rramount", obj.getRramount());
		map.put("rrstatus", obj.getRrstatus());
		map.put("way", obj.getWay());
		if(obj.getMemberid()!=null){
			map.put("memberid",obj.getMemberid().getId());
			map.put("memberusername",obj.getMemberid().getUsersname());
		}else{
			map.put("memberid","");
			map.put("memberusername","");
		}
		map.put("rechargetime", obj.getRechargetime());
		map.put("ipaddress",obj.getIpaddress());
		map.put("reamount", obj.getReamount());
		map.put("successtime", obj.getSuccesstime());
		map.put("finishtime", obj.getFinishtime());
		map.put("seller", obj.getSeller());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_rechargerecord> list) {
		List item = new ArrayList();
		for (Tb_rechargerecord obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("rrid", obj.getRrid());
			map.put("orno", obj.getOrno());
			map.put("alino", obj.getAlino());
			map.put("rramount", obj.getRramount());
			map.put("rrstatus", obj.getRrstatus());
			map.put("way", obj.getWay());
			if(obj.getMemberid()!=null){
				map.put("memberid",obj.getMemberid().getId());
				map.put("memberusername",obj.getMemberid().getUsersname());
			}else{
				map.put("memberid","");
				map.put("memberusername","");
			}
			map.put("rechargetime", obj.getRechargetime());
			map.put("ipaddress",obj.getIpaddress());
			map.put("reamount", obj.getReamount());
			map.put("successtime", obj.getSuccesstime());
			map.put("finishtime", obj.getFinishtime());
			map.put("seller", obj.getSeller());
			item.add(map);
		}
		return item;
	}

}
