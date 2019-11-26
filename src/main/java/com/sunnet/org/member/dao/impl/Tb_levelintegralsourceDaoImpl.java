package com.sunnet.org.member.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.member.dao.ITb_levelintegralsourceDao;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_levelintegralsource接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_levelintegralsourceDaoImpl extends BaseDaoImpl<Tb_levelintegralsource> implements ITb_levelintegralsourceDao
{

	@Override
	public List<Tb_levelintegralsource> listlevel(int levelid) {
		String sql="SELECT * from Tb_levelintegralsource where levelid = "+ levelid;
		if((getSession().createSQLQuery(sql).addEntity(Tb_levelintegralsource.class).list()).size()>0)
			return getSession().createSQLQuery(sql).addEntity(Tb_levelintegralsource.class).list();
		else 
		    return null; 
	}

}
