package com.sunnet.org.competition.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.space.model.Tb_space;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contest接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_contestDao extends IBaseDao<Tb_contest>
{
	public void updateStatus(Class<Tb_contest> entityClass,  String hql);
}
