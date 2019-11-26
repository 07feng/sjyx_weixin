package com.sunnet.org.competition.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.competition.model.Tre_contestaward;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_contestaward Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_contestawardService extends IBaseService<Tre_contestaward>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tre_contestaward> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tre_contestaward
	 */
	public void update(Tre_contestaward tre_contestaward);
	
	public void delete(Object[] entityids);
}
