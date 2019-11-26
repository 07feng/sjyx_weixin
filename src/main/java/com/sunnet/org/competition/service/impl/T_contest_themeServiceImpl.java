package com.sunnet.org.competition.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.competition.dao.IT_contest_themeDao;
import com.sunnet.org.competition.model.T_contest_theme;
import com.sunnet.org.competition.service.IT_contest_themeService;
import com.sunnet.org.competition.vo.T_contest_themeUtil;

/**
 * 
 * <br>
 * <b>功能：</b>T_contest_theme Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class T_contest_themeServiceImpl extends BaseServiceImpl<T_contest_theme>  implements IT_contest_themeService
{
	
	@Autowired
	private IT_contest_themeDao t_contest_themeDao;

	@Override
	public QueryResult<T_contest_theme> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from T_contest_theme o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("contest_id.id"))) {
			str.append(" and contest_id = '" + request.getParameter("contest_id.id") + "'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = t_contest_themeDao.findByHQLCount(T_contest_theme.class, pageBean);
		List<T_contest_theme> list = t_contest_themeDao.findByHQLPage(T_contest_theme.class, pageBean);
		QueryResult<T_contest_theme> result = new QueryResult<T_contest_theme>();
		result.setResultList(T_contest_themeUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(T_contest_theme t_contest_theme) {
		T_contest_theme t_contest_theme2 = t_contest_themeDao.findByPrimaryKey(T_contest_theme.class,
				t_contest_theme.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(t_contest_theme, t_contest_theme2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		t_contest_themeDao.update(t_contest_theme2);
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
		t_contest_themeDao.delete(T_contest_theme.class, hql.toString());
	}
	
	

}
