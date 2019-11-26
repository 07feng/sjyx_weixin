package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tb_appstartpage;

/**
 * tb_appstartpage 返回数据的加载
 * @author 强强
 *
 */
public class Tb_appstartpageUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_appstartpage> list){
		List item= new ArrayList();
		for (Tb_appstartpage obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("note", obj.getNote());
 map.put("imgurl", obj.getImgurl());
 map.put("linktype", obj.getLinktype());
 map.put("linkurl", obj.getLinkurl());
 map.put("edittime", obj.getEdittime());
 if(obj.getEdituserid()!=null){
	 map.put("edituserid", obj.getEdituserid().getFdUserName());
 }else{
	 map.put("edituserid", "");
 }
 

 map.put("ispublic", obj.getIspublic());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_appstartpage obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("note", obj.getNote());
 map.put("imgurl", obj.getImgurl());
 map.put("linktype", obj.getLinktype());
 map.put("linkurl", obj.getLinkurl());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 map.put("ispublic", obj.getIspublic());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_appstartpage> list) {
		List item = new ArrayList();
		for (Tb_appstartpage obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("note", obj.getNote());
 map.put("imgurl", obj.getImgurl());
 map.put("linktype", obj.getLinktype());
 map.put("linkurl", obj.getLinkurl());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 map.put("ispublic", obj.getIspublic());
			item.add(map);
		}
		return item;
	}

}
