package com.sunnet.org.information.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.information.dao.ITb_shufflingDao;
import com.sunnet.org.information.model.Tb_shuffling;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_shuffling接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_shufflingDaoImpl extends BaseDaoImpl<Tb_shuffling> implements ITb_shufflingDao
{

	@Override
	public void updateStatus(Class<Tb_shuffling> entityClass, String hql) {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_shuffling set isshow=0 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}

	@Override
	public void updateStatusok(Class<Tb_shuffling> entityClass, String hql) {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_shuffling set isshow=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}

}
