package com.sunnet.org.information.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.information.dao.Ivie_Tb_DocInfoDao;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.information.service.Ivie_Tb_DocInfoService;
import com.sunnet.org.information.vo.vie_Tb_DocInfoUtil;

/**
 * 
 * <br>
 * <b>功能：</b>vie_Tb_DocInfo Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class vie_Tb_DocInfoServiceImpl extends BaseServiceImpl<vie_Tb_DocInfo>  implements Ivie_Tb_DocInfoService
{
	
	@Autowired
	private Ivie_Tb_DocInfoDao vie_Tb_DocInfoDao;

	@Override
	public QueryResult<vie_Tb_DocInfo> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vie_Tb_DocInfo o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = vie_Tb_DocInfoDao.findByHQLCount(vie_Tb_DocInfo.class, pageBean);
		List<vie_Tb_DocInfo> list = vie_Tb_DocInfoDao.findByHQLPage(vie_Tb_DocInfo.class, pageBean);
		QueryResult<vie_Tb_DocInfo> result = new QueryResult<vie_Tb_DocInfo>();
		result.setResultList(vie_Tb_DocInfoUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(vie_Tb_DocInfo vie_Tb_DocInfo) {
		vie_Tb_DocInfo vie_Tb_DocInfo2 = vie_Tb_DocInfoDao.findByPrimaryKey(vie_Tb_DocInfo.class,
				vie_Tb_DocInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vie_Tb_DocInfo, vie_Tb_DocInfo2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vie_Tb_DocInfoDao.update(vie_Tb_DocInfo2);
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
		vie_Tb_DocInfoDao.delete(vie_Tb_DocInfo.class, hql.toString());
	}
	
	

}
