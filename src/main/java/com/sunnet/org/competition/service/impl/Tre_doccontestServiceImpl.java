package com.sunnet.org.competition.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.competition.dao.ITre_doccontestDao;
import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.competition.service.ITre_doccontestService;
import com.sunnet.org.doc.dao.ITre_friendscircleDao;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.member.dao.ITre_docfilelabelDao;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_doccontest Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tre_doccontestServiceImpl extends BaseServiceImpl<Tre_doccontest>  implements ITre_doccontestService
{
	@Autowired
	private ITre_friendscircleDao tre_friendscircleDao;
	
	@Autowired
	private ITre_doccontestDao tre_doccontestDao;
	@Autowired
	private ITre_docfilelabelDao tre_docfilelabelDao;
	@Override
	public QueryResult<Tre_doccontest> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tre_doccontest o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("membername"))) {
			str.append(" and o.doc_id.memberid.usersname like '%" + request.getParameter("membername")+"%' " );
		}
		if (StringUtils.isStringNull(request.getParameter("audit_status"))) {
			str.append(" and o.audit_status = '" + request.getParameter("audit_status")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("contest_id.id"))) {
			str.append(" and o.contest_id = '" + request.getParameter("contest_id.id")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("contest_idname"))) {
			str.append(" and o.contest_id.id = '" + request.getParameter("contest_idname")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("doc_id.id"))) {
			str.append(" and o.doc_id.id = '" + request.getParameter("doc_id.id")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("doc_name"))) {
			str.append(" and o.doc_id.doctitle like '%" + request.getParameter("doc_name")+"%' " );
		}
		if(StringUtils.isStringNull(request.getParameter("is_get_award"))){
			str.append(" and o.is_get_award = '" + request.getParameter("is_get_award")+"' " );
		} 
		if(StringUtils.isStringNull(request.getParameter("is_shortlisted"))){
			str.append(" and o.is_shortlisted = '" + request.getParameter("is_shortlisted")+"' " );
		}
		if(StringUtils.isStringNull(request.getParameter("contesttheme_id.id"))){
			str.append(" and o.contesttheme_id  = '" + request.getParameter("contesttheme_id.id")+"' " );
		}
		if(StringUtils.isStringNull(request.getParameter("contesttheme_name"))){
			str.append(" and o.contesttheme_id.themename  like '%" + request.getParameter("contesttheme_name")+"%' " );
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.add_date >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.add_date <= '"+request.getParameter("endDate_01")+"'"); 
		}
		if(StringUtils.isStringNull(request.getParameter("is_shortlisted_ok"))){
			str.append(" and o.is_shortlisted = '" + request.getParameter("is_shortlisted_ok")+"' " );
		}
		pageBean.setHql(str.toString());
		int totalRecord = tre_doccontestDao.findByHQLCount(Tre_doccontest.class, pageBean);
		String orderby="";
		if(StringUtils.isStringNull(request.getParameter("is_shortlisted_ok2")) && request.getParameter("is_shortlisted_ok2")=="0" || "0".equals(request.getParameter("is_shortlisted_ok2"))){
			orderby=" ORDER BY o.doc_id.filescore desc " ;
		}else if(StringUtils.isStringNull(request.getParameter("is_shortlisted_ok2")) && request.getParameter("is_shortlisted_ok2")=="1" || "1".equals(request.getParameter("is_shortlisted_ok2"))){
			orderby=" ORDER BY o.id desc " ;
		}else if(StringUtils.isStringNull(request.getParameter("is_shortlisted_ok2")) && request.getParameter("is_shortlisted_ok2")=="2" || "2".equals(request.getParameter("is_shortlisted_ok2"))){
			orderby=" ORDER BY o.edit_time desc ";
		}
		if(StringUtils.isStringNull(request.getParameter("is_shortlisted_ok")) && request.getParameter("is_shortlisted_ok")=="1" || "1".equals(request.getParameter("is_shortlisted_ok"))){
			orderby=" ORDER BY o.doc_id.filescore desc ";
		}	
		if(StringUtils.isStringNull(request.getParameter("is_get_award"))){
			orderby=" ORDER BY award_id.id ";
		}
		str.append(orderby );
		pageBean.setHql(str.toString());
		List<Tre_doccontest> list = tre_doccontestDao.findByHQLPage(Tre_doccontest.class, pageBean);
		
		
		QueryResult<Tre_doccontest> result = new QueryResult<Tre_doccontest>();
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
			if (obj.getIs_get_award() != null) {
				map.put("is_get_award", obj.getIs_get_award());
				if(obj.getIs_get_award()=="1" || "1".equals(obj.getIs_get_award())){
					if (obj.getAward_id() != null) {
						map.put("award_fdId", obj.getAward_id().getId());
						map.put("award_name", obj.getAward_id().getAwardname());
					} else {
						map.put("award_fdId", "");
						map.put("award_name", "");
					}
				}else {
					map.put("award_fdId", "");
					map.put("award_name", "");
				}
			} else {
				map.put("is_get_award", "");
				map.put("isfriendto", "no");
			}
			
			 
			if (obj.getContest_id() != null) {
				map.put("contest_fdId", obj.getContest_id().getId());
				map.put("contest_name", obj.getContest_id().getContestname());
				map.put("conteststatus", obj.getContest_id().getConteststatus());
				map.put("contest_isaudit", obj.getContest_id().getIsaudit());//赛事状态
				if(obj.getContest_id().getContesttypeid()!=null){
					map.put("contesttype", obj.getContest_id().getContesttypeid().getId());
					map.put("contesttypeid", obj.getContest_id().getContesttypeid().getName());
					
					//
				}else{
					map.put("contesttype", "");
					map.put("contesttypeid", "");
				}
				
			} else {
				map.put("contesttype", "");
				map.put("contesttypeid", "");
			}
			/*map.put("contest_id", obj.getContest_id());*/
			if (obj.getDoc_id() != null) {
				map.put("doc_fdId", obj.getDoc_id().getId());
				map.put("doctitle", obj.getDoc_id().getDoctitle());
				map.put("devicenumber", obj.getDoc_id().getDevicenumber());
				if(obj.getDoc_id().getFiletypeid()!=null){
					map.put("filetypeid", obj.getDoc_id().getFiletypeid().getId());
					map.put("filetypename", obj.getDoc_id().getFiletypeid().getTypeName());
				}else{
					map.put("filetypeid", "");
					map.put("filetypename", "");
				}
				
				map.put("filedescribe", obj.getDoc_id().getFiledescribe());
				map.put("filepath", obj.getDoc_id().getFilepath());
				map.put("filegoodcount", obj.getDoc_id().getFilegoodcount());
				map.put("filecommentscount", obj.getDoc_id().getFilecommentscount());
				map.put("filepaycount", obj.getDoc_id().getFilepaycount());
				map.put("fileviewcount", obj.getDoc_id().getFileviewcount());
				map.put("filekeepcount", obj.getDoc_id().getFilekeepcount());
				map.put("usersname", obj.getDoc_id().getMemberid().getUsersname());
				map.put("memberid", obj.getDoc_id().getMemberid().getId());
				map.put("isparticipating", obj.getDoc_id().getIsparticipating());
				map.put("filescore", obj.getDoc_id().getFilescore());
				map.put("filelength", obj.getDoc_id().getFilelength());
				if(obj.getDoc_id().getMemberid().getHeadimg()!=null){
					map.put("headimg", obj.getDoc_id().getMemberid().getHeadimg());
				}else{
					map.put("headimg", "");
				}
				if(null!=obj.getDoc_id().getMemberid().getLevelid()){
					map.put("levelname", obj.getDoc_id().getMemberid().getLevelid().getLevelname());
				}else{
					map.put("levelname", "");
				}
			
				map.put("iheight", obj.getDoc_id().getIheight());
				map.put("iwidht", obj.getDoc_id().getIwidht());
				List<Tre_docfilelabel> listdoc=null;
				if (obj.getDoc_id() != null && !(obj.getDoc_id().equals(""))) {
					 listdoc = tre_docfilelabelDao
							.findByHQL(" from Tre_docfilelabel where docid.id='"+obj.getDoc_id().getId()+"'");
				}
				String me="";
				for (Tre_docfilelabel theme : listdoc) {
					if(null!=theme.getLabelid().getName()){
						me+=","+theme.getLabelid().getName();
					}
				}
				me=me.length()>0? me.substring(1):me;
				map.put("label", me);
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
				map.put("label", "");
				map.put("devicenumber", "");
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
			
			if (obj.getIs_shortlisted() != null) {
				map.put("is_shortlisted", obj.getIs_shortlisted());
			} else {
				map.put("is_shortlisted", "");
			}
			if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
				if(obj.getIs_get_award()=="1" || obj.getIs_get_award().equals("1")){
					StringBuffer strs = new StringBuffer();
					strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
					strs.append(" and o.circlememberid = '" + obj.getDoc_id().getMemberid().getId() + "' ");
					strs.append(" and o.memberid = '" + request.getParameter("memberid.id") + "'");
					List<Tre_friendscircle> listdoc=tre_friendscircleDao.findByHQL(strs.toString());
					if(null!=listdoc && listdoc.size()>0){
						map.put("isfriendto", "yes");
					}else{
						map.put("isfriendto", "no");
					}
				}else{
					map.put("isfriendto", "no");
				}
				if(obj.getIs_shortlisted()=="1" || obj.getIs_shortlisted().equals("1")){
					StringBuffer strs = new StringBuffer();
					strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
					strs.append(" and o.circlememberid = '" + obj.getDoc_id().getMemberid().getId() + "' ");
					strs.append(" and o.memberid = '" + request.getParameter("memberid.id") + "'");
					List<Tre_friendscircle> listdoc=tre_friendscircleDao.findByHQL(strs.toString());
					if(null!=listdoc && listdoc.size()>0){
						map.put("rw_isfriendto", "yes");
					}else{
						map.put("rw_isfriendto", "no");
					}
				}else{
					map.put("rw_isfriendto", "no");
				}
			}
			 
			item.add(map);
		}
		result.setResultList(item);
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tre_doccontest tre_doccontest) {
		Tre_doccontest tre_doccontest2 = tre_doccontestDao.findByPrimaryKey(Tre_doccontest.class,
				tre_doccontest.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tre_doccontest, tre_doccontest2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tre_doccontestDao.update(tre_doccontest2);
	}
	
	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tre_doccontestDao.delete(Tre_doccontest.class, hql.toString());
	}
	
	public void updateStatus(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tre_doccontestDao.updateStatus(Tre_doccontest.class, hql.toString());
	}
	
	public void updateStatus2(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tre_doccontestDao.updateStatus2(Tre_doccontest.class, hql.toString());
	}
	public void updateStatus3(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tre_doccontestDao.updateStatus3(Tre_doccontest.class, hql.toString());
	}

	@Override
	public int updateSql(Integer id) {
		 
		return tre_doccontestDao.updateSql(id);
	}

	@Override
	public void updateSql2(String sql) {

		tre_doccontestDao.updateSql(sql);
	}

	@Override
	public List<Object[]> getByDocid(String docid) {
		String sql= "SELECT c.Id,c.ContestName,c.ContestStatus FROM TRE_DocContest AS dc LEFT JOIN TB_Contest AS c ON dc.contest_id= c.id WHERE dc.doc_id = ?";
		return tre_doccontestDao.findBySql(sql,docid);
	}


}
