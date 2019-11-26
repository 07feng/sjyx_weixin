package com.sunnet.org.member.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.member.dao.ITb_contesttypeDao;
import com.sunnet.org.member.model.Tb_contesttype;
import com.sunnet.org.member.service.ITb_contesttypeService;
import com.sunnet.org.member.vo.Tb_contesttypeUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contesttype Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_contesttypeServiceImpl extends BaseServiceImpl<Tb_contesttype>  implements ITb_contesttypeService
{
	
	@Autowired
	private ITb_contesttypeDao tb_contesttypeDao;

	@Override
	public QueryResult<Tb_contesttype> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_contesttype o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("name"))) {
			str.append(" and o.name like '%" + request.getParameter("name") + "%'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_contesttypeDao.findByHQLCount(Tb_contesttype.class, pageBean);
		List<Tb_contesttype> list = tb_contesttypeDao.findByHQLPage(Tb_contesttype.class, pageBean);
		QueryResult<Tb_contesttype> result = new QueryResult<Tb_contesttype>();
		result.setResultList(Tb_contesttypeUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_contesttype tb_contesttype) {
		Tb_contesttype tb_contesttype2 = tb_contesttypeDao.findByPrimaryKey(Tb_contesttype.class,
				tb_contesttype.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_contesttype, tb_contesttype2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_contesttypeDao.update(tb_contesttype2);
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
		this.tb_contesttypeDao.delete(Tb_contesttype.class, hql.toString());
	}

	@Override
	public void updateType(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" contesttypeid='").append(entityids[i]).append("'");
			} else {
				hql.append(" or contesttypeid='").append(entityids[i]).append("'");
			}
		}
		tb_contesttypeDao.updateType(Tb_contest.class, hql.toString());
	}

	

}
