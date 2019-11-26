package com.sunnet.org.member.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.member.model.Tb_contesttype;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contesttype接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_contesttypeDao extends IBaseDao<Tb_contesttype>
{
	public void updateType(Class<Tb_contest> entityClass,  String hql);
}
