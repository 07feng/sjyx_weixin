package com.sunnet.org.doc.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.doc.model.Tb_dockeep;

/**
 * tb_dockeep 返回数据的加载
 * @author 强强
 *
 */
public class Tb_dockeepUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tb_dockeep> list){
		List item= new ArrayList();
		for (Tb_dockeep obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 if(obj.getDocid()!=null){
     map.put("docid", obj.getDocid().getId()); 
     map.put("doctitle", obj.getDocid().getDoctitle()); 
	 map.put("filegoodcount", obj.getDocid().getFilegoodcount());
	 map.put("filekeepcount", obj.getDocid().getFilekeepcount());
	 map.put("filepaycount", obj.getDocid().getFilepaycount());
	 map.put("fileviewcount", obj.getDocid().getFileviewcount());
	 map.put("filecommentscount", obj.getDocid().getFilecommentscount());
	 map.put("filepath", obj.getDocid().getFilepath());
	 map.put("docid_membername", obj.getDocid().getMemberid().getUsersname());
	 map.put("docid_memberid", obj.getDocid().getMemberid().getId());
	 map.put("filetype", obj.getDocid().getFiletype());
	 map.put("phonethumbnailpathimg", obj.getDocid().getPhonethumbnailpathimg());
	 map.put("iwidht", obj.getDocid().getIwidht());
	 map.put("iheight", obj.getDocid().getIheight());
 }else{
	 map.put("docid", "");
	 map.put("doctitle", ""); 
	 map.put("filegoodcount", "");
	 map.put("filekeepcount","");
	 map.put("filepaycount", "");
	 map.put("fileviewcount", "");
	 map.put("filecommentscount", "");
	 map.put("filepath", "");
	 map.put("docid_membername", "");
	 map.put("docid_memberid", "");
	 map.put("filetype", "");
	 map.put("phonethumbnailpathimg","");
	 map.put("iwidht","");
	 map.put("iheight", "");
 }
 if(obj.getMemberid()!=null){
	 map.put("memberid", obj.getMemberid().getId());
	 map.put("usersname", obj.getMemberid().getUsersname());
 }else{
	 map.put("memberid", "");
	 map.put("usersname","");
 }
 
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(Tb_dockeep obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("docid", obj.getDocid());
 map.put("memberid", obj.getMemberid());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tb_dockeep> list) {
		List item = new ArrayList();
		for (Tb_dockeep obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("docid", obj.getDocid());
 map.put("memberid", obj.getMemberid());
			item.add(map);
		}
		return item;
	}

}
