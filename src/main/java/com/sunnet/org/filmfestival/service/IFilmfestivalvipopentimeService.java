package com.sunnet.org.filmfestival.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.filmfestival.model.Filmfestivalvipopentime;
import com.sunnet.org.view.model.vie_Friendscircle;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvipopentime Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvipopentimeService extends IBaseService<Filmfestivalvipopentime>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Filmfestivalvipopentime> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Filmfestivalvipopentime
	 */
	public void update(Filmfestivalvipopentime filmfestivalvipopentime);
	
	public void delete(Object[] entityids);
	
	
}
