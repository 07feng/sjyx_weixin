package com.sunnet.org.feedback.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.feedback.dao.ITb_feedbackDao;
import com.sunnet.org.feedback.model.Tb_feedback;
import com.sunnet.org.feedback.service.ITb_feedbackService;
import com.sunnet.org.feedback.vo.Tb_feedbackUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_feedback Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_feedbackServiceImpl extends BaseServiceImpl<Tb_feedback>  implements ITb_feedbackService
{
	
	@Autowired
	private ITb_feedbackDao tb_feedbackDao;

	@Override
	public QueryResult<Tb_feedback> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_feedback o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.addtime >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.addtime <= '"+request.getParameter("endDate_01")+"'"); 
		}
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append(" and o.memberid.usersname like '%"+request.getParameter("usersname")+"%' "); 
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_feedbackDao.findByHQLCount(Tb_feedback.class, pageBean);
		str.append(" order by addtime desc ");
		pageBean.setHql(str.toString());  
		List<Tb_feedback> list = tb_feedbackDao.findByHQLPage(Tb_feedback.class, pageBean);
		QueryResult<Tb_feedback> result = new QueryResult<Tb_feedback>();
		result.setResultList(Tb_feedbackUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_feedback tb_feedback) {
		Tb_feedback tb_feedback2 = tb_feedbackDao.findByPrimaryKey(Tb_feedback.class,
				tb_feedback.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_feedback, tb_feedback2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_feedbackDao.update(tb_feedback2);
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
		tb_feedbackDao.delete(Tb_feedback.class, hql.toString());
	}
	
	

}
