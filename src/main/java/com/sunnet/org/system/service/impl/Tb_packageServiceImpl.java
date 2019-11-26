package com.sunnet.org.system.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.system.dao.ITb_packageDao;
import com.sunnet.org.system.model.Tb_package;
import com.sunnet.org.system.service.ITb_packageService;
import com.sunnet.org.system.vo.Tb_packageUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_package Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_packageServiceImpl extends BaseServiceImpl<Tb_package>  implements ITb_packageService
{
	
	@Autowired
	private ITb_packageDao tb_packageDao;

	@Override
	public QueryResult<Tb_package> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_package o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_packageDao.findByHQLCount(Tb_package.class, pageBean);
		str.append(" order by  o.edit_time desc");
		pageBean.setHql(str.toString());
		List<Tb_package> list = tb_packageDao.findByHQLPage(Tb_package.class, pageBean);
		QueryResult<Tb_package> result = new QueryResult<Tb_package>();
		result.setResultList(Tb_packageUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_package tb_package) {
		Tb_package tb_package2 = tb_packageDao.findByPrimaryKey(Tb_package.class,
				tb_package.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_package, tb_package2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_packageDao.update(tb_package2);
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
		tb_packageDao.delete(Tb_package.class, hql.toString());
	}
	
	

}
