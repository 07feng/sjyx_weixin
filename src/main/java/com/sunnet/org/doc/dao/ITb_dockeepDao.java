package com.sunnet.org.doc.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.doc.model.Tb_dockeep;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_dockeep接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_dockeepDao extends IBaseDao<Tb_dockeep>
{
	public List<Tb_dockeep> listkeep(String docid, String memberid);
}
