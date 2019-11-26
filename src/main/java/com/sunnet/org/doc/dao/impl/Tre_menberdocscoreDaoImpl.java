package com.sunnet.org.doc.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.doc.dao.ITre_menberdocscoreDao;
import com.sunnet.org.doc.model.Tre_menberdocscore;


/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdocscore接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tre_menberdocscoreDaoImpl extends BaseDaoImpl<Tre_menberdocscore> implements ITre_menberdocscoreDao
{

	@Override
	public List<Tre_menberdocscore> listkeep(Integer groupid) {
		String sql="select  tmd.DocId,sum(tmd.Score) as newScores  from TRE_MenberDocScore tmd LEFT JOIN Tb_member tm on tmd.MemberId=tm.Id where tm.GroupId=1 GROUP BY tmd.DocId ORDER BY sum(tmd.Score) desc";
		 List list = getSession().createSQLQuery(sql.toString()).list();
		 if(list.size()>0)
				return list;
			else 
			    return null; 
	}

}
