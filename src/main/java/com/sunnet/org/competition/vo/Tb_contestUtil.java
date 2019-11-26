package com.sunnet.org.competition.vo;

import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.model.Tb_contesttheme;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * tb_contest 返回数据的加载
 * 
 * @author 
 *
 */
public class Tb_contestUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_contest> list) {
		List item = new ArrayList();
		for (Tb_contest obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			 
			map.put("contestname", obj.getContestname());
			map.put("contestinfo", obj.getContestinfo());
			map.put("conteststarttime", obj.getConteststarttime());
			map.put("contestendtime", obj.getContestendtime());
			
			map.put("ispublichome", obj.getIspublichome());
			map.put("isrelease", obj.getIsrelease());
			map.put("isaudit", obj.getIsaudit());
			map.put("conteststatus", obj.getConteststatus());
			map.put("contestawardinfo", obj.getContestawardinfo());
			map.put("contestminimg", obj.getContestminimg());
			map.put("contestmaximg", obj.getContestmaximg());
			map.put("isdouble", obj.getIsdouble());
			map.put("goodscore", obj.getGoodscore());
			map.put("expertsgoodscore", obj.getExpertsgoodscore());
			map.put("youkegoodscore", obj.getYoukegoodscore());
			map.put("dazhonggoodscor", obj.getDazhonggoodscore());
			map.put("edittime", obj.getEdittime());
			if(null!=obj.getContesttheme()){
			 
				String me="";
				for (Tb_contesttheme theme : obj.getContesttheme()) {
					if(null!=theme.getThemename()){
						me+=","+theme.getThemename();
					}
				
				}
				me=me.length()>0? me.substring(1):me;
				map.put("themename", me);
			}else{
				map.put("contestthemeid", "");
				map.put("themename", "");
			}
			if(obj.getContesttypeid()!=null){
				map.put("contesttypeid", obj.getContesttypeid().getId());
				map.put("name", obj.getContesttypeid().getName());
			}else {
				map.put("contesttypeid", "");
				map.put("name", "");
			}
			if(obj.getEdituserid()!=null){
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			}else {
				map.put("edituserid", "");
			}
			item.add(map);
		}
		return item;
	}

	/**
	 * 返回分页list
	 * @param stuList
	 * @return
	 */
	public static List<Map<String,Object>> getControllerPage(List stuList) {
		if (stuList != null && stuList.size() > 0) {
			List list = new LinkedList();
			Map<String, Object> st;
			for (int i = 0; i < stuList.size(); i++) {
				st = new HashMap<String, Object>();
				Object[] object = (Object[]) stuList.get(i);// 每行记录不在是一个对象 而是一个数组
//				System.out.println("obj="+object[0].toString()+","+object.length);
				String id = object[0].toString();
				String name =  object[1].toString();
				String img =  object[2].toString();
				String status = object[3].toString();
				String endtime=object[4].toString();
				String count =  object[5].toString();
//				System.out.println("obj="+id+","+name+","+img+","+status+","+endtime+","+count);
				// 重新封装在一个javabean里面
				st.put("match_id", id);
				st.put("match_name", name);
				st.put("match_img", img);
				if (status.equals("0") ) {
					Date endtimeDate = DateFormatUtil.StrToDate(endtime,"yyyy-MM-dd HH:mm:ss");
					long day=endtimeDate.getTime() - new Date().getTime();
					System.out.println("day="+day);
					if(day>=0){
						st.put("match_status", "还剩"+day/(1000*60*60*24)+"天");
						st.put("status", 0);
					}else if(day/(1000*60*60*24)>-30){
						st.put("match_status", "评审中");
						st.put("status", 1);
					}else{
						st.put("match_status", "已结束");
						st.put("status", 2);
					}
				}
				if (status.equals("1")) {
					st.put("match_status", "评审中");
					st.put("status", 1);
				}
				if (status.equals("2")) {
					st.put("match_status", "已结束");
					st.put("status", 2);
				}
				st.put("match_img_count", count);
				list.add(st); // 最终封装在list中 传到前台。
			}
			return list;
		}
		return null;
	}

	/**
	 *
	 * @param list
	 * @return
	 */
	public static Map<String,Object> getControllerMap(List list) {
		Map<String, Object> st = new HashMap<String, Object>();
			Object[] object = (Object[]) list.get(0);// 每行记录不在是一个对象 而是一个数组
//				System.out.println("obj="+object[0].toString()+","+object.length);
			String id = object[0].toString();
			String name =  object[1].toString();
			String describe = object[2].toString();
			String img =  object[3].toString();
			String status = object[4].toString();
			String startime=object[5].toString();
			String endtime=object[6].toString();
			String count =  object[7].toString();
			String contestinfo =  object[8].toString();
			String posterurl =  object[9].toString();
//				System.out.println("obj="+id+","+name+","+img+","+status+","+endtime+","+count);
			// 重新封装在一个javabean里面
			st.put("match_id", id);
			st.put("match_name", name);
			st.put("match_describe", describe);
			st.put("match_img", img+Constants.DOC_PATH_END2);

			if (status.equals("0")  ) {
				Date endtimeDate = DateFormatUtil.StrToDate(object[6].toString(),"yyyy-MM-dd HH:mm:ss");
				long day=endtimeDate.getTime() - new Date().getTime();
				System.out.println("day="+day);
				st.put("time_status",day);
				if(day>=0){
					st.put("match_status", "距结束还有"+day/(1000*60*60*24)+"天");
					st.put("status", 0);
				}else if(day/(1000*60*60*24)>-30){
					st.put("match_status", "评审中");
					st.put("status", 1);
				}else{
					st.put("match_status", "已结束");
					st.put("status", 2);
				}
			}
			if (status.equals("1")) {
				st.put("match_status", "评审中");
				st.put("status", 1);
			}
			if (status.equals("2")) {
				st.put("match_status", "已结束");
				st.put("status", 2);
			}
			Date startimeDate = DateFormatUtil.StrToDate(startime,"yyyy-MM-dd HH:mm:ss");
			Date endtimeDate = DateFormatUtil.StrToDate(endtime,"yyyy-MM-dd HH:mm:ss");
			String match_time=DateFormatUtil.defaultFormatDay(startimeDate).toString()+" - "+DateFormatUtil.defaultFormatDay(endtimeDate).toString();
			st.put("match_time", match_time);
			st.put("match_img_count", count);
			st.put("contestInfo",contestinfo);
			if(posterurl == null){
				st.put("posterurl","");
			}else {
				st.put("posterurl",posterurl);
			}
			return st;
	}

	/**
	 *
	 * @param stuList
	 * @return
	 */
	public static List<Map<String,Object>> getControllerMatchImgLis(List stuList) {
		if (stuList != null && stuList.size() > 0) {
			List list = new LinkedList();
			Map<String, Object> st;
			for (int i = 0; i < stuList.size(); i++) {
				st = new HashMap<String, Object>();
				Object[] object = (Object[]) stuList.get(i);// 每行记录不在是一个对象 而是一个数组
//				System.out.println("obj="+object[0].toString()+","+object.length);
				String id = object[0].toString();
				String doctitle = "";
				if(null != object[1]){
					doctitle =  object[1].toString();
				}
				String filepath =  object[2].toString();
				String filegoodcount = object[3].toString();
				String filecommentscount=object[4].toString();
				String filescore = object[5].toString();
				String iwidht = object[6].toString();
				String iheight = object[7].toString();
				String headimg = object[8].toString();
//				System.out.println("obj="+id+","+name+","+img+","+status+","+endtime+","+count);
				// 重新封装在一个javabean里面
				st.put("imgId", id);
				st.put("doc_title", doctitle);
				st.put("pic", filepath+ Constants.DOC_PATH_END);
				st.put("height",0);
				st.put("good_num", filegoodcount);
				st.put("comment", filecommentscount);
				st.put("filescore",(int)Double.parseDouble(filescore));
				st.put("iwidht",iwidht);
				st.put("iheight",iheight);
				st.put("headimg",headimg);
				list.add(st); // 最终封装在list中 传到前台。
			}
			return list;
		}
		return null;
	}


	/**
	 * 返回单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_contest obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		
		map.put("contestname", obj.getContestname());
		map.put("contestinfo", obj.getContestinfo());
		map.put("conteststarttime", obj.getConteststarttime());
		map.put("contestendtime", obj.getContestendtime());
		map.put("youkegoodscore", obj.getYoukegoodscore());
		map.put("dazhonggoodscor", obj.getDazhonggoodscore());
		map.put("ispublichome", obj.getIspublichome());
		map.put("isrelease", obj.getIsrelease());
		map.put("isaudit", obj.getIsaudit());
		map.put("conteststatus", obj.getConteststatus());
		map.put("contestawardinfo", obj.getContestawardinfo());
		map.put("contestminimg", obj.getContestminimg());
		map.put("contestmaximg", obj.getContestmaximg());
		map.put("isdouble", obj.getIsdouble());
		map.put("goodscore", obj.getGoodscore());
		map.put("expertsgoodscore", obj.getExpertsgoodscore());
		map.put("edittime", obj.getEdittime());
		if(obj.getContesttypeid()!=null){
			map.put("contesttypeid", obj.getContesttypeid().getId());
			map.put("name", obj.getContesttypeid().getName());
		}else {
			map.put("contesttypeid", "");
		}
		if(obj.getEdituserid()!=null){
			map.put("edituserid", obj.getEdituserid().getFdId());
		}else {
			map.put("edituserid", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tb_contest> list) {
		List item = new ArrayList();
		for (Tb_contest obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("contesttypeid", obj.getContesttypeid());
			map.put("contestname", obj.getContestname());
			map.put("contestinfo", obj.getContestinfo());
			map.put("conteststarttime", obj.getConteststarttime());
			map.put("contestendtime", obj.getContestendtime());
			map.put("youkegoodscore", obj.getYoukegoodscore());
			map.put("dazhonggoodscor", obj.getDazhonggoodscore());
			map.put("ispublichome", obj.getIspublichome());
			map.put("isrelease", obj.getIsrelease());
			map.put("isaudit", obj.getIsaudit());
			map.put("conteststatus", obj.getConteststatus());
			map.put("contestawardinfo", obj.getContestawardinfo());
			map.put("contestminimg", obj.getContestminimg());
			map.put("contestmaximg", obj.getContestmaximg());
			map.put("isdouble", obj.getIsdouble());
			map.put("goodscore", obj.getGoodscore());
			map.put("expertsgoodscore", obj.getExpertsgoodscore());
			map.put("edittime", obj.getEdittime());
			if(obj.getContesttypeid()!=null){
				map.put("contesttypeid", obj.getContesttypeid().getId());
				map.put("name", obj.getContesttypeid().getName());
			}else {
				map.put("contesttypeid", "");
			}
			if(obj.getEdituserid()!=null){
				map.put("edituserid", obj.getEdituserid());
			}else {
				map.put("edituserid", "");
			}
			item.add(map);
		}
		return item;
	}

}
