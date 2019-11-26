package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_remark;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_remark Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_remarkService extends IBaseService<Tb_remark>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_remark> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_remark
	 */
	public void update(Tb_remark tb_remark);
	
	public void delete(Object[] entityids);
}
