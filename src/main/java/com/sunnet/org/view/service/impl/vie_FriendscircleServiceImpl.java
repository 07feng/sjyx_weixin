package com.sunnet.org.view.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.view.dao.Ivie_FriendscircleDao;
import com.sunnet.org.view.model.vie_Friendscircle;
import com.sunnet.org.view.service.Ivie_FriendscircleService;
import com.sunnet.org.view.vo.vie_FriendscircleUtil;

/**
 * 
 * <br>
 * <b>功能：</b>vie_Friendscircle Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class vie_FriendscircleServiceImpl extends BaseServiceImpl<vie_Friendscircle>  implements Ivie_FriendscircleService
{
	
	@Autowired
	private Ivie_FriendscircleDao vie_FriendscircleDao;

	@Override
	public QueryResult<vie_Friendscircle> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vie_Friendscircle o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = vie_FriendscircleDao.findByHQLCount(vie_Friendscircle.class, pageBean);
		List<vie_Friendscircle> list = vie_FriendscircleDao.findByHQLPage(vie_Friendscircle.class, pageBean);
		QueryResult<vie_Friendscircle> result = new QueryResult<vie_Friendscircle>();
		result.setResultList(vie_FriendscircleUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(vie_Friendscircle vie_Friendscircle) {
		vie_Friendscircle vie_Friendscircle2 = vie_FriendscircleDao.findByPrimaryKey(vie_Friendscircle.class,
				vie_Friendscircle.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vie_Friendscircle, vie_Friendscircle2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vie_FriendscircleDao.update(vie_Friendscircle2);
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
		vie_FriendscircleDao.delete(vie_Friendscircle.class, hql.toString());
	}
	
	

}
