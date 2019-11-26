package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_memberparameter;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberparameter Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_memberparameterService extends IBaseService<Tb_memberparameter>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_memberparameter> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tb_memberparameter
	 */
	public void update(Tb_memberparameter tb_memberparameter);
	
	public void delete(Object[] entityids);
}
