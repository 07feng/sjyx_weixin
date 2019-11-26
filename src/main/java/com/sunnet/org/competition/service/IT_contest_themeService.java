package com.sunnet.org.competition.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.competition.model.T_contest_theme;

/**
 * 
 * <br>
 * <b>功能：</b>T_contest_theme Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IT_contest_themeService extends IBaseService<T_contest_theme>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<T_contest_theme> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param T_contest_theme
	 */
	public void update(T_contest_theme t_contest_theme);
	
	public void delete(Object[] entityids);
}
