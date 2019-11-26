package com.sunnet.org.pay.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.pay.model.Tb_rechargerecord;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_rechargerecord Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_rechargerecordService extends IBaseService<Tb_rechargerecord>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_rechargerecord> list(PageBean pageBean,HttpServletRequest request);

	public QueryResult<Tb_rechargerecord> memberList(PageBean pageBean,String memberId);
	
	/**
	 * 更新
	 * @param Tb_rechargerecord
	 */
	public void update(Tb_rechargerecord tb_rechargerecord);

	public void updateRecord(String aliNo,Double reamount,String roNO);
	
	public void delete(Object[] entityids);
}
