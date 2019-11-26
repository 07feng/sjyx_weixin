package com.sunnet.org.view.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.view.model.Vie_Info;

/**
 * 
 * <br>
 * <b>功能：</b>Vie_Info Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IVie_InfoService extends IBaseService<Vie_Info>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Vie_Info> list(PageBean pageBean,HttpServletRequest request);

	/**
	 * 返回list集合
	 * @param pageBean
	 * @return
	 */
	public List getList(PageBean pageBean,String memberId);
	
	/**
	 * 更新
	 * @param
	 */
	public void update(Vie_Info vie_Info);
	
	public void delete(Object[] entityids);
	
	
}
