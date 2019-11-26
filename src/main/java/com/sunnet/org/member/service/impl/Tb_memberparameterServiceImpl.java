package com.sunnet.org.member.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.member.dao.ITb_memberparameterDao;
import com.sunnet.org.member.model.Tb_memberparameter;
import com.sunnet.org.member.service.ITb_memberparameterService;
import com.sunnet.org.member.vo.Tb_memberparameterUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_memberparameter Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_memberparameterServiceImpl extends BaseServiceImpl<Tb_memberparameter>  implements ITb_memberparameterService
{
	
	@Autowired
	private ITb_memberparameterDao tb_memberparameterDao;

	@Override
	public QueryResult<Tb_memberparameter> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_memberparameter o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_memberparameterDao.findByHQLCount(Tb_memberparameter.class, pageBean);
		List<Tb_memberparameter> list = tb_memberparameterDao.findByHQLPage(Tb_memberparameter.class, pageBean);
		QueryResult<Tb_memberparameter> result = new QueryResult<Tb_memberparameter>();
		result.setResultList(Tb_memberparameterUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_memberparameter tb_memberparameter) {
		Tb_memberparameter tb_memberparameter2 = tb_memberparameterDao.findByPrimaryKey(Tb_memberparameter.class,
				tb_memberparameter.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_memberparameter, tb_memberparameter2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_memberparameterDao.update(tb_memberparameter2);
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
		tb_memberparameterDao.delete(Tb_memberparameter.class, hql.toString());
	}
	
	

}
