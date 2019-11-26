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
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.member.dao.ITb_levelDao;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.vo.Tb_levelUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_level Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_levelServiceImpl extends BaseServiceImpl<Tb_level>  implements ITb_levelService
{
	
	@Autowired
	private ITb_levelDao tb_levelDao;

	@Override
	public QueryResult<Tb_level> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_level o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_levelDao.findByHQLCount(Tb_level.class, pageBean);
		List<Tb_level> list = tb_levelDao.findByHQLPage(Tb_level.class, pageBean);
		QueryResult<Tb_level> result = new QueryResult<Tb_level>();
		result.setResultList(Tb_levelUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_level tb_level) {
		Tb_level tb_level2 = tb_levelDao.findByPrimaryKey(Tb_level.class,
				tb_level.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_level, tb_level2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_levelDao.update(tb_level2);
	}

	@Override
	public List<Tb_level> listlevel(String exp) {
		// TODO Auto-generated method stub
		return tb_levelDao.listlevel(exp);
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
		tb_levelDao.delete(Tb_level.class, hql.toString());
	}
	

}
