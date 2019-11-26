package com.sunnet.org.doc.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.doc.dao.ITb_groupdocgoodDao;
import com.sunnet.org.doc.model.Tb_groupdocgood;
import com.sunnet.org.doc.model.tb_groupdocgood_view;
import com.sunnet.org.doc.service.ITb_groupdocgoodService;
import com.sunnet.org.doc.vo.Tb_groupdocgoodUtil;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_groupdocgood Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_groupdocgoodServiceImpl extends BaseServiceImpl<Tb_groupdocgood>  implements ITb_groupdocgoodService
{
	
	@Autowired
	private ITb_groupdocgoodDao tb_groupdocgoodDao;

	@Override
	public QueryResult<Tb_groupdocgood> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_groupdocgood o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("member_id"))) {
			str.append("  and o.member_id.id = '" + request.getParameter("member_id")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("contest_id"))) {
			str.append("  and o.contest_id.id = '" + request.getParameter("contest_id")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("contestname"))) {
			str.append("  and o.contest_id.id = '" + request.getParameter("contestname")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("docid"))) {
			str.append("  and o.doc_id.id like '%" + request.getParameter("docid")+"%' " );
		}
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append("  and o.member_id.usersname like '%" + request.getParameter("usersname")+"%' " );
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_groupdocgoodDao.findByHQLCount(Tb_groupdocgood.class, pageBean);
		str.append(" order by o.todaytime desc ");
		pageBean.setHql(str.toString());
		List<Tb_groupdocgood> list = tb_groupdocgoodDao.findByHQLPage(Tb_groupdocgood.class, pageBean);
		QueryResult<Tb_groupdocgood> result = new QueryResult<Tb_groupdocgood>();
		result.setResultList(Tb_groupdocgoodUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_groupdocgood tb_groupdocgood) {
//		Tb_groupdocgood tb_groupdocgood2 = tb_groupdocgoodDao.findByPrimaryKey(Tb_groupdocgood.class,
//				tb_groupdocgood.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tb_groupdocgood, tb_groupdocgood2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		tb_groupdocgoodDao.update(tb_groupdocgood);
	}
	
	@Override
	public List<Tb_groupdocgood> selectId(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		return tb_groupdocgoodDao.selectId(Tb_groupdocgood.class,hql.toString());
	}
	
	@Override
	public List<Tb_groupdocgood> selectId2(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		hql.append(" select obj from Tb_groupdocgood obj where ");
		hql.append(" obj.id in (");
		for (int i = 0; i < entityids.length; i++) {
			if (i == entityids.length -1) {
				hql.append("'").append(entityids[i]).append("'");
			} else {
				hql.append("'").append(entityids[i]).append("',");
			}
		}
		hql.append(") ");
		return tb_groupdocgoodDao.findByHQL(hql.toString());
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
		tb_groupdocgoodDao.delete(Tb_groupdocgood.class, hql.toString());
	}

	@Override
	public QueryResult<tb_groupdocgood_view> getGroupdocgood(PageBean pagebean,
			String memberid) {
		// TODO Auto-generated method stub
		return tb_groupdocgoodDao.getGroupdocgood(pagebean, memberid);
	}
	
	

}
