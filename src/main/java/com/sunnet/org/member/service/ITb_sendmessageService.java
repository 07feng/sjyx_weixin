package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_sendmessage;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_sendmessage Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_sendmessageService extends IBaseService<Tb_sendmessage>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_sendmessage> list(PageBean pageBean,HttpServletRequest request);
	public QueryResult<Tb_sendmessage> listapp(PageBean pageBean,HttpServletRequest request);

	/**
	 * 分页获取系统信息
	 * @param pageBean
	 * @param memberId
	 * @return
	 */
	public QueryResult watchList(PageBean pageBean,String memberId);
	/**
	 * 更新
	 * @param tb_sendmessage
	 */
	public void update(Tb_sendmessage tb_sendmessage);
	
	public void delete(Object[] entityids);
}
