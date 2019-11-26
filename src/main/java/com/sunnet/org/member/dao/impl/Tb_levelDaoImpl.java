package com.sunnet.org.member.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.member.dao.ITb_levelDao;
import com.sunnet.org.member.model.Tb_level;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_level接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_levelDaoImpl extends BaseDaoImpl<Tb_level> implements ITb_levelDao
{

	@Override
	public List<Tb_level> listlevel(String exp) {
		String sql="SELECT * from Tb_level where minexp <='"+exp+"' and '"+exp+"'<=maxexp" ;
		if((getSession().createSQLQuery(sql).addEntity(Tb_level.class).list()).size()>0)
			return getSession().createSQLQuery(sql).addEntity(Tb_level.class).list();
		else 
		    return null; 
	}

	 

}
