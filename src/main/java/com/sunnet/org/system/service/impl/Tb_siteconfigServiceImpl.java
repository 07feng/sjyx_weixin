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

import com.sunnet.org.system.dao.ITb_siteconfigDao;
import com.sunnet.org.system.model.Tb_siteconfig;
import com.sunnet.org.system.service.ITb_siteconfigService;
import com.sunnet.org.system.vo.Tb_siteconfigUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_siteconfig Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_siteconfigServiceImpl extends BaseServiceImpl<Tb_siteconfig>  implements ITb_siteconfigService
{
	
	@Autowired
	private ITb_siteconfigDao tb_siteconfigDao;

	@Override
	public QueryResult<Tb_siteconfig> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_siteconfig o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_siteconfigDao.findByHQLCount(Tb_siteconfig.class, pageBean);
		List<Tb_siteconfig> list = tb_siteconfigDao.findByHQLPage(Tb_siteconfig.class, pageBean);
		QueryResult<Tb_siteconfig> result = new QueryResult<Tb_siteconfig>();
		result.setResultList(Tb_siteconfigUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_siteconfig tb_siteconfig) {
		Tb_siteconfig tb_siteconfig2 = tb_siteconfigDao.findByPrimaryKey(Tb_siteconfig.class,
				tb_siteconfig.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_siteconfig, tb_siteconfig2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_siteconfigDao.update(tb_siteconfig2);
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
		tb_siteconfigDao.delete(Tb_siteconfig.class, hql.toString());
	}
	
	

}
