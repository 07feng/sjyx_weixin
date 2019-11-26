package com.sunnet.org.doc.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.doc.dao.ITre_menberdocscoreDao;
import com.sunnet.org.doc.model.Tre_menberdocscore;
import com.sunnet.org.doc.service.ITre_menberdocscoreService;
import com.sunnet.org.doc.vo.Tre_menberdocscoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdocscore Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tre_menberdocscoreServiceImpl extends BaseServiceImpl<Tre_menberdocscore>  implements ITre_menberdocscoreService
{
	
	@Autowired
	private ITre_menberdocscoreDao tre_menberdocscoreDao;

	@Override
	public QueryResult<Tre_menberdocscore> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tre_menberdocscore o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = tre_menberdocscoreDao.findByHQLCount(Tre_menberdocscore.class, pageBean);
		List<Tre_menberdocscore> list = tre_menberdocscoreDao.findByHQLPage(Tre_menberdocscore.class, pageBean);
		QueryResult<Tre_menberdocscore> result = new QueryResult<Tre_menberdocscore>();
		result.setResultList(Tre_menberdocscoreUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tre_menberdocscore tre_menberdocscore) {
//		Tre_menberdocscore tre_menberdocscore2 = tre_menberdocscoreDao.findByPrimaryKey(Tre_menberdocscore.class,
//				tre_menberdocscore.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tre_menberdocscore, tre_menberdocscore2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		tre_menberdocscoreDao.update(tre_menberdocscore);
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
		tre_menberdocscoreDao.delete(Tre_menberdocscore.class, hql.toString());
	}

	@Override
	public List<Tre_menberdocscore> listScore(Integer groupid) {
		// TODO Auto-generated method stub
		return tre_menberdocscoreDao.listkeep(groupid);
	}
	
	

}
