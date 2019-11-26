package com.sunnet.org.system.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.Tb_siteconfig;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_siteconfig Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_siteconfigService extends IBaseService<Tb_siteconfig>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_siteconfig> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_siteconfig
	 */
	public void update(Tb_siteconfig tb_siteconfig);
	
	public void delete(Object[] entityids);
}
