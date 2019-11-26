package com.sunnet.org.member.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.org.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.dao.ITb_memberextractDao;
import com.sunnet.org.member.model.Tb_memberextract;
import com.sunnet.org.member.service.ITb_memberextractService;
import com.sunnet.org.member.vo.Tb_memberextractUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberextract Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_memberextractServiceImpl extends BaseServiceImpl<Tb_memberextract>  implements ITb_memberextractService
{
	
	@Autowired
	private ITb_memberextractDao tb_memberextractDao;

	@Override
	public QueryResult<Tb_memberextract> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_memberextract o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("extractway"))) {
			str.append("and o.extractway = '" + request.getParameter("extractway")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.applytime >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.applytime <= '"+request.getParameter("endDate_01")+"'"); 
		}
		if (StringUtils.isStringNull(request.getParameter("extractstatus_o"))) {
			str.append(" and o.extractstatus = '0' " );
		}
		if (StringUtils.isStringNull(request.getParameter("extractstatus_i"))) {
			str.append(" and o.extractstatus = '1' " );
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_memberextractDao.findByHQLCount(Tb_memberextract.class, pageBean);
		str.append("  order by  o.applytime desc " );
		pageBean.setHql(str.toString());
		List<Tb_memberextract> list = tb_memberextractDao.findByHQLPage(Tb_memberextract.class, pageBean);
		QueryResult<Tb_memberextract> result = new QueryResult<Tb_memberextract>();
		result.setResultList(Tb_memberextractUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public QueryResult<Tb_memberextract> memberList(PageBean pageBean, String memberId) {
		StringBuffer str = new StringBuffer();
		QueryResult<Tb_memberextract> result = new QueryResult<Tb_memberextract>();
		str.append(" from Tb_memberextract o where  memberId='"+memberId+"'"); //初始化语句
		pageBean.setHql(str.toString());
		int totalRecord = tb_memberextractDao.findByHQLCount(Tb_memberextract.class, pageBean);
		if(pageBean.getTotalPage()<pageBean.getCurrent_Page()){
			return result;
		}
		str.append("  order by o.applytime desc ");
		pageBean.setHql(str.toString());
		List<Tb_memberextract> list = tb_memberextractDao.findByHQLCX(str.toString(), pageBean);
		List item = new ArrayList();
		for (Tb_memberextract obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			map.put("account", obj.getAccount());
			map.put("accountname", obj.getAccountname());
			/* map.put("memberid", obj.getMemberid());*/
			map.put("way", obj.getExtractway());
			map.put("amount", obj.getExtractamount());
			map.put("applytime", DateUtil.DateToString(obj.getApplytime(),"YYYY-MM-dd hh:mm:ss"));
			if (obj.getReturntime() != null)
				map.put("finishtime", DateUtil.DateToString(obj.getReturntime(),"YYYY-MM-dd hh:mm:ss"));
			else
				map.put("finishtime","");
			map.put("extractstatus", obj.getExtractstatus());
			if (obj.getMemberid() != null) {
				map.put("realname", obj.getMemberid().getRealname());
			} else {
				map.put("realname","");
			}
			map.put("typeName","提现");
			item.add(map);
		}
		result.setResultList(item);
		pageBean.setTotalRecord(totalRecord);
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_memberextract tb_memberextract) {
		Tb_memberextract tb_memberextract2 = tb_memberextractDao.findByPrimaryKey(Tb_memberextract.class,
				tb_memberextract.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_memberextract, tb_memberextract2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_memberextractDao.update(tb_memberextract2);
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
		tb_memberextractDao.delete(Tb_memberextract.class, hql.toString());
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
		tb_memberextractDao.updateStatus(Tb_memberextract.class, hql.toString());
	}

}
