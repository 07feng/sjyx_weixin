package com.sunnet.org.member.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.dao.ITb_memberextractDao;
import com.sunnet.org.member.model.Tb_memberextract;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberextract接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_memberextractDaoImpl extends BaseDaoImpl<Tb_memberextract> implements ITb_memberextractDao
{
	public void updateStatus(Class<Tb_memberextract> entityClass, String hql){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_memberextract set extractstatus=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}
}
