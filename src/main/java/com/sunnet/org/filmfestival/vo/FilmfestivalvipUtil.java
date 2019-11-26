package com.sunnet.org.filmfestival.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.filmfestival.model.Filmfestivalvip;

/**
 * filmfestivalvip 返回数据的加载
 * 
 * @author 强强
 *
 */
public class FilmfestivalvipUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Filmfestivalvip> list) {
		List item = new ArrayList();
		for (Filmfestivalvip obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			map.put("titel", obj.getTitel());
			map.put("titelnote", obj.getTitelnote());
			map.put("filecommentscount", obj.getFilecommentscount());
			map.put("filegoodcount", obj.getFilegoodcount());
			map.put("filepaycount", obj.getFilepaycount());
			if (obj.getOpen_time() != null) {
				map.put("open_time", obj.getOpen_time());
			} else {
				map.put("open_time", "");
			}
			if (obj.getTime_length() >= 0) {
				map.put("time_length", obj.getTime_length());
			} else {
				map.put("time_length", "0");
			}
			map.put("sort", obj.getSort());
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id().getId());
				map.put("usersname", obj.getMember_id().getUsersname());
				map.put("levelid", obj.getMember_id().getLevelid().getId());
				map.put("levelname", obj.getMember_id().getLevelid().getLevelname());
				if(obj.getMember_id().getHeadimg()!=null){
					map.put("headimg", obj.getMember_id().getHeadimg());
				}else{
					map.put("headimg", "");
				}
			
				map.put("introduction", obj.getMember_id().getIntroduction());
			} else {
				map.put("member_fdId", "");
				map.put("usersname", "");
				map.put("levelid","");
				map.put("levelname", "");
				map.put("headimg", "");
				map.put("introduction", "");
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
	public static Map getControllerMap(Filmfestivalvip obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("titel", obj.getTitel());
		map.put("titelnote", obj.getTitelnote());
		map.put("filecommentscount", obj.getFilecommentscount());
		map.put("filegoodcount", obj.getFilegoodcount());
		map.put("filepaycount", obj.getFilepaycount());
		if (obj.getOpen_time() != null) {
			map.put("open_time", obj.getOpen_time());
		} else {
			map.put("open_time", "");
		}
		if (obj.getTime_length() > 0) {
			map.put("time_length", obj.getTime_length());
		} else {
			map.put("time_length", "");
		}
		map.put("sort", obj.getSort());
		if (obj.getMember_id() != null) {
			map.put("member_fdId", obj.getMember_id().getId());
		} else {
			map.put("member_fdId", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Filmfestivalvip> list) {
		List item = new ArrayList();
		for (Filmfestivalvip obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("titel", obj.getTitel());
			map.put("titelnote", obj.getTitelnote());
			map.put("filecommentscount", obj.getFilecommentscount());
			map.put("filegoodcount", obj.getFilegoodcount());
			map.put("filepaycount", obj.getFilepaycount());
			if (obj.getOpen_time() != null) {
				map.put("open_time", obj.getOpen_time());
			} else {
				map.put("open_time", "");
			}
			if (obj.getTime_length() > 0) {
				map.put("time_length", obj.getTime_length());
			} else {
				map.put("time_length", "");
			}
			map.put("sort", obj.getSort());
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id().getId());
			} else {
				map.put("member_fdId", "");
			}
			map.put("member_id", obj.getMember_id());
			item.add(map);
		}
		return item;
	}

}
