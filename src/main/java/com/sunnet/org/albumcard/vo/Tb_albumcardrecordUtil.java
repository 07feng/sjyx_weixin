package com.sunnet.org.albumcard.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.albumcard.model.Tb_albumcardrecord;

/**
 * tb_albumcardrecord 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_albumcardrecordUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_albumcardrecord> list) {
		List item = new ArrayList();
		for (Tb_albumcardrecord obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
			if(obj.getFd_filmfirstpic()!=null){
				map.put("fd_filmfirstpic", obj.getFd_filmfirstpic());
			}else{
				map.put("fd_filmfirstpic","");
			}
				
			if (obj.getFd_id() != null) {
				map.put("fd_fdId", obj.getFd_id());
			} else {
				map.put("fd_fdId", "");
			}
			 
			if (obj.getFd_albumvard_id() != null) {
				map.put("fd_albumvard", obj.getFd_albumvard_id().getFd_id());
				map.put("fd_name", obj.getFd_albumvard_id().getFd_name());
			} else {
				map.put("fd_albumvard", "");
				map.put("fd_name", "");
			}
			if (obj.getFd_url() != null) {
				map.put("fd_url", obj.getFd_url());
			} else {
				map.put("fd_url", "");
			}
			if (obj.getFd_ispay() >0) {
				map.put("fd_ispay", obj.getFd_ispay());
			} else {
				map.put("fd_ispay", 0);
			}
			if(obj.getFdmemberid()!=null){
				map.put("memberid", obj.getFdmemberid().getId());
				map.put("usersname", obj.getFdmemberid().getUsersname());
			}else{
				map.put("memberid", "");
			}
			map.put("fd_filmfirstpic", obj.getFd_filmfirstpic());
			
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
	public static Map getControllerMap(Tb_albumcardrecord obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		if(obj.getFd_filmfirstpic()!=null){
			map.put("fd_filmfirstpic", obj.getFd_filmfirstpic());
		}else{
			map.put("fd_filmfirstpic","");
		}
			
		if (obj.getFd_id() != null) {
			map.put("fd_fdId", obj.getFd_id());
		} else {
			map.put("fd_fdId", "");
		}
		map.put("fd_id", obj.getFd_id());
		if (obj.getFd_albumvard_id() != null) {
			map.put("fd_albumvard", obj.getFd_albumvard_id().getFd_id());
			map.put("fd_name", obj.getFd_albumvard_id().getFd_name());
			map.put("fd_type",obj.getFd_albumvard_id().getFd_type());
		} else {
			map.put("fd_albumvard", "");
			map.put("fd_name", "");
			map.put("fd_type", "");
		}
		if (obj.getFd_url() != null) {
			map.put("fd_url", obj.getFd_url());
		} else {
			map.put("fd_url", "");
		}
		if (obj.getFd_ispay() >0) {
			map.put("fd_ispay", obj.getFd_ispay());
		} else {
			map.put("fd_ispay", 0);
		}
		if(obj.getFdmemberid()!=null){
			map.put("memberid", obj.getFdmemberid().getId());
			map.put("usersname", obj.getFdmemberid().getUsersname());
		}else{
			map.put("memberid", "");
		}
		if(obj.getFd_docpath()!=null){
			map.put("docPath", obj.getFd_docpath());
		}else{
			map.put("docPath", "");
		}
		if(obj.getFd_mname()!=null){
			map.put("docName", obj.getFd_mname());
		}else{
			map.put("docName", "");
		}
		if(obj.getFd_docdec()!=null){
			map.put("docDec", obj.getFd_docdec());
		}else{
			map.put("docDec", "");
		}
		
		return map;
	}
	
	/**
	 * 返回单个对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_albumcardrecord obj,int type,String fd_name) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		if(obj.getFd_filmfirstpic()!=null){
			map.put("fd_filmfirstpic", obj.getFd_filmfirstpic());
		}else{
			map.put("fd_filmfirstpic","");
		}
			
		if (obj.getFd_id() != null) {
			map.put("fd_fdId", obj.getFd_id());
		} else {
			map.put("fd_fdId", "");
		}
		map.put("fd_id", obj.getFd_id());
		if (obj.getFd_albumvard_id() != null) {
			map.put("fd_albumvard", obj.getFd_albumvard_id().getFd_id());
			map.put("fd_name", obj.getFd_albumvard_id().getFd_name());
			map.put("fd_type",obj.getFd_albumvard_id().getFd_type());
		} else {
			map.put("fd_albumvard", "");
			map.put("fd_name", fd_name);
			map.put("fd_type", type);
		}
		if (obj.getFd_url() != null) {
			map.put("fd_url", obj.getFd_url());
		} else {
			map.put("fd_url", "");
		}
		if (obj.getFd_ispay() >0) {
			map.put("fd_ispay", obj.getFd_ispay());
		} else {
			map.put("fd_ispay", 0);
		}
		if(obj.getFdmemberid()!=null){
			map.put("memberid", obj.getFdmemberid().getId());
			map.put("usersname", obj.getFdmemberid().getUsersname());
		}else{
			map.put("memberid", "");
		}
		if(obj.getFd_docpath()!=null){
			map.put("docPath", obj.getFd_docpath());
		}else{
			map.put("docPath", "");
		}
		if(obj.getFd_mname()!=null){
			map.put("docName", obj.getFd_mname());
		}else{
			map.put("docName", "");
		}
		if(obj.getFd_docdec()!=null){
			map.put("docDec", obj.getFd_docdec());
		}else{
			map.put("docDec", "");
		}
		
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_albumcardrecord> list) {
		List item = new ArrayList();
		for (Tb_albumcardrecord obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			if(obj.getFd_filmfirstpic()!=null){
				map.put("fd_filmfirstpic", obj.getFd_filmfirstpic());
			}else{
				map.put("fd_filmfirstpic","");
			}
				
			if (obj.getFd_id() != null) {
				map.put("fd_fdId", obj.getFd_id());
			} else {
				map.put("fd_fdId", "");
			}
			map.put("fd_id", obj.getFd_id());
			if (obj.getFd_albumvard_id() != null) {
				map.put("fd_albumvard", obj.getFd_albumvard_id().getFd_id());
			} else {
				map.put("fd_albumvard", "");
			}
			if (obj.getFd_url() != null) {
				map.put("fd_url", obj.getFd_url());
			} else {
				map.put("fd_url", "");
			}
			if (obj.getFd_ispay() >0) {
				map.put("fd_ispay", obj.getFd_ispay());
			} else {
				map.put("fd_ispay", 0);
			}
			if(obj.getFdmemberid()!=null){
				map.put("memberid", obj.getFdmemberid().getId());
				map.put("usersname", obj.getFdmemberid().getUsersname());
			}else{
				map.put("memberid", "");
			}
			item.add(map);
		}
		return item;
	}

}
