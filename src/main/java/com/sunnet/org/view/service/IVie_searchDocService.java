package com.sunnet.org.view.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.view.model.Vie_searchdoc;

/**
 * 
 * <br>
 * <b>功能：</b>Vie_searchDoc Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IVie_searchDocService extends IBaseService<Vie_searchdoc>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Vie_searchdoc> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Vie_searchDoc
	 */
	public void update(Vie_searchdoc vie_searchDoc);
	
	public void delete(Object[] entityids);
	
	public String selectCreate(String str,List<Tb_filetype> type);
}
