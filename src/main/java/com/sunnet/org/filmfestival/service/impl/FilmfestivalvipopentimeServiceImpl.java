package com.sunnet.org.filmfestival.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.model.vie_Friendscircle;
import com.sunnet.org.view.vo.vie_FriendscircleUtil;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvipopentimeDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvipopentime;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipopentimeService;
import com.sunnet.org.filmfestival.vo.FilmfestivalvipopentimeUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvipopentime Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class FilmfestivalvipopentimeServiceImpl extends BaseServiceImpl<Filmfestivalvipopentime>  implements IFilmfestivalvipopentimeService
{
	
	@Autowired
	private IFilmfestivalvipopentimeDao filmfestivalvipopentimeDao;

	@Override
	public QueryResult<Filmfestivalvipopentime> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvipopentime o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvipopentimeDao.findByHQLCount(Filmfestivalvipopentime.class, pageBean);
		List<Filmfestivalvipopentime> list = filmfestivalvipopentimeDao.findByHQLPage(Filmfestivalvipopentime.class, pageBean);
		QueryResult<Filmfestivalvipopentime> result = new QueryResult<Filmfestivalvipopentime>();
		result.setResultList(FilmfestivalvipopentimeUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Filmfestivalvipopentime filmfestivalvipopentime) {
		Filmfestivalvipopentime filmfestivalvipopentime2 = filmfestivalvipopentimeDao.findByPrimaryKey(Filmfestivalvipopentime.class,
				filmfestivalvipopentime.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestivalvipopentime, filmfestivalvipopentime2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalvipopentimeDao.update(filmfestivalvipopentime2);
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
		filmfestivalvipopentimeDao.delete(Filmfestivalvipopentime.class, hql.toString());
	}

	 

	 
	
	 
}
