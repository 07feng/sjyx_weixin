package com.sunnet.org.competition.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.competition.dao.ITb_contestthemeDao;
import com.sunnet.org.competition.model.Tb_contesttheme;
import com.sunnet.org.competition.service.ITb_contestthemeService;
import com.sunnet.org.competition.vo.Tb_contestthemeUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contesttheme Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_contestthemeServiceImpl extends BaseServiceImpl<Tb_contesttheme>  implements ITb_contestthemeService
{
	
	@Autowired
	private ITb_contestthemeDao tb_contestthemeDao;

	@Override
	public QueryResult<Tb_contesttheme> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_contesttheme o where 1=1 "); //初始化语句
		
		/*if (StringUtils.isStringNull(request.getParameter("contestid"))) {
			str.append(" and o.tth.id = '" + request.getParameter("contestid") + "'");
		}*/
		pageBean.setHql(str.toString());
		int totalRecord = tb_contestthemeDao.findByHQLCount(Tb_contesttheme.class, pageBean);
		List<Tb_contesttheme> list = tb_contestthemeDao.findByHQLPage(Tb_contesttheme.class, pageBean);
		QueryResult<Tb_contesttheme> result = new QueryResult<Tb_contesttheme>();
		result.setResultList(Tb_contestthemeUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_contesttheme tb_contesttheme) {
		Tb_contesttheme tb_contesttheme2 = tb_contestthemeDao.findByPrimaryKey(Tb_contesttheme.class,
				tb_contesttheme.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_contesttheme, tb_contesttheme2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_contestthemeDao.update(tb_contesttheme2);
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
		tb_contestthemeDao.delete(Tb_contesttheme.class, hql.toString());
	}
	
	

}
