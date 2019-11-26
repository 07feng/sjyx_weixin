package com.sunnet.org.information.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.information.model.Tb_shuffling;

/**
 * tb_shuffling 返回数据的加载
 * @author 强强
 *
 */
public class Tb_shufflingUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_shuffling> list){
		List item= new ArrayList();
		for (Tb_shuffling obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("title", obj.getTitle());
 map.put("linktype", obj.getLinktype());
 map.put("link", obj.getLink());
 map.put("imgurl", obj.getImgurl());
 map.put("sort", obj.getSort());
 map.put("isshow", obj.getIsshow());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 
 if(obj.getEdituserid()!=null){
	 map.put("edituserid", obj.getEdituserid().getFdUserName());
 }else{
	 map.put("fdUserName", "");
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
	public static Map getControllerMap(Tb_shuffling obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("title", obj.getTitle());
 map.put("linktype", obj.getLinktype());
 map.put("link", obj.getLink());
 map.put("imgurl", obj.getImgurl());
 map.put("sort", obj.getSort());
 map.put("isshow", obj.getIsshow());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if(obj.getEdituserid()!=null){
	 map.put("edituserid", obj.getEdituserid().getFdUserName());
 }else{
	 map.put("fdUserName", "");
 } 
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_shuffling> list) {
		List item = new ArrayList();
		for (Tb_shuffling obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("title", obj.getTitle());
 map.put("linktype", obj.getLinktype());
 map.put("link", obj.getLink());
 map.put("imgurl", obj.getImgurl());
 map.put("sort", obj.getSort());
 map.put("isshow", obj.getIsshow());
 map.put("edittime", obj.getEdittime());
 map.put("edituserid", obj.getEdituserid());
 if(obj.getEdituserid()!=null){
	 map.put("edituserid", obj.getEdituserid().getFdUserName());
 }else{
	 map.put("fdUserName", "");
 } 
	 
			item.add(map);
		}
		return item;
	}

}
