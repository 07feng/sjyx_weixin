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

import com.sunnet.org.system.dao.ISystemItemDao;
import com.sunnet.org.system.interceptor.SystemXT;
import com.sunnet.org.system.model.SystemItem;
import com.sunnet.org.system.service.ISystemItemService;
import com.sunnet.org.system.vo.SystemItemUtil;

/**
 * 
 * <br>
 * <b>功能：</b>SystemItem Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service("systemItemService")
public class SystemItemServiceImpl extends BaseServiceImpl<SystemItem> implements ISystemItemService {

	@Autowired
	private ISystemItemDao systemItemDao;

	@Override
	public QueryResult<SystemItem> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from SystemItem o where 1=1 "); // 初始化语句

		if (StringUtils.isStringNull(request.getParameter("systemTitle"))) {
			str.append(" and o.systemTitle like '%" + request.getParameter("systemTitle") + "%'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = systemItemDao.findByHQLCount(SystemItem.class, pageBean);
		List<SystemItem> list = systemItemDao.findByHQLPage(SystemItem.class, pageBean);
		QueryResult<SystemItem> result = new QueryResult<SystemItem>();
		result.setResultList(SystemItemUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(SystemItem systemItem) {
		SystemItem systemItem2 = systemItemDao.findByPrimaryKey(SystemItem.class, systemItem.getFdId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(systemItem, systemItem2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		systemItemDao.update(systemItem2);
		
		SystemXT.systemImg = systemItem2.getSystemImg();
		SystemXT.systemImgLog = systemItem2.getSystemImgLog();
		SystemXT.systemTitle =systemItem2.getSystemTitle();
		SystemXT.systemVersion = systemItem2.getSystemVersion();
		SystemXT.color =systemItem2.getColor();
		SystemXT.beian = systemItem2.getBeian();
	}

}
