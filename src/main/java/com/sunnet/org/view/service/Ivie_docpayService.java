package com.sunnet.org.view.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.view.model.vie_docpay;

/**
 * 
 * <br>
 * <b>功能：</b>vie_docpay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface Ivie_docpayService extends IBaseService<vie_docpay>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<vie_docpay> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param vie_docpay
	 */
	public void update(vie_docpay vie_docpay);
	
	public void delete(Object[] entityids);
}
