package com.sunnet.org.albumcard.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.albumcard.dao.ITb_albumcardrecordDao;
import com.sunnet.org.albumcard.model.Tb_albumcardrecord;
import com.sunnet.org.albumcard.service.ITb_albumcardrecordService;
import com.sunnet.org.albumcard.vo.Tb_albumcardrecordUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_albumcardrecord Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_albumcardrecordServiceImpl extends BaseServiceImpl<Tb_albumcardrecord>  implements ITb_albumcardrecordService
{
	
	@Autowired
	private ITb_albumcardrecordDao tb_albumcardrecordDao;

	@Override
	public QueryResult<Tb_albumcardrecord> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_albumcardrecord o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("fdmemberid"))) {
			str.append(" and o.fdmemberid.usersname like '%" + request.getParameter("fdmemberid") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("fd_name"))) {
			str.append(" and o.fd_albumvard_id.fd_name like '%" + request.getParameter("fd_name") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("menberId"))) {
			str.append(" and o.fd_member_id.id like '%" + request.getParameter("menberId") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("isPay"))) {
			str.append(" and o.fd_ispay like '%" + request.getParameter("isPay") + "%'");
		}
		if (StringUtils.isStringNull(request.getParameter("type"))) {
			str.append(" and o.fd_albumvard_id.fd_type = '" + request.getParameter("type") + "'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_albumcardrecordDao.findByHQLCount(Tb_albumcardrecord.class, pageBean);
		List<Tb_albumcardrecord> list = tb_albumcardrecordDao.findByHQLPage(Tb_albumcardrecord.class, pageBean);
		QueryResult<Tb_albumcardrecord> result = new QueryResult<Tb_albumcardrecord>();
		result.setResultList(Tb_albumcardrecordUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_albumcardrecord tb_albumcardrecord) {
		Tb_albumcardrecord tb_albumcardrecord2 = tb_albumcardrecordDao.findByPrimaryKey(Tb_albumcardrecord.class,
				tb_albumcardrecord.getFd_id());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_albumcardrecord, tb_albumcardrecord2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_albumcardrecordDao.update(tb_albumcardrecord2);
	}
	
	public void delete(Object[] entityids,String menberId) {
		StringBuilder hql = new StringBuilder("");
		if(menberId != null && !("".equals(menberId))){
			hql.append(" fd_member_id='"+menberId+"'");
		}
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" fd_id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or fd_id='").append(entityids[i]).append("'");
			}
		}
		tb_albumcardrecordDao.delete(Tb_albumcardrecord.class, hql.toString());
	}
	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" fd_id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or fd_id='").append(entityids[i]).append("'");
			}
		}
		tb_albumcardrecordDao.delete(Tb_albumcardrecord.class, hql.toString());
	}
	
	

}
