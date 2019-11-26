package com.sunnet.org.filmfestival.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_goodDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_good;
import com.sunnet.org.util.StringUtils;


/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_good接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Filmfestivalvip_goodDaoImpl extends BaseDaoImpl<Filmfestivalvip_good> implements IFilmfestivalvip_goodDao
{

	@Override
	public List<Filmfestivalvip_good> listgood(int vipid, String memberid,
			String deviceid) {
		StringBuffer str = new StringBuffer();
		str.append(" SELECT * from Filmfestivalvip_good  where 1=1 and vipid = '"+vipid+"' and todaytime> CONVERT(varchar(100),  getdate(), 23) "); //初始化语句
		if (StringUtils.isStringNull(memberid) ) {
			str.append(" and memberid = '" + memberid + "'");
		}
		if (StringUtils.isStringNull(deviceid)) {
			str.append(" and deviceid ='" + deviceid + "'");
		}
		 List<Filmfestivalvip_good> list = getSession().createSQLQuery(str.toString()).addEntity(Filmfestivalvip_good.class).list();
		if(list.size()>0)
			return list;
		else 
		    return null; 
	}
}
