package com.sunnet.org.filmfestival.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_comment_fidDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment_fid;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_comment_fidService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_comment_fidUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_comment_fid Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Filmfestivalvip_comment_fidServiceImpl extends BaseServiceImpl<Filmfestivalvip_comment_fid>  implements IFilmfestivalvip_comment_fidService
{
	
	@Autowired
	private IFilmfestivalvip_comment_fidDao filmfestivalvip_comment_fidDao;

	@Override
	public QueryResult<Filmfestivalvip_comment_fid> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvip_comment_fid o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvip_comment_fidDao.findByHQLCount(Filmfestivalvip_comment_fid.class, pageBean);
		List<Filmfestivalvip_comment_fid> list = filmfestivalvip_comment_fidDao.findByHQLPage(Filmfestivalvip_comment_fid.class, pageBean);
		QueryResult<Filmfestivalvip_comment_fid> result = new QueryResult<Filmfestivalvip_comment_fid>();
		result.setResultList(Filmfestivalvip_comment_fidUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Filmfestivalvip_comment_fid filmfestivalvip_comment_fid) {
		Filmfestivalvip_comment_fid filmfestivalvip_comment_fid2 = filmfestivalvip_comment_fidDao.findByPrimaryKey(Filmfestivalvip_comment_fid.class,
				filmfestivalvip_comment_fid.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestivalvip_comment_fid, filmfestivalvip_comment_fid2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalvip_comment_fidDao.update(filmfestivalvip_comment_fid2);
	}
	
	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		filmfestivalvip_comment_fidDao.delete(Filmfestivalvip_comment_fid.class, hql.toString());
	}
	
	

}
