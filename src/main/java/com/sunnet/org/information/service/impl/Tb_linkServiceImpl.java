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
import com.sunnet.org.information.dao.ITb_linkDao;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.information.model.Tb_link;
import com.sunnet.org.information.service.ITb_linkService;
import com.sunnet.org.information.vo.Tb_linkUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_link Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_linkServiceImpl extends BaseServiceImpl<Tb_link>  implements ITb_linkService
{
	
	@Autowired
	private ITb_linkDao tb_linkDao;

	@Override
	public QueryResult<Tb_link> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_link o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_linkDao.findByHQLCount(Tb_link.class, pageBean);
		
		str.append(" order by sort "); 
		pageBean.setHql(str.toString());
		List<Tb_link> list = tb_linkDao.findByHQLPage(Tb_link.class, pageBean);
		QueryResult<Tb_link> result = new QueryResult<Tb_link>();
		result.setResultList(Tb_linkUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_link tb_link) {
		Tb_link tb_link2 = tb_linkDao.findByPrimaryKey(Tb_link.class,
				tb_link.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_link, tb_link2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_linkDao.update(tb_link2);
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
		this.tb_linkDao.delete(Tb_link.class, hql.toString());
	}
	

}
