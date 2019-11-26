package com.sunnet.org.filmfestival.dao;

import java.util.List;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_good;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_good接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IFilmfestivalvip_goodDao extends IBaseDao<Filmfestivalvip_good>
{
	public List<Filmfestivalvip_good> listgood(int vipid, String memberid,String deviceid);
}
