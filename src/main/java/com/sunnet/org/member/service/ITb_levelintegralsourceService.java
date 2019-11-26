package com.sunnet.org.member.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_levelintegralsource;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_levelintegralsource Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_levelintegralsourceService extends IBaseService<Tb_levelintegralsource>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_levelintegralsource> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param Tb_levelintegralsource
	 */
	public void update(Tb_levelintegralsource tb_levelintegralsource);
	
	public void delete(Object[] entityids);
	
	public List<Tb_levelintegralsource> listlevel(int levelid);
}
