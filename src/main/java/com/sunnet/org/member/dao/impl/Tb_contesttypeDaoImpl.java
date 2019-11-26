package com.sunnet.org.member.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.member.dao.ITb_contesttypeDao;
import com.sunnet.org.member.model.Tb_contesttype;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_contesttype接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_contesttypeDaoImpl extends BaseDaoImpl<Tb_contesttype> implements ITb_contesttypeDao
{

	@Override
	public void updateType(Class<Tb_contest> entityClass, String hql) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_contest set contesttypeid=null where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
		
		
	}

}
