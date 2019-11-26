package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_messagetemplate;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_messagetemplate Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_messagetemplateService extends IBaseService<Tb_messagetemplate>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_messagetemplate> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_messagetemplate
	 */
	public void update(Tb_messagetemplate tb_messagetemplate);
	
	public void delete(Object[] entityids);
}
