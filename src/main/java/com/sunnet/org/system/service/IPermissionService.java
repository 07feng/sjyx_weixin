package com.sunnet.org.system.service;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.Permission;

/**
 * 
 * <br>
 * <b>功能：</b>Permission Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IPermissionService extends IBaseService<Permission>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Permission> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Permission
	 */
	public void update(Permission permission);
}
