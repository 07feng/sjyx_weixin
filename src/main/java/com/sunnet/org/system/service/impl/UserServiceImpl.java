package com.sunnet.org.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.system.dao.IPermissionDao;
import com.sunnet.org.system.dao.IRoleDao;
import com.sunnet.org.system.dao.IUserDao;
import com.sunnet.org.system.model.Permission;
import com.sunnet.org.system.model.Role;
import com.sunnet.org.system.model.User;
import com.sunnet.org.system.service.IUserService;
import com.sunnet.org.system.vo.UserVoUtil;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.PasswordMd5;
import com.sunnet.org.util.StringUtils;

/**
 * 
 * <br>
 * <b>功能：</b>User Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IPermissionDao permissionDao;

	@Override
	public User login(String username, String password) throws Exception {
		User user = null;
		UsernamePasswordToken token = new UsernamePasswordToken(username, PasswordMd5.getMd5Password(password));
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);
		if (subject.isAuthenticated()) {
			user = userDao.findByHQL("select obj from User obj where  obj.fdUserName =? ", username).get(0);
			user.setFdUserName(token.getUsername());
			user.setFdPassword(token.getPassword().toString());
		}
		return user;
	}

	@Override
	public QueryResult<User> list(PageBean pageBean, HttpServletRequest request) {
		QueryResult<User> result = new QueryResult<User>();
		StringBuffer str = new StringBuffer();
		str.append(" from User o where 1=1 and o.isdelete=0 "); // 初始化语句
		if (StringUtils.isStringNull(request.getParameter("fdUserName"))) {
			str.append(" and o.fdUserName like '%" + request.getParameter("fdUserName") + "%'");
		}

		pageBean.setHql(str.toString());
		int totalRecord = userDao.findByHQLCount(User.class, pageBean);// 73
		List<User> list = userDao.findByHQLPage(User.class, pageBean);
		result.setResultList(UserVoUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);// 77
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(User user, String[] role_id) {
		User user2 = userDao.findByPrimaryKey(User.class, user.getFdId());
		if (user.getFdPassword()==null || user.getFdPassword().trim().equals("") || user.getFdPassword().equals(user2.getFdPassword())) {
			user.setFdPassword(user2.getFdPassword());
		}else{
			user.setFdPassword(PasswordMd5.getMd5Password(user.getFdPassword()));
		}
		try {
			MyBeanUtils.copyBeanNotNull2Bean(user, user2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (role_id != null) {
			user2.getRoles().clear();
			Set<Role> set = new HashSet<Role>();
			for (String str : role_id) {
				Role role = roleDao.findByPrimaryKey(Role.class, str);
				role.setFdId(str);
				set.add(role);
			}
			user2.setRoles(set);
		}
		userDao.update(user2);
	}

	@Override
	public List listRole(User user) {
		List<Role> list = roleDao.findAll(Role.class);
		List item = new ArrayList();
		for (Role ro : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (user != null && user.getRoles() != null) {
				boolean falg = false;
				for (Role par : user.getRoles()) {
					if (par.getFdId().equals(ro.getFdId())) {
						falg = true;
					}
				}
				if (falg) {
					map.put("islock", "1");
				}
			} else {
				map.put("islock", "0");
			}
			map.put("fdId", ro.getFdId());
			map.put("fdRoleName", ro.getFdRoleName());
			map.put("fdStatus", ro.getFdStatus());
			item.add(map);
		}
		return item;
	}

	@Override
	public void save(User user, String[] role_id) {
		if (user.getFdPassword() != null) {
			user.setFdPassword(PasswordMd5.getMd5Password(user.getFdPassword()));
		} else {
			user.setFdPassword(PasswordMd5.getMd5Password("123456"));
		}
		user.setFdCreateTime(new Date().getTime());
		if (role_id != null) {
			Set<Role> set = new HashSet<Role>();
			for (String str : role_id) {
				Role role = roleDao.findByPrimaryKey(Role.class, str);
				role.setFdId(str);
				set.add(role);
			}
			user.setRoles(set);
		}
		userDao.save(user);
	}

	@Override
	public Set<Role> loginRole(String username) {
		PageBean page = new PageBean();
		page.setHql(" from User  where fdUserName ='" + username + "' and fdStatus=1 and isdelete=0 ");
		List<User> list = userDao.findByHQLPage(User.class, page);
		if (list.size() > 0) {
			User user = list.get(0);
			return user.getRoles();
		}
		return null;
	}

	@Override
	public Set<Permission> loginPermission(String username) {
		Set<Role> role = this.loginRole(username);
		Set<Permission> item = new HashSet<>();
		if (role.size() > 0) {
			for (Role ro : role) {
				item.addAll(ro.getPermissions());
			}
		}
		return item;
	}

	@Override
	public User findUserByLoginName(String loginName) {
		return userDao.findUserByLoginName(loginName);
	}

	@Override
	public void updateStatus(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" fd_id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or fd_id='").append(entityids[i]).append("'");
			}
		}
		userDao.updateStatus(User.class, hql.toString());
	}

}
