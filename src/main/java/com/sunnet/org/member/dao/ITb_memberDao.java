package com.sunnet.org.member.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_memberextract;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_member接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_memberDao extends IBaseDao<Tb_member>
{
	public void updateStatus(Class<Tb_member> entityClass, String hql);
	public List<Tb_member> getAllByWhere( String hql);
}
