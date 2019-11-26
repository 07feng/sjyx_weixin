package com.sunnet.org.doc.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.doc.dao.ITb_tipoffDao;
import com.sunnet.org.doc.model.Tb_tipoff;
import com.sunnet.org.member.model.Tb_memberextract;
import com.sunnet.org.util.UserUtil;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_tipoff接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_tipoffDaoImpl extends BaseDaoImpl<Tb_tipoff> implements ITb_tipoffDao
{
	public void updateStatus(Class<Tb_tipoff> entityClass, String hql,String user){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_tipoff set status=1,usertime=GETDATE(),user_id='"+user+" ' where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}
}
