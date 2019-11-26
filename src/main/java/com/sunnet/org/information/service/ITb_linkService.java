package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_link;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_link Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_linkService extends IBaseService<Tb_link>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_link> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_link
	 */
	public void update(Tb_link tb_link);
	
	public void delete(Object[] entityids);
}
