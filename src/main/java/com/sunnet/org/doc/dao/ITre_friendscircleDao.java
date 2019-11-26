package com.sunnet.org.doc.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.model.Tre_friendscircle;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_friendscircle接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_friendscircleDao extends IBaseDao<Tre_friendscircle>
{
	public List<Tre_friendscircle> listfriend(String circlememberid, String memberid);
}
