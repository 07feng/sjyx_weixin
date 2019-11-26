package com.sunnet.org.system.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.system.model.Tb_watermark;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_watermark Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_watermarkService extends IBaseService<Tb_watermark>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_watermark> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_watermark
	 */
	public void update(Tb_watermark tb_watermark);
	
	public void delete(Object[] entityids);
}
