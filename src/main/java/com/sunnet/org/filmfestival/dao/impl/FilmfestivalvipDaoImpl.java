package com.sunnet.org.filmfestival.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvipDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.view.model.vie_Friendscircle;


/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class FilmfestivalvipDaoImpl extends BaseDaoImpl<Filmfestivalvip> implements IFilmfestivalvipDao
{

	/**
	 * 展出名单查询 （isfriends 是否关注，0已关注1互相关注2未关注）
	 */
	@Override
	public QueryResult<vie_Friendscircle> findFriendscircle(PageBean pagebean,
			String memberid) {
		List<vie_Friendscircle> list = new ArrayList<vie_Friendscircle>();
		String sql="select * from ( SELECT *,ROW_NUMBER() OVER ( order by o.open_time desc )AS row,(case when (SELECT isfriends FROM TRE_FriendsCircle WHERE TRE_FriendsCircle.Memberid = '"+memberid
				+" ' AND TRE_FriendsCircle.CircleMemberid = Member_id) is null then 2 "
                +"ELSE (SELECT isfriends FROM TRE_FriendsCircle	WHERE TRE_FriendsCircle.Memberid = '"+memberid+" ' AND TRE_FriendsCircle.CircleMemberid = Member_id) end) as isfriends "
	            +" from Filmfestivalvip o "
                +" where 1=1  and GETDATE()-o.open_time <=  o.time_length ) TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		
		String sql1String=" select  * from Filmfestivalvip o where 1=1 and GETDATE()-o.open_time <=  o.time_length order by o.sort";
		List querycount=getSession().createSQLQuery(sql1String).list();
		
		list=getSession().createSQLQuery(sql).addEntity(vie_Friendscircle.class).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<vie_Friendscircle> result = new QueryResult<vie_Friendscircle>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
	}
	
}
