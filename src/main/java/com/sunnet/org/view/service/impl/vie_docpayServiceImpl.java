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

import com.sunnet.org.view.dao.Ivie_docpayDao;
import com.sunnet.org.view.model.vie_docpay;
import com.sunnet.org.view.service.Ivie_docpayService;
import com.sunnet.org.view.vo.vie_docpayUtil;

/**
 * 
 * <br>
 * <b>功能：</b>vie_docpay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class vie_docpayServiceImpl extends BaseServiceImpl<vie_docpay>  implements Ivie_docpayService
{
	
	@Autowired
	private Ivie_docpayDao vie_docpayDao;

	@Override
	public QueryResult<vie_docpay> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vie_docpay o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = vie_docpayDao.findByHQLCount(vie_docpay.class, pageBean);
		List<vie_docpay> list = vie_docpayDao.findByHQLPage(vie_docpay.class, pageBean);
		QueryResult<vie_docpay> result = new QueryResult<vie_docpay>();
		result.setResultList(vie_docpayUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(vie_docpay vie_docpay) {
		vie_docpay vie_docpay2 = vie_docpayDao.findByPrimaryKey(vie_docpay.class,
				vie_docpay.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vie_docpay, vie_docpay2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vie_docpayDao.update(vie_docpay2);
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
		vie_docpayDao.delete(vie_docpay.class, hql.toString());
	}
	
	

}
