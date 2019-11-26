package com.sunnet.org.view.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.view.model.vip_doc_pay;

/**
 * 
 * <br>
 * <b>功能：</b>vip_doc_pay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface Ivip_doc_payService extends IBaseService<vip_doc_pay>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<vip_doc_pay> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param vip_doc_pay
	 */
	public void update(vip_doc_pay vip_doc_pay);
	
	public void delete(Object[] entityids);
}
