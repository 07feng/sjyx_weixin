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

import com.sunnet.org.member.dao.ITb_memberconsumptionDao;
import com.sunnet.org.member.model.Tb_memberconsumption;
import com.sunnet.org.member.service.ITb_memberconsumptionService;
import com.sunnet.org.member.vo.Tb_memberconsumptionUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberconsumption Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_memberconsumptionServiceImpl extends BaseServiceImpl<Tb_memberconsumption>  implements ITb_memberconsumptionService
{
	
	@Autowired
	private ITb_memberconsumptionDao tb_memberconsumptionDao;

	@Override
	public QueryResult<Tb_memberconsumption> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_memberconsumption o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("operationType"))) {
			str.append(" and o.operationtype = " + request.getParameter("operationType") + " ");
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.operationtime >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.operationtime <= '"+request.getParameter("endDate_01")+"' "); 
		}
		if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
			str.append(" and o.memberid = '"+request.getParameter("memberid.id")+"' ");
		}
		if (StringUtils.isStringNull(request.getParameter("membername"))) {
			str.append(" and o.memberid.usersname like '%"+request.getParameter("membername")+"%' "); 
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_memberconsumptionDao.findByHQLCount(Tb_memberconsumption.class, pageBean);
		
		str.append("  order by o.operationtime desc "); 
		pageBean.setHql(str.toString());
		List<Tb_memberconsumption> list = tb_memberconsumptionDao.findByHQLPage(Tb_memberconsumption.class, pageBean);
		QueryResult<Tb_memberconsumption> result = new QueryResult<Tb_memberconsumption>();
		result.setResultList(Tb_memberconsumptionUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_memberconsumption tb_memberconsumption) {
		Tb_memberconsumption tb_memberconsumption2 = tb_memberconsumptionDao.findByPrimaryKey(Tb_memberconsumption.class,
				tb_memberconsumption.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_memberconsumption, tb_memberconsumption2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_memberconsumptionDao.update(tb_memberconsumption2);
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
		tb_memberconsumptionDao.delete(Tb_memberconsumption.class, hql.toString());
	}
	
	

}
