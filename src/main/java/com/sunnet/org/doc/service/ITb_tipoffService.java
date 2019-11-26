package com.sunnet.org.doc.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_tipoff;
import com.sunnet.org.member.model.Tb_memberextract;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_tipoff Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_tipoffService extends IBaseService<Tb_tipoff>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_tipoff> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_tipoff
	 */
	public void update(Tb_tipoff tb_tipoff);
	
	public void delete(Object[] entityids);
	
	/**
	 * 批量修改
	 */
	public void updateStatus(Object[] entityids,String userid);
}
