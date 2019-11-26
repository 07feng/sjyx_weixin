package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tb_docgood;

/**
 * tb_docgood 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_docgoodUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_docgood> list) {
		List item = new ArrayList();
		for (Tb_docgood obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("docid", obj.getDocid());  
			map.put("memberid", obj.getMemberid());
			map.put("goodcound", obj.getGoodcound());
			map.put("deviceid", obj.getDeviceid());
			map.put("todaytime", obj.getTodaytime());
			if(obj.getMemberid() != null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
			}else{
				map.put("memberid", "");
				map.put("usersname", "");
				map.put("headimg", "");
			}
			if(obj.getDocid() != null){
				map.put("docid", obj.getDocid().getId());
				map.put("doctitle", obj.getDocid().getDoctitle());
				map.put("filepath", obj.getDocid().getFilepath());
			}else{
				map.put("docid", "");
			}
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
	public static Map getControllerMap(Tb_docgood obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("docid", obj.getDocid());
		map.put("memberid", obj.getMemberid());
		map.put("goodcound", obj.getGoodcound());
		map.put("deviceid", obj.getDeviceid());
		map.put("todaytime", obj.getTodaytime());
		if(obj.getMemberid() != null){
			map.put("memberid", obj.getMemberid().getId());
			map.put("usersname", obj.getMemberid().getUsersname());
			map.put("headimg", obj.getMemberid().getHeadimg());
		}else{
			map.put("memberid", "");
			map.put("usersname", "");
			map.put("headimg", "");
		}
		if(obj.getDocid() != null){
			map.put("docid", obj.getDocid().getId());
			map.put("doctitle", obj.getDocid().getDoctitle());
			map.put("filepath", obj.getDocid().getFilepath());
		}else{
			map.put("docid", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tb_docgood> list) {
		List item = new ArrayList();
		for (Tb_docgood obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("docid", obj.getDocid());
			map.put("memberid", obj.getMemberid());
			map.put("goodcound", obj.getGoodcound());
			map.put("deviceid", obj.getDeviceid());
			map.put("todaytime", obj.getTodaytime());
			if(obj.getMemberid() != null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
			}else{
				map.put("memberid", "");
				map.put("usersname", "");
				map.put("headimg", "");
			}
			if(obj.getDocid() != null){
				map.put("docid", obj.getDocid().getId());
				map.put("doctitle", obj.getDocid().getDoctitle());
				map.put("filepath", obj.getDocid().getFilepath());
			}else{
				map.put("docid", "");
			}
			item.add(map);
		}
		return item;
	}

}
