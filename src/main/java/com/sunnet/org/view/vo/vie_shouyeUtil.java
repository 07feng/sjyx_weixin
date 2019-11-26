package com.sunnet.org.view.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunnet.org.view.model.vie_shouye;

/**
 * vie_shouye 返回数据的加载
 * @author 强强
 *
 */
public class vie_shouyeUtil {
	
	/**
	 * 返回分页list
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<vie_shouye> list){
		List item= new ArrayList();
		for (vie_shouye obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			
 map.put("id", obj.getId());
 map.put("doctitle", obj.getDoctitle());
 map.put("filetype", obj.getFiletype());
 map.put("createdate", obj.getCreatedate());
 map.put("filetypeid", obj.getFiletypeid());
 map.put("memberid", obj.getMemberid());
 map.put("ispublic", obj.getIspublic());
 map.put("isdelete", obj.getIsdelete());
 map.put("docstatus", obj.getDocstatus());
 map.put("usersname", obj.getUsersname());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("phonethumbnailpathimg", obj.getPhonethumbnailpathimg());
 map.put("filepath", obj.getFilepath());
 map.put("filescore", obj.getFilescore());
 map.put("isparticipating", obj.getIsparticipating());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filekeepcount", obj.getFilekeepcount());
 map.put("filecommentscount", obj.getFilecommentscount());
 map.put("filepaycount", obj.getFilepaycount());
 map.put("actityforwarcount", obj.getActityforwarcount());
			item.add(map);
		}
		return item;
	}
	
	
	/**
	 * 返回单个对象
	 * @param obj
	 * @return
	 */
	public static Map getControllerMap(vie_shouye obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
 map.put("id", obj.getId());
 map.put("doctitle", obj.getDoctitle());
 map.put("filetype", obj.getFiletype());
 map.put("createdate", obj.getCreatedate());
 map.put("filetypeid", obj.getFiletypeid());
 map.put("memberid", obj.getMemberid());
 map.put("ispublic", obj.getIspublic());
 map.put("isdelete", obj.getIsdelete());
 map.put("docstatus", obj.getDocstatus());
 map.put("usersname", obj.getUsersname());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("phonethumbnailpathimg", obj.getPhonethumbnailpathimg());
 map.put("filepath", obj.getFilepath());
 map.put("filescore", obj.getFilescore());
 map.put("isparticipating", obj.getIsparticipating());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filekeepcount", obj.getFilekeepcount());
 map.put("filecommentscount", obj.getFilecommentscount());
 map.put("filepaycount", obj.getFilepaycount());
 map.put("actityforwarcount", obj.getActityforwarcount());
		return map;
	}
	
	
	/**
	 * 返回全部list
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<vie_shouye> list) {
		List item = new ArrayList();
		for (vie_shouye obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			
 map.put("id", obj.getId());
 map.put("doctitle", obj.getDoctitle());
 map.put("filetype", obj.getFiletype());
 map.put("createdate", obj.getCreatedate());
 map.put("filetypeid", obj.getFiletypeid());
 map.put("memberid", obj.getMemberid());
 map.put("ispublic", obj.getIspublic());
 map.put("isdelete", obj.getIsdelete());
 map.put("docstatus", obj.getDocstatus());
 map.put("usersname", obj.getUsersname());
 map.put("iwidht", obj.getIwidht());
 map.put("iheight", obj.getIheight());
 map.put("phonethumbnailpathimg", obj.getPhonethumbnailpathimg());
 map.put("filepath", obj.getFilepath());
 map.put("filescore", obj.getFilescore());
 map.put("isparticipating", obj.getIsparticipating());
 map.put("fileviewcount", obj.getFileviewcount());
 map.put("filegoodcount", obj.getFilegoodcount());
 map.put("filekeepcount", obj.getFilekeepcount());
 map.put("filecommentscount", obj.getFilecommentscount());
 map.put("filepaycount", obj.getFilepaycount());
 map.put("actityforwarcount", obj.getActityforwarcount());
			item.add(map);
		}
		return item;
	}

}
