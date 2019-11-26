package com.sunnet.org.competition.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.competition.model.Tb_contesttheme;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contesttheme Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_contestthemeService extends IBaseService<Tb_contesttheme>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_contesttheme> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_contesttheme
	 */
	public void update(Tb_contesttheme tb_contesttheme);
	
	public void delete(Object[] entityids);
}
