package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_membermessagesteup;

/**
 * tb_membermessagesteup 返回数据的加载
 * @author 强强
 *
 */
public class Tb_membermessagesteupUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_membermessagesteup> list){
		List item= new ArrayList();
		for (Tb_membermessagesteup obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("isaudit", obj.getIsaudit());
 map.put("timebetween", obj.getTimebetween());
 map.put("disablewords", obj.getDisablewords());
 map.put("replacewords", obj.getReplacewords());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_membermessagesteup obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("isaudit", obj.getIsaudit());
 map.put("timebetween", obj.getTimebetween());
 map.put("disablewords", obj.getDisablewords());
 map.put("replacewords", obj.getReplacewords());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_membermessagesteup> list) {
		List item = new ArrayList();
		for (Tb_membermessagesteup obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("isaudit", obj.getIsaudit());
 map.put("timebetween", obj.getTimebetween());
 map.put("disablewords", obj.getDisablewords());
 map.put("replacewords", obj.getReplacewords());
			item.add(map);
		}
		return item;
	}

}
