package com.sunnet.org.doc.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_appstartpage;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_appstartpage Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_appstartpageService extends IBaseService<Tb_appstartpage>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_appstartpage> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_appstartpage
	 */
	public void update(Tb_appstartpage tb_appstartpage);
	
	public void delete(Object[] entityids);
}
