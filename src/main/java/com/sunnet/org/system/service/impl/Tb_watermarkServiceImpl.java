package com.sunnet.org.system.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.system.dao.ITb_watermarkDao;
import com.sunnet.org.system.model.Tb_watermark;
import com.sunnet.org.system.service.ITb_watermarkService;
import com.sunnet.org.system.vo.Tb_watermarkUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_watermark Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_watermarkServiceImpl extends BaseServiceImpl<Tb_watermark>  implements ITb_watermarkService
{
	
	@Autowired
	private ITb_watermarkDao tb_watermarkDao;

	@Override
	public QueryResult<Tb_watermark> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_watermark o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tb_watermarkDao.findByHQLCount(Tb_watermark.class, pageBean);
		List<Tb_watermark> list = tb_watermarkDao.findByHQLPage(Tb_watermark.class, pageBean);
		QueryResult<Tb_watermark> result = new QueryResult<Tb_watermark>();
		result.setResultList(Tb_watermarkUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_watermark tb_watermark) {
		Tb_watermark tb_watermark2 = tb_watermarkDao.findByPrimaryKey(Tb_watermark.class,
				tb_watermark.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_watermark, tb_watermark2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_watermarkDao.update(tb_watermark2);
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
		tb_watermarkDao.delete(Tb_watermark.class, hql.toString());
	}
	
	

}
