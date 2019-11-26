package com.sunnet.org.competition.service.impl;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.competition.dao.ITb_contestDao;
import com.sunnet.org.competition.dao.ITb_contest_prizeDao;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.model.Tb_contest_prize;
import com.sunnet.org.competition.service.ITb_contestService;
import com.sunnet.org.competition.vo.Tb_contestUtil;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_contest Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_contestServiceImpl extends BaseServiceImpl<Tb_contest>  implements ITb_contestService
{

	@Autowired
	private ITb_contestDao tb_contestDao;
    @Autowired
    private IBaseDao baseDao;
    @Autowired
	private ITb_contest_prizeDao tb_contest_prizeDao;
	@Override
	public QueryResult<Tb_contest> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_contest o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("conteststatus"))) {
			str.append("and o.conteststatus = '" + request.getParameter("conteststatus")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.conteststarttime >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.contestendtime <= '"+request.getParameter("endDate_01")+"'"); 
		}
		//App使用-----------
	 	if (!StringUtils.isStringNull(request.getParameter("isrelease"))) {
			str.append(" and o.isrelease ='1' "); 
		}else if(StringUtils.isStringNull(request.getParameter("isrelease"))){
			if ("0"==request.getParameter("isrelease") || "0".equals(request.getParameter("isrelease"))) {
				str.append(" and o.isrelease ='0' "); 
			}else if ("1"==request.getParameter("isrelease") || "1".equals(request.getParameter("isrelease"))) {
				str.append(" and o.isrelease ='1' "); 
			}else if ("2"==request.getParameter("isrelease") || "2".equals(request.getParameter("isrelease"))) {
				 
			}
			 
		}
	 	//-------------
		if (StringUtils.isStringNull(request.getParameter("contestname"))) {
			str.append(" and o.contestname like '%"+request.getParameter("contestname")+"%' "); 
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_contestDao.findByHQLCount(Tb_contest.class, pageBean);
		str.append(" order by o.conteststarttime desc");
		pageBean.setHql(str.toString());
		List<Tb_contest> list = tb_contestDao.findByHQLPage(Tb_contest.class, pageBean);
		QueryResult<Tb_contest> result = new QueryResult<Tb_contest>();
		result.setResultList(Tb_contestUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);  
		return result;
	}

	@Override
	@CachePut(value = "sunnet_flb_ehcache")
	public QueryResult getList(int page, int matchStatus){
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		QueryResult queryResult = new QueryResult();
		StringBuffer str = new StringBuffer();
		str.append("select * from(select c.id,c.contestname,c.contestminimg,c.conteststatus,c.contestendtime,count(d.id) as doccount,ROW_NUMBER() over(order by c.showorder desc,c.conteststarttime desc)T from  (SELECT * from Tb_contest where isrelease='1'"); //初始化语句
		if (matchStatus == 1) {
			str.append(" and isactive=1");
		}
		if(matchStatus == 2){
			str.append(" and isactive=0");
		}
		str.append(") as c left JOIN Tre_doccontest d on c.id = d.contest_id group by c.id,c.contestname,c.contestminimg,c.conteststatus,c.contestendtime,c.showorder,c.conteststarttime)TT WHERE TT.T between ? and ?");
		pageBean.setHql(str.toString());
		List list = baseDao.findBySql(str.toString(),pageBean.getStartRow(),pageBean.getEndRow());
//		System.out.println("totalRecord="+totalRecord);
		if(list.size() == 0 || null == list){
			return null;
		}
		queryResult.setResultList(Tb_contestUtil.getControllerPage(list));
		pageBean.setHql("");
		pageBean.setSql("");
		queryResult.setPageBean(pageBean);
		return queryResult;
	}

	@Override
	public Map<String,Object> getTb_contest(Integer matchId){
		StringBuffer str = new StringBuffer();
		List<Object[]> list;
		List<Map> listPrize = new ArrayList<>();
		Map<String,Object> map;
		str.append("select c.id,c.contestname,c.contestawardinfo,c.contestminimg,c.conteststatus,c.conteststarttime,c.contestendtime,count(*),c.contestinfo,c.posterurl,c.showorder from (select * from Tb_contest where id =?) as c left join Tre_doccontest d on c.id = d.contest_id "); //初始化语句
		str.append(" group by c.id,c.contestname,c.contestawardinfo,c.contestminimg,c.conteststatus,c.conteststarttime,c.contestendtime,c.contestinfo,c.posterurl,c.showorder" );
//		System.out.println("str="+str.toString());

		list = baseDao.findBySql(str.toString(), matchId);
		map = Tb_contestUtil.getControllerMap(list);

		String sql = "SELECT * FROM Tb_ContestPrize WHERE ContestId = ?";
		List<Object[]> list1= tb_contest_prizeDao.findBySql(sql, matchId);
		for (Object[] objects : list1) {
			Map<String, Object> prize = new HashMap<>();
			String prizename = objects[1].toString();
			String prizeimgurl = objects[2].toString();
			prize.put("prizename",prizename);
			prize.put("prizeimgurl",prizeimgurl);
			listPrize.add(prize);
		}
	 	map.put("listPrize",listPrize);
		return map;
	}


	@Override
	@CachePut(value = "sunnet_flb_ehcache")
	public Map<String,Object> getTb_contest(int matchId,int page,int code){
		QueryResult queryResult = new QueryResult();
		Map<String, Object> map = new HashMap<>();
//		str.append("select c.id,name,c.conteststatus,c.conteststarttime,c.contestendtime from Tb_contest c "); //初始化语句
		//赛事状态和ID
		List<Object[]> tlist= tb_contestDao.findBySql("select c.id,c.conteststatus,c.contestendtime from Tb_contest c where c.id=?",matchId);
		map.put("match_id",tlist.get(0)[0]);
		Integer status = Integer.parseInt(tlist.get(0)[1].toString());
		if (status==0) {
			Date endTime= DateUtil.StringToDate(tlist.get(0)[2].toString());
			long day=endTime.getTime() - new Date().getTime();
			System.out.println("day="+day);
			if(day>=0){
				map.put("match_status", "还剩"+day/(1000*60*60*24)+"天");
				map.put("status", 0);
			}else if(day/(1000*60*60*24)>-30){
				map.put("match_status", "评审中");
				map.put("status", 1);
			}else{
				map.put("match_status", "已结束");
				map.put("status", 2);
			}
		}
		if (status==1) {
			map.put("match_status", "评审中");
			map.put("status", 1);
		}
		if (status==2) {
			map.put("match_status", "已结束");
			map.put("status", 2);
		}
        StringBuffer str = new StringBuffer();
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
		//数据列表      select docId , docTitle , docFilepath , count(Tb_docgood) , count(Tre_menberdoccomment) , docFilescore
		//
//		str.append("select v.id,v.doctitle,v.filepath,v.filegoodcount,v.filecommentscount,v.filescore,v.iwidht,v.iheight from vie_doccontestmember v  where v.contest_id = "+matchId); //初始化语句
		str.append("select * from (select d.id as id,d.doctitle as title,d.filepath as filepath,d.filegoodcount as goodcount,d.FileCommentsCount as commentcount,d.filescore as filescore," +
				"d.iwidht as iwidht,d.iheight as iheight,m.HeadImg as headimg,ROW_NUMBER() over(order by");
		if (code == 3) {
			//人气作品排序
			str.append(" d.filegoodcount desc");
		} else {
			//时间排序
			str.append(" d.uploadtime DESC");
		}
		str.append(")T from TB_Doc d LEFT JOIN Tb_member m on m.id = d.memberid INNER JOIN TRE_DocContest c ON d.id=c.doc_id and c.Audit_Status =1 AND c.contest_id="+matchId );
//                "GROUP BY d.id,d.doctitle,d.filepath,d.filescore,d.FileViewCount,d.UploadTime ";
        if(code == 2){
			//专家点赞
			str.append(" INNER JOIN TB_GroupDocGood p ON d.id=p.Doc_id AND c.contest_id=p.contest_id");
		}
		if(code == 4){
			//获奖作品
			str.append(" AND c.is_get_award='1'");
		}
		if(code == 5){
			//入围作品
			str.append(" AND c.Is_ShortListEd='1'");
		}
		pageBean.setSql(str.toString());
//		System.out.println("totalRecord="+totalRecord);
//		str.append(" LEFT JOIN TB_DocGood g ON d.id=g.docid LEFT JOIN TRE_MenberDocComment m ON d.id=m.docid");
		str.append(" GROUP BY d.id,d.doctitle,d.filepath,d.filescore,d.iwidht,d.iheight,m.headimg,d.filegoodcount,d.FileCommentsCount,d.UploadTime)TT WHERE TT.T between ? and ?");
		pageBean.setSql(str.toString());
		//ROW_NUMBER() over(order by d.uploadtime DESC)T )TT WHERE TT.T between ? and ?
		List list = baseDao.findBySql(str.toString(),pageBean.getStartRow(),pageBean.getEndRow());
//		List list = baseDao.findBySql(str.toString());
		if(list.size()==0){
			map.put("imglist","");
		}else {
			map.put("imglist", Tb_contestUtil.getControllerMatchImgLis(list));
		}
		return map;
	}

	@Override
	@CachePut(value = "sunnet_flb_ehcache")
	public List getPrizeMassage(int matchId, int code, int page,String memberId) {
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(page);
		StringBuffer str = new StringBuffer();
		str.append("select * from (select m.id as memberid,m.levelid as levelid,m.headimg as headimg,m.usersname as usersname,d.id as docid,d.doctitle as doctitle,");
		if(memberId.equals("")){
			str.append("0 as isfollow");
		}else {
//			str.append("select d from Tre_friendscircle d where d.circlememberid.id =? and d.memberid.id=?");
			str.append("count(f.isfriends) as isfollow");
		}
		str.append(",ROW_NUMBER() over(order by m.RegTime DESC)T from Tb_doc d INNER JOIN Tre_doccontest c on d.id=c.doc_id and c.Audit_Status =1 and c.contest_id="+ matchId);
		if (code == 2) {
			str.append(" and c.is_get_award=1");
		}
		if (code == 1) {
			str.append(" and c.is_shortlisted=1");
		}
		str.append(" INNER JOIN Tb_member m on m.id=d.memberid"); //初始化语句
		if(!memberId.equals("")){
			str.append(" LEFT JOIN TRE_FriendsCircle f ON m.id=f.circlememberid AND f.MemberId='"+memberId+"'");
		}
		str.append(" GROUP BY m.id,m.levelid ,m.headimg,m.usersname,d.id,d.doctitle,m.RegTime)TT WHERE TT.T between ? and ?");
		List stuList = baseDao.findBySql(str.toString(),pageBean.getStartRow(),pageBean.getEndRow());
		List<Map<String, Object>> list = new LinkedList();
		if (stuList != null && stuList.size() > 0) {
			Map<String, Object> st;
			for (int i = 0; i < stuList.size(); i++) {
				st = new HashMap<String, Object>();
				Object[] object = (Object[]) stuList.get(i);// 每行记录不在是一个对象 而是一个数组
//				System.out.println("obj="+object[0].toString()+","+object.length);
				String id = object[0].toString();
				String user_rank = object[1].toString();
				String portrait ="";
				if (null != object[2])
					portrait = object[2].toString();
				String user_name = object[3].toString();
				String prize_imgid = object[4].toString();
				String prize_title="";
				if(null != object[5]) {
					prize_title = object[5].toString();
				}
				String isfollow = object[6].toString();
				if(list.size() == 0){
					st.put("user_id", id);
					st.put("user_rank", user_rank);
					st.put("portrait", portrait);
					st.put("user_name", user_name);
					st.put("isfollow", isfollow);
					List prize_titleList=new ArrayList();
					Map prize_titleMap=new HashMap();
					prize_titleMap.put("id", prize_imgid);
					prize_titleMap.put("name", prize_title);
					prize_titleList.add(prize_titleMap);
					st.put("prize_title",prize_titleList);
					list.add(st); // 最终封装在list中 传到前台。
				}else{
					int account=0;
					//判斷此作品的作者是否在list中
					for(int j=0;j<list.size();j++){
						Map<String,Object> prize=list.get(j);
						//判斷是否是同一個人的作品
						if(id.equals(prize.get("user_id").toString())){
							List prize_titleList=(List)prize.get("prize_title");
							Map prize_titleMap=new HashMap();
							prize_titleMap.put("id", prize_imgid);
							prize_titleMap.put("name", prize_title);
							prize_titleList.add(prize_titleMap);
							prize.put("prize_title",prize_titleList);
							list.set(j,prize);
							account++;
						}
					}
					if(account==1){
						continue;
					}
					st.put("user_id", id);
					st.put("user_rank", user_rank);
					st.put("portrait", portrait);
					st.put("user_name", user_name);
					st.put("isfollow", isfollow);
					List prize_titleList=new ArrayList();
					Map prize_titleMap=new HashMap();
					prize_titleMap.put("id", prize_imgid);
					prize_titleMap.put("name", prize_title);
					prize_titleList.add(prize_titleMap);
					st.put("prize_title",prize_titleList);
					list.add(st); // 最终封装在list中 传到前台。
				}
			}
		}
		return list;
	}

	@Override
	public void update(Tb_contest tb_contest) {
		Tb_contest tb_contest2 = tb_contestDao.findByPrimaryKey(Tb_contest.class,
				tb_contest.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_contest, tb_contest2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_contestDao.update(tb_contest2);
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
		tb_contestDao.delete(Tb_contest.class, hql.toString());
	}
	@Override
	public void updateStatus(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tb_contestDao.updateStatus(Tb_contest.class, hql.toString());
	}

	@Override
	public List<Object[]> curContest(){
		String sql ="select Id,ContestName from Tb_contest where IsRelease = 1 and contestendtime>? and conteststarttime<? order by showOrder desc";
		return tb_contestDao.findBySql(sql, DateUtil.getDate(),DateUtil.getDate());
	}


}
