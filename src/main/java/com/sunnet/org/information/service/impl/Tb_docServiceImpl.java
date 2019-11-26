package com.sunnet.org.information.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.competition.dao.ITb_contestDao;
import com.sunnet.org.competition.dao.ITre_doccontestDao;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.doc.dao.*;
import com.sunnet.org.doc.model.*;
import com.sunnet.org.filmfestival.dao.IFilmfestivalDao;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.information.dao.ITb_docDao;
import com.sunnet.org.information.dao.ITb_photoalbumDao;
import com.sunnet.org.information.dao.ITre_menberdoccommentDao;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.Tb_photoalbum;
import com.sunnet.org.information.model.Tre_menberdoccomment;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.information.vo.Tb_docUtil;
import com.sunnet.org.member.dao.ITb_filelabelDao;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.dao.ITb_sendmessageDao;
import com.sunnet.org.member.dao.ITre_docfilelabelDao;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_sendmessage;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.scenicSpot.dao.ITb_ScenicSpotDao;
import com.sunnet.org.scenicSpot.dao.ITb_ScenicSpotFileDao;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpot;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpotFile;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_doc Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_docServiceImpl extends BaseServiceImpl<Tb_doc> implements
		ITb_docService {

	@Autowired
	private ITb_docDao tb_docDao;
	@Autowired
	private ITb_memberDao tb_memberDao;
	@Autowired
	private ITb_photoalbumDao tb_photoalbumDao;
	@Autowired
	private ITb_dockeepDao tb_dockeepDao;
	@Autowired
	private ITb_docpayDao tb_docpayDao;
	@Autowired
	private ITb_docgoodDao tb_docgoodDao;
	@Autowired
	private ITre_menberdoccommentDao iTre_menberdoccommentDao;
	@Autowired
	private ITb_tipoffDao iTb_tipoffDao;
	@Autowired
	private IFilmfestivalDao iFilmfestivalDao;
	@Autowired
	private ITb_groupdocgoodDao tb_groupdocgoodDao;
	@Autowired
	private ITre_docfilelabelDao filetolabel;
	@Autowired
	private ITb_filelabelDao filelabel;
	@Autowired
	private ITre_menberdocscoreDao tre_menberdocscoreDao;
	@Autowired
	private ITb_contestDao iTb_contestDao;
	@Autowired
	private ITre_doccontestDao tre_doccontestDao;
	@Autowired
	private ITb_sendmessageDao iTb_sendmessageDao;
	@Autowired
	private ITb_ScenicSpotDao iTb_scenicSpotDao;
	@Autowired
	private ITb_ScenicSpotFileDao iTb_scenicSpotFileDao;

	/*@Autowired
	private OSSClientUtil ossClient;*/

	@Override
	public QueryResult<Tb_doc> list(PageBean pageBean,
			HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append("from Tb_doc o where 1=1 "); // 初始化语句
		if (StringUtils.isStringNull(request.getParameter("filetypeid.id"))) {
			str.append("and o.ispublic=1  and o.filetypeid = '" + request.getParameter("filetypeid.id")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("filetypeid"))) {
			str.append("  and o.filetypeid.id = '" + request.getParameter("filetypeid")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("filetype"))) {
			str.append("and o.ispublic=1  and o.filetype = " + request.getParameter("filetype") );
		}
		if (StringUtils.isStringNull(request.getParameter("filetype_s"))) {
			str.append(" and o.filetype = " + request.getParameter("filetype_s") );
		}
		if (StringUtils.isStringNull(request.getParameter("fid"))) {
			str.append("and o.ispublic=1  and o.fid = " + request.getParameter("fid") ); 
		}
		if (StringUtils.isStringNull(request.getParameter("doctitle"))) {
			str.append(" and o.doctitle like '%" + request.getParameter("doctitle")+"%' or o.memberid.usersname like '%" + request.getParameter("doctitle")+"%'" );
		}
		if (StringUtils.isStringNull(request.getParameter("ispublic"))) {
			str.append(" and o.ispublic = " + request.getParameter("ispublic") );
		}
		if (StringUtils.isStringNull(request.getParameter("isdelete"))) {
			str.append(" and o.isdelete = " + request.getParameter("isdelete") );
		}
		if (StringUtils.isStringNull(request.getParameter("docstatus"))) {
			str.append(" and o.docstatus = " + request.getParameter("docstatus") );
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.uploadtime >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.uploadtime <= '"+request.getParameter("endDate_01")+"'"); 
		}
		pageBean.setHql(str.toString()); 
		int totalRecord = tb_docDao.findByHQLCount(Tb_doc.class, pageBean);
		
		str.append(" order by uploadtime desc ");
		pageBean.setHql(str.toString());
		List<Tb_doc> list = tb_docDao.findByHQLPage(Tb_doc.class, pageBean);
		QueryResult<Tb_doc> result = new QueryResult<Tb_doc>();
		
		result.setResultList(Tb_docUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}



	@Override
	public void update(Tb_doc tb_doc) {
//		Tb_doc tb_doc2 = tb_docDao.findByPrimaryKey(Tb_doc.class,
//				tb_doc.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tb_doc, tb_doc2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		tb_docDao.update(tb_doc);
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
		tb_docDao.delete(Tb_doc.class, hql.toString());
	}

	@Override
	public void deletebyArr(String[] strArray) throws Exception{
		StringBuilder hql = new StringBuilder("");
		hql.append("update Tb_doc set isdelete=2 where");
			for (int i = 0; i < strArray.length; i++) {
				//删除文件
				if (i == 0) {
					hql.append(" id='").append(strArray[i]).append("'");
				} else {
					hql.append(" or id='").append(strArray[i]).append("'");
				}
				//修改容量信息
				Tb_doc tb_doc = getByKey(strArray[i]);
//				System.out.println("memberId="+tb_doc.getMemberid().getId());
				Tb_member tb_member = tb_memberDao.findByHQL("from Tb_member where id =?",tb_doc.getMemberid().getId()).get(0);
				Double Remainingcapacity = Double.parseDouble(tb_member.getRemainingcapacity()) + ((tb_doc.getFilelength() + 0.0) / (1024 * 1024 * 1024));
				tb_member.setRemainingcapacity(Remainingcapacity.toString());
				tb_memberDao.update(tb_member);
				tb_docDao.update(hql.toString());
//				String whereHql = "o.docid.id='" + strArray[i] + "'";
//				String where_Hql = "o.doc_id.id='" + strArray[i] + "'";

				//删除相册中的这个文件
//				System.out.println("imgId=" + strArray[i]);
//				tb_photoalbumDao.delete(Tb_photoalbum.class,whereHql);
//
//				//删除收藏、打赏、点赞、评论（二级评论）、举报表中的记录
//				tb_dockeepDao.delete(Tb_dockeep.class,whereHql); //收藏
//				tb_docpayDao.delete(Tb_docpay.class,whereHql);  //打赏
//				tb_docgoodDao.delete(Tb_docgood.class,whereHql); //点赞
//				iTre_menberdoccommentDao.delete(Tre_menberdoccomment.class,whereHql);  //评论
//				iTb_tipoffDao.delete(Tb_tipoff.class,where_Hql);    //举报
//
//				//删除个人影展作品中的记录
//				iFilmfestivalDao.delete(Filmfestival.class,where_Hql);
//
//				//删除专家点赞表GroupDocGood的记录
//				tb_groupdocgoodDao.delete(Tb_groupdocgood.class,where_Hql);
//
//				//删除文件标签中间表DocFileLabel的记录
//				filetolabel.delete(Tre_docfilelabel.class,whereHql);
//
//				//删除用户评分历史表MenberDocScore的记录
//				tre_menberdocscoreDao.delete(Tre_menberdocscore.class,whereHql);
			}
//			System.out.println("hql="+hql.toString());
//			tb_docDao.delete(Tb_doc.class, hql.toString());
	}



	public void updateStatus(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tb_docDao.updateStatus(Tb_doc.class, hql.toString());
	}

	@Override
	public List<Tb_doc> findorderby(PageBean pagebean) {
		  
			 return tb_docDao.findtypeId(pagebean);
		  
	}

	@Override
	public List<Tb_doc> findfileviewcount() {
		// TODO Auto-generated method stub
		return tb_docDao.findfileviewcount();
	}

	@Override
	public List<Tb_doc> findfilegoodcount() {
		// TODO Auto-generated method stub
		return tb_docDao.findfilegoodcount();
	}

	@Override
	public List<Tb_doc> findfilekeepcount() {
		// TODO Auto-generated method stub
		return tb_docDao.findfilekeepcount();
	}

	@Override
	public List<Tb_filelabel> find(String docid) {
		// TODO Auto-generated method stub
		return tb_docDao.find(docid);
	}

	@Override
	public List<Tb_doc> list(Class t, PageBean pageBean) {
		 
		return tb_docDao.findByHQLPage(t, pageBean);
	}

	@Override
	public QueryResult<Tb_doc> getPage(PageBean pagebean, String wherename,
			String TbName, String TbId, String order ,Class c) {
		// TODO Auto-generated method stub
		return tb_docDao.getPage(pagebean, wherename, TbName, TbId,order, c);
	}

	@Override
	public int updateSql(Integer id, String sql) {
		// TODO Auto-generated method stub
		return tb_docDao.updateSql(id, sql);
	}

	@Override
	public void updatePicList(StringBuilder hql) {
		
		tb_docDao.updatePicList(Tb_doc.class, hql.toString());
		
	}

	@Override
	public int update2(String hql) {
		// TODO Auto-generated method stub
		return tb_docDao.update2(hql);
	}

	public QueryResult<vie_Tb_DocInfo> getDocPage(PageBean pagebean,String wherename,String memberid,Class c)
	{
		return tb_docDao.getDocPage(pagebean, wherename, memberid, c);
		
	}



	@Override
	public QueryResult<Tb_doc> getDoc(PageBean pagebean, String sql) {
		// TODO Auto-generated method stub
		return tb_docDao.getDoc(pagebean, sql);
	}

	@Override
	public Tb_doc getByKey(String docid) {
		String hql= "from Tb_doc as d where d.id =?";
		List<Tb_doc> list = tb_docDao.findByHQL(hql,docid);
		return list.get(0);
	}

	@Override
	public List<Object[]> getDocMemLevel(String docid){
		String sql = "SELECT d.Id AS did,d.FileCommentsCount,m.Id,m.Levelid,m.LevelIntegral,l.LevelName,l.MaxExp FROM TB_Doc AS d LEFT JOIN Tb_member AS m ON d.memberid = m.Id LEFT JOIN TB_Level AS l ON m.Levelid = l.Id WHERE d.id =?";
		return tb_memberDao.findBySql(sql,docid);
	}

	@Override
	@CachePut(value = "sunnet_flb_ehcache")
	public List<Object[]> getVideo(int startRow ,int endRow) {
		String sql ="select * from (select d.Id,d.filetype,d.iwidht,d.iheight,d.filepath,d.phonethumbnailpathimg,d.filegoodcount,d.filecommentscount,d.IsDouble,d.isboutique,ROW_NUMBER() over(order by d.isboutique DESC,d.uploadtime DESC)T FROM Tb_doc AS d where d.docstatus =1 and d.filetype = 1 and d.ispublic =1 and d.isdelete =0 and filetypeid<>1)TT WHERE TT.T between ? and ?";
		return tb_docDao.findBySql(sql,startRow,endRow);
	}

	@Override
	@CachePut(value = "sunnet_flb_ehcache")
	public List<Object[]> getVideo1(int startRow ,int endRow) {
		String sql ="select * from (select d.Id,d.filetype,d.iwidht,d.iheight,d.filepath,d.phonethumbnailpathimg,d.filegoodcount,d.filecommentscount,d.IsDouble,d.isboutique,ROW_NUMBER() over(order by d.isboutique DESC,d.uploadtime DESC)T FROM Tb_doc AS d where d.docstatus =1 and d.filetype = 1 and d.ispublic =1 and d.isdelete =0 and filetypeid<>1 and d.UploadTime >'2019-09-22 00:00:00' and d.UploadTime <'2019-11-22 23:59:59')TT WHERE TT.T between ? and ?";
		return tb_docDao.findBySql(sql,startRow,endRow);
	}

	@Override
	@CachePut(value = "sunnet_flb_ehcache")
	public List<Object[]> getAll(int startRow ,int endRow){
		String sql ="select * from (select d.Id,d.filetype,d.iwidht,d.iheight,d.filepath,d.phonethumbnailpathimg,d.filegoodcount,d.filecommentscount,d.IsDouble,d.isboutique,ROW_NUMBER() over(order by d.isboutique DESC,d.uploadtime DESC)T FROM Tb_doc AS d LEFT JOIN TB_FileType AS ft ON d.Filetypeid = ft.Id where d.docstatus =1 and d.ispublic =1 and d.isdelete =0 and d.filetype = 0 AND ft.type_type =1)TT WHERE TT.T between ? and ?";
		return tb_docDao.findBySql(sql,startRow,endRow);
	}

	@Override
	public List<Tb_doc> getByManyIds(String[] ids) {
		StringBuffer hql = new StringBuffer("from Tb_doc where id in(");
		if (null != ids && ids.length>0){
			for (int i = 0; i <ids.length; i++){
				if(i == ids.length-1)
					hql.append("'").append(ids[i]).append("')");
				else
					hql.append("'").append(ids[i]).append("',");
			}
		}
		return tb_docDao.findByHQL(hql.toString());
	}

	@Override
	@CachePut(value = "sunnet_flb_ehcache")
	public List<Object[]> getByFiletypeid(String typeid,int startRow ,int endRow) {
		String sql ="select * from (select d.Id,d.filetype,d.iwidht,d.iheight,d.filepath,d.phonethumbnailpathimg,d.filegoodcount,d.filecommentscount,d.IsDouble,d.isboutique,ROW_NUMBER() over(order by d.isboutique DESC,d.uploadtime DESC)T FROM Tb_doc AS d where d.docstatus =1 and d.filetype = 0 and d.ispublic =1 and d.isdelete =0 and d.filetypeid =?)TT WHERE TT.T between ? and ?";
		return tb_docDao.findBySql(sql,Integer.parseInt(typeid),startRow,endRow);
	}

	@Override
	public List<Object[]> getByDocId(String docid){
		String sql ="SELECT d.memberId,m.UsersName,ft.id,ft.type_name,l.LevelName,m.HeadImg,d.FileType,d.FilePath,d.DocTitle,d.FileDescribe,d.FileViewCount,d.FileGoodCount,d.FilekeepCount,d.iheight,d.iwidht,d.DeviceType,convert(varchar(255),d.UploadTime) as deptName,d.FID FROM TB_Doc AS d LEFT JOIN Tb_member AS m ON d.memberId=m.Id LEFT JOIN TB_FileType AS ft ON d.Filetypeid=ft.id LEFT JOIN TB_Level AS l ON m.Levelid = l.id WHERE d.id =?";
		return tb_docDao.findBySql(sql,docid);
	}

	@Override
	public List<Map> getNextWorks(String time, String typeId, String code, String memberId){
		List<Map> back = new ArrayList<>();
		StringBuffer sb = new StringBuffer("SELECT * FROM (SELECT d.id,ROW_NUMBER() over(order by d.UploadTime ");
		if ("1".equals(code)){
			sb.append("ASC)T FROM TB_Doc AS d  WHERE d.UploadTime > ?");
		}
		if ("-1".equals(code)){
			sb.append("DESC)T FROM TB_Doc AS d  WHERE d.UploadTime < ?");
		}
		sb.append(" AND d.Filetypeid = ? and d.ispublic=1 and d.isdelete=0 and d.docstatus=1) TT WHERE TT.T between 1 and 5");
		List<Object[]> docIds = tb_docDao.findBySql(sb.toString(),time,Integer.parseInt(typeId));
		if (docIds != null && docIds.size()>0 ){
			for (int i = 0; i< docIds.size(); i++){
				Map map = new HashMap();
				List<Object[]> temp = tb_docDao.findBySql("SELECT d.memberId,m.UsersName,ft.id,ft.type_name,l.LevelName,m.HeadImg,d.FileType,d.FilePath,d.DocTitle,d.FileDescribe,d.FileViewCount,d.FileGoodCount,d.FilekeepCount,d.iheight,d.iwidht,convert(varchar(255),d.UploadTime) as deptName,d.FID,fl.Name AS labelName,c.Id AS contestId,c.ContestName,c.ContestStatus FROM TB_Doc AS d \n" +
						"LEFT JOIN Tb_member AS m ON d.memberId=m.Id \n" +
						"LEFT JOIN TB_FileType AS ft ON d.Filetypeid=ft.id \n" +
						"LEFT JOIN TB_Level AS l ON m.Levelid = l.id \n" +
						"LEFT JOIN  TRE_DocFileLabel AS dfl ON d.id = dfl.docid\n" +
						"LEFT JOIN TB_FileLabel fl ON dfl.LabelId = fl.id \n" +
						"LEFT JOIN TRE_DocContest AS dc ON dc.doc_id = d.id\n" +
						"LEFT JOIN TB_Contest AS c ON dc.contest_id= c.id \n" +
						"WHERE d.id = ?",docIds.get(i)[0].toString());


				Object[] ob = temp.get(0);
				map.put("member_id", ob[0].toString());
				map.put("user_name", ob[1].toString());
				if (null != ob[2]) {
					map.put("type_id", Integer.parseInt(ob[2].toString()));
					map.put("type_name", ob[3].toString());
				}
				map.put("rank", Integer.parseInt(ob[4].toString()));
				map.put("user_portrait", ob[5].toString() + Constants.DOC_PATH_END1);
				map.put("fileType", Integer.parseInt(ob[6].toString()));
				if ("1".equals(ob[6].toString())) {
					map.put("images", ob[7].toString());
					map.put("type_id", -1);
				} else
					map.put("images", ob[7].toString());// + Constants.DOC_PATH_END2);
				if (null != ob[8])
					map.put("title", ob[8].toString());
				else
					map.put("title","");
				if (null != ob[9]) {
					try {
						map.put("describe", URLDecoder.decode(ob[9].toString(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (null != ob[10])
					map.put("view_num", Integer.parseInt(ob[10].toString()) + 1);
				map.put("good_num", ob[11].toString());
				map.put("collect_num", ob[12].toString());
				map.put("height", 0);
				map.put("iheight", ob[13].toString());
				map.put("iwidht", ob[14].toString());
				//上传时间
				String uploadtime = ob[15].toString();
				Date upload = null;
				if (-1 != uploadtime.lastIndexOf("."))
					upload = DateUtil.StringToDate(uploadtime.substring(0, uploadtime.lastIndexOf(".")), "yyyy-MM-dd HH:mm:ss");
				else
					upload = DateUtil.StringToDate(uploadtime, "yyyy-MM-dd HH:mm:ss");
				String diffDate = DateUtil.getDatePoor(DateUtil.getDate(), upload);
				map.put("upload_time", diffDate);
				map.put("time", uploadtime);

				//点赞用户
				List<Object[]> list = tb_docDao.findBySql("select * from(select m.id,m.HeadImg,ROW_NUMBER() over(order by dg.todaytime DESC)T FROM TB_DocGood AS dg LEFT JOIN Tb_member AS m ON dg.MemberId=m.Id WHERE dg.MemberId is not null AND dg.DocId =?)TT WHERE TT.T between 1 and 6",docIds.get(i)[0].toString());
				List<Map> goodList = new ArrayList<>();

				if (null != list && list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {

						Map ms = new HashMap();
						Object[] d = (Object[]) list.get(j);
						ms.put("userId", d[0].toString());
						ms.put("userHeading", d[1].toString() + Constants.DOC_PATH_END1);
						goodList.add(ms);
					}
				}
				map.put("goodList", goodList);
				//参赛信息
				List matchL = new ArrayList();
				if (null != ob[18]) {
					Map<Object, Object> mapContest = new HashMap<Object, Object>();
					mapContest.put("match_id", Integer.parseInt(ob[18].toString()));
					mapContest.put("match_name", ob[19].toString());
					mapContest.put("match_status", ob[20].toString());
					matchL.add(mapContest);
				}
				map.put("match", matchL);
				//标签信息
				List<String> labelList = new ArrayList<>();
				for (int j = 0; j < temp.size(); j++){
					labelList.add(temp.get(j).toString());
				}
				map.put("label", labelList);

				if (null != memberId) {
					String[] strs = new String[]{memberId,ob[0].toString(),docIds.get(i)[0].toString(),memberId,docIds.get(i)[0].toString(),memberId};		//6个参数
					List<Object[]> nums = tb_docDao.findBySql("select count(fc.id) AS num,0 AS test from Tre_friendscircle AS fc where memberid = ? and circlememberid = ?\n" +
							"UNION ALL\n" +
							"SELECT count(dg.id) AS num,0 AS test from Tb_docgood AS dg  where docid =? and DATEDIFF(DAY, todaytime, GETDATE())<1 AND MemberId =?\n" +
							"UNION ALL\n" +
							"select count(dk.id) AS num,0 AS test from Tb_dockeep AS dk where docid = ? and memberid =?", strs);
					if ("0".equals(nums.get(0)[0].toString())){		//是否关注作者
						map.put("is_follow", 0);
					}else {
						map.put("is_follow", 1);
					}
					if ("0".equals(nums.get(1)[0].toString())){		//今天是否点赞
						map.put("isGood", 0);
					}else {
						map.put("isGood", 1);
					}
					if ("0".equals(nums.get(2)[0].toString())){		//是否收藏
						map.put("isKeep", 0);
					}else {
						map.put("isKeep", 1);
					}
				}else {
					map.put("is_follow", 0);
					map.put("isGood", 0);
					map.put("isKeep", 0);
				}

				System.out.println("docIds.size()="+docIds.size()+"--------docIds.get(i)[0].toString()="+docIds.get(i)[0].toString());
				tb_docDao.updateSql("update Tb_doc set FileViewCount = ? where id =?",Integer.parseInt(ob[10].toString()) + 1,docIds.get(i)[0].toString());
				back.add(map);
			}
		}
		return back;
	}

	@Override
	public  List<Object[]> getWorksInfo(String docid){
		String sql ="SELECT d.memberid,d.DocTitle,d.IsPublic,d.iheight,d.iwidht,d.IsParticiPating,d.FileDescribe,d.Filetypeid,ft.type_name,d.FileType,d.FilePath,p.id,p.PhotoAlbumName FROM TB_Doc AS d LEFT JOIN TB_FileType AS ft ON d.Filetypeid=ft.id LEFT JOIN TB_PhotoAlbum AS p ON d.Photoalbumid = p.Id WHERE d.id =?";
		return tb_docDao.findBySql(sql,docid);
	}

	@Override
	public void updateViewNum(String docid,int viewnum){
		String sql = "update Tb_doc set FileViewCount = ? where id =?";
		tb_docDao.updateSql(sql,viewnum,docid);
	}

	@Override
	public void updateCommentscount(String docid,int commentnum){
		String sql = "update Tb_doc set filecommentscount = ? where id =?";
		tb_docDao.updateSql(sql,commentnum,docid);
	}

	@Override
	public void updateIspublic(String docid,int ispublic){
		String sql = "update Tb_doc set ispublic = ?,publictime =? where id =?";
		tb_docDao.updateSql(sql,ispublic, DateUtil.getDate(),docid);
	}

	@Override
	public List<Object[]> getFocusUserDoc(String memberid,int startRow,int endRow){
//		String s =" AND (d.id = d.FID OR d.FID IS NULL)";
		String sql = "SELECT * FROM (SELECT d.id,d.doctitle,d.filedescribe,d.filetype,d.phonethumbnailpathimg,d.filepath,d.fileviewcount,d.iheight,d.iwidht,d.filegoodcount,d.filecommentscount,convert(varchar(255),d.UploadTime) as deptName,m.id AS mid,m.usersname,m.headimg,l.levelname,d.IsDouble,ROW_NUMBER() over(order by d.UploadTime DESC)T FROM Tre_friendscircle AS f LEFT JOIN Tb_member AS m ON m.id =f.circlememberid LEFT JOIN TB_Doc AS d ON m.id = d.memberId LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE f.memberid =? and d.ispublic=1 and d.isdelete=0 and d.docstatus=1) TT WHERE TT.T between ? and ?";
		return  tb_docDao.findBySql(sql,memberid,startRow,endRow);
	}

	@Override
	public List<Object[]> getDocList1(){
		String sql ="SELECT Top 5 d.id,d.doctitle,d.filedescribe,d.filetype,d.phonethumbnailpathimg,d.filepath,d.fileviewcount,d.iheight,d.iwidht,d.filegoodcount,d.filecommentscount,convert(varchar(255),d.UploadTime) as deptName,m.id AS mid,m.usersname,m.headimg,l.levelname,d.IsDouble,NEWID() AS newid FROM Tb_doc AS d LEFT JOIN Tb_member AS m ON d.memberid = m.id LEFT JOIN TB_Level AS l ON m.Levelid = l.id where  DATEDIFF(DAY, publictime, GETDATE())<8 AND ispublic=1 and isdelete=0 and docstatus=1 ORDER BY newid";
		return tb_docDao.findBySql(sql);
	}
	@Override
	public List<Object[]> getDocList(int[] is){
		String sql ="SELECT * FROM (SELECT d.id,d.doctitle,d.filedescribe,d.filetype,d.phonethumbnailpathimg,d.filepath,d.fileviewcount,d.iheight,d.iwidht,d.filegoodcount,d.filecommentscount,convert(varchar(255),d.UploadTime) as deptName,m.id AS mid,m.usersname,m.headimg,l.levelname,d.IsDouble,ROW_NUMBER() over(order by d.UploadTime DESC)T FROM Tb_doc AS d LEFT JOIN Tb_member AS m ON d.memberid = m.id LEFT JOIN TB_Level AS l ON m.Levelid = l.id where  DATEDIFF(DAY, publictime, GETDATE())<8 AND ispublic=1 and isdelete=0 and docstatus=1) TT WHERE TT.T=? OR TT.T=? OR TT.T=? OR TT.T=? OR TT.T=?";
		return tb_docDao.findBySql(sql,is[0],is[1],is[2],is[3],is[4]);
	}

	@Override
	public void addDoc(Tb_doc tb_doc,Object[] memInfo,String label,String matchId,String memberId,String space,String spotId) {
		//给作品添加作品标签
		try {
			if (null != label && label.length() > 0) {
				String[] strs = label.split("[,;，；、]");
				for (String str : strs) {
					List<Tb_filelabel> count = filelabel.findByHQL("from Tb_filelabel where name = ?", str);
					int macid1 = filetolabel.getMaxId(Tre_docfilelabel.class);
					Tb_filelabel l = null;
					if (count.size() == 0) {    //添加标签表，作品标签表数据
						int maxid2 = filelabel.getMaxId(Tb_filelabel.class);
						l = new Tb_filelabel();
						l.setId(maxid2 + 1);
						l.setName(str);
						filelabel.save(l);
					} else
						l = count.get(0);
					//作品标签表数据
					Tre_docfilelabel dl = new Tre_docfilelabel();
					dl.setId(macid1 + 1);
					dl.setLabelid(l);
					dl.setDocid(tb_doc);
					filetolabel.save(dl);
				}
			}

			//修改用户经验值 参赛积分，上传作品积分 验证等级  修改剩余容量
			if (null != memInfo) {
				// 修改会员积分
				// 查询 会员等级积分来源表点赞数量
				Double s = null;
				if (null != matchId || "0".equals(matchId))
					s = Double.valueOf(memInfo[3].toString()) + Integer.parseInt(memInfo[2].toString());
				else
					s = Double.valueOf(memInfo[3].toString()) + Integer.parseInt(memInfo[2].toString()) + 1;
				//判断 用户是否满足升级条件
				if (s > Double.valueOf(memInfo[4].toString()) && Integer.parseInt(memInfo[2].toString()) <= 4) {
					String sqlLevel = "update Tb_member set levelid=?,levelintegral = ? where id=?";
					if (Integer.parseInt(memInfo[1].toString()) == 4)
						tb_memberDao.updateSql(sqlLevel, 15, s, memberId);
					else
						tb_memberDao.updateSql(sqlLevel, Integer.parseInt(memInfo[1].toString()) + 1, s, memberId);
				} else {
					String sqlLevel = "update Tb_member set levelintegral=? where id=?";
					tb_memberDao.updateSql(sqlLevel, s, memberId);
				}
			}
			if (tb_doc.getDocstatus() == 0) {
				//添加系统消息
				Tb_sendmessage sm = new Tb_sendmessage();
				sm.setEdittime(DateUtil.getDate());
				sm.setMessagetype("系统消息");
				sm.setMessageinfo("您上传的作品" + tb_doc.getDoctitle() + "需要经过审核！");
				sm.setMemberid(tb_doc.getMemberid());
				iTb_sendmessageDao.save(sm);    //保存系统消息
			}
			//修改云库容量
			tb_memberDao.updateSql("update Tb_member set RemainingCapacity = ? where id = ?", space, memberId);
			if (null != matchId) {
				if ("0".equals(matchId)) {       //没有参赛
					tb_doc.setIsparticipating(0);
					tb_docDao.save(tb_doc);
				} else {
					tb_doc.setIsparticipating(1);
					tb_doc.setIspublic(1);
					Tre_doccontest doccontest = new Tre_doccontest();   //作品赛事表添加数据
					Integer contests = iTb_contestDao.getAllNum("SELECT count(*) from Tb_contest c where c.id=?", Integer.parseInt(matchId));  //作品参赛赛事;
					if (null != contests && contests > 0) {
						//int maxId = iTre_doccontestService.getMaxId(Tre_doccontest.class);

						doccontest.setAdd_date(DateUtil.getDate());
						//doccontest.setId(maxId + 1);
						Tb_contest contest = new Tb_contest();
						contest.setId(Integer.parseInt(matchId));
						doccontest.setContest_id(contest);
						doccontest.setIs_get_award("0");
						doccontest.setIs_shortlisted("0");
						doccontest.setAudit_status(1);
						tb_doc.setIsparticipating(1);
						tb_docDao.save(tb_doc);
						doccontest.setDoc_id(tb_doc);
						tre_doccontestDao.save(doccontest);
					}
				}
			} else {
				tb_doc.setIsparticipating(0);
				tb_docDao.save(tb_doc);
			}
			//是否景区作品
			if (!"0".equals(spotId)){
				TB_ScenicSpotFile ssf = new TB_ScenicSpotFile();

				TB_ScenicSpot ss = new TB_ScenicSpot();
				ss.setId(Integer.parseInt(spotId));
				ssf.setScenicspotid(ss);

				ssf.setFiletype(1);
				ssf.setSort(0);
				ssf.setUpdatetime(DateUtil.getDate());
				ssf.setFileid(tb_doc.getId());
				iTb_scenicSpotFileDao.save(ssf);
				List<Object[]> spotInfo = iTb_scenicSpotDao.findBySql("SELECT PhotoNum,ArticleNum,VideoNum FROM TB_ScenicSpot WHERE id =?",spotId);
				//修改景点作品数量
				if(tb_doc.getFiletype() ==0)
					iTb_scenicSpotDao.updateSql("UPDATE TB_ScenicSpot SET PhotoNum = ? WHERE id =?",Integer.parseInt(spotInfo.get(0)[0].toString())+1,spotId);
				else
					iTb_scenicSpotDao.updateSql("UPDATE TB_ScenicSpot SET VideoNum = ? WHERE id =?",Integer.parseInt(spotInfo.get(0)[2].toString())+1,spotId);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
