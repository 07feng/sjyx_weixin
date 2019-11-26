package com.sunnet.org.filmfestival.service.impl;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvipDao;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_goodDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_good;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_goodService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_goodUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_good Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Filmfestivalvip_goodServiceImpl extends BaseServiceImpl<Filmfestivalvip_good>  implements IFilmfestivalvip_goodService
{
	
	@Autowired
	private IFilmfestivalvip_goodDao filmfestivalvip_goodDao;
	@Autowired
	private IFilmfestivalvipDao iFilmfestivalvipDao;
	@Autowired
	private ITb_memberDao iTb_memberDao;
    @Autowired
    private IBaseDao baseDao;

	@Override
	public QueryResult<Filmfestivalvip_good> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvip_good o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("vipid"))) {
			str.append(" and o.vipid = '" + request.getParameter("vipid") + "'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvip_goodDao.findByHQLCount(Filmfestivalvip_good.class, pageBean);
		str.append(" order by o.goodcound desc "); 
		pageBean.setHql(str.toString());
		List<Filmfestivalvip_good> list = filmfestivalvip_goodDao.findByHQLPage(Filmfestivalvip_good.class, pageBean);
		QueryResult<Filmfestivalvip_good> result = new QueryResult<Filmfestivalvip_good>();
		result.setResultList(Filmfestivalvip_goodUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public QueryResult getList(PageBean pageBean, String memberId) {
		QueryResult result7 = new QueryResult();
		StringBuffer hql=new StringBuffer();
		hql.append("select o.id,o.titel,g.memberid.id,g.memberid.usersname,g.todaytime from Filmfestivalvip o,Filmfestivalvip_good g where o.id=g.vipid and o.member_id.id='"+memberId+"'");
		pageBean.setHql(hql.toString());
		int totle = baseDao.findByHQLCount(hql.toString());
		pageBean.setTotalRecord(totle);
		List list7 = new ArrayList();
		if(pageBean.getTotalPage()<pageBean.getCurrent_Page()){
			list7 = null;
		}else {
			hql.append(" order by g.todaytime");
			pageBean.setHql(hql.toString());
			List strList = baseDao.findByHQLPage(Object.class, pageBean);
			if(strList.size() != 0) {
				for (int i = 0; i < strList.size(); i++) {
					Map<String, Object> map = new HashMap();
					Object[] object = (Object[]) strList.get(i);
					map.put("doc_id", object[0].toString());
					map.put("doc_name", object[1].toString());
					map.put("user_id", object[2].toString());
					map.put("user_name", object[3].toString());
					String time = DateUtil.getDatePoor(new Date(), DateUtil.StringToDate(object[4].toString()));
					map.put("time", time);
					map.put("info_type", "7");
					map.put("info_content", "点赞了你的影展");
					list7.add(map);
				}
			}
		}
		pageBean.setHql("");
		pageBean.setSql("");
		result7.setPageBean(pageBean);
		result7.setResultList(list7);
		return result7;
	}

	@Override
	public void update(Filmfestivalvip_good filmfestivalvip_good) {
		Filmfestivalvip_good filmfestivalvip_good2 = filmfestivalvip_goodDao.findByPrimaryKey(Filmfestivalvip_good.class,
				filmfestivalvip_good.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestivalvip_good, filmfestivalvip_good2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalvip_goodDao.update(filmfestivalvip_good2);
	}
	
	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		filmfestivalvip_goodDao.delete(Filmfestivalvip_good.class, hql.toString());
	}

	@Override
	public List<Filmfestivalvip_good> listgood(int vipid, String memberid,
			String deviceid) {
		// TODO Auto-generated method stub
		return filmfestivalvip_goodDao.listgood(vipid, memberid, deviceid);
	}
	
	@Override
	public int doGood(String opId,Object[] mem,Object[] memc,List<Object[]> isGood,int vipid){
		if (isGood.size()>0 && isGood.get(0).length>0){		//存在该用户点赞的记录
			if (DateUtil.areSameDay(DateUtil.getDate(), (Date)isGood.get(0)[5])){
				return 0;
			}else {		//修改点影展赞记录
				StringBuffer sb = new StringBuffer("update Filmfestivalvip_Good set ");
				if (null != mem) {
					if (null == isGood.get(0)[2])
						sb.append("Memberid='").append(mem[0].toString()).append("',");
					if (null == isGood.get(0)[4])
						sb.append("deviceid='").append(opId).append("',");
				}
				sb.append("Goodcound=").append(Integer.parseInt(isGood.get(0)[3].toString())+1).append(",todaytime=GETDATE()").append("where Id=?");
				filmfestivalvip_goodDao.updateSql(sb.toString(),Integer.parseInt(isGood.get(0)[0].toString()));
			}
		}else {		//不存在用户点赞记录，新增记录
			Filmfestivalvip_good fg = new Filmfestivalvip_good();
			fg.setGoodcound(1);
			fg.setDeviceid(opId);
			fg.setTodaytime(DateUtil.getDate());
			Tb_member m = new Tb_member();
			m.setId(mem[0].toString());
			fg.setMemberid(m);
			fg.setVipid(vipid);
			filmfestivalvip_goodDao.save(fg);
		}
		//修改影展点赞数
		List goodCount = iFilmfestivalvipDao.findBySql("SELECT filegoodcount FROM FilmfestivalVIP WHERE id =?",vipid);
		int count = 1;
		/*if (null != mem)
			count =10;
		else
			count = 1;*/
		if (null != goodCount.get(0))
			iFilmfestivalvipDao.updateSql("update FilmfestivalVIP set filegoodcount=? where id=?",Integer.parseInt(goodCount.get(0).toString())+count,vipid);
		else
			iFilmfestivalvipDao.updateSql("update FilmfestivalVIP set filegoodcount=? where id=?",count,vipid);
		//修改用户经验等级
		String sqlLevel = "update Tb_member set levelid=?,levelintegral = ? where id=?";		//修改等级和经验
		String sqlLevel1 = "update Tb_member set levelintegral=? where id=?";		//修改经验
		if (null != mem) {		//点赞用户
			Double levelIntegral = Double.valueOf(mem[2].toString()) + Integer.parseInt(mem[3].toString());
			if (levelIntegral > Double.valueOf(mem[4].toString()) && Integer.parseInt(mem[3].toString()) <= 4) {
				if (Integer.parseInt(mem[1].toString()) == 4)
					iTb_memberDao.updateSql(sqlLevel, 15, levelIntegral, mem[0].toString());
				else
					iTb_memberDao.updateSql(sqlLevel, Integer.parseInt(mem[1].toString()) + 1, levelIntegral, mem[0].toString());
			} else
				iTb_memberDao.updateSql(sqlLevel1, levelIntegral, mem[0].toString());
		}

		//影展作者------------------------------------------------
		Double levelIntegral2 = Double.valueOf(memc[4].toString())+ Integer.parseInt(memc[5].toString());
		//判断 两位用户是否满足升级条件
		if (levelIntegral2>Double.valueOf(memc[6].toString()) && Integer.parseInt(memc[5].toString())<=4){
			if (Integer.parseInt(memc[3].toString())==4)
				iTb_memberDao.updateSql(sqlLevel,15,levelIntegral2,memc[2].toString());
			else
				iTb_memberDao.updateSql(sqlLevel,Integer.parseInt(memc[3].toString())+1,levelIntegral2,memc[2].toString());
		}else
			iTb_memberDao.updateSql(sqlLevel1,levelIntegral2,memc[2].toString());
		return 1;
	}

}
