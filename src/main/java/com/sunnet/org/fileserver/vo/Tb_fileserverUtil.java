package com.sunnet.org.fileserver.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.fileserver.model.Tb_fileserver;

/**
 * tb_fileserver 返回数据的加载
 * @author 强强
 *
 */
public class Tb_fileserverUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_fileserver> list){
		List item= new ArrayList();
		for (Tb_fileserver obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("fId", obj.getFId());
 map.put("serverName", obj.getServerName());
 map.put("serverUrl", obj.getServerUrl());
 map.put("serverIp", obj.getServerIp());
 map.put("serverStatus", obj.getServerStatus());
 map.put("serverCapacity", obj.getServerCapacity());
 map.put("serverRemainingCapacity", obj.getServerRemainingCapacity());
 map.put("serverPhysicalPath", obj.getServerPhysicalPath());
 map.put("serverVirtualDirectory", obj.getServerVirtualDirectory());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_fileserver obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("fId", obj.getFId());
 map.put("serverName", obj.getServerName());
 map.put("serverUrl", obj.getServerUrl());
 map.put("serverIp", obj.getServerIp());
 map.put("serverStatus", obj.getServerStatus());
 map.put("serverCapacity", obj.getServerCapacity());
 map.put("serverRemainingCapacity", obj.getServerRemainingCapacity());
 map.put("serverPhysicalPath", obj.getServerPhysicalPath());
 map.put("serverVirtualDirectory", obj.getServerVirtualDirectory());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_fileserver> list) {
		List item = new ArrayList();
		for (Tb_fileserver obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("fId", obj.getFId());
 map.put("serverName", obj.getServerName());
 map.put("serverUrl", obj.getServerUrl());
 map.put("serverIp", obj.getServerIp());
 map.put("serverStatus", obj.getServerStatus());
 map.put("serverCapacity", obj.getServerCapacity());
 map.put("serverRemainingCapacity", obj.getServerRemainingCapacity());
 map.put("serverPhysicalPath", obj.getServerPhysicalPath());
 map.put("serverVirtualDirectory", obj.getServerVirtualDirectory());
			item.add(map);
		}
		return item;
	}

}
