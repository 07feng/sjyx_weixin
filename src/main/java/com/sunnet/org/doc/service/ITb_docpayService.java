package com.sunnet.org.doc.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.view.model.vie_docpay;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_docpay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_docpayService extends IBaseService<Tb_docpay>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_docpay> list(PageBean pageBean,HttpServletRequest request);
	public QueryResult<vie_docpay> listf(PageBean pageBean,HttpServletRequest request);

	public List payList(PageBean pageBean, String memberId, int code);
	
	/**
	 * 更新
	 * @param tb_docpay
	 */
	public void update(Tb_docpay tb_docpay);
	
	public void delete(Object[] entityids);
	
	public QueryResult<Tb_docpay> getPages(PageBean pagebean,String wherename,String TbName,String TbId,String order,Class c);

	public int findCount(String memberId);
}
