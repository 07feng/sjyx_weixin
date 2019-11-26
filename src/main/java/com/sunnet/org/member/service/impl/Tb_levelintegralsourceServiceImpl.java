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

import com.sunnet.org.member.dao.ITb_levelintegralsourceDao;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.vo.Tb_levelintegralsourceUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_levelintegralsource Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_levelintegralsourceServiceImpl extends BaseServiceImpl<Tb_levelintegralsource>  implements ITb_levelintegralsourceService
{
	
	@Autowired
	private ITb_levelintegralsourceDao tb_levelintegralsourceDao;

	@Override
	public QueryResult<Tb_levelintegralsource> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_levelintegralsource o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_levelintegralsourceDao.findByHQLCount(Tb_levelintegralsource.class, pageBean);
		List<Tb_levelintegralsource> list = tb_levelintegralsourceDao.findByHQLPage(Tb_levelintegralsource.class, pageBean);
		QueryResult<Tb_levelintegralsource> result = new QueryResult<Tb_levelintegralsource>();
		result.setResultList(Tb_levelintegralsourceUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_levelintegralsource tb_levelintegralsource) {
		Tb_levelintegralsource tb_levelintegralsource2 = tb_levelintegralsourceDao.findByPrimaryKey(Tb_levelintegralsource.class,
				tb_levelintegralsource.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_levelintegralsource, tb_levelintegralsource2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_levelintegralsourceDao.update(tb_levelintegralsource2);
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
		tb_levelintegralsourceDao.delete(Tb_levelintegralsource.class, hql.toString());
	}

	@Override
	public List<Tb_levelintegralsource> listlevel(int levelid) {
		// TODO Auto-generated method stub
		return tb_levelintegralsourceDao.listlevel(levelid);
	}
	
	

}
