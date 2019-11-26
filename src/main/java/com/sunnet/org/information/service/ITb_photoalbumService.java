package com.sunnet.org.information.service;

import com.sunnet.framework.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.information.model.Tb_photoalbum;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_photoalbum Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_photoalbumService extends IBaseService<Tb_photoalbum>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @param request
	 * @return
	 */
	public QueryResult<Tb_photoalbum> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param tb_photoalbum
	 */
	public void update(Tb_photoalbum tb_photoalbum);
	
	public void delete(Object[] entityids);

	/**
	 * 个人相册列表
	 * @param memberId
	 * @return
	 */
	public List<Object[]> memAlbum(String memberId);
}
