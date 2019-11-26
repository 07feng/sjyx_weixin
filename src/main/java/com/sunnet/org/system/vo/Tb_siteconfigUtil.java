package com.sunnet.org.system.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.system.model.Tb_siteconfig;

/**
 * tb_siteconfig 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_siteconfigUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_siteconfig> list) {
		List item = new ArrayList();
		for (Tb_siteconfig obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("sitename", obj.getSitename());
			map.put("siteswitch", obj.getSiteswitch());
			map.put("logoimg", obj.getLogoimg());
			map.put("isupload", obj.getIsupload());
			map.put("uploadformat", obj.getUploadformat());
			map.put("watermarkswitch", obj.getWatermarkswitch());
			map.put("sietdescribe", obj.getSietdescribe());
			map.put("sitekeywords", obj.getSitekeywords());
			map.put("siterecord", obj.getSiterecord());
			map.put("sitecopyright", obj.getSitecopyright());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid().getFdId());
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
	public static Map getControllerMap(Tb_siteconfig obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("sitename", obj.getSitename());
		map.put("siteswitch", obj.getSiteswitch());
		map.put("logoimg", obj.getLogoimg());
		map.put("isupload", obj.getIsupload());
		map.put("uploadformat", obj.getUploadformat());
		map.put("watermarkswitch", obj.getWatermarkswitch());
		map.put("sietdescribe", obj.getSietdescribe());
		map.put("sitekeywords", obj.getSitekeywords());
		map.put("siterecord", obj.getSiterecord());
		map.put("sitecopyright", obj.getSitecopyright());
		map.put("edittime", obj.getEdittime());
		map.put("edituserid", obj.getEdituserid().getFdId());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_siteconfig> list) {
		List item = new ArrayList();
		for (Tb_siteconfig obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("sitename", obj.getSitename());
			map.put("siteswitch", obj.getSiteswitch());
			map.put("logoimg", obj.getLogoimg());
			map.put("isupload", obj.getIsupload());
			map.put("uploadformat", obj.getUploadformat());
			map.put("watermarkswitch", obj.getWatermarkswitch());
			map.put("sietdescribe", obj.getSietdescribe());
			map.put("sitekeywords", obj.getSitekeywords());
			map.put("siterecord", obj.getSiterecord());
			map.put("sitecopyright", obj.getSitecopyright());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid().getFdId());
			item.add(map);
		}
		return item;
	}

}
