package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_filelabel;

/**
 * tb_filelabel 返回数据的加载
 * @author 强强
 *
 */
public class Tb_filelabelUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_filelabel> list){
		List item= new ArrayList();
		for (Tb_filelabel obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_filelabel obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_filelabel> list) {
		List item = new ArrayList();
		for (Tb_filelabel obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
			item.add(map);
		}
		return item;
	}

}
