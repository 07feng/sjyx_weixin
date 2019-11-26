package com.sunnet.org.system.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_link;
import com.sunnet.org.system.model.SysLogItem;

/**
 * 
 * <br>
 * <b>功能：</b>SysLogItem Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ISysLogItemService extends IBaseService<SysLogItem>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<SysLogItem> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param SysLogItem
	 */
	public void update(SysLogItem sysLogItem);
	
	public void delete(Object[] entityids);
}
