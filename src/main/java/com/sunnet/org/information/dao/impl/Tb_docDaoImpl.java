package com.sunnet.org.information.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.text.StrBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.information.dao.ITb_docDao;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.member.model.Tb_filelabel;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_doc接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_docDaoImpl extends BaseDaoImpl<Tb_doc> implements ITb_docDao
{
	public void updateStatus(Class<Tb_doc> entityClass, String hql){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update Tb_doc set docstatus=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}
	
	/**
	 *sql分页
	 */
	public QueryResult<vie_Tb_DocInfo> getDocPage(PageBean pagebean,String wherename,String memberid,Class c)
	{
		List<vie_Tb_DocInfo> list = new ArrayList<vie_Tb_DocInfo>();
		StrBuilder  sql=new StrBuilder();
		sql.append("select * from ( SELECT ROW_NUMBER() OVER ( ORDER BY T.UploadTime)AS row,* from ");
		sql.append("(select td.*,tg.todaytime,tg.Memberid as memberiddz,tk.Memberid as memberidsc  from TB_Doc td ");
		sql.append("LEFT JOIN TB_DocGood tg on td.Id=tg.Docid and todaytime> CONVERT(varchar(100),  getdate(), 23) and tg.Memberid='"+memberid+"' ");
		sql.append("LEFT JOIN TB_DocKeep tk on tg.Docid=tk.Docid and tk.Memberid='"+memberid+"') as T "+wherename+") TT ");
		sql.append("WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize()));
		
		StrBuilder  sql1String=new StrBuilder();
		sql1String.append("select id from ");
		sql1String.append(" (select td.*,tg.todaytime,tg.Memberid as memberiddz,tk.Memberid as memberidsc  from TB_Doc td ");
		sql1String.append(" LEFT JOIN TB_DocGood tg on td.Id=tg.Docid and todaytime> CONVERT(varchar(100),  getdate(), 23) and tg.Memberid='"+memberid+"' ");
		sql1String.append(" LEFT JOIN TB_DocKeep tk on td.Id=tk.Docid and tk.Memberid='"+memberid+"') as T "+wherename+" ");
		List querycount=getSession().createSQLQuery(sql1String.toString()).list();
		list=getSession().createSQLQuery(sql.toString()).addEntity(c).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<vie_Tb_DocInfo> result = new QueryResult<vie_Tb_DocInfo>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
		
	}
	
	/**
	 * 按照最新上传的图片排序
	 */
	@Override
	public List<Tb_doc> findtypeId(PageBean pagebean){
		List<Tb_doc> list = getSession().createQuery(" from Tb_doc o where 1=1 and o.ispublic=0 and o.filetype=0 Order By o.uploadtime Desc ").list();
		return list;
		/*String sql="select *  from Tb_doc o where 1=1 and o.ispublic=0  Order By o.PublicTime Desc ";
		Query query = getSession().createSQLQuery(sql).addEntity(Tb_doc.class);
		List<Tb_doc> list= query.list();
		if(list.size()>0)
			return list; 
		else 
		    return null; */
	 
		 
	}
	/**
	 * 按照浏览数量（人气）排序
	 * @return
	 */
	public List<Tb_doc> findfileviewcount(){
		List<Tb_doc> list = getSession().createQuery(" from Tb_doc o where 1=1 and o.ispublic=0 and o.filetype=0 Order By o.fileviewcount Desc ").list();
		return list;
		 
	}
	/**
	 * 按照点赞数量排序
	 * @return
	 */
	public List<Tb_doc> findfilegoodcount(){
		List<Tb_doc> list = getSession().createQuery(" from Tb_doc o where 1=1 and o.ispublic=0  and o.filetype=0 Order By o.filegoodcount Desc ").list();
		return list;
	 
	}
	/**
	 * 按照 收藏数量排序
	 * @return
	 */
	public List<Tb_doc> findfilekeepcount(){
		List<Tb_doc> list = getSession().createQuery(" from Tb_doc o where 1=1 and o.ispublic=0 and o.filetype=0 Order By o.filekeepcount Desc ").list();
		return list;
	 
	}

	@Override
	public List<Tb_filelabel> find(String docid) {
		List<Tb_filelabel> list = getSession().createSQLQuery("select t.* from TRE_DocFileLabel o "+
                       " LEFT JOIN TB_FileLabel t on t.Id=o.LabelId"+
                       " where o.DocId='"+docid+"'").addEntity(Tb_filelabel.class).list();
		return list;
	}



	@Override
	public int updateSql(Integer id, String sql) {
		// TODO Auto-generated method stub
		String sqls="update Tb_dockeep set docid='"+sql+"'  where id="+id ;
		Query q= getSession().createSQLQuery(sqls);
		
		return q.executeUpdate();
	}



	@Override
	public int update2(String hql) {
		Query q= getSession().createSQLQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public QueryResult<Tb_doc> getDoc(PageBean pagebean,String memberid) {
		List<Tb_doc> list = new ArrayList<Tb_doc>();
		String sql="select * from ( SELECT ROW_NUMBER() OVER ( order by td.uploadtime desc)AS row,td.* from  Tre_friendscircle f  LEFT JOIN Tb_doc td on f.CircleMemberId=td.memberid where td.ispublic=1 and td.isdelete=0 and docstatus=1 and f.MemberId='" + memberid + "' ) TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		String sql1String="select td.* from Tre_friendscircle f LEFT JOIN Tb_doc td on f.CircleMemberId=td.memberid where td.ispublic=1 and td.isdelete=0 and docstatus=1 and f.MemberId='" + memberid + "' ";
		List querycount=getSession().createSQLQuery(sql1String).addEntity(Tb_doc.class).list();
		
		list=getSession().createSQLQuery(sql).addEntity(Tb_doc.class).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<Tb_doc> result = new QueryResult<Tb_doc>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
	}


/*	@Override
	public List<Tb_doc> finddoccontest(Integer conid, PageBean pagebean) {
		List<Tb_doc> list = new ArrayList<Tb_doc>();
		String sql="select * from ( SELECT ROW_NUMBER() OVER ( order by d.filescore desc )AS row,* from   "+TbName+"  "+wherename+") TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());

		String sqls="SELECT d.* from vie_doccontestmember "+
					"where contest_id="+conid+" and isparticipating =1 and filetype=0 order by filescore desc";
		List querycount=getSession().createSQLQuery(sqls).list();
		
		list=getSession().createSQLQuery(sql).addEntity(Tb_doc.class).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<T> result = new QueryResult<T>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
		 
	}*/
}
