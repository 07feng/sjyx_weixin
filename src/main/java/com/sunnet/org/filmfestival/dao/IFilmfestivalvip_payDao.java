package com.sunnet.org.filmfestival.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_pay;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_pay接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvip_payDao extends IBaseDao<Filmfestivalvip_pay>
{
	public QueryResult<Filmfestivalvip_pay> getPages(PageBean pagebean,String wherename,String TbName,String TbId,String order,Class c);
	
}
