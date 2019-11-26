package com.sunnet.org.system.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.Tb_package;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_package Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_packageService extends IBaseService<Tb_package>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_package> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_package
	 */
	public void update(Tb_package tb_package);
	
	public void delete(Object[] entityids);
}
