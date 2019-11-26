package com.sunnet.org.information.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.information.model.Tb_photoalbum;

/**
 * tb_photoalbum 返回数据的加载
 * 
 * @author
 *
 */
public class Tb_photoalbumUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_photoalbum> list) {
		List item = new ArrayList();
		for (Tb_photoalbum obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getDocid() != null) {
				map.put("frontcover_docid", obj.getDocid().getId());
				map.put("doc_Filepath", obj.getDocid().getFilepath());
			} else {
				map.put("frontcover_docid", "");
				map.put("doc_Filepath","");
			}
			map.put("photoalbumname", obj.getPhotoalbumname());
			map.put("memberid", obj.getMemberid());
			map.put("createtime", obj.getCreatetime());
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
	public static Map getControllerMap(Tb_photoalbum obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("photoalbumname", obj.getPhotoalbumname());
		if (obj.getDocid() != null) {
			map.put("frontcover_docid", obj.getDocid().getId());
			map.put("doc_Filepath", obj.getDocid().getFilepath());
		} else {
			map.put("frontcover_docid", "");
			map.put("doc_Filepath","");
		}
		map.put("memberid", obj.getMemberid());
		map.put("createtime", obj.getCreatetime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_photoalbum> list) {
		List item = new ArrayList();
		for (Tb_photoalbum obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getDocid() != null) {
				map.put("frontcover_docid", obj.getDocid().getId());
				map.put("doc_Filepath", obj.getDocid().getFilepath());
			} else {
				map.put("frontcover_docid", "");
				map.put("doc_Filepath","");
			}
			map.put("photoalbumname", obj.getPhotoalbumname());
			map.put("memberid", obj.getMemberid());
			map.put("createtime", obj.getCreatetime());
			item.add(map);
		}
		return item;
	}

}
