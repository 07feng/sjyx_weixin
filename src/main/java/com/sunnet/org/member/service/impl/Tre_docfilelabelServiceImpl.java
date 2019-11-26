package com.sunnet.org.member.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.member.dao.ITre_docfilelabelDao;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.member.service.ITre_docfilelabelService;
import com.sunnet.org.member.vo.Tre_docfilelabelUtil;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_docfilelabel Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tre_docfilelabelServiceImpl extends BaseServiceImpl<Tre_docfilelabel>  implements ITre_docfilelabelService
{
	
	@Autowired
	private ITre_docfilelabelDao tre_docfilelabelDao;

	@Override
	public QueryResult<Tre_docfilelabel> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tre_docfilelabel o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("docid"))) {
			str.append("and o.docid = '" + request.getParameter("docid")+"' " );
		}
		pageBean.setHql(str.toString());
		int totalRecord = tre_docfilelabelDao.findByHQLCount(Tre_docfilelabel.class, pageBean);
		List<Tre_docfilelabel> list = tre_docfilelabelDao.findByHQLPage(Tre_docfilelabel.class, pageBean);
		QueryResult<Tre_docfilelabel> result = new QueryResult<Tre_docfilelabel>();
		result.setResultList(Tre_docfilelabelUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tre_docfilelabel tre_docfilelabel) {
		Tre_docfilelabel tre_docfilelabel2 = tre_docfilelabelDao.findByPrimaryKey(Tre_docfilelabel.class,
				tre_docfilelabel.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tre_docfilelabel, tre_docfilelabel2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tre_docfilelabelDao.update(tre_docfilelabel2);
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
		tre_docfilelabelDao.delete(Tre_docfilelabel.class, hql.toString());
	}

	@Override
	public void deletedoclabel(String entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
			if (null != entityids) {
				hql.append(" docid='").append(entityids).append("'");
			}  
		tre_docfilelabelDao.delete(Tre_docfilelabel.class, hql.toString());
	}

	@Override
	public List findByDocId(String docid) {
		String hql= "SELECT fl.Name FROM TRE_DocFileLabel AS dfl LEFT JOIN TB_FileLabel fl ON dfl.LabelId = fl.id WHERE dfl.docid = ?";
		return  tre_docfilelabelDao.findBySql(hql,docid);
	}


}
