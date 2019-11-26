package com.sunnet.org.filmfestival.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.filmfestival.model.Filmfestivalvip_good;

/**
 * filmfestivalvip_good 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Filmfestivalvip_goodUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Filmfestivalvip_good> list) {
		List item = new ArrayList();
		for (Filmfestivalvip_good obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("vipid", obj.getVipid());
			if(obj.getMemberid()!=null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
			}else{
				map.put("memberid", "");
				map.put("usersname","");
				map.put("headimg", "");
			}
			
			map.put("goodcound", obj.getGoodcound());
			map.put("deviceid", obj.getDeviceid());
			map.put("todaytime", obj.getTodaytime());
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
	public static Map getControllerMap(Filmfestivalvip_good obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("vipid", obj.getVipid());
		if(obj.getMemberid()!=null){
			map.put("memberid", obj.getMemberid().getId());
			map.put("usersname", obj.getMemberid().getUsersname());
			map.put("headimg", obj.getMemberid().getHeadimg());
		}else{
			map.put("memberid", "");
			map.put("usersname","");
			map.put("headimg", "");
		}
		map.put("memberid", obj.getMemberid());
		map.put("goodcound", obj.getGoodcound());
		map.put("deviceid", obj.getDeviceid());
		map.put("todaytime", obj.getTodaytime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Filmfestivalvip_good> list) {
		List item = new ArrayList();
		for (Filmfestivalvip_good obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("vipid", obj.getVipid());
			if(obj.getMemberid()!=null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
			}else{
				map.put("memberid", "");
				map.put("usersname","");
				map.put("headimg", "");
			}
			map.put("memberid", obj.getMemberid());
			map.put("goodcound", obj.getGoodcound());
			map.put("deviceid", obj.getDeviceid());
			map.put("todaytime", obj.getTodaytime());
			item.add(map);
		}
		return item;
	}

}
