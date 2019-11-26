package com.sunnet.org.member.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_memberextract;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberextract接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_memberextractDao extends IBaseDao<Tb_memberextract>
{
	public void updateStatus(Class<Tb_memberextract> entityClass, String hql);
}
