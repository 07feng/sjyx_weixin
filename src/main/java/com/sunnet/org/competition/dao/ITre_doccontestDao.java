package com.sunnet.org.competition.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.information.model.Tb_doc;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_doccontest接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_doccontestDao extends IBaseDao<Tre_doccontest>
{
	public void updateStatus(Class<Tre_doccontest> entityClass, String hql);
	public void updateStatus2(Class<Tre_doccontest> entityClass, String hql);
	public void updateStatus3(Class<Tre_doccontest> entityClass, String hql);
	
	public int updateSql(Integer id);
}
