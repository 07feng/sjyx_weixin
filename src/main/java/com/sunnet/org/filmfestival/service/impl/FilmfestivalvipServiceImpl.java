package com.sunnet.org.filmfestival.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.org.filmfestival.dao.IFilmfestivalDao;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_commentDao;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvipopentimeDao;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment;
import com.sunnet.org.information.dao.ITb_docDao;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.scenicSpot.dao.ITb_ScenicSpotFileDao;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpotFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.model.vie_Friendscircle;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvipDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.vo.FilmfestivalvipUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class FilmfestivalvipServiceImpl extends BaseServiceImpl<Filmfestivalvip>  implements IFilmfestivalvipService
{
	
	@Autowired
	private IFilmfestivalvipDao filmfestivalvipDao;
	@Autowired
	private ITb_docDao iTb_docDao;
	@Autowired
	private ITb_memberDao iTb_memberDao;
	@Autowired
	private IFilmfestivalDao filmfestivalDao;
	@Autowired
	private IFilmfestivalvip_commentDao iFilmfestivalvip_commentDao;
	@Autowired
	private IFilmfestivalvipopentimeDao iFilmfestivalvipopentimeDao;
	@Autowired
	private ITb_ScenicSpotFileDao iTb_scenicSpotFileDao;

	@Override
	public QueryResult<Filmfestivalvip> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvip o where 1=1 "); //初始化语句
		System.out.println(request.getParameter("currentPage"));
		System.out.println(request.getParameter("totalPage"));
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append(" and o.member_id.usersname  like '%" + request.getParameter("usersname") + "%' ");
		}
		if (!StringUtils.isStringNull(request.getParameter("open_time"))) {
			//未到期会员
			str.append(" and GETDATE()-o.open_time <=  o.time_length " );
		}else if(request.getParameter("open_time")=="0" || request.getParameter("open_time").equals("0")){
			//到期会员
			str.append(" and GETDATE()-o.open_time >  o.time_length " );
		} 
		
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvipDao.findByHQLCount(Filmfestivalvip.class, pageBean);
		
		str.append(" order by o.open_time desc " );
		pageBean.setHql(str.toString());
		List<Filmfestivalvip> list = filmfestivalvipDao.findByHQLPage(Filmfestivalvip.class, pageBean);
		QueryResult<Filmfestivalvip> result = new QueryResult<Filmfestivalvip>();
		result.setResultList(FilmfestivalvipUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Filmfestivalvip filmfestivalvip) {
		Filmfestivalvip filmfestivalvip2 = filmfestivalvipDao.findByPrimaryKey(Filmfestivalvip.class,
				filmfestivalvip.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestivalvip, filmfestivalvip2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalvipDao.update(filmfestivalvip2);
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
		filmfestivalvipDao.delete(Filmfestivalvip.class, hql.toString());
	}

	@Override
	public QueryResult<vie_Friendscircle> findFriendscircle(PageBean pagebean,
			String memberid) {
		return filmfestivalvipDao.findFriendscircle(pagebean, memberid);
	}

	@Override
	public Filmfestivalvip getByKey(String ffvId) {
		String hql= "from Filmfestivalvip as d where d.id =?";
		List<Filmfestivalvip> list = filmfestivalvipDao.findByHQL(hql,Integer.parseInt(ffvId));
		return list.get(0);
	}

	@Override
	public void publishShow(int vip_id,Integer code){
		if (code == 1) {
			//查询影展会员套餐信息
			List<Object[]> ffvOpenTimes = iFilmfestivalvipopentimeDao.findBySql("select * from Filmfestivalvipopentime where id =?", 14);
			filmfestivalvipDao.updateSql("update FilmfestivalVIP set open_time=GETDATE(),time_length=? where id =?", Integer.parseInt(ffvOpenTimes.get(0)[2].toString()), vip_id);
		}
		if (code == 0) 		//取消发布
			filmfestivalvipDao.updateSql("update FilmfestivalVIP set open_time=GETDATE(),time_length=0 where id =?",vip_id);
	}

	@Override
	public void delShow(List<Object[]> result,String vip_id){
		filmfestivalvipDao.delete(Filmfestivalvip.class,"id ="+vip_id);
		filmfestivalDao.delete(Filmfestival.class,"vip_id ="+vip_id);
		if (result.size()>0){
			for (int i =0 ; i< result.size() ; i++) {
				iTb_docDao.updateSql("update TB_doc set isDelete =1 where id =?", result.get(i)[7].toString());
			}
		}
		List<Object[]> list = iTb_scenicSpotFileDao.findBySql("select * from Tb_scenicSpotFile where fileId = ?",vip_id);
		if (list.size()>0)
			iTb_scenicSpotFileDao.delete(TB_ScenicSpotFile.class,"id ="+list.get(0)[0]);
	}

	@Override
	public void updateViewnum(int viewnum,int vip_id){
		String sql = "UPDATE FilmfestivalVIP SET viewnum = ? WHERE id =?";
		filmfestivalvipDao.updateSql(sql,viewnum,vip_id);
	}

	@Override
	public List<Object[]> getByVip_id(String vip_id,Integer startRow,Integer endRow){
		String sql = "SELECT * FROM (SELECT m.id,m.UsersName,m.HeadImg,ffc.Comments,ffc.id as fid,ffc.comment_time,ROW_NUMBER() over(order by ffc.comment_time desc)T FROM Filmfestivalvip_Comment AS ffc LEFT JOIN Tb_member AS m ON ffc.member_id = m.id WHERE ffc.is_public ='1' AND ffc.vipid=?)TT WHERE TT.T between ? and ?";
		return filmfestivalvipDao.findBySql(sql,Integer.parseInt(vip_id),startRow,endRow);
	}

	@Override
	public List<Object[]> findByCommentId(Integer commentId){
		String sql = "SELECT * FROM (select m.id,ms.id AS mid,m.UsersName AS un,ms.UsersName,f.fd_comment_note,f.fd_comment_time,ROW_NUMBER() over(order by f.fd_comment_time asc)T from Filmfestivalvip_comment_fid AS f LEFT JOIN Tb_member AS m ON m.id = f.fd_member_id LEFT JOIN Tb_member AS ms ON ms.id = f.fd_c_member_id where f.fd_doccomment_id = ? )TT WHERE TT.T between 1 and 15";
		return filmfestivalvipDao.findBySql(sql,commentId);
	}

	@Override
	public List<Object[]> friendFilm(String memberid,String startTime,String endTime){
		String sql = "SELECT ffv.id,ffv.Sort,ffv.FileGoodCount,ffv.FileCommentScount,ffv.titel,ffv.Time_length,ffv.viewnum,m.Id AS mid,m.UsersName,m.HeadImg,m.Levelid,ffv.Open_time,ffv.titelnote FROM Tre_friendscircle AS f inner JOIN FilmfestivalVIP AS ffv ON f.circlememberid = ffv.Member_id LEFT JOIN Tb_member AS m ON ffv.Member_id = m.id WHERE f.memberid=? AND ffv.Time_length<>0 and ffv.Open_time>? AND ffv.Open_time<?";
		return  filmfestivalvipDao.findBySql(sql,memberid,startTime,endTime);
	}

	@Override
	public List<Object[]> getFilmMemLevel(Integer worksShowId){
		String sql = "SELECT ff.Id AS fid,ff.filecommentscount,m.Id,m.Levelid,m.LevelIntegral,l.LevelName,l.MaxExp FROM FilmfestivalVIP AS ff LEFT JOIN Tb_member AS m ON ff.Member_id = m.Id LEFT JOIN TB_Level AS l ON m.Levelid = l.Id WHERE ff.id =?";
		return filmfestivalvipDao.findBySql(sql,worksShowId);
	}

	@Override
	public Integer addComment(Filmfestivalvip_comment comment1, Object[] mem, Object[] mem1, Integer vip_id, String memberId){
		iFilmfestivalvip_commentDao.save(comment1);	//保存评论
		Double levelIntegral = Double.valueOf(mem[2].toString())+ Integer.parseInt(mem[3].toString());
		//判断 两位用户是否满足升级条件
		String sqlLevel = "update Tb_member set levelid=?,levelintegral = ? where id=?";		//修改等级和经验
		String sqlLevel1 = "update Tb_member set levelintegral=? where id=?";		//修改经验
		if (levelIntegral>Double.valueOf(mem[4].toString()) && Integer.parseInt(mem[3].toString())<=4){
			if (Integer.parseInt(mem[1].toString()) ==4)
				iTb_memberDao.updateSql(sqlLevel,15,levelIntegral,memberId);
			else
				iTb_memberDao.updateSql(sqlLevel,Integer.parseInt(mem[1].toString())+1,levelIntegral,memberId);
		}else
			iTb_memberDao.updateSql(sqlLevel1,levelIntegral,memberId);

		//被评论人------------------------------------------------
		Double levelIntegral2 = Double.valueOf(mem1[4].toString())+ Integer.parseInt(mem1[5].toString());
		//判断 两位用户是否满足升级条件
		if (levelIntegral2>Double.valueOf(mem1[6].toString()) && Integer.parseInt(mem1[5].toString())<=4){
			if (Integer.parseInt(mem1[3].toString())==4)
				iTb_memberDao.updateSql(sqlLevel,15,levelIntegral2,mem1[2].toString());
			else
				iTb_memberDao.updateSql(sqlLevel,Integer.parseInt(mem1[3].toString())+1,levelIntegral2,mem1[2].toString());
		}else
			iTb_memberDao.updateSql(sqlLevel1,levelIntegral2,mem1[2].toString());
		Integer result = 0;
		if (null != mem1[1] )
			result = Integer.parseInt(mem1[1].toString());
        filmfestivalvipDao.updateSql("update Filmfestivalvip set filecommentscount = ? where id =?",result+1,vip_id);  //修改文件评论数
		return result;
	}
}
