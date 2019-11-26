package com.sunnet.org.space.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.space.dao.ITb_spaceDao;
import com.sunnet.org.space.model.Tb_space;
import com.sunnet.org.space.service.ITb_spaceService;
import com.sunnet.org.space.vo.Tb_spaceUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_space Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_spaceServiceImpl extends BaseServiceImpl<Tb_space>  implements ITb_spaceService
{
	
	@Autowired
	private ITb_spaceDao tb_spaceDao;

	@Override
	public QueryResult<Tb_space> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_space o where 1=1 "); //初始化语句
		if (!StringUtils.isStringNull(request.getParameter("sppacestatus"))) {
			//sppacestatus等于空，只显示已上架的数据
			str.append(" and o.sppacestatus = '1' ");
		}else if(StringUtils.isStringNull(request.getParameter("sppacestatus")) && request.getParameter("sppacestatus")=="0" 
				|| "0".equals(request.getParameter("sppacestatus"))){
			//未上架
			str.append(" and o.sppacestatus = '0' ");
		}else{
			
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_spaceDao.findByHQLCount(Tb_space.class, pageBean);
		List<Tb_space> list = tb_spaceDao.findByHQLPage(Tb_space.class, pageBean);
		QueryResult<Tb_space> result = new QueryResult<Tb_space>();
		result.setResultList(Tb_spaceUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_space tb_space) {
		Tb_space tb_space2 = tb_spaceDao.findByPrimaryKey(Tb_space.class,
				tb_space.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_space, tb_space2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_spaceDao.update(tb_space2);
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
		tb_spaceDao.delete(Tb_space.class, hql.toString());
	}

	@Override
	public void updateStatus(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tb_spaceDao.updateStatus(Tb_space.class, hql.toString());
	}
	
	

}
