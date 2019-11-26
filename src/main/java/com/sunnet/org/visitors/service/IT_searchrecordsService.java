package com.sunnet.org.visitors.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.visitors.model.T_searchrecords;

/**
 * 
 * <br>
 * <b>功能：</b>T_searchrecords Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IT_searchrecordsService extends IBaseService<T_searchrecords>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<T_searchrecords> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param T_searchrecords
	 */
	public void update(T_searchrecords t_searchrecords);
	
	public void delete(Object[] entityids);
}
