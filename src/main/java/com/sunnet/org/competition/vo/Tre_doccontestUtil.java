package com.sunnet.org.competition.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.member.service.ITre_docfilelabelService;
import com.sunnet.org.member.vo.Tre_docfilelabelUtil;

/**
 * tre_doccontest 返回数据的加载
 * 
 * @author 强强
 *
 */
public class Tre_doccontestUtil {
	@Autowired
	private ITre_docfilelabelService tre_docfilelabelService;
	/**
	 * 返回分页list
	 * 
	 * @param list
	 * @return
	 */
	public static List getControllerList(List<Tre_doccontest> list) {
		List item = new ArrayList();
		for (Tre_doccontest obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("add_date", obj.getAdd_date());
			if(obj.getContesttheme_id()!=null){
				map.put("contesttheme_id", obj.getContesttheme_id().getId());
				map.put("themename", obj.getContesttheme_id().getThemename());
			}else{
				map.put("contesttheme_id", "");
				map.put("themename", "");
			}
			if (obj.getAudit_status() >0) {
				map.put("audit_status", obj.getAudit_status());
			} else {
				map.put("audit_status", "");
			}
			if (obj.getAward_id() != null) {
				map.put("award_fdId", obj.getAward_id().getId());
				map.put("award_name", obj.getAward_id().getAwardname());
			} else {
				map.put("award_fdId", "");
				map.put("award_name", "");
			}
			 
			if (obj.getContest_id() != null) {
				map.put("contest_fdId", obj.getContest_id().getId());
				map.put("contest_name", obj.getContest_id().getContestname());
				map.put("contesttypeid", obj.getContest_id().getContesttypeid().getName());
			} else {
				map.put("contest_fdId", "");
				map.put("contest_name", "");
			}
			/*map.put("contest_id", obj.getContest_id());*/
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getId());
				map.put("doctitle", obj.getDoc_id().getDoctitle());
				map.put("filedescribe", obj.getDoc_id().getFiledescribe());
				map.put("filepath", obj.getDoc_id().getFilepath());
				map.put("filegoodcount", obj.getDoc_id().getFilegoodcount());
				map.put("filecommentscount", obj.getDoc_id().getFilecommentscount());
				map.put("usersname", obj.getDoc_id().getMemberid().getUsersname());
				map.put("memberid", obj.getDoc_id().getMemberid().getId());
				
				if(obj.getDoc_id().getMemberid().getHeadimg()!=null){
					map.put("headimg", obj.getDoc_id().getMemberid().getHeadimg());
				}else{
					map.put("headimg", "");
				}
				
				map.put("levelname", obj.getDoc_id().getMemberid().getLevelid().getLevelname());
				map.put("iheight", obj.getDoc_id().getIheight());
				map.put("iwidht", obj.getDoc_id().getIwidht());
			} else {
				map.put("doc_fdId", "");
				map.put("doctitle","");
				map.put("filedescribe","");
				map.put("filepath", "");
				map.put("filegoodcount", "");
				map.put("filecommentscount", "");
				map.put("usersname", "");
				map.put("memberid", "");
				map.put("headimg", "");
				map.put("levelname", "");
			}
			//map.put("doc_id", obj.getDoc_id());
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time() );
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_user_id() != null) {
				map.put("edit_user", obj.getEdit_user_id().getFdUserName());
			} else {
				map.put("edit_user", "");
			}
			if (obj.getIs_get_award() != null) {
				map.put("is_get_award", obj.getIs_get_award());
			} else {
				map.put("is_get_award", "");
			}
			if (obj.getIs_shortlisted() != null) {
				map.put("is_shortlisted", obj.getIs_shortlisted());
			} else {
				map.put("is_shortlisted", "");
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
	public static Map getControllerMap(Tre_doccontest obj) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		map.put("id", obj.getId());
		map.put("add_date", obj.getAdd_date());
		if(obj.getContesttheme_id()!=null){
			map.put("contesttheme_id", obj.getContesttheme_id().getId());
			map.put("themename", obj.getContesttheme_id().getThemename());
		}else{
			map.put("contesttheme_id", "");
			map.put("themename", "");
		}
		if (obj.getAudit_status() >0) {
			map.put("audit_status", obj.getAudit_status());
		} else {
			map.put("audit_status", "");
		}
		if (obj.getAward_id() != null) {
			map.put("award_fdId", obj.getAward_id().getId());
		} else {
			map.put("award_fdId", "");
		}
		map.put("award_id", obj.getAward_id());
		if (obj.getContest_id() != null) {
			map.put("contest_fdId", obj.getContest_id().getId());
		} else {
			map.put("contest_fdId", "");
		}
		map.put("contest_id", obj.getContest_id());
		if (obj.getDoc_id() != null) {
			map.put("doc_fdId", obj.getDoc_id().getId());
			map.put("doctitle", obj.getDoc_id().getDoctitle());
			map.put("filedescribe", obj.getDoc_id().getFiledescribe());
			map.put("filepath", obj.getDoc_id().getFilepath());
			map.put("filegoodcount", obj.getDoc_id().getFilegoodcount());
			map.put("filecommentscount", obj.getDoc_id().getFilecommentscount());
			map.put("usersname", obj.getDoc_id().getMemberid().getUsersname());
		} else {
			map.put("doc_fdId", "");
			map.put("doctitle", "");
			map.put("filedescribe","");
			map.put("filepath","");
			map.put("filegoodcount", "");
			map.put("filecommentscount","");
			map.put("usersname", "");
		}
	//	map.put("doc_id", obj.getDoc_id());
		if (obj.getEdit_time() != null) {
			map.put("edit_time", obj.getEdit_time());
		} else {
			map.put("edit_time", "");
		}
		if (obj.getEdit_user_id() != null) {
			map.put("edit_user", obj.getEdit_user_id().getFdUserName());
		} else {
			map.put("edit_user", "");
		}
		if (obj.getIs_get_award() != null) {
			map.put("is_get_award", obj.getIs_get_award());
		} else {
			map.put("is_get_award", "");
		}
		if (obj.getIs_shortlisted() != null) {
			map.put("is_shortlisted", obj.getIs_shortlisted());
		} else {
			map.put("is_shortlisted", "");
		}
		return map;
	}

	/**
	 * 返回全部list
	 * 
	 * @param obj
	 * @return
	 */
	public static List getControllerListAll(List<Tre_doccontest> list) {
		List item = new ArrayList();
		for (Tre_doccontest obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("add_date", obj.getAdd_date());
			if(obj.getContesttheme_id()!=null){
				map.put("contesttheme_id", obj.getContesttheme_id().getId());
				map.put("themename", obj.getContesttheme_id().getThemename());
			}else{
				map.put("contesttheme_id", "");
				map.put("themename", "");
			}
			if (obj.getAudit_status() > 0) {
				map.put("audit_status", obj.getAudit_status());
			} else {
				map.put("audit_status", "");
			}
			if (obj.getAward_id() != null) {
				map.put("award_fdId", obj.getAward_id().getId());
			} else {
				map.put("award_fdId", "");
			}
			map.put("award_id", obj.getAward_id());
			if (obj.getContest_id() != null) {
				map.put("contest_fdId", obj.getContest_id().getId());
			} else {
				map.put("contest_fdId", "");
			}
			map.put("contest_id", obj.getContest_id());
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getId());
				map.put("doctitle", obj.getDoc_id().getDoctitle());
				map.put("filedescribe", obj.getDoc_id().getFiledescribe());
				map.put("filepath", obj.getDoc_id().getFilepath());
				map.put("filegoodcount", obj.getDoc_id().getFilegoodcount());
				map.put("filecommentscount", obj.getDoc_id().getFilecommentscount());
				map.put("usersname", obj.getDoc_id().getMemberid().getUsersname());
			} else {
				map.put("doc_fdId", "");
			}
		//	map.put("doc_id", obj.getDoc_id());
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_user_id() != null) {
				map.put("edit_user", obj.getEdit_user_id().getFdUserName());
			} else {
				map.put("edit_user", "");
			}
			if (obj.getIs_get_award() != null) {
				map.put("is_get_award", obj.getIs_get_award());
			} else {
				map.put("is_get_award", "");
			}
			if (obj.getIs_shortlisted() != null) {
				map.put("is_shortlisted", obj.getIs_shortlisted());
			} else {
				map.put("is_shortlisted", "");
			}
			item.add(map);
		}
		return item;
	}

}
