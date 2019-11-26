package com.sunnet.org.space.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.space.model.Tb_space;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_space接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_spaceDao extends IBaseDao<Tb_space>
{
	/**
	 * 审核影像管理
	 * @param entityClass
	 * @param hql
	 */
	public void updateStatus(Class<Tb_space> entityClass, String hql);
}
