package com.sunnet.org.visitors.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.visitors.model.T_topsearchterm;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>T_topsearchterm Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IT_topsearchtermService extends IBaseService<T_topsearchterm>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<T_topsearchterm> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param t_topsearchterm
	 */
	public void update(T_topsearchterm t_topsearchterm);
	
	public void delete(Object[] entityids);

	/**
	 * author jinhao
	 * 获取热门搜索词
	 * @param dateRange
	 * @return
	 */
	public List<T_topsearchterm> getByDate(String dateRange);
}
