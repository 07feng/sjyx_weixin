package com.sunnet.org.doc.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.doc.dao.ITre_friendscircleDao;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.model.Tre_friendscircle;


/**
 * 
 * <br>
 * <b>功能：</b>Tre_friendscircle接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tre_friendscircleDaoImpl extends BaseDaoImpl<Tre_friendscircle> implements ITre_friendscircleDao
{

	@Override
	public List<Tre_friendscircle> listfriend(String circlememberid,
			String memberid) {
		String sql="SELECT * from Tre_friendscircle where circlememberid ='"+circlememberid +
			    "' and memberid='"+memberid +"' ";
	if((getSession().createSQLQuery(sql).addEntity(Tre_friendscircle.class).list()).size()>0)
		return getSession().createSQLQuery(sql).addEntity(Tre_friendscircle.class).list();
	else 
	    return null; 
	}

}
