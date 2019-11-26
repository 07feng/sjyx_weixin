package com.sunnet.org.doc.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tre_menberdocscore;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdocscore Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_menberdocscoreService extends IBaseService<Tre_menberdocscore>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tre_menberdocscore> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tre_menberdocscore
	 */
	public void update(Tre_menberdocscore tre_menberdocscore);
	
	public void delete(Object[] entityids);
	
	public List<Tre_menberdocscore> listScore(Integer groupid);
}
