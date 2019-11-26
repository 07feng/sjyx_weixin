package com.sunnet.org.competition.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.competition.dao.ITre_doccontestDao;
import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.information.model.Tb_doc;


/**
 * 
 * <br>
 * <b>功能：</b>Tre_doccontest接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tre_doccontestDaoImpl extends BaseDaoImpl<Tre_doccontest> implements ITre_doccontestDao
{
	public void updateStatus(Class<Tre_doccontest> entityClass, String hql){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tre_doccontest set audit_status=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}
	
	public void updateStatus2(Class<Tre_doccontest> entityClass, String hql){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tre_doccontest set audit_status=2 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}
	public void updateStatus3(Class<Tre_doccontest> entityClass, String hql){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tre_doccontest set is_shortlisted=1,audit_status=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}

	@Override
	public int updateSql(Integer id) {
		String sqls="update TRE_DocContest set award_id=null where id="+id ;
		Query q= getSession().createSQLQuery(sqls);
		return q.executeUpdate();
	}
}
