package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tre_menberdocscore;

/**
 * tre_menberdocscore 返回数据的加载
 * 
 * @author  
 *
 */
public class Tre_menberdocscoreUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tre_menberdocscore> list) {
		List item = new ArrayList();
		for (Tre_menberdocscore obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if(obj.getDocid()!=null){
			map.put("docid", obj.getDocid().getId());
			map.put("doctitle", obj.getDocid().getDoctitle());
			map.put("filepath", obj.getDocid().getFilepath());
			map.put("filescore", obj.getDocid().getFilescore());
			map.put("filegoodcount", obj.getDocid().getFilegoodcount());
			}else{
				map.put("docid","");
				map.put("doctitle", "");
				map.put("filepath", "");
				map.put("filescore", "");
				map.put("filegoodcount", "");
			}
		 	if(obj.getMemberid()!=null){
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
			}else{
				map.put("memberid", "");
				map.put("usersname","");
			} 
			
			
			map.put("score", obj.getScore());
			 map.put("scoretime", obj.getScoretime()); 
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
	public static Map getControllerMap(Tre_menberdocscore obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("docid", obj.getDocid().getId());
		map.put("memberid", obj.getMemberid().getId());
		map.put("score", obj.getScore());
		map.put("scoretime", obj.getScoretime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tre_menberdocscore> list) {
		List item = new ArrayList();
		for (Tre_menberdocscore obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("docid", obj.getDocid());
			map.put("memberid", obj.getMemberid());
			map.put("score", obj.getScore());
			map.put("scoretime", obj.getScoretime());
			item.add(map);
		}
		return item;
	}

}
