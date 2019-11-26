package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.QueryResult;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.app.entity.FileType;
import com.sunnet.org.information.model.Tb_filetype;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_filetype Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_filetypeService extends IBaseService<Tb_filetype>
{
	/**
	 * author jinhao
	 * @return
	 */
    public List<Object[]> ListFileType();
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<Tb_filetype> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tb_filetype
	 */
	public void update(Tb_filetype tb_filetype);
	
	public void delete(Object[] entityids);
}
