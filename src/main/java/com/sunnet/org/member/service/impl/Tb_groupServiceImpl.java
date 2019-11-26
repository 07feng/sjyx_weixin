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

import com.sunnet.org.member.dao.ITb_groupDao;
import com.sunnet.org.member.model.Tb_group;
import com.sunnet.org.member.service.ITb_groupService;
import com.sunnet.org.member.vo.Tb_groupUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_group Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_groupServiceImpl extends BaseServiceImpl<Tb_group>  implements ITb_groupService
{
	
	@Autowired
	private ITb_groupDao tb_groupDao;

	@Override
	public QueryResult<Tb_group> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_group o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_groupDao.findByHQLCount(Tb_group.class, pageBean);
		List<Tb_group> list = tb_groupDao.findByHQLPage(Tb_group.class, pageBean);
		QueryResult<Tb_group> result = new QueryResult<Tb_group>();
		result.setResultList(Tb_groupUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_group tb_group) {
		Tb_group tb_group2 = tb_groupDao.findByPrimaryKey(Tb_group.class,
				tb_group.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_group, tb_group2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_groupDao.update(tb_group2);
	}
	
	

}
