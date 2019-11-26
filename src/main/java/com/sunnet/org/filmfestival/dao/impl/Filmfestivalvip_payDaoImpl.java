package com.sunnet.org.filmfestival.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_payDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_pay;


/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_pay接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Filmfestivalvip_payDaoImpl extends BaseDaoImpl<Filmfestivalvip_pay> implements IFilmfestivalvip_payDao
{

	@Override
	public QueryResult<Filmfestivalvip_pay> getPages(PageBean pagebean,
			String wherename, String TbName, String TbId, String order, Class c) {
		List<Filmfestivalvip_pay> list = new ArrayList<Filmfestivalvip_pay>();
		String sql="select * from ( SELECT ROW_NUMBER() OVER ( "+order+")AS row,*  FROM "+
			" Filmfestivalvip_pay WHERE id in "+
			"(select max(id)	FROM  Filmfestivalvip_pay  "+wherename+" GROUP BY memberid)) TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		String sql1String=" select  "+TbId+"  from Filmfestivalvip_pay WHERE id in (select max(id) FROM Filmfestivalvip_pay "+wherename+" GROUP BY memberid )";
		List querycount=getSession().createSQLQuery(sql1String).list();
		
		list=getSession().createSQLQuery(sql).addEntity(c).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<Filmfestivalvip_pay> result = new QueryResult<Filmfestivalvip_pay>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
	}

}
