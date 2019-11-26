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

import com.sunnet.org.member.dao.ITb_membermessagesteupDao;
import com.sunnet.org.member.model.Tb_membermessagesteup;
import com.sunnet.org.member.service.ITb_membermessagesteupService;
import com.sunnet.org.member.vo.Tb_membermessagesteupUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_membermessagesteup Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_membermessagesteupServiceImpl extends BaseServiceImpl<Tb_membermessagesteup>  implements ITb_membermessagesteupService
{
	
	@Autowired
	private ITb_membermessagesteupDao tb_membermessagesteupDao;

	@Override
	public QueryResult<Tb_membermessagesteup> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_membermessagesteup o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_membermessagesteupDao.findByHQLCount(Tb_membermessagesteup.class, pageBean);
		List<Tb_membermessagesteup> list = tb_membermessagesteupDao.findByHQLPage(Tb_membermessagesteup.class, pageBean);
		QueryResult<Tb_membermessagesteup> result = new QueryResult<Tb_membermessagesteup>();
		result.setResultList(Tb_membermessagesteupUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_membermessagesteup tb_membermessagesteup) {
		Tb_membermessagesteup tb_membermessagesteup2 = tb_membermessagesteupDao.findByPrimaryKey(Tb_membermessagesteup.class,
				tb_membermessagesteup.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_membermessagesteup, tb_membermessagesteup2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_membermessagesteupDao.update(tb_membermessagesteup2);
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
		tb_membermessagesteupDao.delete(Tb_membermessagesteup.class, hql.toString());
	}
	
	

}
