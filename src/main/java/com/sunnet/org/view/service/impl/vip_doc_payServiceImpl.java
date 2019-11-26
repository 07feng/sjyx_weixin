package com.sunnet.org.view.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.view.dao.Ivip_doc_payDao;
import com.sunnet.org.view.model.vip_doc_pay;
import com.sunnet.org.view.service.Ivip_doc_payService;
import com.sunnet.org.view.vo.vip_doc_payUtil;

/**
 * 
 * <br>
 * <b>功能：</b>vip_doc_pay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class vip_doc_payServiceImpl extends BaseServiceImpl<vip_doc_pay>  implements Ivip_doc_payService
{
	
	@Autowired
	private Ivip_doc_payDao vip_doc_payDao;

	@Override
	public QueryResult<vip_doc_pay> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vip_doc_pay o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = vip_doc_payDao.findByHQLCount(vip_doc_pay.class, pageBean);
		List<vip_doc_pay> list = vip_doc_payDao.findByHQLPage(vip_doc_pay.class, pageBean);
		QueryResult<vip_doc_pay> result = new QueryResult<vip_doc_pay>();
		result.setResultList(vip_doc_payUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(vip_doc_pay vip_doc_pay) {
		vip_doc_pay vip_doc_pay2 = vip_doc_payDao.findByPrimaryKey(vip_doc_pay.class,
				vip_doc_pay.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vip_doc_pay, vip_doc_pay2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vip_doc_payDao.update(vip_doc_pay2);
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
		vip_doc_payDao.delete(vip_doc_pay.class, hql.toString());
	}
	
	

}
