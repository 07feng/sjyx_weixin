package com.sunnet.org.member.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.dao.IBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.member.dao.ITb_sendmessageDao;
import com.sunnet.org.member.model.Tb_sendmessage;
import com.sunnet.org.member.service.ITb_sendmessageService;
import com.sunnet.org.member.vo.Tb_sendmessageUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_sendmessage Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_sendmessageServiceImpl extends BaseServiceImpl<Tb_sendmessage>  implements ITb_sendmessageService
{
	
	@Autowired
	private ITb_sendmessageDao tb_sendmessageDao;
	@Autowired
    private IBaseDao baseDao;

	@Override
	public QueryResult<Tb_sendmessage> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_sendmessage o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("messagetype"))) {
			str.append(" and o.messagetype like '%" + request.getParameter("messagetype") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("messageinfo"))) {
			str.append(" and o.messageinfo like '%" + request.getParameter("messageinfo") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("memberid"))) {
			str.append(" and o.memberid.usersname like '%" + request.getParameter("memberid") + "%'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_sendmessageDao.findByHQLCount(Tb_sendmessage.class, pageBean);
		str.append("  order by o.edittime desc ");
		pageBean.setHql(str.toString());
		List<Tb_sendmessage> list = tb_sendmessageDao.findByHQLPage(Tb_sendmessage.class, pageBean);
		QueryResult<Tb_sendmessage> result = new QueryResult<Tb_sendmessage>();
		result.setResultList(Tb_sendmessageUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}
	
	@Override
	public QueryResult<Tb_sendmessage> listapp(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_sendmessage o where 1=1 "); //初始化语句
		str.append(" and o.messagetype like '%App%' ");
		 
		if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
			str.append(" and o.memberid = '" + request.getParameter("memberid.id") + "' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_sendmessageDao.findByHQLCount(Tb_sendmessage.class, pageBean);
		str.append(" order by o.edittime desc ");
		pageBean.setHql(str.toString());
		List<Tb_sendmessage> list = tb_sendmessageDao.findByHQLPage(Tb_sendmessage.class, pageBean);
		QueryResult<Tb_sendmessage> result = new QueryResult<Tb_sendmessage>();
		result.setResultList(Tb_sendmessageUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public QueryResult watchList(PageBean pageBean, String memberId) {
		StringBuffer str = new StringBuffer();
		str.append("select o.edittime,o.messageinfo from Tb_sendmessage o where o.messageType<>'短信推送' and o.memberid='"+memberId+"'"); //初始化语句
		pageBean.setSql(str.toString());
		int totalRecord = baseDao.findConutBySql("select count(*) from Tb_sendmessage o where o.messageType<>'短信推送' and o.memberid='"+memberId+"'");
		pageBean.setTotalRecord(totalRecord);
		baseDao.sqlexecuteUpdate("update Tb_member set messagetag=GETDATE() where id='"+memberId+"'");
		if(totalRecord > 0 && pageBean.getTotalPage() >= pageBean.getCurrent_Page()) {
			str.append(" order by o.edittime desc ");
			pageBean.setSql(str.toString());
			List list = baseDao.findBySqlPage(Object.class, pageBean);
			QueryResult result = new QueryResult();
			result.setResultList(Tb_sendmessageUtil.getWatchControllerList(list));
			pageBean.setHql("");
			pageBean.setSql("");
			result.setPageBean(pageBean);
			return result;
		}else{
			return null;
		}
	}

	@Override
	public void update(Tb_sendmessage tb_sendmessage) {
		Tb_sendmessage tb_sendmessage2 = tb_sendmessageDao.findByPrimaryKey(Tb_sendmessage.class,
				tb_sendmessage.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_sendmessage, tb_sendmessage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_sendmessageDao.update(tb_sendmessage2);
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
		tb_sendmessageDao.delete(Tb_sendmessage.class, hql.toString());
	}
	
	

}
