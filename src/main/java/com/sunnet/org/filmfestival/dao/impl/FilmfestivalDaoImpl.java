package com.sunnet.org.filmfestival.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.filmfestival.dao.IFilmfestivalDao;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.view.model.vie_Friendscircle;


/**
 * 
 * <br>
 * <b>功能：</b>Filmfestival接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class FilmfestivalDaoImpl extends BaseDaoImpl<Filmfestival> implements IFilmfestivalDao
{
    /*批量修改doc
     * (non-Javadoc)
     * @see com.sunnet.org.filmfestival.dao.IFilmfestivalDao#updatedoc(java.lang.String)
     */
	@Override
	public int updatedoc(String sql) {
		return getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public QueryResult<Filmfestival> findval(PageBean pagebean) {
	 
			List<Filmfestival> list = new ArrayList<Filmfestival>();
			String sql="select * from ( "+
                       "(SELECT ROW_NUMBER() OVER (order by vip_id desc )AS row, * from filmfestival where id in (select MIN(f.Id) as id from  filmfestival f where vip_id in "+
                       "(select id from FilmfestivalVIP v where  v.time_length>0 and GETDATE() - v.open_time <=  v.time_length  ) GROUP BY f.Member_id,f.vip_id  ) ))  TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
			String sql1String=" select * from filmfestival where id in (select MIN(f.Id) as id from  filmfestival f where vip_id in "+
                       "(select id from FilmfestivalVIP v where  v.time_length>0 and GETDATE() - v.open_time <=  v.time_length  ) GROUP BY f.Member_id,f.vip_id  ) ";
			List querycount=getSession().createSQLQuery(sql1String).list();
			
			list=getSession().createSQLQuery(sql).addEntity(Filmfestival.class).list();
			pagebean.setTotalRecord(querycount.size());
			pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
			
			QueryResult<Filmfestival> result = new QueryResult<Filmfestival>();
			result.setResultList(list);
			result.setPageBean(pagebean);
			return  result;
		 
	}

	@Override
	public QueryResult<Filmfestival> findmemberval(PageBean pagebean,
			String memberid) {
		List<Filmfestival> list = new ArrayList<Filmfestival>();
		String sql="select * from ( "+
                   "(SELECT ROW_NUMBER() OVER (order by vip_id desc )AS row, * from filmfestival WHERE id IN ( SELECT MIN (f.Id) AS id FROM filmfestival f WHERE vip_id in "+
                   "(select id from FilmfestivalVIP v where v.time_length>0 and GETDATE() - v.open_time <=  v.time_length  ) "+
                   " and  member_id='"+memberid+"' GROUP BY f.vip_id "+
                   "  ) ))  TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		String sql1String=" SELECT * FROM filmfestival WHERE id IN ( SELECT MIN (f.Id) AS id FROM filmfestival f "+
                          " WHERE vip_id in (select id from FilmfestivalVIP v where v.time_length>0 and GETDATE() - v.open_time <=  v.time_length  ) "+
                          " and  member_id='"+memberid+"' GROUP BY f.vip_id )";
		List querycount=getSession().createSQLQuery(sql1String).list();
		
		list=getSession().createSQLQuery(sql).addEntity(Filmfestival.class).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<Filmfestival> result = new QueryResult<Filmfestival>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
	}
	
	@Override
	public QueryResult<Filmfestival> findmemberval_caogao(PageBean pagebean,
			String memberid) {
		List<Filmfestival> list = new ArrayList<Filmfestival>();
		String sql="select * from ( "+
                   "(SELECT ROW_NUMBER() OVER (order by vip_id desc )AS row, * from filmfestival WHERE id IN ( SELECT MIN (f.Id) AS id FROM filmfestival f WHERE vip_id in "+
                   "(select id from FilmfestivalVIP v where v.time_length=0 ) "+
                   " and  member_id='"+memberid+"' GROUP BY f.vip_id "+
                   "  ) ))  TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		String sql1String=" SELECT * FROM filmfestival WHERE id IN ( SELECT MIN (f.Id) AS id FROM filmfestival f "+
                          " WHERE vip_id in (select id from FilmfestivalVIP v where v.time_length=0 ) "+
                          " and  member_id='"+memberid+"' GROUP BY f.vip_id )";
		List querycount=getSession().createSQLQuery(sql1String).list();
		
		list=getSession().createSQLQuery(sql).addEntity(Filmfestival.class).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<Filmfestival> result = new QueryResult<Filmfestival>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
	}

}
