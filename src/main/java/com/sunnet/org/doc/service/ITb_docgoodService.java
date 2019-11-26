package com.sunnet.org.doc.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_docgood;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_docgood Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_docgoodService extends IBaseService<Tb_docgood>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_docgood> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tb_docgood
	 */
	public void update(Tb_docgood tb_docgood);
	
	public void delete(Object[] entityids);
	
	public List<Tb_docgood> listgood(String docid,String memberid,String deviceid);

	/**
	 * author jinhao
	 * @param docid
	 * @param memberId
	 * @return
	 */
	public int  findcountbyId(String docid, String memberId);

	public Integer getmaxId();

	/**
	 * 今天是否点过赞
	 * @param docid
	 * @param opId
	 * @return
	 */
	public int isgood(String docid, String opId);

	/**
	 * 点赞了该作品的所有用户信息
	 * @param docid
	 * @return
	 */
	public List<Object[]> goodDocList(String docid);

	/**
	 * 查询组图信息
	 * @param fid
	 * @return
	 */
	public List<Object[]> groupList(String fid);

	/**
	 * 点赞了本人作品的所有用户信息
	 * @param memberId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Object[]> goodMemDocList(String memberId,String startRow,String endRow);
}
