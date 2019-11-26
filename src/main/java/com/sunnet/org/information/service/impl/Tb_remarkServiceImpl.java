package com.sunnet.org.information.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.information.dao.ITb_remarkDao;
import com.sunnet.org.information.model.Tb_remark;
import com.sunnet.org.information.service.ITb_remarkService;
import com.sunnet.org.information.vo.Tb_remarkUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_remark Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_remarkServiceImpl extends BaseServiceImpl<Tb_remark>  implements ITb_remarkService
{
	
	@Autowired
	private ITb_remarkDao tb_remarkDao;

	@Override
	public QueryResult<Tb_remark> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_remark o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_remarkDao.findByHQLCount(Tb_remark.class, pageBean);
		List<Tb_remark> list = tb_remarkDao.findByHQLPage(Tb_remark.class, pageBean);
		QueryResult<Tb_remark> result = new QueryResult<Tb_remark>();
		result.setResultList(Tb_remarkUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_remark tb_remark) {
		Tb_remark tb_remark2 = tb_remarkDao.findByPrimaryKey(Tb_remark.class,
				tb_remark.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_remark, tb_remark2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_remarkDao.update(tb_remark2);
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
		tb_remarkDao.delete(Tb_remark.class, hql.toString());
	}
	
	

}
