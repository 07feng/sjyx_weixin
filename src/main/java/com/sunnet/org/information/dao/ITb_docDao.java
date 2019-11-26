package com.sunnet.org.information.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.member.model.Tre_docfilelabel;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_doc接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_docDao extends IBaseDao<Tb_doc>
{
	/**
	 * 审核影像管理
	 * @param entityClass
	 * @param hql
	 */
	public void updateStatus(Class<Tb_doc> entityClass, String hql);
	public List<Tb_doc> findtypeId(PageBean pagebean);
	public List<Tb_doc> findfileviewcount();
	public List<Tb_doc> findfilegoodcount();
	public List<Tb_doc> findfilekeepcount();
	
	public List<Tb_filelabel> find(String docid);
	
	public int updateSql(Integer id, String sql);
	//app分页查文件
	public int update2(String hql);
	 
	//查询文件
	public QueryResult<vie_Tb_DocInfo> getDocPage(PageBean pagebean,String wherename,String memberid,Class c);
	public QueryResult<Tb_doc> getDoc(PageBean pagebean,  String sql);


	
}
