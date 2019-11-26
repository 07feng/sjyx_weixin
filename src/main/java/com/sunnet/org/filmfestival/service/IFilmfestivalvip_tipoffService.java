package com.sunnet.org.filmfestival.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_tipoff;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_tipoff Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvip_tipoffService extends IBaseService<Filmfestivalvip_tipoff>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Filmfestivalvip_tipoff> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Filmfestivalvip_tipoff
	 */
	public void update(Filmfestivalvip_tipoff filmfestivalvip_tipoff);
	
	public void delete(Object[] entityids);
}
