package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tb_docpay;

/**
 * tb_docpay 返回数据的加载
 * 
 * @author 
 *
 */
public class Tb_docpayUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_docpay> list) {
		List item = new ArrayList();
		for (Tb_docpay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("payamount", obj.getPayamount());
			map.put("addtime", obj.getAddtime());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
				map.put("Levelname", obj.getMemberid().getLevelid().getLevelname());
			} else {
				map.put("memberid", "");
				map.put("usersname", "");
				map.put("headimg", "");
				map.put("Levelname", "");
			}
			if (obj.getDocid() != null) {
				map.put("docid", obj.getDocid().getId());
				map.put("doctitle", obj.getDocid().getDoctitle());
				map.put("filepath", obj.getDocid().getFilepath());
				map.put("doc_member_id", obj.getDocid().getMemberid().getId());
				map.put("doc_member_name", obj.getDocid().getMemberid().getUsersname());
				map.put("doc_member_headimg", obj.getDocid().getMemberid().getHeadimg());
			} else {
				map.put("docid", "");
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
	public static Map getControllerMap(Tb_docpay obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("payamount", obj.getPayamount());
		map.put("addtime", obj.getAddtime());
		if (obj.getMemberid() != null) {
			map.put("memberid", obj.getMemberid().getId());
			map.put("usersname", obj.getMemberid().getUsersname());
			map.put("headimg", obj.getMemberid().getHeadimg());
		} else {
			map.put("memberid", "");
			map.put("usersname", "");
			map.put("headimg", "");
		}
		if (obj.getDocid() != null) {
			map.put("docid", obj.getDocid().getId());
			map.put("doctitle", obj.getDocid().getDoctitle());
			map.put("filepath", obj.getDocid().getFilepath());
			map.put("doc_member_id", obj.getDocid().getMemberid().getId());
			map.put("doc_member_name", obj.getDocid().getMemberid().getUsersname());
			map.put("doc_member_headimg", obj.getDocid().getMemberid().getHeadimg());
		} else {
			map.put("docid", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_docpay> list) {
		List item = new ArrayList();
		for (Tb_docpay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("addtime", obj.getAddtime());
			map.put("payamount", obj.getPayamount());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
			} else {
				map.put("memberid", "");
				map.put("usersname", "");
				map.put("headimg", "");
			}
			if (obj.getDocid() != null) {
				map.put("docid", obj.getDocid().getId());
				map.put("doctitle", obj.getDocid().getDoctitle());
				map.put("filepath", obj.getDocid().getFilepath());
				map.put("doc_member_id", obj.getDocid().getMemberid().getId());
				map.put("doc_member_name", obj.getDocid().getMemberid().getUsersname());
				map.put("doc_member_headimg", obj.getDocid().getMemberid().getHeadimg());
			} else {
				map.put("docid", "");
			}
			item.add(map);
		}
		return item;
	}

}
