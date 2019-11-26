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

import com.sunnet.org.member.dao.ITb_memberrechargeDao;
import com.sunnet.org.member.model.Tb_memberrecharge;
import com.sunnet.org.member.service.ITb_memberrechargeService;
import com.sunnet.org.member.vo.Tb_memberrechargeUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberrecharge Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_memberrechargeServiceImpl extends BaseServiceImpl<Tb_memberrecharge>  implements ITb_memberrechargeService
{
	
	@Autowired
	private ITb_memberrechargeDao tb_memberrechargeDao;

	@Override
	public QueryResult<Tb_memberrecharge> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_memberrecharge o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("way"))) {
			str.append(" and o.way like '%" + request.getParameter("way") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.rechargetime >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.rechargetime <= '"+request.getParameter("endDate_01")+"'"); 
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_memberrechargeDao.findByHQLCount(Tb_memberrecharge.class, pageBean);
		List<Tb_memberrecharge> list = tb_memberrechargeDao.findByHQLPage(Tb_memberrecharge.class, pageBean);
		QueryResult<Tb_memberrecharge> result = new QueryResult<Tb_memberrecharge>();
		result.setResultList(Tb_memberrechargeUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_memberrecharge tb_memberrecharge) {
		Tb_memberrecharge tb_memberrecharge2 = tb_memberrechargeDao.findByPrimaryKey(Tb_memberrecharge.class,
				tb_memberrecharge.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_memberrecharge, tb_memberrecharge2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_memberrechargeDao.update(tb_memberrecharge2);
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
		tb_memberrechargeDao.delete(Tb_memberrecharge.class, hql.toString());
	}
	
	

}
