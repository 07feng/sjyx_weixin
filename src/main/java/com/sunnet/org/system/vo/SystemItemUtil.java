package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.SystemItem;

/**
 * systemItem 返回数据的加载
 * @author 强强
 *
 */
public class SystemItemUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<SystemItem> list){
		List item= new ArrayList();
		for (SystemItem obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("fdId", obj.getFdId());
 map.put("systemImg", obj.getSystemImg());
 map.put("systemImgLog", obj.getSystemImgLog());
 map.put("systemTitle", obj.getSystemTitle());
 map.put("systemVersion", obj.getSystemVersion());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(SystemItem obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("fdId", obj.getFdId());
 map.put("systemImg", obj.getSystemImg());
 map.put("systemImgLog", obj.getSystemImgLog());
 map.put("systemTitle", obj.getSystemTitle());
 map.put("systemVersion", obj.getSystemVersion());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<SystemItem> list) {
		List item = new ArrayList();
		for (SystemItem obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("fdId", obj.getFdId());
 map.put("systemImg", obj.getSystemImg());
 map.put("systemImgLog", obj.getSystemImgLog());
 map.put("systemTitle", obj.getSystemTitle());
 map.put("systemVersion", obj.getSystemVersion());
			item.add(map);
		}
		return item;
	}

}
