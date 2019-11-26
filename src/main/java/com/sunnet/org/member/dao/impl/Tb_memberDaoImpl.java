package com.sunnet.org.member.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_memberextract;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_member接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_memberDaoImpl extends BaseDaoImpl<Tb_member> implements ITb_memberDao
{

	public void updateStatus(Class<Tb_member> entityClass, String hql){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_member set status=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}

	@Override
	public List<Tb_member> getAllByWhere(String sql) {
		Query query = getSession().createQuery(sql);
		return query.list();
	}

}
