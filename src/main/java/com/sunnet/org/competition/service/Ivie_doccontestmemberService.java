package com.sunnet.org.competition.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.competition.model.vie_doccontestmember;

/**
 * 
 * <br>
 * <b>功能：</b>vie_doccontestmember Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface Ivie_doccontestmemberService extends IBaseService<vie_doccontestmember>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<vie_doccontestmember> list(PageBean pageBean,HttpServletRequest request);

	/**
	 * 返回模糊查询的list
	 * @param pageBean
	 * @param keyWord
	 * @return
	 */
	public QueryResult searchList(PageBean pageBean,String keyWord,String addressIp,String memberId);

	
	/**
	 * 更新
	 * @param vie_doccontestmember
	 */
	public void update(vie_doccontestmember vie_doccontestmember);
	
	public void delete(Object[] entityids);
}
