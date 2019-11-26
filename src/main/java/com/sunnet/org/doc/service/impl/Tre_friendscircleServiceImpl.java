package com.sunnet.org.doc.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.doc.dao.ITre_friendscircleDao;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.doc.vo.Tre_friendscircleUtil;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
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
 * <b>功能：</b>Tre_friendscircle Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tre_friendscircleServiceImpl extends BaseServiceImpl<Tre_friendscircle>  implements ITre_friendscircleService
{
	
	@Autowired
	private ITre_friendscircleDao tre_friendscircleDao;
	@Autowired
	private ITb_memberDao iTb_memberDao;

	@Override
	public QueryResult<Tre_friendscircle> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("circlememberid.id"))) {
			str.append(" and o.circlememberid = '" + request.getParameter("circlememberid.id") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
			str.append(" and o.memberid = '" + request.getParameter("memberid.id") + "'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tre_friendscircleDao.findByHQLCount(Tre_friendscircle.class, pageBean);
		List<Tre_friendscircle> list = tre_friendscircleDao.findByHQLPage(Tre_friendscircle.class, pageBean);
		QueryResult<Tre_friendscircle> result = new QueryResult<Tre_friendscircle>();
		result.setResultList(Tre_friendscircleUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tre_friendscircle tre_friendscircle) {
//		Tre_friendscircle tre_friendscircle2 = tre_friendscircleDao.findByPrimaryKey(Tre_friendscircle.class,
//				tre_friendscircle.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tre_friendscircle, tre_friendscircle2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		tre_friendscircleDao.update(tre_friendscircle);
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
		tre_friendscircleDao.delete(Tre_friendscircle.class, hql.toString());
	}

	@Override
	public List<Tre_friendscircle> listfriend(String circlememberid,String memberid) {
		// TODO Auto-generated method stub
		return tre_friendscircleDao.listfriend(circlememberid, memberid);
	}

	@Override
	public boolean idFucos(String memberid, String circleMemberId) {
		String hql= "select count(*) from Tre_friendscircle where memberid = ? and circlememberid = ?";
		List paras = new ArrayList();
		paras.add(memberid);
		paras.add(circleMemberId);
		Integer count = tre_friendscircleDao.findCount(hql,paras);
		if(count>0)
			return  true;
		else
			return false;
	}

	@Override
	public int findCount(String memberId) {
		String sql =  "select count(*) from Tre_friendscircle where memberId = ?";
		List condition = new ArrayList();
		condition.add(memberId);
		return tre_friendscircleDao.findCount(sql,condition);
	}

	public int findCount2(String memberId){
		String sql =  "select count(*) from Tre_friendscircle where CircleMemberId = ?";
		List condition = new ArrayList();
		condition.add(memberId);
		return tre_friendscircleDao.findCount(sql,condition);
	}

	@Override
	public void updateIsfriends(int id,int isfriends){
		String sql ="update Tre_friendscircle set isfriends=? where id=?";
		tre_friendscircleDao.updateSql(sql,isfriends,id);
	}

	@Override
	public List<Object[]> AfocusB(String idA,String idB){
		String sql ="SELECT id,isfriends FROM TRE_FriendsCircle WHERE memberId=? AND CircleMemberId=?";
		return  tre_friendscircleDao.findBySql(sql,idA,idB);
	}

	@Override
	public Map changeFollowStatus(String memberId,String userId,String code){
		List<Object[]> mems = iTb_memberDao.findBySql("SELECT m.Id,m.Levelid,m.LevelIntegral,l.LevelName,l.MaxExp FROM Tb_member AS m LEFT JOIN TB_Level AS l ON m.Levelid = l.Id WHERE m.Id =?",memberId);   //操作人等级经验信息
		Object[] m = mems.get(0);
		Tb_member mem = new Tb_member();
		mem.setId(m[0].toString());
		List<Object[]> memsB = iTb_memberDao.findBySql("SELECT m.Id,m.Levelid,m.LevelIntegral,l.LevelName,l.MaxExp FROM Tb_member AS m LEFT JOIN TB_Level AS l ON m.Levelid = l.Id WHERE m.Id =?",userId);    //被关注人等级经验信息
		Object[] mB = memsB.get(0);
		Tb_member memb = new Tb_member();
		memb.setId(mB[0].toString());
		Map data = new HashMap();
		data.put("user_id", m[0].toString());
		//是否有他 关注 你的记录
		List<Object[]> tres = tre_friendscircleDao.findBySql("SELECT id,isfriends FROM TRE_FriendsCircle WHERE memberId=? AND CircleMemberId=?",userId,memberId);

		if ("1".equals(code)) {  //关注
//                        int maxId = tre_friendscircleService.getMaxId(Tre_friendscircle.class) + 1;
			Tre_friendscircle tre_friendscircle = new Tre_friendscircle();
//                        tre_friendscircle.setId(maxId);
			tre_friendscircle.setAddtime(DateUtil.getDate());
			tre_friendscircle.setCirclememberid(memb);
			tre_friendscircle.setMemberid(mem);
			if (tres.size() > 0 && null != tres.get(0)) {
				tre_friendscircle.setIsfriends(1);
				//修改互相关注状态
				tre_friendscircleDao.updateSql("update Tre_friendscircle set isfriends=? where id=?",Integer.parseInt(tres.get(0)[0].toString()),1);
			} else
				tre_friendscircle.setIsfriends(0);
			tre_friendscircleDao.save(tre_friendscircle);
			// 修改会员积分
			Double levelIntegral = Double.valueOf(m[2].toString())+ Integer.parseInt(m[3].toString());
			String sqlLevel = "update Tb_member set levelid=?,levelintegral = ? where id=?";		//修改等级和经验
			String sqlLevel1 = "update Tb_member set levelintegral=? where id=?";		//修改经验
			//判断 两位用户是否满足升级条件
			if (levelIntegral>Double.valueOf(m[4].toString()) && Integer.parseInt(m[3].toString())<=4){
				if (Integer.parseInt(m[1].toString()) ==4)
					iTb_memberDao.updateSql(sqlLevel,15,levelIntegral,memberId);
				else
					iTb_memberDao.updateSql(sqlLevel,Integer.parseInt(m[1].toString())+1,levelIntegral,memberId);
			}else
				iTb_memberDao.updateSql(sqlLevel1,levelIntegral,memberId);

			Double levelIntegral2 = Double.valueOf(mB[2].toString())+ Integer.parseInt(mB[3].toString());
			//判断 两位用户是否满足升级条件
			if (levelIntegral2>Double.valueOf(mB[4].toString()) && Integer.parseInt(mB[3].toString())<=4){
				if (Integer.parseInt(mB[1].toString()) ==4)
					iTb_memberDao.updateSql(sqlLevel,15,levelIntegral2,userId);
				else
					iTb_memberDao.updateSql(sqlLevel,Integer.parseInt(mB[1].toString())+1,levelIntegral2,userId);
			}else
				iTb_memberDao.updateSql(sqlLevel1,levelIntegral2,userId);
		}
		if ("0".equals(code)) {   //取消关注
			if (null != tres && tres.size() > 0) {
				//修改互相关注状态
				tre_friendscircleDao.updateSql("update Tre_friendscircle set isfriends=? where id=?",Integer.parseInt(tres.get(0)[0].toString()),0);
			}
			List<Object[]> tres2 = tre_friendscircleDao.findBySql("SELECT id,isfriends FROM TRE_FriendsCircle WHERE memberId=? AND CircleMemberId=?",memberId,userId);
			if (tres2.size() > 0 && null != tres2.get(0)) {
				StringBuilder hql = new StringBuilder("");
				hql.append(" id='").append(Integer.parseInt(tres2.get(0)[0].toString())).append("'");
				tre_friendscircleDao.delete(Tre_friendscircle.class, hql.toString());    //删除关注记录
			}
		}
		data.put("follow_status", code);
		return data;
	}
}
