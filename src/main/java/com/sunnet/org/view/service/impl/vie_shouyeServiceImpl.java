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

import com.sunnet.org.view.dao.Ivie_shouyeDao;
import com.sunnet.org.view.model.vie_shouye;
import com.sunnet.org.view.service.Ivie_shouyeService;
import com.sunnet.org.view.vo.vie_shouyeUtil;

/**
 * 
 * <br>
 * <b>功能：</b>vie_shouye Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class vie_shouyeServiceImpl extends BaseServiceImpl<vie_shouye>  implements Ivie_shouyeService
{
	
	@Autowired
	private Ivie_shouyeDao vie_shouyeDao;

	@Override
	public QueryResult<vie_shouye> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vie_shouye o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = vie_shouyeDao.findByHQLCount(vie_shouye.class, pageBean);
		List<vie_shouye> list = vie_shouyeDao.findByHQLPage(vie_shouye.class, pageBean);
		QueryResult<vie_shouye> result = new QueryResult<vie_shouye>();
		result.setResultList(vie_shouyeUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(vie_shouye vie_shouye) {
		vie_shouye vie_shouye2 = vie_shouyeDao.findByPrimaryKey(vie_shouye.class,
				vie_shouye.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vie_shouye, vie_shouye2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vie_shouyeDao.update(vie_shouye2);
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
		vie_shouyeDao.delete(vie_shouye.class, hql.toString());
	}
	
	

}
