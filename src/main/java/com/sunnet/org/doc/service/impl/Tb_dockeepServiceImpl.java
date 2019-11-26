package com.sunnet.org.doc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.doc.dao.ITb_dockeepDao;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.service.ITb_dockeepService;
import com.sunnet.org.doc.vo.Tb_dockeepUtil;
import com.sunnet.org.information.dao.ITb_docDao;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_dockeep Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_dockeepServiceImpl extends BaseServiceImpl<Tb_dockeep>  implements ITb_dockeepService
{
	
	@Autowired
	private ITb_dockeepDao tb_dockeepDao;
	@Autowired
	private ITb_docDao tb_docDao;
	@Autowired
	private ITb_memberDao tb_memberDao;



	@Override
	public QueryResult<Tb_dockeep> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_dockeep o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
			str.append(" and o.memberid = '" + request.getParameter("memberid.id") + "'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_dockeepDao.findByHQLCount(Tb_dockeep.class, pageBean);
		str.append(" order by o.addtime desc ");
		pageBean.setHql(str.toString());
		List<Tb_dockeep> list = tb_dockeepDao.findByHQLPage(Tb_dockeep.class, pageBean);
		QueryResult<Tb_dockeep> result = new QueryResult<Tb_dockeep>();
		result.setResultList(Tb_dockeepUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void collect(String docId, int collect,String memberId) {
		StringBuffer sql = new StringBuffer();
		Integer isExist = tb_dockeepDao.findBySql("select id from Tb_dockeep d where d.docid=? and d.memberid=?", docId, memberId).size();
			//collect==1是收藏操作要  插入一条数据 和  文件收藏数量+1
			if (collect==1) {
				//这条记录是否存在
				if (isExist == null || isExist <= 0) {
//                    tb_dockeep.setId(id);
					Tb_dockeep tb_dockeep = new Tb_dockeep();
					Tb_member tb_member = new Tb_member();
					tb_member.setId(memberId);
					tb_dockeep.setMemberid(tb_member);
					Tb_doc tb_doc = new Tb_doc();
					tb_doc.setId(docId);
					tb_dockeep.setDocid(tb_doc);
					tb_dockeep.setAddtime(new Date());
					tb_dockeepDao.save(tb_dockeep);
					String updateSql = "update Tb_doc set filekeepcount=filekeepcount+1 where id=?";
					tb_docDao.updateSql(updateSql,docId);
				}
				//收藏别人的作品添加积分
                String memSql = "SELECT m.Id,m.Levelid,m.LevelIntegral,l.LevelName,l.MaxExp FROM Tb_member AS m LEFT JOIN TB_Level AS l ON m.Levelid = l.Id WHERE m.Id =?";
                List<Object[]> tb_memberList = tb_memberDao.findBySql(memSql,memberId);
				Double levelIntegral = Double.valueOf(tb_memberList.get(0)[2].toString())+ Integer.parseInt(tb_memberList.get(0)[3].toString());
				//判断 两位用户是否满足升级条件
				if (levelIntegral>Double.valueOf(tb_memberList.get(0)[4].toString()) && Integer.parseInt(tb_memberList.get(0)[3].toString())<=4){
					String updateMemSql = "update Tb_member set levelid=?,levelintegral = ? where id=?";
					if (Integer.parseInt(tb_memberList.get(0)[1].toString()) ==4) {
						tb_memberDao.updateSql(updateMemSql, 15, levelIntegral, memberId);
					}
					else
						tb_memberDao.updateSql(updateMemSql, Integer.parseInt(tb_memberList.get(0)[1].toString())+1,levelIntegral,memberId);
				}else {
                    tb_memberDao.updateSql("update Tb_member set levelintegral=? where id=?", levelIntegral, memberId);
                }
			} else {
				if (isExist > 0) {
					//collect==0是取消收藏操作要  删除一条数据 和  文件收藏数量-1
					tb_dockeepDao.sqlexecuteUpdate("DELETE TB_DocKeep WHERE DocId='"+docId+"' AND MemberId='"+memberId+"'");
                    String updateSql = "update Tb_doc set filekeepcount=filekeepcount-1 where id=?";
                    tb_docDao.updateSql(updateSql,docId);
				}
			}
	}

	@Override
	public void update(Tb_dockeep tb_dockeep) {
		Tb_dockeep tb_dockeep2 = tb_dockeepDao.findByPrimaryKey(Tb_dockeep.class,
				tb_dockeep.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_dockeep, tb_dockeep2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_dockeepDao.update(tb_dockeep2);
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
		tb_dockeepDao.delete(Tb_dockeep.class, hql.toString());
	}

	@Override
	public void deleteByHql(String hql) {
		tb_dockeepDao.delete(Tb_dockeep.class, hql);
	}

	@Override
	public List<Tb_dockeep> listkeep(String docid, String memberid) {
		// TODO Auto-generated method stub
		return tb_dockeepDao.listkeep(docid, memberid);
	}


	public int findCount(String memberId){
		String sql =  "select count(*) from Tb_docKeep AS k LEFT JOIN TB_Doc AS d ON d.id = k.Docid where k.memberId =? AND d.IsPublic = 1 AND d.IsDelete = 0";
		List condition = new ArrayList();
		condition.add(memberId);
		return tb_dockeepDao.findCount(sql,condition);
	}

	@Override
	public int isKeepDocId(String docid,String membetId){
		String sql = "select count(*) from Tb_dockeep where docid = ? and memberid =?";
		return tb_dockeepDao.getAllNum(sql,docid,membetId);
	}

	@Override
	public List<Object[]> keepDocList(String memberId, int startRow,int endRow){
		String sql  = "SELECT * FROM (SELECT d.id,d.doctitle,d.filedescribe,d.filetype,d.phonethumbnailpathimg,d.filepath,d.fileviewcount,d.iheight,d.iwidht,d.filegoodcount,d.filecommentscount,convert(varchar(255),d.UploadTime) as deptName,m.id AS mid,m.usersname,m.headimg,l.levelname,d.IsDouble,ROW_NUMBER() over(order by k.AddTime DESC)T FROM TB_DocKeep AS k LEFT JOIN TB_Doc AS d ON d.id = k.docid LEFT JOIN Tb_member AS m ON m.id =d.memberid LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE k.memberid =? and d.ispublic=1 and d.isdelete=0 and d.docstatus=1) TT WHERE TT.T between ? and ?";
		return tb_dockeepDao.findBySql(sql,memberId,startRow,endRow);
	}
}
