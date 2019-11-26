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

import com.sunnet.org.member.dao.ITb_messagetemplateDao;
import com.sunnet.org.member.model.Tb_messagetemplate;
import com.sunnet.org.member.service.ITb_messagetemplateService;
import com.sunnet.org.member.vo.Tb_messagetemplateUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_messagetemplate Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_messagetemplateServiceImpl extends BaseServiceImpl<Tb_messagetemplate>  implements ITb_messagetemplateService
{
	
	@Autowired
	private ITb_messagetemplateDao tb_messagetemplateDao;

	@Override
	public QueryResult<Tb_messagetemplate> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_messagetemplate o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("fd_name"))) {
			str.append(" and o.templateinfo like '%" + request.getParameter("fd_name") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("fd_type"))) {
			str.append(" and o.templatetype like '%" + request.getParameter("fd_type") + "%'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_messagetemplateDao.findByHQLCount(Tb_messagetemplate.class, pageBean);
		List<Tb_messagetemplate> list = tb_messagetemplateDao.findByHQLPage(Tb_messagetemplate.class, pageBean);
		QueryResult<Tb_messagetemplate> result = new QueryResult<Tb_messagetemplate>();
		result.setResultList(Tb_messagetemplateUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_messagetemplate tb_messagetemplate) {
		Tb_messagetemplate tb_messagetemplate2 = tb_messagetemplateDao.findByPrimaryKey(Tb_messagetemplate.class,
				tb_messagetemplate.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_messagetemplate, tb_messagetemplate2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_messagetemplateDao.update(tb_messagetemplate2);
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
		tb_messagetemplateDao.delete(Tb_messagetemplate.class, hql.toString());
	}
	
	

}
