package com.sunnet.org.doc.service;

import java.util.List;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.doc.model.Tb_groupdocgood;
import com.sunnet.org.doc.model.tb_groupdocgood_view;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_groupdocgood Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_groupdocgoodService extends IBaseService<Tb_groupdocgood>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_groupdocgood> list(PageBean pageBean,HttpServletRequest request);
	public QueryResult<tb_groupdocgood_view> getGroupdocgood(PageBean pagebean,String memberid);
	/**
	 * 更新
	 * @param Tb_groupdocgood
	 */
	public void update(Tb_groupdocgood tb_groupdocgood);
	public List<Tb_groupdocgood> selectId(Object[] entityids);
	public List<Tb_groupdocgood> selectId2(Object[] entityids);
	public void delete(Object[] entityids);
}
