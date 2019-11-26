package com.sunnet.org.doc.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.doc.dao.ITb_dockeepDao;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.doc.model.Tb_dockeep;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_dockeep接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_dockeepDaoImpl extends BaseDaoImpl<Tb_dockeep> implements ITb_dockeepDao
{
	@Override
	public List<Tb_dockeep> listkeep(String docid, String memberid) {
		String sql="SELECT * from Tb_dockeep where docid ='"+docid +
				    "' and memberid='"+memberid +"' ";
		if((getSession().createSQLQuery(sql).addEntity(Tb_dockeep.class).list()).size()>0)
			return getSession().createSQLQuery(sql).addEntity(Tb_dockeep.class).list();
		else 
		    return null; 
	}
}
