package com.sunnet.org.filmfestival.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.view.model.vie_Friendscircle;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvipService extends IBaseService<Filmfestivalvip>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Filmfestivalvip> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param filmfestivalvip
	 */
	public void update(Filmfestivalvip filmfestivalvip);
	
	public void delete(Object[] entityids);
	public QueryResult<vie_Friendscircle> findFriendscircle(PageBean pagebean,String memberid);

	public Filmfestivalvip getByKey(String ffvId);

	/**
	 * 发布影展
	 * @param vip_id
	 */
	public void publishShow(int vip_id,Integer code);

	/**
	 * 删除影展
	 * @param vip_id
	 */
	public void delShow(List<Object[]> result,String vip_id);

	/**
	 * 修改浏览数量
	 * @param viewnum
	 * @param vip_id
	 */
	public void updateViewnum(int viewnum,int vip_id);

	/**
	 * 影展评论列表
	 * @param vip_id
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Object[]> getByVip_id(String vip_id,Integer startRow,Integer endRow);

    /**
     * 二级评论
     * @param commentId
     * @return
     */
    public List<Object[]> findByCommentId(Integer commentId);

    /**
     * 影展用户等级信息
     * @param worksShowId
     * @return
     */
    public List<Object[]> getFilmMemLevel(Integer worksShowId);

    /**
     * 添加影展评论
     * @param comment1
     * @param mem
     * @param mem1
     * @param vip_id
     * @param memberId
     * @return
     */
    public Integer addComment(Filmfestivalvip_comment comment1, Object[] mem, Object[] mem1, Integer vip_id, String memberId);

	/**
	 * 用户关注影集列表
	 * @param memberid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    public List<Object[]> friendFilm(String memberid,String startTime,String endTime);

}
