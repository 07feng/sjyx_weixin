package com.sunnet.org.system.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.SystemItem;

/**
 * 
 * <br>
 * <b>功能：</b>SystemItem Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ISystemItemService extends IBaseService<SystemItem>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<SystemItem> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param SystemItem
	 */
	public void update(SystemItem systemItem);
}
