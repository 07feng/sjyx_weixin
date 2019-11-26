package com.sunnet.org.doc.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.model.Tb_groupdocgood;
import com.sunnet.org.doc.model.tb_groupdocgood_view;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_groupdocgood接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_groupdocgoodDao extends IBaseDao<Tb_groupdocgood>
{
	public QueryResult<tb_groupdocgood_view> getGroupdocgood(PageBean pagebean,String memberid);
	
	public List<Tb_groupdocgood> selectId(Class<Tb_groupdocgood> entityClass, String hql);
}
