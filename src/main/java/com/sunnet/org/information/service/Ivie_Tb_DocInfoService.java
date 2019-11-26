package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.vie_Tb_DocInfo;

/**
 * 
 * <br>
 * <b>功能：</b>vie_Tb_DocInfo Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface Ivie_Tb_DocInfoService extends IBaseService<vie_Tb_DocInfo>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<vie_Tb_DocInfo> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param vie_Tb_DocInfo
	 */
	public void update(vie_Tb_DocInfo vie_Tb_DocInfo);
	
	public void delete(Object[] entityids);
}
