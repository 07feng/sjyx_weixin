package com.sunnet.org.doc.service;

import java.util.List;
import java.util.Map;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.model.Tre_friendscircle;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_friendscircle Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_friendscircleService extends IBaseService<Tre_friendscircle>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tre_friendscircle> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tre_friendscircle
	 */
	public void update(Tre_friendscircle tre_friendscircle);
	
	public void delete(Object[] entityids);
	
	public List<Tre_friendscircle> listfriend(String circlememberid,String memberid);

	/**
	 * 是否关注作者
	 * author jinhao
	 * @param memberid
	 * @param circleMemberId
	 * @return
	 */
	public boolean idFucos(String memberid,String circleMemberId);

	/**
	 * 关注人数
	 * @param memberId
	 * @return
	 */
	public int findCount(String memberId);

	/**
	 * 粉丝数
	 */
	public int findCount2(String memberId);

	public void updateIsfriends(int id,int isfriends);

	/**
	 * A关注B的记录
	 * @param idA
	 * @param idB
	 * @return
	 */
	public List<Object[]> AfocusB(String idA,String idB);

	/**
	 * 修改关注信息
	 * @param memberId
	 * @param userId
	 * @param code
	 */
	public Map changeFollowStatus(String memberId, String userId, String code);
}
