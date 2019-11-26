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

import com.sunnet.org.visitors.dao.IT_searchrecordsDao;
import com.sunnet.org.visitors.model.T_searchrecords;
import com.sunnet.org.visitors.service.IT_searchrecordsService;
import com.sunnet.org.visitors.vo.T_searchrecordsUtil;

/**
 * 
 * <br>
 * <b>功能：</b>T_searchrecords Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class T_searchrecordsServiceImpl extends BaseServiceImpl<T_searchrecords>  implements IT_searchrecordsService
{
	
	@Autowired
	private IT_searchrecordsDao t_searchrecordsDao;

	@Override
	public QueryResult<T_searchrecords> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from T_searchrecords o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("fd_searchterm"))) {
			str.append(" and o.fd_searchterm like '%" + request.getParameter("fd_searchterm") + "%' ");
		}
		if (StringUtils.isStringNull(request.getParameter("fdMembername"))) {
			str.append(" and o.fdMemberId.usersname like '%" + request.getParameter("fdMembername") + "%' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = t_searchrecordsDao.findByHQLCount(T_searchrecords.class, pageBean);
		str.append(" order by fd_searchtime desc ");
		pageBean.setHql(str.toString());
		List<T_searchrecords> list = t_searchrecordsDao.findByHQLPage(T_searchrecords.class, pageBean);
		QueryResult<T_searchrecords> result = new QueryResult<T_searchrecords>();
		result.setResultList(T_searchrecordsUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(T_searchrecords t_searchrecords) {
		T_searchrecords t_searchrecords2 = t_searchrecordsDao.findByPrimaryKey(T_searchrecords.class,
				t_searchrecords.getFdId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(t_searchrecords, t_searchrecords2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		t_searchrecordsDao.update(t_searchrecords2);
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
		t_searchrecordsDao.delete(T_searchrecords.class, hql.toString());
	}
	
	

}
