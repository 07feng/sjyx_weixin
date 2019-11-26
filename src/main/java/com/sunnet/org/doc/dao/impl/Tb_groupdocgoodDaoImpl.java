package com.sunnet.org.doc.dao.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.dao.ITb_groupdocgoodDao;
import com.sunnet.org.doc.model.Tb_groupdocgood;
import com.sunnet.org.doc.model.tb_groupdocgood_view;
import com.sunnet.org.information.model.Tb_doc;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_groupdocgood接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_groupdocgoodDaoImpl extends BaseDaoImpl<Tb_groupdocgood> implements ITb_groupdocgoodDao
{
	@Override
	public QueryResult<tb_groupdocgood_view> getGroupdocgood(PageBean pagebean,String memberid) {
		List<tb_groupdocgood_view> list = new ArrayList<tb_groupdocgood_view>();
		String sql="select * from ( SELECT ROW_NUMBER() OVER ( order by td.ContestStartTime desc)AS row,td.* FROM "+
					"( "+
					"select contest.*,TB_Contest.ContestName,TB_Contest.ContestStatus,TB_Contest.ContestStartTime from ( "+
					"select contest_id,count(Doc_id) as DocCount from TB_GroupDocGood where Member_id='" + memberid + "' "+
					"GROUP BY contest_id ) as contest "+
					"left join  TB_Contest on TB_Contest.Id = contest.contest_id "+
					") as td) TT "+
					"WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		/*String sql1String="select contest.*,TB_Contest.ContestName,TB_Contest.ContestStatus,TB_Contest.ContestStartTime from ( "+
					"select contest_id,count(Doc_id) as DocCount from TB_GroupDocGood where Member_id='" + memberid + "' "+
					"GROUP BY contest_id ) as contest "+
					"left join  TB_Contest on TB_Contest.Id = contest.contest_id ";*/
		List querycount=getSession().createSQLQuery(sql).list();
		List<tb_groupdocgood_view> resultList = new ArrayList<tb_groupdocgood_view>();
		for (Iterator iterator = querycount.iterator(); iterator.hasNext();) {
 			 Object[] obj =(Object[]) iterator.next();
 			tb_groupdocgood_view view=new tb_groupdocgood_view(); 
 		 	view.setContest_id(Integer.parseInt(obj[1].toString()));
 		 	view.setDoccount(Integer.parseInt(obj[2].toString()));
 		 	view.setContestName(obj[3].toString());
 		 	view.setContestStatus(Integer.parseInt(obj[4].toString()));
 		 	resultList.add(view);
		}
		pagebean.setTotalRecord(resultList.size());
		pagebean.setTotalPage((int)Math.ceil(resultList.size()/(double)pagebean.getPageSize()));
		QueryResult<tb_groupdocgood_view> result = new QueryResult<tb_groupdocgood_view>();
		result.setResultList(resultList);
		result.setPageBean(pagebean);
		return  result;
	}

	@Override
	public List<Tb_groupdocgood> selectId(Class<Tb_groupdocgood> entityClass, String hql) {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" select * from Tb_groupdocgood where 1=1 and ");
		stringBuilder.append(hql);
		List<Tb_groupdocgood> list=getSession().createSQLQuery(stringBuilder.toString()).addEntity(Tb_groupdocgood.class).list(); 
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
		/*Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.list();*/
	}
	
 
}
