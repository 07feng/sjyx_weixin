package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_shuffling;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_shuffling Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_shufflingService extends IBaseService<Tb_shuffling>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_shuffling> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_shuffling
	 */
	public void update(Tb_shuffling tb_shuffling);
	
	public void delete(Object[] entityids);
	
	public void updateStatusok(Object[] entityids);
	public void updateStatus(Object[] entityids);
}
