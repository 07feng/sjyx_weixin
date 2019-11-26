package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.SysLogItem;

/**
 * sysLogItem 返回数据的加载
 * @author 强强
 *
 */
public class SysLogItemUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<SysLogItem> list){
		List item= new ArrayList();
		for (SysLogItem obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("fdUserName", obj.getFdUserName());
 map.put("fdContent", obj.getFdContent());
 map.put("fdCreateDate", obj.getFdCreateDate());
 map.put("fdIp", obj.getFdIp());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(SysLogItem obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("fdUserName", obj.getFdUserName());
 map.put("fdContent", obj.getFdContent());
 map.put("fdCreateDate", obj.getFdCreateDate());
 map.put("fdIp", obj.getFdIp());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<SysLogItem> list) {
		List item = new ArrayList();
		for (SysLogItem obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("fdUserName", obj.getFdUserName());
 map.put("fdContent", obj.getFdContent());
 map.put("fdCreateDate", obj.getFdCreateDate());
 map.put("fdIp", obj.getFdIp());
			item.add(map);
		}
		return item;
	}

}
