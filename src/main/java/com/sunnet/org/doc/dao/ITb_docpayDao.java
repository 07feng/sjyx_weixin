package com.sunnet.org.doc.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.model.Tb_docpay;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_docpay接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_docpayDao extends IBaseDao<Tb_docpay>
{
	public QueryResult<Tb_docpay> getPages(PageBean pagebean,String wherename,String TbName,String TbId,String order,Class c);
	
}
