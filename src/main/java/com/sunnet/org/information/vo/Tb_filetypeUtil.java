package com.sunnet.org.information.vo;

import com.sunnet.org.information.model.Tb_filetype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tb_filetype 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_filetypeUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_filetype> list) {
		List item = new ArrayList();
		for (Tb_filetype obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			map.put("fId", obj.getFId());
			map.put("typeName", obj.getTypeName());
			map.put("typeSort", obj.getTypeSort());
			map.put("typeNotes", obj.getTypeNotes());
			map.put("type_type", obj.getType_type());
			
			/* 如果对象为空，请做好准备 ,因为你没有外键关系*/
			if(obj.getEditUserId() != null){
				map.put("fdUserName", obj.getEditUserId().getFdUserName());
			}else{
				map.put("fdUserName", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("editTime", obj.getEdit_time());
			} else {
				map.put("editTime", "");
			}
			item.add(map);
		}
		return item;
	}

	/**
	 * 返回分页list
	 *
	 * @param list
	 * @return
	 * author jinhao
	 */
	public static List getControllerListJ(List<Object[]> list) {
		List item = new ArrayList();
		for (Object[] obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			if((Integer)obj[0] == 14)
				map.put("id", -1);
			else
				map.put("id", obj[0]);
			map.put("type", obj[1]);
			map.put("backgroundImg", obj[2]);
			item.add(map);
		}
		return item;
	}

	/**
	 * 返回分页list
	 *
	 * @param list
	 * @return
	 * author jinhao
	 */
	public static List getControllerListH(List<Object[]> list) {
		List item = new ArrayList();
		for (Object[] obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			if((Integer)obj[0] != 14) {
				map.put("id", obj[0]);
				map.put("type", obj[1]);
				item.add(map);
			}
		}
		return item;
	}

	/**
	 * 返回单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_filetype obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("fId", obj.getFId());
		map.put("typeName", obj.getTypeName());
		map.put("typeSort", obj.getTypeSort());
		map.put("typeNotes", obj.getTypeNotes());
		map.put("type_type", obj.getType_type());
		 
		if(obj.getEditUserId() != null){
			map.put("fdUserName", obj.getEditUserId().getFdUserName());
		}else{
			map.put("fdUserName", "");
		}
		if (obj.getEdit_time() != null) {
			map.put("editTime", obj.getEdit_time());
		} else {
			map.put("editTime", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tb_filetype> list) {
		List item = new ArrayList();
		for (Tb_filetype obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("fId", obj.getFId());
			map.put("typeName", obj.getTypeName());
			map.put("typeSort", obj.getTypeSort());
			map.put("typeNotes", obj.getTypeNotes());
			map.put("type_type", obj.getType_type());
			 
			if(obj.getEditUserId() != null){
				map.put("fdUserName", obj.getEditUserId().getFdUserName());
			}else{
				map.put("fdUserName", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("editTime", obj.getEdit_time());
			} else {
				map.put("editTime", "");
			}
			item.add(map);
		}
		return item;
	}

}
