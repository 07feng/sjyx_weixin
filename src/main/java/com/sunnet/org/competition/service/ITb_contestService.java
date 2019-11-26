package com.sunnet.org.competition.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.competition.model.Tb_contest;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contest Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_contestService extends IBaseService<Tb_contest>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_contest> list(PageBean pageBean,HttpServletRequest request);

	/**
	 * 分页获取list
	 * @param tb_contest
	 * @return
	 */
	public QueryResult getList(int page, int matchStatus);

	/**
	 * 获取单个对象
	 * @param matchId
	 * @return
	 */
	public Map<String,Object> getTb_contest(Integer matchId);


	/**
	 * 获取参赛作品信息list
	 * @param matchId
	 * @return
	 */
	public Map<String,Object> getTb_contest(int matchId,int page,int code);

	/**
	 * 获取赛事获奖名单
	 * @param matchId
	 * @param code
	 * @return
	 */
	public List getPrizeMassage(int matchId,int code,int page,String memberId);

	/**
	 * 更新
	 * @param tb_contest
	 */
	public void update(Tb_contest tb_contest);
	 
	public void delete(Object[] entityids);
	public void updateStatus(Object[] entityids);

	/**
	 * 当前赛事信息
	 * @return
	 */
	public List<Object[]> curContest();
}
