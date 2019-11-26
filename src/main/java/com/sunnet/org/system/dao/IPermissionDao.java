package com.sunnet.org.system.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.system.model.Permission;

/**
 * 
 * <br>
 * <b>功能：</b>Permission接口<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IPermissionDao extends IBaseDao<Permission>
{

	/**
	 * 更新
	 * @param hql
	 */
	public void update(String hql);
	
	/**
	 * 防止 sql 注入的 list 查询
	 * @param hql 
	 * @param values 参数值
	 * @return
	 */
	public List<Permission> findByHQL(String hql,Object... values);

}
