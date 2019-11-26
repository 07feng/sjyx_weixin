package com.sunnet.org.doc.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_dockeep;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_dockeep Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_dockeepService extends IBaseService<Tb_dockeep>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_dockeep> list(PageBean pageBean,HttpServletRequest request);


	/**
	 * 点赞
	 * @param docId
	 * @param collect
	 */
	public void collect(String docId, int collect,String memberId);

	/**
	 * 更新
	 * @param tb_dockeep
	 */
	public void update(Tb_dockeep tb_dockeep);
	
	public void delete(Object[] entityids);

	public void deleteByHql(String hql);
	
	public List<Tb_dockeep> listkeep(String docid,String memberid);

	/**
	 * 个人收藏数
	 * @param memberId
	 * @return
	 */
	public int findCount(String memberId);

	/**
	 * 是否收藏该作品
	 * @param docid
	 * @param membetId
	 * @return
	 */
	public int isKeepDocId(String docid,String membetId);

	/**
	 * 个人收藏作品信息
	 * @param memberId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Object[]> keepDocList(String memberId, int startRow,int endRow);
}
