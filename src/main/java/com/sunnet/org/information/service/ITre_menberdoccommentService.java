package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tre_menberdoccomment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdoccomment Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_menberdoccommentService extends IBaseService<Tre_menberdoccomment>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tre_menberdoccomment> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tre_menberdoccomment
	 */
	public void update(Tre_menberdoccomment tre_menberdoccomment);
	
	public void delete(Object[] entityids);
	
	public void updateStatus(Object[] entityids);//显示
	public void updateStatus2(Object[] entityids);//不显示

	/**
	 * authorjinhao
	 * @param docid
	 * @return
	 */
	public List<Object[]> getAllByDocId(String docid,int startRow,int endRow);

	public int findCount(List condition);

	public Integer getmaxId();

	public void saveComment(List condition);

	/**
	 * 修改添加评论相关信息
	 * @param comment
	 * @param mem
	 * @param mem1
	 * @param docid
	 * @param memid
	 * @return
	 */
	public Integer addComment(Tre_menberdoccomment comment,Object[] mem,Object[] mem1,String docid,String memid);
}
