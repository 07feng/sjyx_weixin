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

import com.sunnet.org.view.dao.Ivie_vippayDao;
import com.sunnet.org.view.model.vie_vippay;
import com.sunnet.org.view.service.Ivie_vippayService;
import com.sunnet.org.view.vo.vie_vippayUtil;

/**
 * 
 * <br>
 * <b>功能：</b>vie_vippay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class vie_vippayServiceImpl extends BaseServiceImpl<vie_vippay>  implements Ivie_vippayService
{
	
	@Autowired
	private Ivie_vippayDao vie_vippayDao;

	@Override
	public QueryResult<vie_vippay> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vie_vippay o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = vie_vippayDao.findByHQLCount(vie_vippay.class, pageBean);
		List<vie_vippay> list = vie_vippayDao.findByHQLPage(vie_vippay.class, pageBean);
		QueryResult<vie_vippay> result = new QueryResult<vie_vippay>();
		result.setResultList(vie_vippayUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(vie_vippay vie_vippay) {
		vie_vippay vie_vippay2 = vie_vippayDao.findByPrimaryKey(vie_vippay.class,
				vie_vippay.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vie_vippay, vie_vippay2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vie_vippayDao.update(vie_vippay2);
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
		vie_vippayDao.delete(vie_vippay.class, hql.toString());
	}
	
	

}
