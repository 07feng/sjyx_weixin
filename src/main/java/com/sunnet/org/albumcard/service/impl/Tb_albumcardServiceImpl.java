package com.sunnet.org.albumcard.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.albumcard.dao.ITb_albumcardDao;
import com.sunnet.org.albumcard.model.Tb_albumcard;
import com.sunnet.org.albumcard.service.ITb_albumcardService;
import com.sunnet.org.albumcard.vo.Tb_albumcardUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_albumcard Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_albumcardServiceImpl extends BaseServiceImpl<Tb_albumcard>  implements ITb_albumcardService
{
	
	@Autowired
	private ITb_albumcardDao tb_albumcardDao;

	@Override
	public QueryResult<Tb_albumcard> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_albumcard o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("fd_type"))) {
			str.append(" and o.fd_type = '" + request.getParameter("fd_type") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("fd_name"))) {
			str.append(" and o.fd_name like '%" + request.getParameter("fd_name") + "%' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_albumcardDao.findByHQLCount(Tb_albumcard.class, pageBean);
		str.append(" order by o.fd_usertime desc ");
		pageBean.setHql(str.toString());
		List<Tb_albumcard> list = tb_albumcardDao.findByHQLPage(Tb_albumcard.class, pageBean);
		QueryResult<Tb_albumcard> result = new QueryResult<Tb_albumcard>();
		result.setResultList(Tb_albumcardUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public QueryResult<Tb_albumcard> Tb_albumcardList(PageBean pageBean, String type) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_albumcard o where 1=1 "); //初始化语句
		if(type.equals("1")){
			//相册
//			str.append("")
		}
		if(type.equals("2")){
			//贺卡

		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_albumcardDao.findByHQLCount(Tb_albumcard.class, pageBean);
		str.append(" order by o.fd_usertime desc ");
		pageBean.setHql(str.toString());
		List<Tb_albumcard> list = tb_albumcardDao.findByHQLPage(Tb_albumcard.class, pageBean);
		QueryResult<Tb_albumcard> result = new QueryResult<Tb_albumcard>();
		result.setResultList(Tb_albumcardUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_albumcard tb_albumcard) {
		Tb_albumcard tb_albumcard2 = tb_albumcardDao.findByPrimaryKey(Tb_albumcard.class,
				tb_albumcard.getFd_id());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_albumcard, tb_albumcard2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_albumcardDao.update(tb_albumcard2);
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
		tb_albumcardDao.delete(Tb_albumcard.class, hql.toString());
	}
	
	

}
