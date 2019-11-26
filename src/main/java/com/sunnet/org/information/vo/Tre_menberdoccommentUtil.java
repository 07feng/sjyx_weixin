package com.sunnet.org.information.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sunnet.org.information.dao.IT_comment_fidDao;
import com.sunnet.org.information.model.T_comment_fid;
import com.sunnet.org.information.model.Tre_menberdoccomment;

/**
 * tre_menberdoccomment 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tre_menberdoccommentUtil {
	
	
	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tre_menberdoccomment> list) {
		List item = new ArrayList();
		for (Tre_menberdoccomment obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			map.put("fid", obj.getFid());
			map.put("comments", obj.getComments());
			//map.put("member_id", obj.getMember_id());
			 if(obj.getMember_id()!=null){
				 map.put("memberid", obj.getMember_id().getId());
				 map.put("usersname", obj.getMember_id().getUsersname());
			 }else{
				 map.put("memberid", "");
				 map.put("usersname","");
			 }
			if (obj.getDocid() != null) {
				map.put("docid", obj.getDocid().getId());
				map.put("docmembername", obj.getDocid().getMemberid().getUsersname());
				map.put("doctitle", obj.getDocid().getDoctitle());
				map.put("filegoodcount", obj.getDocid().getFilegoodcount());
				map.put("filekeepcount", obj.getDocid().getFilekeepcount());
				map.put("filepaycount", obj.getDocid().getFilepaycount());
				map.put("fileviewcount", obj.getDocid().getFileviewcount());
				map.put("filecommentscount", obj.getDocid()
						.getFilecommentscount());
				map.put("filepath", obj.getDocid().getFilepath());
			} else {
				map.put("docid", "");
				map.put("doctitle", "");
				map.put("filegoodcount", "");
				map.put("filekeepcount", "");
				map.put("filepaycount", "");
				map.put("fileviewcount", "");
				map.put("filecommentscount", "");
				map.put("filepath", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getMember_id() != null) {
				map.put("member_id", obj.getMember_id().getUsersname());
			} else {
				map.put("usersname", "");
			}
			if (obj.getComment_time() != null) {
				map.put("comment_time", obj.getComment_time());
			} else {
				map.put("comment_time", "");
			}
			if (obj.getEdit_userid() != null) {
				map.put("edit_userid", obj.getEdit_userid().getFdUserName());
			} else {
				map.put("edit_userid", "");
			}
			if (obj.getIs_public() != null) {
				map.put("is_public", obj.getIs_public());
			} else {
				map.put("is_public", "");
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
	public static Map getControllerMap(Tre_menberdoccomment obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("fid", obj.getFid());
		map.put("docid", obj.getDocid());
		map.put("comments", obj.getComments());
		map.put("member_id", obj.getMember_id());
		if (obj.getDocid() != null) {
			map.put("docid", obj.getDocid().getDoctitle());
		} else {
			map.put("doctitle", "");
		}
		if (obj.getEdit_time() != null) {
			map.put("edit_time", obj.getEdit_time());
		} else {
			map.put("edit_time", "");
		}

		if (obj.getMember_id() != null) {
			map.put("member_id", obj.getMember_id().getUsersname());
		} else {
			map.put("usersname", "");
		}
		if (obj.getComment_time() != null) {
			map.put("comment_time", obj.getComment_time());
		} else {
			map.put("comment_time", "");
		}
		if (obj.getEdit_userid() != null) {
			map.put("edit_userid", obj.getEdit_userid().getFdUserName());
		} else {
			map.put("fd_user_name", "");
		}
		if (obj.getIs_public() != null) {
			map.put("is_public", obj.getIs_public());
		} else {
			map.put("is_public", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tre_menberdoccomment> list) {
		List item = new ArrayList();
		for (Tre_menberdoccomment obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("fid", obj.getFid());
			map.put("docid", obj.getDocid());
			map.put("comments", obj.getComments());
			map.put("member_id", obj.getMember_id());
			if (obj.getDocid() != null) {
				map.put("docid", obj.getDocid().getDoctitle());
			} else {
				map.put("doctitle", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}

			if (obj.getMember_id() != null) {
				map.put("member_id", obj.getMember_id().getUsersname());
			} else {
				map.put("usersname", "");
			}
			if (obj.getComment_time() != null) {
				map.put("comment_time", obj.getComment_time());
			} else {
				map.put("comment_time", "");
			}
			if (obj.getEdit_userid() != null) {
				map.put("edit_userid", obj.getEdit_userid().getFdUserName());
			} else {
				map.put("fd_user_name", "");
			}
			if (obj.getIs_public() != null) {
				map.put("is_public", obj.getIs_public());
			} else {
				map.put("is_public", "");
			}
			item.add(map);
		}
		return item;
	}

}
