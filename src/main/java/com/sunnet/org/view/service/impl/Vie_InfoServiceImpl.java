package com.sunnet.org.view.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.dao.IVie_InfoDao;
import com.sunnet.org.view.model.Vie_Info;
import com.sunnet.org.view.service.IVie_InfoService;
import com.sunnet.org.view.vo.Vie_InfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Vie_Info Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Vie_InfoServiceImpl extends BaseServiceImpl<Vie_Info>  implements IVie_InfoService
{
	@Override
	public List getList(PageBean pageBean,String memberId) {
		StringBuffer str = new StringBuffer();
		str.append("SELECT * FROM(SELECT id,UsersName,docid,docname,infotype,adddate,ROW_NUMBER() over(order by adddate DESC)T " +
				"FROM Vie_Info  WHERE MemberId='"+memberId+"' and id<>'"+memberId+"' and infotype <>6)TT WHERE TT.T between ? and ?"); //初始化语句
		List<Object[]> list = vie_InfoDao.findBySql(str.toString(),pageBean.getStartRow(),pageBean.getEndRow());
		//int totalRecord = Vie_InfoUtil.getControllerList(list).size();
		if(list.size() == 0){
			return list;
		}
		vie_InfoDao.sqlexecuteUpdate("update Tb_member set messagetag=GETDATE() where id='"+memberId+"'");
		return Vie_InfoUtil.getWatchList(list);
	}

	@Autowired
	private IVie_InfoDao vie_InfoDao;

	@Override
	public QueryResult<Vie_Info> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Vie_Info o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
			str.append("and o.memberid = '" + request.getParameter("memberid.id")+"'  and  o.id <> '" + request.getParameter("memberid.id")+"' " );
		}
		 
		 
		pageBean.setHql(str.toString());
	 	int totalRecord = vie_InfoDao.findByHQLCount(Vie_Info.class, pageBean);
	 	str.append(" ORDER BY o.adddate desc"); 
	 	pageBean.setHql(str.toString());
		List<Vie_Info> list = vie_InfoDao.findByHQLPage(Vie_Info.class, pageBean);
		//int totalRecord = Vie_InfoUtil.getControllerList(list).size();
		QueryResult<Vie_Info> result = new QueryResult<Vie_Info>();
		result.setResultList(Vie_InfoUtil.getControllerList(list));
		PageBean pageBean2 = new PageBean();
		pageBean2.setPageSize(pageBean.getPageSize());
		pageBean2.setCurrentPage(pageBean.getCurrent_Page());
		pageBean2.setTotalPage(((totalRecord + pageBean.getPageSize() - 1) / pageBean.getPageSize()));
		pageBean2.setTotalRecord(totalRecord);
		pageBean2.setHql("");
		pageBean2.setSql("");
		result.setPageBean(pageBean2);
		return result;
	}

	@Override
	public void update(Vie_Info vie_Info) {
		Vie_Info vie_Info2 = vie_InfoDao.findByPrimaryKey(Vie_Info.class,
				vie_Info.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vie_Info, vie_Info2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vie_InfoDao.update(vie_Info2);
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
		vie_InfoDao.delete(Vie_Info.class, hql.toString());
	}
	
	

}
