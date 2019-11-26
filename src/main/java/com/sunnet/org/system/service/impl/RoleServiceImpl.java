package com.sunnet.org.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.system.dao.IPermissionDao;
import com.sunnet.org.system.dao.IRoleDao;
import com.sunnet.org.system.model.Permission;
import com.sunnet.org.system.model.Role;
import com.sunnet.org.system.service.IRoleService;
import com.sunnet.org.system.vo.RoleUtil;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>Role Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
	@Autowired
	private IPermissionDao permissionDao;

	@Autowired
	private IRoleDao roleDao;

	@Override
	public QueryResult<Role> list(PageBean pageBean, HttpServletRequest request) {
		QueryResult<Role> result = new QueryResult<Role>();
		StringBuffer str = new StringBuffer();
		str.append(" from Role o where 1=1 "); // 初始化语句
		if (StringUtils.isStringNull(request.getParameter("fdRoleName"))) {
			str.append(" and o.fdRoleName like '%" + request.getParameter("fdRoleName") + "%'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = roleDao.findByHQLCount(Role.class, pageBean);// 73
		List<Role> list = roleDao.findByHQLPage(Role.class, pageBean);
		result.setResultList(RoleUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);// 77
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Role role, String[] permissions) {
		Role role2 = roleDao.findByPrimaryKey(Role.class, role.getFdId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(role, role2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		role2.getPermissions().clear();
		if (permissions != null) {
			Set<Permission> set = new HashSet<Permission>();
			for (String str : permissions) {
				Permission permission = permissionDao.findByPrimaryKey(Permission.class, str);
				set.add(permission);
			}
			role2.setPermissions(set);
		}
		roleDao.update(role2);

	}

	@Override
	public List listPermission(Role role) {
		List<Permission> list = permissionDao.findAll(Permission.class);
		List item = new ArrayList();
		for (Permission permission : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (role != null && role.getPermissions() != null) {
				boolean falg = false;
				for (Permission par : role.getPermissions()) {
					if (par.getFdId().equals(permission.getFdId())) {
						falg = true;
					}
				}
				if (falg) {
					map.put("islock", "1");
				}
			} else {
				map.put("islock", "0");
			}
			map.put("fdId", permission.getFdId());
			map.put("fdPermissionName", permission.getFdPermissionName());
			map.put("fdStatus", permission.getFdStatus());
			item.add(map);
		}
		return item;
	}

	@Override
	public void save(Role role, String[] permissions) {
		if (permissions != null) {
			Set<Permission> set = new HashSet<Permission>();
			for (String str : permissions) {
				Permission permission = permissionDao.findByPrimaryKey(Permission.class, str);
				set.add(permission);
			}
			role.setPermissions(set);
		}
		role.setFdStatus(1);
		role.setFdCreateTime(new Date().getTime());
		roleDao.save(role);
	}

}
