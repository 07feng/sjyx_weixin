package com.sunnet.org.visitors.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.visitors.model.T_behavior;

/**
 * 
 * <br>
 * <b>功能：</b>T_behavior Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IT_behaviorService extends IBaseService<T_behavior>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<T_behavior> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param T_behavior
	 */
	public void update(T_behavior t_behavior);
	
	public void delete(Object[] entityids);
}
