package com.sunnet.org.member.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.member.model.Tb_level;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_level接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_levelDao extends IBaseDao<Tb_level>
{
	public List<Tb_level> listlevel(String exp);
 
}
