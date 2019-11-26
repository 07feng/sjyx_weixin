package com.sunnet.org.filmfestival.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_good;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_good Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvip_goodService extends IBaseService<Filmfestivalvip_good>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Filmfestivalvip_good> list(PageBean pageBean,HttpServletRequest request);

	public QueryResult getList(PageBean pageBean,String memberId);
	/**
	 * 更新
	 * @param filmfestivalvip_good
	 */
	public void update(Filmfestivalvip_good filmfestivalvip_good);
	
	public void delete(Object[] entityids);
	
	public List<Filmfestivalvip_good> listgood(int vipid,String memberid,String deviceid);

	/**
	 * 点赞数据操作
	 * @param mem
	 * @param memc
	 * @param isGood
	 * @return
	 */
	public int doGood(String opId,Object[] mem,Object[] memc,List<Object[]> isGood,int vipid);
	
}
