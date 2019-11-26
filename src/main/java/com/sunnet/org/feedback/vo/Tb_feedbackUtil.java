package com.sunnet.org.feedback.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.feedback.model.Tb_feedback;

/**
 * tb_feedback 返回数据的加载
 * @author  
 *
 */
public class Tb_feedbackUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_feedback> list){
		List item= new ArrayList();
		for (Tb_feedback obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("id", obj.getId());
			map.put("feedbackinfo", obj.getFeedbackinfo());
			map.put("addtime", obj.getAddtime());
			map.put("feedbackstatus", obj.getFeedbackstatus());
			map.put("edittime", obj.getEdittime());
			if( obj.getEdituserid()!=null){
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			}else{
				map.put("edituserid", "");
			}
			 
			if(obj.getMemberid()!=null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
			}else{
				map.put("memberid","");
				map.put("usersname","");
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
	public static Map getControllerMap(Tb_feedback obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", obj.getId());
		map.put("feedbackinfo", obj.getFeedbackinfo());
		map.put("addtime", obj.getAddtime());
		map.put("feedbackstatus", obj.getFeedbackstatus());
		map.put("edittime", obj.getEdittime());
		if( obj.getEdituserid()!=null){
			map.put("edituserid", obj.getEdituserid().getFdUserName());
		}else{
			map.put("edituserid", "");
		}
		if(obj.getMemberid()!=null){
			map.put("memberid", obj.getMemberid().getId());
			map.put("usersname", obj.getMemberid().getUsersname());
		}else{
			map.put("memberid","");
			map.put("usersname","");
		}
		
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_feedback> list) {
		List item = new ArrayList();
		for (Tb_feedback obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			map.put("feedbackinfo", obj.getFeedbackinfo());
			map.put("addtime", obj.getAddtime());
			map.put("feedbackstatus", obj.getFeedbackstatus());
			map.put("edittime", obj.getEdittime());
			if( obj.getEdituserid()!=null){
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			}else{
				map.put("edituserid", "");
			}
			if(obj.getMemberid()!=null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
			}else{
				map.put("memberid","");
				map.put("usersname","");
			}
			
			item.add(map);
		}
		return item;
	}

}
