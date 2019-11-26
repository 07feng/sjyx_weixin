package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_contesttype;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contesttype Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_contesttypeService extends IBaseService<Tb_contesttype>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_contesttype> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_contesttype
	 */
	public void update(Tb_contesttype tb_contesttype);
	
	public void delete(Object[] entityids) ;
	/**
	 * 当赛事类型删除时，赛事表中的类型清空
	 * @param entityids
	 */
	public void updateType(Object[] entityids);

}
