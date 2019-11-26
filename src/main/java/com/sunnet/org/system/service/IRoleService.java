package com.sunnet.org.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.Role;

/**
 * 
 * <br>
 * <b>功能：</b>Role Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IRoleService extends IBaseService<Role>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */	
	public QueryResult<Role> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Role
	 */
	public void update(Role role,String[] permissions);
	
	/**
	 * 权限
	 * @param Role
	 */
	public List listPermission(Role role);
	
	/**
	 * 新增
	 * @param Role
	 */
	public void save(Role role,String[] permissions);
	
	
	
	
}
