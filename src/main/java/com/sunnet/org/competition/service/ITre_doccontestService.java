package com.sunnet.org.competition.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.competition.model.Tre_doccontest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_doccontest Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_doccontestService extends IBaseService<Tre_doccontest>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tre_doccontest> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tre_doccontest
	 */
	public void update(Tre_doccontest tre_doccontest);
	
	public void delete(Object[] entityids);
	/**
	 * 审核
	 * @param entityids
	 */
	public void updateStatus(Object[] entityids);//通过
	public void updateStatus2(Object[] entityids);//不通过
	public void updateStatus3(Object[] entityids);//入围
	public int updateSql(Integer id);

	public void updateSql2(String sql);


	/**
	 * author jinhao
	 * @param docid
	 * @return
	 */
	public List<Object[]> getByDocid(String docid);
}
