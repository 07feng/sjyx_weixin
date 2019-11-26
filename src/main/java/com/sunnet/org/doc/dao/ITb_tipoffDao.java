package com.sunnet.org.doc.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.doc.model.Tb_tipoff;
import com.sunnet.org.member.model.Tb_memberextract;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_tipoff接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_tipoffDao extends IBaseDao<Tb_tipoff>
{
	public void updateStatus(Class<Tb_tipoff> entityClass, String hql,String user);
}
