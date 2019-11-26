package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_memberrecharge;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberrecharge Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_memberrechargeService extends IBaseService<Tb_memberrecharge>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_memberrecharge> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_memberrecharge
	 */
	public void update(Tb_memberrecharge tb_memberrecharge);
	
	public void delete(Object[] entityids);
}
