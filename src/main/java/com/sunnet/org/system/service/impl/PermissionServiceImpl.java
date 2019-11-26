package com.sunnet.org.system.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.system.dao.IPermissionDao;
import com.sunnet.org.system.model.Permission;
import com.sunnet.org.system.service.IPermissionService;
import com.sunnet.org.system.vo.PermissionUtil;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>Permission Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {

	@Autowired
	private IPermissionDao permissionDao;

	@Override
	public QueryResult<Permission> list(PageBean pageBean, HttpServletRequest request) {
		QueryResult<Permission> result = new QueryResult<Permission>();
		StringBuffer str = new StringBuffer();
		str.append(" from Permission o where fdStatus!=0 "); // 初始化语句
		if (StringUtils.isStringNull(request.getParameter("fdPermissionName"))) {
			str.append(" and o.fdPermissionName like '%" + request.getParameter("fdPermissionName") + "%'");
		}

		pageBean.setHql(str.toString());
		int totalRecord = permissionDao.findByHQLCount(Permission.class, pageBean);// 73
		List<Permission> list = permissionDao.findByHQLPage(Permission.class, pageBean);
		result.setResultList(PermissionUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);// 77
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Permission permission) {
		Permission permission2 = permissionDao.findByPrimaryKey(Permission.class, permission.getFdId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(permission, permission2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		permissionDao.update(permission2);
	}

}
