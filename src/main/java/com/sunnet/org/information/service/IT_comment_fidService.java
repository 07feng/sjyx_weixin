package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.T_comment_fid;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>T_comment_fid Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IT_comment_fidService extends IBaseService<T_comment_fid>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<T_comment_fid> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param t_comment_fid
	 */
	public void update(T_comment_fid t_comment_fid);
	
	public void delete(Object[] entityids);

	public  int getMaxId();

	/**
	 * 获取二级评论
	 * @param commentId
	 * @return
	 */
	public List<Object[]> findByCommentId(int commentId);

	/**
	 * 保存二级评论
	 * @param Fd_doccomment_id
	 * @param memid
	 * @param cMemid
	 * @param fid_comment
	 */
	public void sqlSave(int Fd_doccomment_id,String memid,String cMemid,String fid_comment);
}
