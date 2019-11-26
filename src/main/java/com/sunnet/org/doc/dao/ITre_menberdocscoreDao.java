package com.sunnet.org.doc.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.model.Tre_menberdocscore;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdocscore接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_menberdocscoreDao extends IBaseDao<Tre_menberdocscore>
{
	public List<Tre_menberdocscore> listkeep(Integer docid) ;

}
