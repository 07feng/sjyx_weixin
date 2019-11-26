package com.sunnet.org.member.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.vo.Tb_memberUtil;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_member Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_memberServiceImpl extends BaseServiceImpl<Tb_member>  implements ITb_memberService
{
	
	@Autowired
	private ITb_memberDao tb_memberDao;

	@Override
	public QueryResult<Tb_member> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_member o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("id"))) {
			str.append(" and o.id like '%" + request.getParameter("id") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("mobilenumber"))) {
			str.append(" and o.mobilenumber like '%" + request.getParameter("mobilenumber") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("levelid"))) {
			str.append(" and o.levelid.id like '%" + request.getParameter("levelid") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append(" and o.usersname like '%" + request.getParameter("usersname") + "%'");
			str.append(" or o.realname like '%" + request.getParameter("usersname") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("status"))) {
			str.append(" and o.status like '%" + request.getParameter("status") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.regtime >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.regtime <= '"+request.getParameter("endDate_01")+"'"); 
		}
		if (StringUtils.isStringNull(request.getParameter("groupname"))) {
			str.append(" and o.groupid.id = '"+request.getParameter("groupname")+"'"); 
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_memberDao.findByHQLCount(Tb_member.class, pageBean);
		str.append(" order by regtime desc");
		pageBean.setHql(str.toString());
		List<Tb_member> list = tb_memberDao.findByHQLPage(Tb_member.class, pageBean);
		QueryResult<Tb_member> result = new QueryResult<Tb_member>();
		result.setResultList(Tb_memberUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}
	
	

/*	@Override
	public void updateUserDate(Tb_member tb_member) {
		Tb_member tb_member2 = tb_memberDao.findByPrimaryKey(Tb_member.class,
				tb_member.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tb_member, tb_member2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		if(){
			
		}
		tb_memberDao.update(tb_member);
	}
	*/

	@Override
	public void update(Tb_member tb_member) {
//		Tb_member tb_member2 = tb_memberDao.findByPrimaryKey(Tb_member.class,
//				tb_member.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tb_member, tb_member2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		tb_memberDao.update(tb_member);
	}

	@Override
	public void updateUserPhone(String memberId,Tb_member tb_member) {
		//注册
		tb_memberDao.save(tb_member);
//		System.out.println("tb_member.getId()="+tb_member.getId());
		//解绑原手机
		String sql="update Tb_member set openid='' where id=?";
		tb_memberDao.updateSql(sql,memberId);

	}

	@Override
	public void updateUserOpenId(Tb_member tb_member, String memberId, String openId) {
		//解绑原手机
		String sql="update Tb_member set openid='' where id=?";
		tb_memberDao.updateSql(sql,memberId);
		//绑定新手机
		String sql2="update Tb_member set openid=? where id=?";
		tb_memberDao.updateSql(sql2,openId,tb_member.getId());
	}


	@Override
	public List<Tb_member> getAllByWhere(String sql) {
		// TODO Auto-generated method stubtb_levelDao
		return tb_memberDao.getAllByWhere(sql);
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
		tb_memberDao.delete(Tb_member.class, hql.toString());
	}

	@Override
	public Tb_member getByKey(String memberid) {
		String hql= "from Tb_member where id =?";
		List paras = new ArrayList();
		paras.add(memberid);
		List<Tb_member> list = tb_memberDao.findByHQL(hql,paras);
		return list.get(0);
	}

	@Override
	public Object[] getMemInfo(String memberId){
		String sql ="SELECT m.RemainingCapacity,m.Levelid,l.LevelName,m.LevelIntegral,l.MaxExp FROM Tb_member AS m LEFT JOIN TB_Level AS l ON m.Levelid = l.id WHERE m.id=?";
		List<Object[]> result = tb_memberDao.findBySql(sql,memberId);
		if(null != result && null != result.get(0))
			return result.get(0);
		else
			return null;
	}

	@Override
	public List<Object[]> getMemLevel(String memberId){
		String sql = "SELECT m.Id,m.Levelid,m.LevelIntegral,l.LevelName,l.MaxExp,m.Groupid FROM Tb_member AS m LEFT JOIN TB_Level AS l ON m.Levelid = l.Id WHERE m.Id =?";
		return tb_memberDao.findBySql(sql,memberId);
	}

	@Override
	public  List<Object[]> getByMenid(String memberid){
		String sql= "SELECT m.Id,m.UsersName,m.HeadImg,m.Introduction,m.RealName,m.Address,m.birthDay,m.Gender,l.id,l.LevelName,m.LevelIntegral,m.Groupid,m.Capacity,m.RemainingCapacity,m.backgroundimgoneimg,m.devicid,m.QQNumber,m.WeiXinNumber,m.WeiBoNumber,m.alipayname,m.messagetag FROM Tb_member AS m LEFT JOIN TB_Level AS l ON m.Levelid = l.id WHERE m.id =?";
		return tb_memberDao.findBySql(sql,memberid);
	}

	@Override
	public List<Tb_member> getByPhone(String phone) {
		String hql= "from Tb_member where mobilenumber =?";
		List paras = new ArrayList();
//		BigInteger b = new BigInteger(phone);
		paras.add(phone);
		List<Tb_member> list = tb_memberDao.findByHQL(hql,paras);
		return list;
	}

	@Override
	public int getViewnum(String memberId){
		String sql = "SELECT viewnum FROM [dbo].[Tb_member] where Id = ? ";
		List condition = new ArrayList();
		condition.add(memberId);
		return tb_memberDao.findCount(sql,condition);
	};

	@Override
    public void updateCapacity(String space,String memberId){
        tb_memberDao.updateSql("update Tb_member set RemainingCapacity = ? where id = ?",space,memberId);
    }

	@Override
	public void updateViewnum(int num,String id){
		tb_memberDao.updateSql("update Tb_member set viewnum = ? where id = ?",num,id);
	}

	@Override
	public void updateOpenId(String openId, String memberId) {
		String sql="update Tb_member set openid=?,viewnum=0 where id=?";
		tb_memberDao.updateSql(sql,openId,memberId);
	}

	@Override
	public void updateMemberInfo(String sql,Object... values){
		tb_memberDao.updateSql(sql,values);
	}

	@Override
	public void updatelevel(Double levelintegral,String memberId) {
		String sql="update Tb_member set levelintegral=? where id=?";
		tb_memberDao.updateSql(sql,levelintegral,memberId);
	}
	@Override
	public void updatelevel2(Integer level, Double levelintegral,String memberId) {
		String sql="update Tb_member set levelid=?,levelintegral = ? where id=?";
		tb_memberDao.updateSql(sql,level,levelintegral,memberId);
	}

	@Override
	public void updateHeading(String headPath,String menberid){
		String sql="update Tb_member set HeadImg=? where id=?";
		tb_memberDao.updateSql(sql,headPath,menberid);
	}

	@Override
	public void updateBg(String bgPath,String menberId){
		String sql="update Tb_member set backgroundimgoneimg=? where id=?";
		tb_memberDao.updateSql(sql,bgPath,menberId);
	}
}
