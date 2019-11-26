package com.sunnet.org.filmfestival.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.view.model.vie_Friendscircle;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvipDao extends IBaseDao<Filmfestivalvip>
{
	public QueryResult<vie_Friendscircle> findFriendscircle(PageBean pagebean,String memberid);
	
}
