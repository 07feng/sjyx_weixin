package com.sunnet.org.view.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.view.model.vie_vippay;

/**
 * 
 * <br>
 * <b>功能：</b>vie_vippay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface Ivie_vippayService extends IBaseService<vie_vippay>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<vie_vippay> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param vie_vippay
	 */
	public void update(vie_vippay vie_vippay);
	
	public void delete(Object[] entityids);
}
