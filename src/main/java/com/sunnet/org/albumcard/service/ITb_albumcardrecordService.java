package com.sunnet.org.albumcard.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.albumcard.model.Tb_albumcardrecord;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_albumcardrecord Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_albumcardrecordService extends IBaseService<Tb_albumcardrecord>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_albumcardrecord> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_albumcardrecord
	 */
	public void update(Tb_albumcardrecord tb_albumcardrecord);
	
	public void delete(Object[] entityids, String menberId);
}
