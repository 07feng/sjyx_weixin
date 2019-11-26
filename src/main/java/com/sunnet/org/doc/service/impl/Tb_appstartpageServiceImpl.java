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

import com.sunnet.org.doc.dao.ITb_appstartpageDao;
import com.sunnet.org.doc.model.Tb_appstartpage;
import com.sunnet.org.doc.service.ITb_appstartpageService;
import com.sunnet.org.doc.vo.Tb_appstartpageUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_appstartpage Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_appstartpageServiceImpl extends BaseServiceImpl<Tb_appstartpage>  implements ITb_appstartpageService
{
	
	@Autowired
	private ITb_appstartpageDao tb_appstartpageDao;

	@Override
	public QueryResult<Tb_appstartpage> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_appstartpage o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("linktype"))) {
			str.append(" and o.linktype = '" + request.getParameter("linktype") + "'");
		}
		if (!StringUtils.isStringNull(request.getParameter("ispublic"))) {
			str.append(" and ispublic=1 ");
		}else if(StringUtils.isStringNull(request.getParameter("ispublic")) && "0"==request.getParameter("ispublic") || "0".equals(request.getParameter("ispublic"))){
			str.append(" and ispublic=0 ");
		}else if(StringUtils.isStringNull(request.getParameter("ispublic")) && "1"==request.getParameter("ispublic") || "1".equals(request.getParameter("ispublic"))){
			str.append(" and ispublic=1 ");
		}else if(StringUtils.isStringNull(request.getParameter("ispublic")) && "2"==request.getParameter("ispublic") || "2".equals(request.getParameter("ispublic"))){
			
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_appstartpageDao.findByHQLCount(Tb_appstartpage.class, pageBean);
		List<Tb_appstartpage> list = tb_appstartpageDao.findByHQLPage(Tb_appstartpage.class, pageBean);
		QueryResult<Tb_appstartpage> result = new QueryResult<Tb_appstartpage>();
		result.setResultList(Tb_appstartpageUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_appstartpage tb_appstartpage) {
		Tb_appstartpage tb_appstartpage2 = tb_appstartpageDao.findByPrimaryKey(Tb_appstartpage.class,
				tb_appstartpage.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_appstartpage, tb_appstartpage2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_appstartpageDao.update(tb_appstartpage2);
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
		tb_appstartpageDao.delete(Tb_appstartpage.class, hql.toString());
	}
	
	

}
