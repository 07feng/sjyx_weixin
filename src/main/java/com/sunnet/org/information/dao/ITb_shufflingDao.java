package com.sunnet.org.information.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.information.model.Tb_shuffling;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_shuffling接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_shufflingDao extends IBaseDao<Tb_shuffling>
{
	public void updateStatus(Class<Tb_shuffling> entityClass,  String hql);
	public void updateStatusok(Class<Tb_shuffling> entityClass,  String hql);
}
