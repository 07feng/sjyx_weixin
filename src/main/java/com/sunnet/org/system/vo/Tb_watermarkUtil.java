package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.Tb_watermark;

/**
 * tb_watermark 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_watermarkUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_watermark> list) {
		List item = new ArrayList();
		for (Tb_watermark obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("watermarktype", obj.getWatermarktype());
			map.put("watermarktxt", obj.getWatermarktxt());
			map.put("watermarkimg", obj.getWatermarkimg());
			map.put("watermarkwidth", obj.getWatermarkwidth());
			map.put("watermarkheight", obj.getWatermarkheight());
			map.put("watermarkposition", obj.getWatermarkposition());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid());
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
	public static Map getControllerMap(Tb_watermark obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("watermarktype", obj.getWatermarktype());
		map.put("watermarktxt", obj.getWatermarktxt());
		map.put("watermarkimg", obj.getWatermarkimg());
		map.put("watermarkwidth", obj.getWatermarkwidth());
		map.put("watermarkheight", obj.getWatermarkheight());
		map.put("watermarkposition", obj.getWatermarkposition());
		map.put("edittime", obj.getEdittime());
		map.put("edituserid", obj.getEdituserid());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_watermark> list) {
		List item = new ArrayList();
		for (Tb_watermark obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("watermarktype", obj.getWatermarktype());
			map.put("watermarktxt", obj.getWatermarktxt());
			map.put("watermarkimg", obj.getWatermarkimg());
			map.put("watermarkwidth", obj.getWatermarkwidth());
			map.put("watermarkheight", obj.getWatermarkheight());
			map.put("watermarkposition", obj.getWatermarkposition());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid());
			item.add(map);
		}
		return item;
	}

}
