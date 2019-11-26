package com.sunnet.org.view.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.view.model.vie_vippay;

/**
 * vie_vippay 返回数据的加载
 * @author 强强
 *
 */
public class vie_vippayUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<vie_vippay> list){
		List item= new ArrayList();
		for (vie_vippay obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("usersname", obj.getUsersname());
 map.put("headimg", obj.getHeadimg());
 map.put("levelid", obj.getLevelid());
 map.put("levelname", obj.getLevelname());
 map.put("memberid", obj.getMemberid());
 map.put("cpaynum", obj.getCpaynum());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(vie_vippay obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("usersname", obj.getUsersname());
 map.put("headimg", obj.getHeadimg());
 map.put("levelid", obj.getLevelid());
 map.put("levelname", obj.getLevelname());
 map.put("memberid", obj.getMemberid());
 map.put("cpaynum", obj.getCpaynum());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<vie_vippay> list) {
		List item = new ArrayList();
		for (vie_vippay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("usersname", obj.getUsersname());
 map.put("headimg", obj.getHeadimg());
 map.put("levelid", obj.getLevelid());
 map.put("levelname", obj.getLevelname());
 map.put("memberid", obj.getMemberid());
 map.put("cpaynum", obj.getCpaynum());
			item.add(map);
		}
		return item;
	}

}
