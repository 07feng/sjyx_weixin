package com.sunnet.org.visitors.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.visitors.dao.IT_topsearchtermDao;
import com.sunnet.org.visitors.model.T_topsearchterm;
import com.sunnet.org.visitors.service.IT_topsearchtermService;
import com.sunnet.org.visitors.vo.T_topsearchtermUtil;

/**
 * 
 * <br>
 * <b>功能：</b>T_topsearchterm Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class T_topsearchtermServiceImpl extends BaseServiceImpl<T_topsearchterm>  implements IT_topsearchtermService
{
	
	@Autowired
	private IT_topsearchtermDao t_topsearchtermDao;

	@Override
	public QueryResult<T_topsearchterm> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from T_topsearchterm o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = t_topsearchtermDao.findByHQLCount(T_topsearchterm.class, pageBean);
		
		str.append(" order by o.fd_searchnumber desc "); 
		pageBean.setHql(str.toString());
		List<T_topsearchterm> list = t_topsearchtermDao.findByHQLPage(T_topsearchterm.class, pageBean);
		QueryResult<T_topsearchterm> result = new QueryResult<T_topsearchterm>();
		result.setResultList(T_topsearchtermUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(T_topsearchterm t_topsearchterm) {
		T_topsearchterm t_topsearchterm2 = t_topsearchtermDao.findByPrimaryKey(T_topsearchterm.class,
				t_topsearchterm.getFdSearchterm());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(t_topsearchterm, t_topsearchterm2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		t_topsearchtermDao.update(t_topsearchterm2);
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
		t_topsearchtermDao.delete(T_topsearchterm.class, hql.toString());
	}

	@Override
	public List<T_topsearchterm> getByDate(String dateRange) {
		String sql = "SELECT * FROM T_topsearchterm where DATEDIFF(DAY, fd_lastsearchtime, GETDATE())<? and DATEDIFF(DAY, fd_lastsearchtime, GETDATE())>=? ORDER BY fd_searchNumber DESC;";
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(8);
		List condition = new ArrayList();
		condition.add(Integer.parseInt(dateRange)*30);
		condition.add((Integer.parseInt(dateRange)-1)*30);
		return t_topsearchtermDao.findPageSqlBy(pageBean,sql,condition,T_topsearchterm.class);
	}
}
