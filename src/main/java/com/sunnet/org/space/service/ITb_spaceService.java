package com.sunnet.org.space.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.space.model.Tb_space;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_space Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_spaceService extends IBaseService<Tb_space>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_space> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_space
	 */
	public void update(Tb_space tb_space);
	
	public void delete(Object[] entityids);
	
	/**
	 * 审核
	 * @param entityids
	 */
	public void updateStatus(Object[] entityids);
}
