package com.sunnet.org.fileserver.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.fileserver.model.Tb_fileserver;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_fileserver Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_fileserverService extends IBaseService<Tb_fileserver>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_fileserver> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_fileserver
	 */
	public void update(Tb_fileserver tb_fileserver);
	
	public void delete(Object[] entityids);
}
