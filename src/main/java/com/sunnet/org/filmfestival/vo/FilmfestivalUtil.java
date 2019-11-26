package com.sunnet.org.filmfestival.vo;

import java.text.SimpleDateFormat;
import java.util.*;

import com.sunnet.org.app.entity.SortDocTime;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;

/**
 * filmfestival 返回数据的加载
 * 
 * @author
 *
 */
public class FilmfestivalUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Filmfestival> list) {
		List item = new ArrayList();
		for (Filmfestival obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			 
			if(obj.getVip_id()!=null){
				map.put("vip_id", obj.getVip_id().getId());
				map.put("time_length", obj.getVip_id().getTime_length());
				map.put("titel", obj.getVip_id().getTitel());
				map.put("titelnote", obj.getVip_id().getTitelnote());
				map.put("vip_time", obj.getVip_id().getOpen_time());
				map.put("vip_goodcount", obj.getVip_id().getFilegoodcount());
				map.put("vip_paycount", obj.getVip_id().getFilepaycount());
				map.put("vip_commentscount", obj.getVip_id().getFilecommentscount());
			}else{
				map.put("vip_id","");
				map.put("titel", "");
				map.put("titelnote","");
				map.put("vip_goodcount","0");
				map.put("vip_paycount","0");
				map.put("vip_commentscount", "0");
			}
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getId());
				map.put("doctitle", obj.getDoc_id().getDoctitle());
				map.put("filedescribe", obj.getDoc_id().getFiledescribe());
				map.put("filegoodcount", obj.getDoc_id().getFilegoodcount());
				map.put("filekeepcount", obj.getDoc_id().getFilekeepcount());
				map.put("filepaycount", obj.getDoc_id().getFilepaycount());
				map.put("fileviewcount", obj.getDoc_id().getFileviewcount());
				map.put("filecommentscount", obj.getDoc_id()
						.getFilecommentscount());
				map.put("filepath", obj.getDoc_id().getFilepath());
				map.put("iwidht", obj.getDoc_id().getIwidht());
				map.put("iheight", obj.getDoc_id().getIheight());
			} else {
				map.put("doc_fdId", "");
				map.put("doctitle", "");
				map.put("filegoodcount", "");   
				map.put("filekeepcount", "");
				map.put("filepaycount", "");
				map.put("fileviewcount", "");
				map.put("filecommentscount", "");
				map.put("filepath", "");
				map.put("filedescribe", "");
				map.put("iwidht","");
				map.put("iheight", "");
			}
		
			map.put("sort", obj.getSort());
			if (obj.getMember_id() != null) {
				map.put("member_id", obj.getMember_id().getId());
				if(obj.getMember_id().getUsersname()!=null){
					map.put("usersname", obj.getMember_id().getUsersname());
				}else{
					map.put("usersname", "");
				}
				if(obj.getMember_id().getLevelid() != null){
					map.put("levelname", obj.getMember_id().getLevelid().getLevelname());
				}else{
					map.put("levelname", "");
				}
				if(obj.getMember_id().getHeadimg()!=null){
					map.put("headimg", obj.getMember_id().getHeadimg());
				}else{
					map.put("headimg", "");
				}
				
			} else {
				map.put("member_id", "");
				map.put("usersname", "");
				map.put("headimg", "");
				map.put("levelname", "");
			}
			 
			item.add(map);
		}
		return item;
	}

	/**
	 * 返回关注用户的作品影集数据集合信息
	 * @return
	 */
	public static List<Object[]> getFilmList(List<Object[]> friendFilm,List<Object[]> friendFilmDoc,List<Object[]> data){
		for (int i = 0 ; i<friendFilm.size(); i++) {
			Object[] obj = new Object[17];
			Map<String, Object> map = new HashMap<>();
			obj[0] = friendFilm.get(i)[0];    //doc_id	vip_id
			obj[1] = friendFilm.get(i)[4];    //doc_title
			obj[2] = friendFilm.get(i)[12];    //describe
			obj[3] = 0;    //fileType
			obj[4] = "";    //doc_path one
			obj[5] = friendFilmDoc.get(i)[1];    //doc_path
			obj[6] = friendFilm.get(i)[6];    //view_num
			obj[7] = friendFilmDoc.get(i)[3];    //iheight
			obj[8] = friendFilmDoc.get(i)[2];    //iwidth
			obj[9] = friendFilm.get(i)[2];    //good_num
			obj[10] = friendFilm.get(i)[3];    //comment_num
			String upload_time = null;
			if (-1 != friendFilm.get(i)[11].toString().lastIndexOf("."))
				upload_time = friendFilm.get(i)[11].toString().substring(0, friendFilm.get(i)[11].toString().lastIndexOf("."));
			else
				upload_time = friendFilm.get(i)[11].toString();
			obj[11] = upload_time;    //upload_time
			obj[12] = friendFilm.get(i)[7];    //author_id
			obj[13] = friendFilm.get(i)[8];    //author_name
			obj[14] = friendFilm.get(i)[9];    //author_portrait
			obj[15] = friendFilm.get(i)[10];    //rank
			obj[16] = 2;    //isDouble
			data.add(obj);
		}

		System.out.println("data="+data.size());
		List<SortDocTime> sdtList = new ArrayList<>();
		for (int i =0; i<data.size(); i++){
			SortDocTime sdt = new SortDocTime(i,data.get(i)[11].toString());
			sdtList.add(sdt);
		}
//		System.out.println("开始排序了");
		ListSort(sdtList);
//		System.out.println("结束排序了");
		List<Object[]> result = new ArrayList<>();
		for (int i =sdtList.size()-1; i>=0; i--){
			result.add(data.get(sdtList.get(i).getId()));
		}
		System.out.println("返回去了");
		return result;
	}


	private static void ListSort(List<SortDocTime> list) {
		Collections.sort(list, new Comparator<SortDocTime>() {
			@Override
			public int compare(SortDocTime o1, SortDocTime o2) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date dt1 = format.parse(o1.getTime());
					Date dt2 = format.parse(o2.getTime());
					if (dt1.getTime() > dt2.getTime()) {
						return 1;
					} else if (dt1.getTime() < dt2.getTime()) {
						return -1;
					} else {
						return 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
		/**
         * 返回单个对象
         *
         * @param obj
         * @return
         */
	public static Map getControllerMap(Filmfestival obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		if (obj.getDoc_id() != null) {
			map.put("doc_fdId", obj.getDoc_id().getId());
		} else {
			map.put("doc_fdId", "");
		}
		map.put("doc_id", obj.getDoc_id());
		map.put("sort", obj.getSort());
		if (obj.getMember_id() != null) {
			map.put("member_fdId", obj.getMember_id().getId());
		} else {
			map.put("member_fdId", "");
		}
		if(obj.getVip_id()!=null){
			map.put("vip_id", obj.getVip_id().getId());
			map.put("titel", obj.getVip_id().getTitel());
			map.put("titelnote", obj.getVip_id().getTitelnote());
		}else{
			map.put("vip_id","");
			map.put("titel", "");
			map.put("titelnote","");
		}

		return map;
	}

	/**
	 * 返回全部list
	 * @return
	 */
	public static List getControllerListAll(List<Filmfestival> list) {
		List item = new ArrayList();
		for (Filmfestival obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getId());
			} else {
				map.put("doc_fdId", "");
			}
			map.put("doc_id", obj.getDoc_id());
			map.put("sort", obj.getSort());
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id().getId());
			} else {
				map.put("member_fdId", "");
			}
			if(obj.getVip_id()!=null){
				map.put("vip_id", obj.getVip_id().getId());
				map.put("titel", obj.getVip_id().getTitel());
				map.put("titelnote", obj.getVip_id().getTitelnote());
			}else{
				map.put("vip_id","");
				map.put("titel", "");
				map.put("titelnote","");
			}
			item.add(map);
		}
		return item;
	}

}
