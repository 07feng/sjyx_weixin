package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tb_groupdocgood;

/**
 * tb_groupdocgood 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tb_groupdocgoodUtil {

	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_groupdocgood> list) {
		List item = new ArrayList();
		for (Tb_groupdocgood obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if(obj.getContesttheme_id()!=null){
				map.put("contesttheme_id", obj.getContesttheme_id().getId());
				map.put("themename", obj.getContesttheme_id().getThemename());
			}else{
				map.put("contesttheme_id", "");
				map.put("themename", "");
			}
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getId());
				map.put("doc_filepath", obj.getDoc_id().getFilepath());
				map.put("doc_doctitle", obj.getDoc_id().getDoctitle());
				map.put("doc_filegoodcount", obj.getDoc_id().getFilegoodcount());
				map.put("doc_filecommentscount", obj.getDoc_id().getFilecommentscount());
				map.put("doc_filecommentscount", obj.getDoc_id().getFilecommentscount());
				map.put("doc_filewidth", obj.getDoc_id().getIwidht());
				map.put("doc_fileheigth", obj.getDoc_id().getIheight());
				map.put("doc_filescore", obj.getDoc_id().getFilescore());
				map.put("doc_member", obj.getDoc_id().getMemberid().getUsersname());
			} else {
				map.put("doc_fdId", "");
				map.put("doc_filepath","");
				map.put("doc_doctitle", "");
				map.put("doc_filegoodcount", "");
				map.put("doc_filecommentscount", "");
				map.put("doc_filewidth", "");
				map.put("doc_fileheigth", "");
				map.put("doc_filescore", "");
			}
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id().getId());
				map.put("member_usersname", obj.getMember_id().getUsersname());
			} else {
				map.put("member_fdId", "");
				map.put("member_usersname", "");
			}
			if (obj.getContest_id() != null) {
				map.put("contest_fdId", obj.getContest_id().getId());
				map.put("contest_contestname", obj.getContest_id().getContestname());
			} else {
				map.put("contest_fdId", "");
				map.put("contest_contestname","");
			}
			map.put("goodcound", obj.getGoodcound());
			map.put("todaytime", obj.getTodaytime());
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
	public static Map getControllerMap(Tb_groupdocgood obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		if (obj.getDoc_id() != null) {
			map.put("doc_fdId", obj.getDoc_id().getId());
		} else {
			map.put("doc_fdId", "");
		}
		map.put("doc_id", obj.getDoc_id());
		if (obj.getMember_id() != null) {
			map.put("member_fdId", obj.getMember_id().getId());
		} else {
			map.put("member_fdId", "");
		}
		map.put("member_id", obj.getMember_id());
		if (obj.getContest_id() != null) {
			map.put("contest_fdId", obj.getContest_id().getId());
		} else {
			map.put("contest_fdId", "");
		}
		map.put("contest_id", obj.getContest_id());
		map.put("goodcound", obj.getGoodcound());
		map.put("todaytime", obj.getTodaytime());
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_groupdocgood> list) {
		List item = new ArrayList();
		for (Tb_groupdocgood obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getId());
			} else {
				map.put("doc_fdId", "");
			}
			map.put("doc_id", obj.getDoc_id());
			if (obj.getMember_id() != null) {
				map.put("member_fdId", obj.getMember_id().getId());
			} else {
				map.put("member_fdId", "");
			}
			map.put("member_id", obj.getMember_id());
			if (obj.getContest_id() != null) {
				map.put("contest_fdId", obj.getContest_id().getId());
			} else {
				map.put("contest_fdId", "");
			}
			map.put("contest_id", obj.getContest_id());
			map.put("goodcound", obj.getGoodcound());
			map.put("todaytime", obj.getTodaytime());
			item.add(map);
		}
		return item;
	}

}
