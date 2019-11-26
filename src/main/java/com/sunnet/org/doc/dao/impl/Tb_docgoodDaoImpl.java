package com.sunnet.org.doc.dao.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.dao.ITb_docgoodDao;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.vo.docvo;
import com.sunnet.org.util.StringUtils;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_docgood接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tb_docgoodDaoImpl extends BaseDaoImpl<Tb_docgood> implements ITb_docgoodDao
{

	 
	
	 

	@Override
	public List<Tb_docgood> listgood(String docid, String memberid,String deviceid) {
		StringBuffer str = new StringBuffer();
		str.append(" SELECT * from Tb_docgood  where 1=1 and docid = '"+docid+"' and todaytime> CONVERT(varchar(100),  getdate(), 23) "); //初始化语句
		if (StringUtils.isStringNull(memberid) ) {
			str.append(" and memberid = '" + memberid + "'");
		}
		if (StringUtils.isStringNull(deviceid)) {
			str.append(" and deviceid ='" + deviceid + "'");
		}
		 
		/*String sql="SELECT * from Tb_docgood where docid ='"+docid +
				    "' and memberid='"+memberid +"' ";*/
		 List<Tb_docgood> list = getSession().createSQLQuery(str.toString()).addEntity(Tb_docgood.class).list();
		if(list.size()>0)
			return list;
		else 
		    return null; 
	}

	@Override
	public List<docvo> findtypeId() {
		docvo vo;
		List<docvo> lists= new ArrayList<docvo>();
		String sql="select docid,SUM(GoodCound) countGood from Tb_docgood GROUP BY docid";
		 List list = getSession().createSQLQuery(sql.toString()).list();
		 for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			 vo =new docvo();
			Object[] object = (Object[]) iterator.next();
			vo.setDocid(object[0]+"");
			
		    vo.setCountgood(Integer.parseInt(object[1].toString()));
		    lists.add(vo);
		}
 
		return lists; 
	 
	}

	@Override
	public Integer getmaxId() {
		String sql = "SELECT max(TB_DocGood.Id) maxid FROM [dbo].[TB_DocGood]";
		return (Integer)(getSession().createSQLQuery(sql).uniqueResult());
	}

	@Override
	public int countgood() {
		// TODO Auto-generated method stub
		return 0;
	}

}
