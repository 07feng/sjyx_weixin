package com.sunnet.org.information.service.impl;

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
import com.sunnet.org.information.dao.ITb_shufflingDao;
import com.sunnet.org.information.model.Tb_link;
import com.sunnet.org.information.model.Tb_shuffling;
import com.sunnet.org.information.service.ITb_shufflingService;
import com.sunnet.org.information.vo.Tb_shufflingUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_shuffling Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_shufflingServiceImpl extends BaseServiceImpl<Tb_shuffling>  implements ITb_shufflingService
{
	
	@Autowired
	private ITb_shufflingDao tb_shufflingDao;

	@Override
	public QueryResult<Tb_shuffling> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_shuffling o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("title"))) {
			str.append(" and o.title like '%" + request.getParameter("title") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("isshow"))) {
			str.append(" and o.isshow like '%" + request.getParameter("isshow") + "%'");
		}
		 
		pageBean.setHql(str.toString());
		int totalRecord = tb_shufflingDao.findByHQLCount(Tb_shuffling.class, pageBean);
		str.append(" order by o.sort desc ");
		pageBean.setHql(str.toString());
		List<Tb_shuffling> list = tb_shufflingDao.findByHQLPage(Tb_shuffling.class, pageBean);
		QueryResult<Tb_shuffling> result = new QueryResult<Tb_shuffling>();
		result.setResultList(Tb_shufflingUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_shuffling tb_shuffling) {
		Tb_shuffling tb_shuffling2 = tb_shufflingDao.findByPrimaryKey(Tb_shuffling.class,
				tb_shuffling.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_shuffling, tb_shuffling2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_shufflingDao.update(tb_shuffling2);
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
		this.tb_shufflingDao.delete(Tb_shuffling.class, hql.toString());
	}

	@Override
	public void updateStatusok(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tb_shufflingDao.updateStatusok(Tb_shuffling.class, hql.toString());
	}

	@Override
	public void updateStatus(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tb_shufflingDao.updateStatus(Tb_shuffling.class, hql.toString());
	}
	

}
