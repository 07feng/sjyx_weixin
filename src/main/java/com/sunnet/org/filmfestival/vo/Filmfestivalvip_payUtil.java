package com.sunnet.org.filmfestival.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.filmfestival.model.Filmfestivalvip_pay;

/**
 * filmfestivalvip_pay 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Filmfestivalvip_payUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Filmfestivalvip_pay> list) {
		List item = new ArrayList();
		for (Filmfestivalvip_pay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("membername", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
				if (obj.getMemberid().getLevelid() != null) {
					map.put("levelid", obj.getMemberid().getLevelid().getId());
					map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
				} else {
					map.put("levelid", "");
					map.put("levelname", "");
				}
			} else {
				map.put("memberid", "");
				map.put("membername", "");
				map.put("headimg", "");
				map.put("levelid", "");
				map.put("levelname", "");
			}
			if(null!=obj.getVipid()){
				map.put("vipid", obj.getVipid().getId());
				map.put("viptitel", obj.getVipid().getTitel());
			}else{
				map.put("vipid", "");
				map.put("viptitel", "");
			}
			
			
			map.put("payamount", obj.getPayamount());
			map.put("addtime", obj.getAddtime());
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
	public static Map getControllerMap(Filmfestivalvip_pay obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("memberid", obj.getMemberid());
		map.put("vipid", obj.getVipid());
		map.put("payamount", obj.getPayamount());
		map.put("addtime", obj.getAddtime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Filmfestivalvip_pay> list) {
		List item = new ArrayList();
		for (Filmfestivalvip_pay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("memberid", obj.getMemberid());
			map.put("vipid", obj.getVipid());
			map.put("payamount", obj.getPayamount());
			map.put("addtime", obj.getAddtime());
			item.add(map);
		}
		return item;
	}

}
