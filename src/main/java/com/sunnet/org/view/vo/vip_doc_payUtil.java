package com.sunnet.org.view.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.view.model.vip_doc_pay;

/**
 * vip_doc_pay 返回数据的加载
 * 
 * @author 强强
 *
 */
public class vip_doc_payUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<vip_doc_pay> list) {
		List item = new ArrayList();
		for (vip_doc_pay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("docid", obj.getDocid());
			map.put("paymemberid", obj.getPaymemberid());
			map.put("payusersname", obj.getPayusersname());
			map.put("docmemberid", obj.getDocmemberid());
			map.put("docmembername", obj.getDocmembername());
			map.put("payamount", obj.getPayamount());
			map.put("addtime", obj.getAddtime());
			map.put("infotype", obj.getInfotype());
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
	public static Map getControllerMap(vip_doc_pay obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("docid", obj.getDocid());
		map.put("paymemberid", obj.getPaymemberid());
		map.put("payusersname", obj.getPayusersname());
		map.put("docmemberid", obj.getDocmemberid());
		map.put("docmembername", obj.getDocmembername());
		map.put("payamount", obj.getPayamount());
		map.put("addtime", obj.getAddtime());
		map.put("infotype", obj.getInfotype());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<vip_doc_pay> list) {
		List item = new ArrayList();
		for (vip_doc_pay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("docid", obj.getDocid());
			map.put("paymemberid", obj.getPaymemberid());
			map.put("payusersname", obj.getPayusersname());
			map.put("docmemberid", obj.getDocmemberid());
			map.put("docmembername", obj.getDocmembername());
			map.put("payamount", obj.getPayamount());
			map.put("addtime", obj.getAddtime());
			map.put("infotype", obj.getInfotype());
			item.add(map);
		}
		return item;
	}

}
