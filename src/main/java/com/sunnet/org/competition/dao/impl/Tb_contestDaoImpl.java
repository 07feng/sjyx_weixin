package com.sunnet.org.competition.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.competition.dao.ITb_contestDao;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.space.model.Tb_space;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_contest接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_contestDaoImpl extends BaseDaoImpl<Tb_contest> implements ITb_contestDao
{

	@Override
	public void updateStatus(Class<Tb_contest> entityClass, String hql) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_contest set isrelease=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
		
	}

}
