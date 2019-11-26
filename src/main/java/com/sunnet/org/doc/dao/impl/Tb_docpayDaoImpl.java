package com.sunnet.org.doc.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.dao.ITb_docpayDao;
import com.sunnet.org.doc.model.Tb_docpay;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_docpay接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_docpayDaoImpl extends BaseDaoImpl<Tb_docpay> implements ITb_docpayDao
{

	@Override
	public QueryResult<Tb_docpay> getPages(PageBean pagebean, String wherename,
			String TbName, String TbId, String order, Class c) {
		List<Tb_docpay> list = new ArrayList<Tb_docpay>();
		String sql="select * from ( SELECT ROW_NUMBER() OVER ( "+order+")AS row,*  FROM "+
			" Tb_docpay WHERE id in "+
			"(select max(id)	FROM  Tb_docpay  "+wherename+" GROUP BY memberid)) TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		String sql1String=" select  "+TbId+"  from Tb_docpay WHERE id in (select max(id) FROM Tb_docpay "+wherename+" GROUP BY memberid )";
		List querycount=getSession().createSQLQuery(sql1String).list();
		
		list=getSession().createSQLQuery(sql).addEntity(c).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<Tb_docpay> result = new QueryResult<Tb_docpay>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
	}
}
