package com.sunnet.org.doc.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.doc.dao.ITb_tipoffDao;
import com.sunnet.org.doc.model.Tb_tipoff;
import com.sunnet.org.doc.service.ITb_tipoffService;
import com.sunnet.org.doc.vo.Tb_tipoffUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_tipoff Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_tipoffServiceImpl extends BaseServiceImpl<Tb_tipoff>  implements ITb_tipoffService
{
	
	@Autowired
	private ITb_tipoffDao tb_tipoffDao;

	@Override
	public QueryResult<Tb_tipoff> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_tipoff o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("username"))) {
			str.append(" and o.member_id.usersname like '%" + request.getParameter("username") + "%' ");
		}
		if (StringUtils.isStringNull(request.getParameter("docusername"))) {
			str.append(" and o.doc_id.memberid.usersname like '%" + request.getParameter("docusername") + "%' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_tipoffDao.findByHQLCount(Tb_tipoff.class, pageBean);
		str.append(" order by causetime desc  ");
		pageBean.setHql(str.toString());
		List<Tb_tipoff> list = tb_tipoffDao.findByHQLPage(Tb_tipoff.class, pageBean);
		QueryResult<Tb_tipoff> result = new QueryResult<Tb_tipoff>();
		result.setResultList(Tb_tipoffUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_tipoff tb_tipoff) {
		Tb_tipoff tb_tipoff2 = tb_tipoffDao.findByPrimaryKey(Tb_tipoff.class,
				tb_tipoff.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_tipoff, tb_tipoff2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_tipoffDao.update(tb_tipoff2);
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
		tb_tipoffDao.delete(Tb_tipoff.class, hql.toString());
	}

	@Override
	public void updateStatus(Object[] entityids,String userid) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tb_tipoffDao.updateStatus(Tb_tipoff.class, hql.toString(),userid);
	}
	
	

}
