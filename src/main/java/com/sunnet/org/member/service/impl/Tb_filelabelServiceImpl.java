package com.sunnet.org.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tre_docfilelabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.member.dao.ITb_filelabelDao;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.member.service.ITb_filelabelService;
import com.sunnet.org.member.vo.Tb_filelabelUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_filelabel Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_filelabelServiceImpl extends BaseServiceImpl<Tb_filelabel>  implements ITb_filelabelService
{
	
	@Autowired
	private ITb_filelabelDao tb_filelabelDao;

	@Override
	public QueryResult<Tb_filelabel> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_filelabel o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_filelabelDao.findByHQLCount(Tb_filelabel.class, pageBean);
		List<Tb_filelabel> list = tb_filelabelDao.findByHQLPage(Tb_filelabel.class, pageBean);
		QueryResult<Tb_filelabel> result = new QueryResult<Tb_filelabel>();
		result.setResultList(Tb_filelabelUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_filelabel tb_filelabel) {
		Tb_filelabel tb_filelabel2 = tb_filelabelDao.findByPrimaryKey(Tb_filelabel.class,
				tb_filelabel.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_filelabel, tb_filelabel2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_filelabelDao.update(tb_filelabel2);
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
		tb_filelabelDao.delete(Tb_filelabel.class, hql.toString());
	}


}
