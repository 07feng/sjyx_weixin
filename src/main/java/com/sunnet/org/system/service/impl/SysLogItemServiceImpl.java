package com.sunnet.org.system.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.information.model.Tb_link;
import com.sunnet.org.system.dao.ISysLogItemDao;
import com.sunnet.org.system.model.SysLogItem;
import com.sunnet.org.system.service.ISysLogItemService;
import com.sunnet.org.system.vo.SysLogItemUtil;

/**
 * 
 * <br>
 * <b>功能：</b>SysLogItem Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class SysLogItemServiceImpl extends BaseServiceImpl<SysLogItem>  implements ISysLogItemService
{
	
	@Autowired
	private ISysLogItemDao sysLogItemDao;

	@Override
	public QueryResult<SysLogItem> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from SysLogItem o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("fdUserName"))) {
			str.append(" and o.fdUserName like '%" + request.getParameter("fdUserName") + "%' ");
		}
		if (StringUtils.isStringNull(request.getParameter("fdContent"))) {
			str.append(" and o.fdContent like '%" + request.getParameter("fdContent") + "%' ");
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.fdCreateDate >= '" + request.getParameter("startDate_01") + "' ");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.fdCreateDate <= '" + request.getParameter("endDate_01") + "' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = sysLogItemDao.findByHQLCount(SysLogItem.class, pageBean);
		str.append(" order by fdCreateDate desc ");
		pageBean.setHql(str.toString());
		List<SysLogItem> list = sysLogItemDao.findByHQLPage(SysLogItem.class, pageBean);
		QueryResult<SysLogItem> result = new QueryResult<SysLogItem>();
		result.setResultList(SysLogItemUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(SysLogItem sysLogItem) {
		SysLogItem sysLogItem2 = sysLogItemDao.findByPrimaryKey(SysLogItem.class,
				sysLogItem.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(sysLogItem, sysLogItem2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sysLogItemDao.update(sysLogItem2);
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
		this.sysLogItemDao.delete(SysLogItem.class, hql.toString());
	}

}
