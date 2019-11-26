package com.sunnet.org.member.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.member.model.Tb_memberconsumption;

/**
 * tb_memberconsumption 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_memberconsumptionUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_memberconsumption> list) {
		List item = new ArrayList();
		for (Tb_memberconsumption obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("amount", obj.getAmount());
			map.put("operationType", obj.getOperationtype());
			map.put("withdrawalstate", obj.getWithdrawalstate());
			map.put("overage", obj.getOverage());
			map.put("operationTime", obj.getOperationtime());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
			} else {
				map.put("usersname", "");
				map.put("memberid", "");
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
	public static Map getControllerMap(Tb_memberconsumption obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("amount", obj.getAmount());
		map.put("operationType", obj.getOperationtype());
		map.put("withdrawalstate", obj.getWithdrawalstate());
		map.put("overage", obj.getOverage());
		map.put("operationTime", obj.getOperationtime());
		if (obj.getMemberid() != null) {
			map.put("memberid", obj.getMemberid().getId());
			map.put("usersname", obj.getMemberid().getUsersname());
		} else {
			map.put("usersname", "");
			map.put("memberid", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_memberconsumption> list) {
		List item = new ArrayList();
		for (Tb_memberconsumption obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("amount", obj.getAmount());
			map.put("operationType", obj.getOperationtype());
			map.put("withdrawalstate", obj.getWithdrawalstate());
			map.put("overage", obj.getOverage());
			map.put("operationTime", obj.getOperationtime());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("usersname", obj.getMemberid().getUsersname());
			} else {
				map.put("usersname", "");
				map.put("memberid", "");
			}
			item.add(map);
		}
		return item;
	}

}
