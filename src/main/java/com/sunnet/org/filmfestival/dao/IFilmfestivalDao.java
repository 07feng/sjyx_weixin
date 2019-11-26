package com.sunnet.org.filmfestival.dao;

import java.util.ArrayList;
import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.view.model.vie_Friendscircle;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestival接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalDao extends IBaseDao<Filmfestival>
{
	public int updatedoc(String sql);
	
	 
	public QueryResult<Filmfestival> findval(PageBean pagebean) ;
	public QueryResult<Filmfestival> findmemberval(PageBean pagebean,String memberid) ;
	public QueryResult<Filmfestival> findmemberval_caogao(PageBean pagebean,String memberid) ;
}
