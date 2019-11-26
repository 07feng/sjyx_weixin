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

import com.sunnet.org.system.dao.IMenuDao;
import com.sunnet.org.system.model.Menu;
import com.sunnet.org.system.service.IMenuService;
import com.sunnet.org.system.vo.MenuUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Menu Service<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {

	@Autowired
	private IMenuDao menuDao;

	@Override
	public QueryResult<Menu> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Menu o where 1=1 "); // 初始化语句

		if (StringUtils.isStringNull(request.getParameter("fdPermission"))) {
			str.append(" and o.fdPermission like '%" + request.getParameter("fdPermission") + "%'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = menuDao.findByHQLCount(Menu.class, pageBean);
		List<Menu> list = menuDao.findByHQLPage(Menu.class, pageBean);
		QueryResult<Menu> result = new QueryResult<Menu>();
		result.setResultList(MenuUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Menu menu) {
		Menu menu2 = menuDao.findByPrimaryKey(Menu.class, menu.getFdId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(menu, menu2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(menu.getMenu() == null || menu.getMenu().getFdId() == null || menu.getMenu().getFdId().trim().equals("")){
			menu2.setMenu(null);
		}
		menuDao.update(menu2);
	}

}
