package com.sunnet.org.system.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.Menu;

/**
 * 
 * <br>
 * <b>功能：</b>Menu Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IMenuService extends IBaseService<Menu>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Menu> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Member
	 */
	public void update(Menu menu);
}
