package com.sunnet.org.system.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.Permission;
import com.sunnet.org.system.model.Role;
import com.sunnet.org.system.model.User;

/**
 * 
 * <br>
 * <b>功能：</b>User Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IUserService extends IBaseService<User>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<User> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Dome
	 */
	public void update(User user,String[] role_id);
	
	
	/**
	 * 新增
	 * @param Dome
	 */
	public void save(User user,String[] role_id);
	
	/**
	 * 权限
	 * @param Role
	 */
	public List listRole(User user);
	
	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User login(String username, String password) throws Exception;
	
	/**
	 * 获取角色
	 * @param username
	 * @return
	 */
	public Set<Role> loginRole(String username);
	
	/**
	 * 获取角色权限
	 * @param username
	 * @return
	 */
	public Set<Permission> loginPermission(String username);
	

	/**
	 * 通过用户账号查找用户
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User findUserByLoginName(String username);
	
	public void updateStatus(Object[] entityids);
}
