package com.sunnet.org.view.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.view.model.vie_Friendscircle;

/**
 * 
 * <br>
 * <b>功能：</b>vie_Friendscircle Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface Ivie_FriendscircleService extends IBaseService<vie_Friendscircle>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<vie_Friendscircle> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param vie_Friendscircle
	 */
	public void update(vie_Friendscircle vie_Friendscircle);
	
	public void delete(Object[] entityids);
}
