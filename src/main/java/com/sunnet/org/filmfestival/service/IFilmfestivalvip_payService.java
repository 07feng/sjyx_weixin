package com.sunnet.org.filmfestival.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_pay;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_pay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvip_payService extends IBaseService<Filmfestivalvip_pay>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Filmfestivalvip_pay> list(PageBean pageBean,HttpServletRequest request);
	public QueryResult<Filmfestivalvip_pay> lists(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param filmfestivalvip_pay
	 */
	public void update(Filmfestivalvip_pay filmfestivalvip_pay);
	
	public void delete(Object[] entityids);
	
	public QueryResult<Filmfestivalvip_pay> getPages(PageBean pagebean,String wherename,String TbName,String TbId,String order,Class c);

	public int findCount(String memberId);

}
