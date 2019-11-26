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

import com.sunnet.org.competition.dao.ITre_contestawardDao;
import com.sunnet.org.competition.model.Tre_contestaward;
import com.sunnet.org.competition.service.ITre_contestawardService;
import com.sunnet.org.competition.vo.Tre_contestawardUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_contestaward Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tre_contestawardServiceImpl extends BaseServiceImpl<Tre_contestaward>  implements ITre_contestawardService
{
	
	@Autowired
	private ITre_contestawardDao tre_contestawardDao;

	@Override
	public QueryResult<Tre_contestaward> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tre_contestaward o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("contestid.id"))) {
			str.append("and o.contestid = '" + request.getParameter("contestid.id")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("contestid"))) {
			str.append("and o.contestid.id = '" + request.getParameter("contestid")+"' " );
		}
		pageBean.setHql(str.toString());
		int totalRecord = tre_contestawardDao.findByHQLCount(Tre_contestaward.class, pageBean);
		List<Tre_contestaward> list = tre_contestawardDao.findByHQLPage(Tre_contestaward.class, pageBean);
		QueryResult<Tre_contestaward> result = new QueryResult<Tre_contestaward>();
		result.setResultList(Tre_contestawardUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tre_contestaward tre_contestaward) {
		Tre_contestaward tre_contestaward2 = tre_contestawardDao.findByPrimaryKey(Tre_contestaward.class,
				tre_contestaward.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tre_contestaward, tre_contestaward2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tre_contestawardDao.update(tre_contestaward2);
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
		tre_contestawardDao.delete(Tre_contestaward.class, hql.toString());
	}
	
	

}
