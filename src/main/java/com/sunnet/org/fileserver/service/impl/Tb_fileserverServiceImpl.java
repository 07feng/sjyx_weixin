package com.sunnet.org.fileserver.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.fileserver.dao.ITb_fileserverDao;
import com.sunnet.org.fileserver.model.Tb_fileserver;
import com.sunnet.org.fileserver.service.ITb_fileserverService;
import com.sunnet.org.fileserver.vo.Tb_fileserverUtil;
import com.sunnet.org.information.model.Tb_shuffling;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_fileserver Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_fileserverServiceImpl extends BaseServiceImpl<Tb_fileserver>  implements ITb_fileserverService
{
	
	@Autowired
	private ITb_fileserverDao tb_fileserverDao;

	@Override
	public QueryResult<Tb_fileserver> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_fileserver o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_fileserverDao.findByHQLCount(Tb_fileserver.class, pageBean);
		List<Tb_fileserver> list = tb_fileserverDao.findByHQLPage(Tb_fileserver.class, pageBean);
		QueryResult<Tb_fileserver> result = new QueryResult<Tb_fileserver>();
		result.setResultList(Tb_fileserverUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_fileserver tb_fileserver) {
		Tb_fileserver tb_fileserver2 = tb_fileserverDao.findByPrimaryKey(Tb_fileserver.class,
				tb_fileserver.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_fileserver, tb_fileserver2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_fileserverDao.update(tb_fileserver2);
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
		this.tb_fileserverDao.delete(Tb_fileserver.class, hql.toString());
	}

}
