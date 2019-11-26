package com.sunnet.org.member.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tre_docfilelabel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_docfilelabel Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_docfilelabelService extends IBaseService<Tre_docfilelabel>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tre_docfilelabel> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tre_docfilelabel
	 */
	public void update(Tre_docfilelabel tre_docfilelabel);
	
	public void delete(Object[] entityids);
	
	public void deletedoclabel(String entityids);

	/**
	 * author jinhao
	 * @param docid
	 * @return
	 */
	public List findByDocId(String docid);
}
