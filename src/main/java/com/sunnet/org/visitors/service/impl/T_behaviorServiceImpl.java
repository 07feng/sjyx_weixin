package com.sunnet.org.visitors.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.visitors.dao.IT_behaviorDao;
import com.sunnet.org.visitors.model.T_behavior;
import com.sunnet.org.visitors.service.IT_behaviorService;
import com.sunnet.org.visitors.vo.T_behaviorUtil;

/**
 * 
 * <br>
 * <b>功能：</b>T_behavior Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class T_behaviorServiceImpl extends BaseServiceImpl<T_behavior>  implements IT_behaviorService
{
	
	@Autowired
	private IT_behaviorDao t_behaviorDao;

	@Override
	public QueryResult<T_behavior> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from T_behavior o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("fd_region"))) {
			str.append(" and o.fd_region like '%" + request.getParameter("fd_region") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("fd_system"))) {
			str.append(" and o.fd_system like '%" + request.getParameter("fd_system") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("fd_ipaddress"))) {
			str.append(" and o.fd_ipaddress like '%" + request.getParameter("fd_ipaddress") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append(" and o.fd_member_id.usersname like '%" + request.getParameter("usersname") + "%'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = t_behaviorDao.findByHQLCount(T_behavior.class, pageBean);
		str.append(" order by o.fd_operatingtime desc ");
		pageBean.setHql(str.toString());
		List<T_behavior> list = t_behaviorDao.findByHQLPage(T_behavior.class, pageBean);
		QueryResult<T_behavior> result = new QueryResult<T_behavior>();
		result.setResultList(T_behaviorUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(T_behavior t_behavior) {
		T_behavior t_behavior2 = t_behaviorDao.findByPrimaryKey(T_behavior.class,
				t_behavior.getFdId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(t_behavior, t_behavior2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		t_behaviorDao.update(t_behavior2);
	}
	
/*	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		t_behaviorDao.delete(T_behavior.class, hql.toString());
	}*/
	
	

}
