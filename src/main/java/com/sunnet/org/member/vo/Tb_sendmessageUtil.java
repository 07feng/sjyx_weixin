package com.sunnet.org.member.vo;

import java.util.*;

import com.sunnet.org.member.model.Tb_sendmessage;
import com.sunnet.org.util.DateUtil;

/**
 * tb_sendmessage 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_sendmessageUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_sendmessage> list) {
		List item = new ArrayList();
		for (Tb_sendmessage obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("messagetype", obj.getMessagetype());
			map.put("memberid", obj.getMemberid());
			map.put("messageinfo", obj.getMessageinfo());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getUsersname());
				map.put("mobilenumber", obj.getMemberid().getMobilenumber());
			} else {
				map.put("usersname", "");
			}
			if (obj.getEdituserid() != null) {
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			 
			} else {
				map.put("fdUserName", "");
			}
			item.add(map);
		}
		return item;
	}

	/**
	 * 返回分页list
	 *
	 * @param stuList
	 * @return
	 */
	public static List getWatchControllerList(List stuList) {
		System.out.println("stuList.size()="+stuList.size());
		List item = new ArrayList();
		Map<String, Object> st=null;
		for (int i = 0; i < stuList.size(); i++) {
			st = new HashMap<String, Object>();
			Object[] object = (Object[]) stuList.get(i);// 每行记录不在是一个对象 而是一个数组
			String time = object[0].toString();
			String content =  object[1].toString();
//			System.out.println("time="+time);
			Date minTime = DateUtil.StringToDate(time);
            String min=DateUtil.getDatePoor(new Date(),minTime);
			st.put("time",min);
			st.put("content",content);
			item.add(st);
		}
		return item;
	}

	/**
	 * 返回单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_sendmessage obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("messagetype", obj.getMessagetype());
		map.put("memberid", obj.getMemberid());
		map.put("messageinfo", obj.getMessageinfo());
		map.put("edittime", obj.getEdittime());
		map.put("edituserid", obj.getEdituserid());
		if (obj.getMemberid() != null) {
			map.put("memberid", obj.getMemberid().getUsersname());
		} else {
			map.put("usersname", "");
		}
		if (obj.getEdituserid() != null) {
			map.put("edituserid", obj.getEdituserid().getFdUserName());
		} else {
			map.put("fdUserName", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerListAll(List<Tb_sendmessage> list) {
		List item = new ArrayList();
		for (Tb_sendmessage obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("messagetype", obj.getMessagetype());
			map.put("memberid", obj.getMemberid());
			map.put("messageinfo", obj.getMessageinfo());
			map.put("edittime", obj.getEdittime());
			map.put("edituserid", obj.getEdituserid());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getUsersname());
			} else {
				map.put("usersname", "");
			}

			if (obj.getEdituserid() != null) {
				map.put("edituserid", obj.getEdituserid().getFdUserName());
			} else {
				map.put("fdUserName", "");
			}

			item.add(map);
		}
		return item;
	}

}
