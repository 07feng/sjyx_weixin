package com.sunnet.org.space.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.space.dao.ITb_spaceDao;
import com.sunnet.org.space.model.Tb_space;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_space接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_spaceDaoImpl extends BaseDaoImpl<Tb_space> implements ITb_spaceDao
{
	public void updateStatus(Class<Tb_space> entityClass, String hql){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_space set sppacestatus=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}
}
