package com.sunnet.org.filmfestival.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.app.entity.AddFilm;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.information.model.Tb_doc;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestival Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalService extends IBaseService<Filmfestival>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Filmfestival> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param filmfestival
	 */
	public void update(Filmfestival filmfestival);

	/**
	 * 首页影展列表
	 * @return
	 */
	public List<Object[]> festivalList(String memberId,int startRow,int endRow,String code);

    /**
     * 我的影展列表
     * @param memberId
     * @param startRow
     * @param endRow
     * @param code
     * @return
     */
    public List<Object[]> festivalListForMine(String memberId,int startRow,int endRow,String code);

	/**
	 * 影展数量
	 * @return
	 */
	public int festivalCount(String memberId);

	/**
	 * 影展列表
	 * @return
	 */
	public List<Object[]> recommendFestivalList(int startRow,int endRow,String code);

	/**
	 * 影展封面
	 * @param worksShowId
	 * @return
	 */
	public List<Object[]> festivalCover(String worksShowId);

	/**
	 * 影展详情
	 * @param vip_id
	 * @return
	 */
	public  List<Object[]> festivalDetail(String vip_id);

	/**
	 * 添加影展 以及作品信息
	 * @param list
	 * @param worksDesc
	 * @param worksTitle
	 * @param memberId
	 * @return
	 */
	public String addFilm (Tb_doc tb_doc,Object[] memInfo,List<AddFilm> list,String worksDesc,String worksTitle,String memberId,int count,String worksShowId,String space,String isPublic,String musicid,String code,String spotId,String coverurl);

	/**
	 * 修改影展
	 * @param list
	 * @param tb_doc
	 * @param vip_id
	 * @param count
	 * @param memberId
	 * @param worksTitle
	 * @param worksDesc
	 */
	public void updateFilm(List<AddFilm> list,Tb_doc tb_doc,int vip_id,int count,String memberId,String worksDesc,String worksTitle,String space,String isPublic,String musicid,String code,String coverurl);

	/**
	 * 影展点赞用户列表
	 * @param vip_id
	 * @return
	 */
	public List<Object[]> goodList(Integer vip_id,int startRow,int endRow);


	/**
	 * 查询用户今天是否点过赞
	 * @param vip_id
	 * @param memberId
	 * @param opId
	 * @return
	 */
	public int isGood(String vip_id,String memberId,String opId);

	
	public void delete(Object[] entityids);
	public void deletevipid(Integer entityids);
	public int updatedoc(String sql);
	
	public QueryResult<Filmfestival> findval(PageBean pagebean) ;
	public QueryResult<Filmfestival> findmemberval(PageBean pagebean,String memberid) ;
	
	public QueryResult<Filmfestival> findmemberval_caogao(PageBean pagebean,String memberid) ;
}
