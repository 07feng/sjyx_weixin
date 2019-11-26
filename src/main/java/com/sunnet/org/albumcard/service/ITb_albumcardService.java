package com.sunnet.org.albumcard.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.albumcard.model.Tb_albumcard;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_albumcard Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_albumcardService extends IBaseService<Tb_albumcard>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_albumcard> list(PageBean pageBean,HttpServletRequest request);

	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_albumcard> Tb_albumcardList(PageBean pageBean,String type);
	
	/**
	 * 更新
	 * @param Tb_albumcard
	 */
	public void update(Tb_albumcard tb_albumcard);
	
	public void delete(Object[] entityids);
}
