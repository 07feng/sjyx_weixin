package com.sunnet.org.information.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.org.app.entity.FileType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.information.dao.ITb_filetypeDao;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.information.service.ITb_filetypeService;
import com.sunnet.org.information.vo.Tb_filetypeUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_filetype Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_filetypeServiceImpl extends BaseServiceImpl<Tb_filetype>  implements ITb_filetypeService
{
	
	@Autowired
	private ITb_filetypeDao tb_filetypeDao;


	/**
	 * author jinhao
	 * @return
	 */
	@Override
	public List<Object[]> ListFileType() {
		String sql = "select o.id,o.type_name,o.backgroundImg from Tb_filetype as o where o.type_type = 1 order by o.type_sort";
		return tb_filetypeDao.findBySql(sql);
	}

	@Override
	public QueryResult<Tb_filetype> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_filetype o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("type_type"))) {
			str.append("and o.type_type = '" + request.getParameter("type_type")+"' " );
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_filetypeDao.findByHQLCount(Tb_filetype.class, pageBean);
		str.append("");
		pageBean.setHql(str.toString());
		List<Tb_filetype> list = tb_filetypeDao.findByHQLPage(Tb_filetype.class, pageBean);
		QueryResult<Tb_filetype> result = new QueryResult<Tb_filetype>();
		result.setResultList(Tb_filetypeUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_filetype tb_filetype) {
		Tb_filetype tb_filetype2 = tb_filetypeDao.findByPrimaryKey(Tb_filetype.class,
				tb_filetype.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_filetype, tb_filetype2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_filetypeDao.update(tb_filetype2);
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
		this.tb_filetypeDao.delete(Tb_filetype.class, hql.toString());
	}
	

}
