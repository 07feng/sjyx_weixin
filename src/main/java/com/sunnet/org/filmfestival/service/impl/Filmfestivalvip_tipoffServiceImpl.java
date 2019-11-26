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

import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_tipoffDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_tipoff;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_tipoffService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_tipoffUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_tipoff Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Filmfestivalvip_tipoffServiceImpl extends BaseServiceImpl<Filmfestivalvip_tipoff>  implements IFilmfestivalvip_tipoffService
{
	
	@Autowired
	private IFilmfestivalvip_tipoffDao filmfestivalvip_tipoffDao;

	@Override
	public QueryResult<Filmfestivalvip_tipoff> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvip_tipoff o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append(" and o.member_id.usersname like '%" + request.getParameter("usersname") + "%' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvip_tipoffDao.findByHQLCount(Filmfestivalvip_tipoff.class, pageBean);
		str.append(" order by o.causetime desc "); 
		pageBean.setHql(str.toString());
		List<Filmfestivalvip_tipoff> list = filmfestivalvip_tipoffDao.findByHQLPage(Filmfestivalvip_tipoff.class, pageBean);
		QueryResult<Filmfestivalvip_tipoff> result = new QueryResult<Filmfestivalvip_tipoff>();
		result.setResultList(Filmfestivalvip_tipoffUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Filmfestivalvip_tipoff filmfestivalvip_tipoff) {
		Filmfestivalvip_tipoff filmfestivalvip_tipoff2 = filmfestivalvip_tipoffDao.findByPrimaryKey(Filmfestivalvip_tipoff.class,
				filmfestivalvip_tipoff.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestivalvip_tipoff, filmfestivalvip_tipoff2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalvip_tipoffDao.update(filmfestivalvip_tipoff2);
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
		filmfestivalvip_tipoffDao.delete(Filmfestivalvip_tipoff.class, hql.toString());
	}
	
	

}
