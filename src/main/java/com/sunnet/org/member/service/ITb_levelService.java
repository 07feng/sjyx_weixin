package com.sunnet.org.member.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_member;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_level Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_levelService extends IBaseService<Tb_level>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_level> list(PageBean pageBean,HttpServletRequest request);
	public void delete(Object[] entityids);
	/**
	 * 更新
	 * @param Tb_level
	 */
	public void update(Tb_level tb_level);
	
	public List<Tb_level> listlevel(String exp);
	
}
