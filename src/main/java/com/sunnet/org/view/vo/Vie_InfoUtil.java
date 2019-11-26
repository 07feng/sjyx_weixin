package com.sunnet.org.view.vo;

import java.net.URLDecoder;
import java.util.*;

import com.sunnet.org.util.DateUtil;
import com.sunnet.org.view.model.Vie_Info;

/**
 * vie_Info 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Vie_InfoUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Vie_Info> list) {
		List item = new ArrayList();
		try {
			for (Vie_Info obj : list) {
				Map<Object, Object> map = new HashMap<Object, Object>();
				if(obj.getId()!=null){
					map.put("id", obj.getId());
				}
				if(null!=obj.getUsersname()){
					map.put("usersname", obj.getUsersname());
				}else{
					map.put("usersname", "");
				}
				map.put("docid", obj.getDocid());
				map.put("docname", obj.getDocname());
				map.put("infotype", obj.getInfotype());
				map.put("adddate", obj.getAdddate());
				map.put("memberId", obj.getMemberid());
				if(null!=obj.getMemberid()){
					item.add(map);
				} 
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return item;
	}

	public static List getWatchList(List<Object[]> list) {
		List item = new ArrayList();
		try {
			for (Object[] obj : list) {
				Map<Object, Object> map = new HashMap<Object, Object>();
				if(null!=obj[0]){
					map.put("user_id", obj[0]);
				}
				if(null!=obj[1]){
					map.put("user_name", obj[1]);
				}else{
					map.put("user_name", "");
				}
				if(null!=obj[2]){
					map.put("doc_id", obj[2].toString());
				}else{
					map.put("doc_id", "");
				}
				if(null!=obj[3]){
					map.put("doc_name", URLDecoder.decode(obj[3].toString(),"utf-8"));
				}else{
					map.put("doc_name", "");
				}
				int info_type = Integer.parseInt(obj[4].toString());
				map.put("info_type", obj[4].toString());
				String time= DateUtil.getDatePoor(new Date(),DateUtil.StringToDate(obj[5].toString()));
				map.put("time", time);
				switch (info_type){
					case 1:
						map.put("info_content","关注了你");
						break ;
					case 2:
						map.put("info_content","收藏你的作品");
						break ;
					case 3:
						map.put("info_content","打赏了你的作品");
						break ;
					case 4:
						map.put("info_content","赞了你的作品");
						break ;
					case 5:
						map.put("info_content","评论了你的作品");
						break ;
					case 6:
						map.put("info_content","回复了你评论");
						break ;
					case 7:
						map.put("info_content","评论了你的影展");
						break ;
					case 8:
						map.put("info_content","点赞了你的影展");
						break ;
					case 9:
						map.put("info_content","打赏你的影展");
						break ;
				}
				item.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * 返回单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Vie_Info obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("usersname", obj.getUsersname());
		map.put("docid", obj.getDocid());
		map.put("docname", obj.getDocname());
		map.put("infotype", obj.getInfotype());
		map.put("adddate", obj.getAdddate());
		map.put("memberId", obj.getMemberid());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Vie_Info> list) {
		List item = new ArrayList();
		for (Vie_Info obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("usersname", obj.getUsersname());
			map.put("docid", obj.getDocid());
			map.put("docname", obj.getDocname());
			map.put("infotype", obj.getInfotype());
			map.put("adddate", obj.getAdddate());
			map.put("memberId", obj.getMemberid());
			item.add(map);
		}
		return item;
	}

}
