package com.sunnet.org.filmfestival.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_comment Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvip_commentService extends IBaseService<Filmfestivalvip_comment>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Filmfestivalvip_comment> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Filmfestivalvip_comment
	 */
	public void update(Filmfestivalvip_comment filmfestivalvip_comment);
	
	public void delete(Object[] entityids);
}
