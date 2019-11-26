package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_memberextract;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberextract Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_memberextractService extends IBaseService<Tb_memberextract>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_memberextract> list(PageBean pageBean,HttpServletRequest request);
	public void updateStatus(Object[] entityids);

	public QueryResult<Tb_memberextract> memberList(PageBean pageBean, String memberId);
	/**
	 * 更新
	 * @param Tb_memberextract
	 */
	public void update(Tb_memberextract tb_memberextract);
	
	public void delete(Object[] entityids);
}
