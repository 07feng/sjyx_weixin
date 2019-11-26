package com.sunnet.org.feedback.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.feedback.model.Tb_feedback;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_feedback Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_feedbackService extends IBaseService<Tb_feedback>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_feedback> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_feedback
	 */
	public void update(Tb_feedback tb_feedback);
	
	public void delete(Object[] entityids);
}
