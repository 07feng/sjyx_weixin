package com.sunnet.org.doc.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.vo.docvo;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_docgood接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>zz
 */
public interface ITb_docgoodDao extends IBaseDao<Tb_docgood>
{
	public List<Tb_docgood> listgood(String docid, String memberid,String deviceid);
	
	public int countgood();
	
	public List<docvo> findtypeId();

	public Integer getmaxId();
}
