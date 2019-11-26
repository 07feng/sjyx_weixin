package com.sunnet.org.member.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_levelintegralsource接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_levelintegralsourceDao extends IBaseDao<Tb_levelintegralsource>
{
	public List<Tb_levelintegralsource> listlevel(int levelid);
}
