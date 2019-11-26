package com.sunnet.org.competition.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.util.Constants;
import com.sunnet.org.visitors.dao.IT_searchrecordsDao;
import com.sunnet.org.visitors.model.T_searchrecords;
import com.sunnet.org.visitors.service.IT_searchrecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.competition.dao.Ivie_doccontestmemberDao;
import com.sunnet.org.competition.model.vie_doccontestmember;
import com.sunnet.org.competition.service.Ivie_doccontestmemberService;
import com.sunnet.org.competition.vo.vie_doccontestmemberUtil;

/**
 * 
 * <br>
 * <b>功能：</b>vie_doccontestmember Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class vie_doccontestmemberServiceImpl extends BaseServiceImpl<vie_doccontestmember>  implements Ivie_doccontestmemberService
{
	
	@Autowired
	private Ivie_doccontestmemberDao vie_doccontestmemberDao;
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private IT_searchrecordsDao iT_searchrecordsDao;

	@Override
	public QueryResult<vie_doccontestmember> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vie_doccontestmember o where 1=1 "); //初始化语句
		pageBean.setHql(str.toString());
		int totalRecord = vie_doccontestmemberDao.findByHQLCount(vie_doccontestmember.class, pageBean);
		List<vie_doccontestmember> list = vie_doccontestmemberDao.findByHQLPage(vie_doccontestmember.class, pageBean);
		QueryResult<vie_doccontestmember> result = new QueryResult<vie_doccontestmember>();
		result.setResultList(vie_doccontestmemberUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public QueryResult searchList(PageBean pageBean, String keyWord,String addressIp,String memberId) {
		StringBuffer str1 = new StringBuffer();
		StringBuffer str2= new StringBuffer();
		QueryResult result = new QueryResult();
		int current_page=pageBean.getCurrent_Page();
		if(current_page==1) {
			T_searchrecords t_searchrecords = new T_searchrecords();
			StringBuffer insertSql = new StringBuffer();
			iT_searchrecordsDao.save(t_searchrecords);
			System.out.println("t_searchrecords2=" + t_searchrecords.getFdId());
			insertSql.append("insert into t_SearchRecords values('" + t_searchrecords.getFdId() + "',");
			if (memberId.equals("")) {
				insertSql.append("null");
			} else {
				insertSql.append("'" + memberId + "'");
			}
			insertSql.append(",'" + keyWord + "','" + addressIp + "',GETDATE())");
			iT_searchrecordsDao.sqlexecuteUpdate(insertSql.toString());
		}
		str1.append("select  d.id as id,d.doctitle as doctitle,d.filegoodcount as filegoodcount,d.filecommentscount as filecommentscount,d.filepath as filepath," +
				"0 as z,d.iwidht as iwidht,d.iheight as iheight,d.filetype as filetype,d.phonethumbnailpathimg as phonethumbnailpathimg," +
				"d.fileviewcount as fileviewcount,d.isdouble as isdouble,ROW_NUMBER() over(order by d.uploadtime desc)T from Tb_doc d " +
				"where d.ispublic=1 and d.isdelete=0 and d.docstatus=1 and d.doctitle like '%"+keyWord+"%' "); //初始化语句
//		str2.append("select d.id,d.doctitle,d.filegoodcount,d.filecommentscount,d.filepath,0,d.iwidht,d.iheight,d.filetype,d.phonethumbnailpathimg from Tb_doc d ,Tre_docfilelabel da,Tb_filelabel a " +
//				"where d.id=da.docid and da.labelid=a.id and a.name like '%"+keyWord+"%'"); //初始化语句
		str2.append("select d.id as id,d.doctitle as doctitle,d.filegoodcount as filegoodcount,d.filecommentscount as filecommentscount,d.filepath as filepath," +
				"0 as z,d.iwidht as iwidht,d.iheight as iheight,d.filetype as filetype,d.phonethumbnailpathimg as phonethumbnailpathimg," +
				"d.fileviewcount as fileviewcount,d.isdouble as isdouble,ROW_NUMBER() over(order by d.uploadtime desc)T from Tb_doc d,Tb_member m " +
				"where d.ispublic=1 and d.isdelete=0 and d.docstatus=1 and d.memberid=m.id and m.usersname like '%"+keyWord+"%' "); //初始化语句
		pageBean.setHql(str1.toString());
//		System.out.println("str1="+str1.toString());
		List totalList1=baseDao.findConutBySqllist(str1.toString());
		int totalRecord1 = totalList1.size();
		pageBean.setHql(str2.toString());
//		System.out.println("str2="+str2.toString());
		List totalList2=baseDao.findConutBySqllist(str2.toString());
		int totalRecord2 = totalList2.size();
		System.out.println(totalRecord2+"=totalRecord2");
		System.out.println(totalRecord1+"=totalRecord1");
		pageBean.setTotalRecord(totalRecord1+totalRecord2);
//		pageBean.setTotalRecord(totalRecord1);
		if(pageBean.getTotalRecord()  > 0  && pageBean.getTotalPage() >= pageBean.getCurrent_Page()) {
			if (pageBean.getPageSize() * pageBean.getCurrent_Page() <= totalRecord1 || totalRecord2==0) {
//				pageBean.setHql(str1.toString());
				StringBuffer sql=new StringBuffer();
				sql.append("SELECT * FROM(");
				sql.append(str1);
				sql.append(")TT WHERE TT.T between ? and ?");
				List list = baseDao.findBySql(sql.toString(), pageBean.getPageSize()*(pageBean.getCurrent_Page()-1),pageBean.getPageSize()*pageBean.getCurrent_Page());
				result.setResultList(vie_doccontestmemberUtil.getControllerList(list));
			}
			else if (pageBean.getPageSize() * (pageBean.getCurrent_Page() - 1) < totalRecord1) {
//				pageBean.setHql(str1.toString());
				StringBuffer sql=new StringBuffer();
				sql.append("SELECT * FROM(");
				sql.append(str1);
				sql.append(")TT WHERE TT.T between ? and ?");
//				List list1 = baseDao.findBySqlPage(Object.class, pageBean);
				List list1 = baseDao.findBySql(sql.toString(), pageBean.getPageSize()*(pageBean.getCurrent_Page()-1),pageBean.getPageSize()*pageBean.getCurrent_Page());
//				System.out.println(list1.size()+"=list1");
				StringBuffer sql2=new StringBuffer();
				sql2.append("SELECT * FROM(");
				sql2.append(str2);
				sql2.append(")TT WHERE TT.T between ? and ?");
//				pageBean.setHql(str2.toString());
				pageBean.setCurrentPage(1);
				pageBean.setPageSize((pageBean.getPageSize() * current_page) - totalRecord1);
//				System.out.println(pageBean.getCurrent_Page()+"=pageBean.getCurrent_Page()");
//				System.out.println((pageBean.getPageSize() * current_page) - totalRecord1+"=pageBean");
//				List list2 = baseDao.findByHQLPage(Object.class, pageBean);
				List list2 = baseDao.findBySql(sql.toString(), pageBean.getPageSize()*(pageBean.getCurrent_Page()-1),pageBean.getPageSize()*pageBean.getCurrent_Page());
//				System.out.println(list2.size()+"=list2");
				result.setResultList(vie_doccontestmemberUtil.getControllerDoubleList(list1, list2));
				pageBean.setCurrentPage(current_page);
				pageBean.setPageSize(Constants.DEFAULT_PAGESIZE);
			} else {
				StringBuffer sql=new StringBuffer();
				sql.append("SELECT * FROM(");
				sql.append(str2);
				sql.append(")TT WHERE TT.T between ? and ?");
//				pageBean.setHql(str2.toString());
				pageBean.setStartRow((pageBean.getPageSize() * (current_page-1)) - totalRecord1);
//				System.out.println("sd"+((pageBean.getPageSize() * (current_page-1)) - totalRecord1));
//				List list = baseDao.findByHQLPage(Object.class, pageBean);
				List list = baseDao.findBySql(sql.toString(), (pageBean.getPageSize() * (current_page-1)) - totalRecord1,(pageBean.getPageSize() * current_page) - totalRecord1);
				result.setResultList(vie_doccontestmemberUtil.getControllerList(list));
				pageBean.setCurrentPage(current_page);
			}
		}else{
			result.setResultList(null);
		}
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(vie_doccontestmember vie_doccontestmember) {
		vie_doccontestmember vie_doccontestmember2 = vie_doccontestmemberDao.findByPrimaryKey(vie_doccontestmember.class,
				vie_doccontestmember.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vie_doccontestmember, vie_doccontestmember2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		vie_doccontestmemberDao.update(vie_doccontestmember2);
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
		vie_doccontestmemberDao.delete(vie_doccontestmember.class, hql.toString());
	}
	
	

}
