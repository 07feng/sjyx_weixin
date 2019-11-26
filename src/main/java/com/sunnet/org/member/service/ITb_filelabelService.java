package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.member.model.Tre_docfilelabel;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_filelabel Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_filelabelService extends IBaseService<Tb_filelabel>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_filelabel> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tb_filelabel
	 */
	public void update(Tb_filelabel tb_filelabel);
	
	public void delete(Object[] entityids);
}
