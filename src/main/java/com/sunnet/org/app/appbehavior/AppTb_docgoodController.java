package com.sunnet.org.app.appbehavior;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.sunnet.framework.util.Dwon;
import com.sunnet.framework.util.FileUploadUtil;
import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.framework.pagenation.QueryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.sunnet.org.apppush.util.JPushAllUtil;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.model.Tb_contesttheme;
import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.competition.service.ITre_doccontestService;
import com.sunnet.org.competition.vo.Tb_contestUtil;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.doc.model.Tb_groupdocgood;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.model.Tre_menberdocscore;
import com.sunnet.org.doc.service.ITb_docgoodService;
import com.sunnet.org.doc.service.ITb_groupdocgoodService;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.doc.service.ITre_menberdocscoreService;
import com.sunnet.org.doc.vo.Tb_docgoodUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.information.vo.Tb_docUtil;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.util.DownloadFile;
import com.sunnet.org.util.StringUtils;

/***
 * 点赞
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_docgoodController extends BaseController {

	@Autowired
	private ITb_docgoodService tb_docgoodService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_levelService tb_levelService;
	@Autowired
	private ITre_menberdocscoreService tre_menberdocscoreService;
	@Autowired
	private ITre_doccontestService tre_doccontestService;
	@Autowired
	private ITre_friendscircleService tre_friendscircleService;
	@Autowired
	private ITb_groupdocgoodService tb_groupdocgoodService;
	/**
	 * 返回分页 tb_docgood
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/docgood/list")
	public String list(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tb_docgood> list = null;
			StringBuffer str = new StringBuffer();
			str.append(" where 1=1  "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("docid.id"))) {
				str.append(" and docid = '" + request.getParameter("docid.id") + "'");
			}
			//根据用户查询用户点赞的文件
			if (StringUtils.isStringNull(request.getParameter("memberid"))) {
				str.append(" and memberid = '" + request.getParameter("memberid") + "'");
			}else{
				str.append(" and memberid is not null  ");
			}
		//	int totalRecord = tb_docgoodService.findByHQLCount(str.toString());
			QueryResult<Tb_docgood> result =tb_docgoodService.getPage(pageBean, str.toString(), "Tb_docgood", "id", " order by goodcound desc ", Tb_docgood.class);
			list = result.getResultList();
			/*pageBean.setTotalRecord(list.size());
			pageBean.setTotalPage((int)Math.ceil(list.size()/pageBean.getPageSize()));*/
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("startPage", pageBean.getStartPage());// 当前页
			map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
			map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
			map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
			item.add(map1);
			for (Tb_docgood obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("id", obj.getId());
				map.put("docid", obj.getDocid());  
				map.put("memberid", obj.getMemberid());
				map.put("goodcound", obj.getGoodcound());
				map.put("deviceid", obj.getDeviceid());
				map.put("todaytime", obj.getTodaytime());
				if(obj.getMemberid() != null){
					map.put("memberid", obj.getMemberid().getId());
					map.put("usersname", obj.getMemberid().getUsersname());
					map.put("headimg", obj.getMemberid().getHeadimg());
					map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
					if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
						StringBuffer strs = new StringBuffer();
						strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
						strs.append(" and o.circlememberid = '" + obj.getMemberid().getId() + "' ");
						strs.append(" and o.memberid = '" + request.getParameter("memberid_id") + "'");
						List<Tre_friendscircle> listdoc=tre_friendscircleService.findByHQL(strs.toString());
						if(null!=listdoc && listdoc.size()>0){
							map.put("isfriends", listdoc.get(0).getIsfriends());
							map.put("isfriendto", "yes");
						}else{
							map.put("isfriends", "");
							map.put("isfriendto", "no");
					} 
				}else{
					map.put("isfriendto", "no");
				}
				}else{
					map.put("memberid", "");
					map.put("usersname", "");
					map.put("headimg", "");
				}
				if(obj.getDocid() != null){
					map.put("docid", obj.getDocid().getId());
					map.put("doctitle", obj.getDocid().getDoctitle());
					map.put("filepath", obj.getDocid().getFilepath());
					map.put("fileviewcount", obj.getDocid().getFileviewcount());
					map.put("filegoodcount", obj.getDocid().getFilegoodcount());
					map.put("filekeepcount", obj.getDocid().getFilekeepcount());
					map.put("filecommentscount", obj.getDocid().getFilecommentscount());
					map.put("filepaycount", obj.getDocid().getFilepaycount());
					map.put("actityforwarcount", obj.getDocid().getActityforwarcount());
				}else{
					map.put("docid", "");
				}
				
				item.add(map);
			}
		/*	QueryResult<Tb_docgood> result = tb_docgoodService.list(pageBean,
					request);*/
			jsonResult.setResult(item);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}

		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 返回List列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/docgood/listAll")
	public String listAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tb_docgood> result = tb_docgoodService.findAll();
			jsonResult.setResult(Tb_docgoodUtil.getControllerList(result));
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 返回单个对象
	 * 
	 * @param fdId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/docgood/selectKey")
	public String selectKey(String fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Tb_docgood result = tb_docgoodService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_docgoodUtil.getControllerMap(result));
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
			} else {
				jsonResult.setCode("300");
				jsonResult.setMessage("获取出错!请检查fdId参数是否传入!");
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 保存 tb_docgood
	 * 
	 * @param tb_docgood
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/docgood/save")
	public String save(Tb_docgood tb_docgood, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			String msg = StringUtils.isObject(
					new Object[] { tb_docgood.getDocid() },
					new String[] { "点赞文件不能为空" });
			if (!msg.equals("")) {
				jsonResult.setCode("504");
				jsonResult.setMessage(msg);
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			if (null == tb_docgood.getMemberid() && null == tb_docgood.getDeviceid()) {
				jsonResult.setCode("504");
				jsonResult.setMessage("设备id不能为空或者会员不能为空");
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			List<Tb_docgood> result =null;
			if(null!=tb_docgood.getMemberid()){
				//查询今天是否点过赞
				 result = tb_docgoodService.listgood(tb_docgood.getDocid().getId(), tb_docgood.getMemberid().getId(),
						tb_docgood.getDeviceid());
			}else{
				  result = tb_docgoodService.listgood(tb_docgood.getDocid().getId(), null,
						tb_docgood.getDeviceid());
			}
			
			// 查询文件id与会员id是不是存在，存在则修改点赞数，不存在则新增数据
			StringBuffer str = new StringBuffer();
			str.append(" SELECT d from Tb_docgood d where 1=1 and d.docid = '"
					+ tb_docgood.getDocid().getId() + "' "); // 初始化语句
			if (null!=tb_docgood.getMemberid()) {
				str.append(" and d.memberid = '"+ tb_docgood.getMemberid().getId() + "'");
			}
			if (null!=tb_docgood.getDeviceid()) {
				str.append(" and d.deviceid ='" + tb_docgood.getDeviceid()
						+ "'");
			}
			List<Tb_docgood> listdocgood = tb_docgoodService.findByHQL(str
					.toString());
			// 查询文件信息
			Tb_doc docresult = tb_docService.findByPrimaryKey(tb_docgood
					.getDocid().getId());
			Tb_member mem =null;
			if(null!=tb_docgood.getMemberid()){
				mem = tb_memberService.findByPrimaryKey(tb_docgood
						.getMemberid().getId());
			}
			
			Tb_member memb = tb_memberService.findByPrimaryKey(docresult.getMemberid().getId());
//			String time = (new Date()).toString();
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = f.format(new Date()).toString();

			if (listdocgood.size() > 0) {

				if (null != result) {
					// 这里判断今天是否点过赞
					jsonResult.setCode("201");
					jsonResult.setMessage("您今天已点过赞！请明天再来！");
				} else {
					//------------------参赛---------------------------------------------------------------
					// 如果参赛，需要修改TRE_MenberDocScore用户评分历史表，修改文件表的评分数
					// 如果会员是专家或者普通用户，点赞分数获取从TB_Contest赛事表获取 TRE_DocContest参赛作品表
					String docstr = "SELECT d from Tre_doccontest d where 1=1 and d.doc_id = '"
							+ tb_docgood.getDocid().getId() + "' ";
					List<Tre_doccontest> dc = tre_doccontestService.findByHQL(docstr.toString());

					if (docresult.getIsparticipating() == 1) {
						String sql="select * from Tre_doccontest where doc_id='"+tb_docgood.getDocid().getId()+"'";
						List<Tre_doccontest> con=tre_doccontestService.findlistBySql(Tre_doccontest.class, sql);
						if(null!=con && con.get(0).getContest_id().getConteststatus()==0){//参赛的状态为征集中
							List<Tre_menberdocscore> dt=null;
							if(null!=tb_docgood.getMemberid()){
							String sdt = "SELECT d from Tre_menberdocscore d where 1=1 and d.docid = '"
									+ tb_docgood.getDocid().getId()
									+ "' and d.memberid='"
									+ tb_docgood.getMemberid().getId() + "' ";
							dt = tre_menberdocscoreService
									.findByHQL(sdt.toString());
						}
                        if(null != tb_docgood.getDeviceid()){
                        	if (dc.size() > 0) {
								docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())) );  
							}
                        }
						if (null != mem) {// 如果用户存在
								if (dt.size() > 0) {
									Tre_menberdocscore md = tre_menberdocscoreService
											.findByPrimaryKey(dt.get(0).getId());// 用户评分历史表
									md.setId(dt.get(0).getId());
									if (mem.getGroupid().getId() == 3) {// 如果会员是专家，修改评分
										if (dc.size() > 0) {
											md.setScore((Double.parseDouble(dt.get(0).getScore())
													+ Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore()))+"");// 获取专家点赞分数，新增评分数
											docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore())) );  
											List<Tb_groupdocgood> gdg=null;
											String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
													+ tb_docgood.getDocid().getId()
													+ "' and d.member_id='"
													+ tb_docgood.getMemberid().getId() + "' ";
											gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
											Tb_groupdocgood good= null;
											if(null!=gdg && gdg.size()>0){
												good=new Tb_groupdocgood();
												good.setId(gdg.get(0).getId());
												good.setGoodcound(gdg.get(0).getGoodcound()+1);
												good.setTodaytime(new Date());
												tb_groupdocgoodService.update(good);
											}else{
												good=new Tb_groupdocgood();
												good.setMember_id(tb_docgood.getMemberid());
												good.setDoc_id(tb_docgood.getDocid());
												good.setGoodcound(1);
												good.setContest_id(con.get(0).getContest_id());
												good.setContesttheme_id(con.get(0).getContesttheme_id());
												good.setTodaytime(new Date());
												tb_groupdocgoodService.save(good);
											}
										}
									} else if(mem.getGroupid().getId() == 1) {// 普通会员
										if (dc.size() > 0) {
											md.setScore((Double.parseDouble(dt.get(0).getScore())
													+ Double.parseDouble(dc.get(0).getContest_id().getGoodscore()))+"");// 获取普通用户点赞分数
											docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getGoodscore())) );  
										}
									} else if(mem.getGroupid().getId() == 2) {// 大众评委 
										if (dc.size() > 0) {
											md.setScore((Double.parseDouble(dt.get(0).getScore())
													+ Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore()))+"");// 获取大众评委点赞分数
											docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore())) );  
										}
									}else{//游客
										if (dc.size() > 0) {
											md.setScore((Double.parseDouble(dt.get(0).getScore())
													+ Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore()))+"");// 获取游客用户点赞分数
											docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())) );  
										}
									}
									md.setScoretime(time);
									tre_menberdocscoreService.update(md);// 修改用户评分历史表
								} else {
									Tre_menberdocscore md = new Tre_menberdocscore();
									if (mem.getGroupid().getId() == 3) {// 如果会员是专家
										if (dc.size() > 0) {
											md.setScore(dc.get(0).getContest_id().getExpertsgoodscore());// 获取专家点赞分数，新增评分数
											List<Tb_groupdocgood> gdg=null;
											String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
													+ tb_docgood.getDocid().getId()
													+ "' and d.member_id='"
													+ tb_docgood.getMemberid().getId() + "' ";
											gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
											Tb_groupdocgood good=null;
											if(null!=gdg && gdg.size()>0){
												 good=new Tb_groupdocgood();
												good.setId(gdg.get(0).getId());
												good.setGoodcound(gdg.get(0).getGoodcound()+1);
												good.setTodaytime(new Date());
												tb_groupdocgoodService.update(good);
											}else{
												good=new Tb_groupdocgood();
												good.setMember_id(tb_docgood.getMemberid());
												good.setDoc_id(tb_docgood.getDocid());
												good.setGoodcound(1);
												good.setContest_id(con.get(0).getContest_id());
												good.setContesttheme_id(con.get(0).getContesttheme_id());
												good.setTodaytime(new Date());
												tb_groupdocgoodService.save(good);
											}
										 
										}
									} else if(mem.getGroupid().getId() == 1){// 普通会员
										if (dc.size() > 0) {
											md.setScore(dc.get(0).getContest_id().getGoodscore());// 获取普通用户点赞分数
										}
									}else if(mem.getGroupid().getId() == 2) {// 大众评委 
										if (dc.size() > 0) {
											md.setScore(dc.get(0).getContest_id().getDazhonggoodscore());// 获取普通用户点赞分数
										}
									}else{//游客
										if (dc.size() > 0) {
											md.setScore(dc.get(0).getContest_id().getYoukegoodscore());// 获取普通用户点赞分数
										}
									}
									md.setMemberid(mem);
									md.setDocid(docresult);
									md.setScoretime(time);
									tre_menberdocscoreService.save(md);// 新增用户评分历史表
									docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(md.getScore())) );  
								}
						}
					}
					}
                 //--------------------------------------------------------------------------------------------
					// 修改点赞表
					tb_docgood.setId(listdocgood.get(0).getId());
			 	 	tb_docgood.setTodaytime(new Date());
					tb_docgood.setGoodcound(listdocgood.get(0).getGoodcound() + 1);
					tb_docgoodService.update(tb_docgood);
					Map map = new HashMap();
					if(null!=docresult.getMemberid().getRandroidios() && docresult.getMemberid().getRandroidios()==0 ){
						  //ios
						  JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),mem.getUsersname()+" 点赞了你的文件  ："+docresult.getDoctitle(), map, 0);
					  }else if(null!=docresult.getMemberid().getRandroidios() && docresult.getMemberid().getRandroidios()==1){
							//安卓
						  JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),mem.getUsersname()+" 点赞了你的文件  ："+docresult.getDoctitle(), map, 1);
					  }
					
					if (null != mem) {
						// 修改会员积分
						// 查询 会员等级积分来源表点赞数量
						List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
						if (null!=discuss && discuss.size() > 0) {
							mem.setLevelintegral(mem.getLevelintegral()
									+ discuss.get(0).getGood());
							List<Tb_level> level = tb_levelService
									.listlevel(mem.getLevelintegral() + "");
							if (null != level) {
								mem.setLevelid(level.get(0));
							}
							tb_memberService.update(mem);
						}  

					}
					// 修改文件表点赞数量
					if (null != docresult) {
						docresult.setId(tb_docgood.getDocid().getId());
						docresult.setFilegoodcount(docresult.getFilegoodcount() + 1);
					}
					//被点赞人加积分
					List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(memb.getLevelid().getId());
					if (null!=discuss2 && discuss2.size() > 0) {
						Double i=discuss2.get(0).getUngood();
						memb.setLevelintegral(memb.getLevelintegral()+ i);
						List<Tb_level> level = tb_levelService.listlevel((memb
								.getLevelintegral()+ i )+ "");
						if (null != level) {
							memb.setLevelid(level.get(0));
						}
						tb_memberService.update(memb);
					} 
					tb_docService.update(docresult);
					jsonResult.setCode("200");
					jsonResult.setMessage("点赞成功！");
				  	Tb_doc Tb_docresult =tb_docService.findByPrimaryKey(docresult.getId());
					jsonResult.setResult(Tb_docUtil.getControllerMap(Tb_docresult));  
				}
			} else {
				
				//------------------参赛---------------------------------------------------------------
				// 如果参赛，需要修改TRE_MenberDocScore用户评分历史表，修改文件表的评分数
				// 如果会员是专家或者普通用户，点赞分数获取从TB_Contest赛事表获取 TRE_DocContest参赛作品表
				String docstr = "SELECT d from Tre_doccontest d where 1=1 and d.doc_id = '"
						+ tb_docgood.getDocid().getId() + "' ";
				List<Tre_doccontest> dc = tre_doccontestService.findByHQL(docstr.toString());

				if (docresult.getIsparticipating() == 1) {
					String sql="select * from Tre_doccontest where doc_id='"+tb_docgood.getDocid().getId()+"'";
					List<Tre_doccontest> con=tre_doccontestService.findlistBySql(Tre_doccontest.class, sql);
					if(null!=con && con.get(0).getContest_id().getConteststatus()==0){//参赛的状态为征集中
                    if(null != tb_docgood.getDeviceid()){
                    	if (dc.size() > 0) {
							docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())) );  
						}
                    }
					if (null != mem) {// 如果用户存在
							List<Tre_menberdocscore> dt =null;
							if(null!=tb_docgood.getMemberid()){
								String sdt = "SELECT d from Tre_menberdocscore d where 1=1 and d.docid = '"
										+ tb_docgood.getDocid().getId()
										+ "' and d.memberid='"
										+ tb_docgood.getMemberid().getId() + "' ";
								 dt = tre_menberdocscoreService
										.findByHQL(sdt.toString());
							}
							if (null!=dt && dt.size()>0) {
								Tre_menberdocscore md = tre_menberdocscoreService
										.findByPrimaryKey(dt.get(0).getId());// 用户评分历史表
								 
									md.setId(dt.get(0).getId());
								
								if (mem.getGroupid().getId() == 3) {// 如果会员是专家，修改评分
									if (dc.size() > 0) {
										md.setScore((Double.parseDouble(dt.get(0).getScore())
												+ Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore()))+"");// 获取专家点赞分数，新增评分数
										docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore())) );  
										List<Tb_groupdocgood> gdg=null;
										String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
												+ tb_docgood.getDocid().getId()
												+ "' and d.member_id='"
												+ tb_docgood.getMemberid().getId() + "' ";
										gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
										if(null!=gdg && gdg.size()>0){
											Tb_groupdocgood good=new Tb_groupdocgood();
											good.setId(gdg.get(0).getId());
											good.setGoodcound(gdg.get(0).getGoodcound()+1);
											good.setTodaytime(new Date());
											tb_groupdocgoodService.update(good);
										}else{
											Tb_groupdocgood good=new Tb_groupdocgood();
											good.setMember_id(tb_docgood.getMemberid());
											good.setDoc_id(tb_docgood.getDocid());
											good.setGoodcound(1);
											good.setContest_id(con.get(0).getContest_id());
											good.setContesttheme_id(con.get(0).getContesttheme_id());
											good.setTodaytime(new Date());
											tb_groupdocgoodService.save(good);
										}
									}
								} else if(mem.getGroupid().getId() == 1) {// 普通会员
									if (dc.size() > 0) {
										md.setScore((Double.parseDouble(dt.get(0).getScore())
												+ Double.parseDouble(dc.get(0).getContest_id().getGoodscore()))+"");// 获取普通用户点赞分数
										docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getGoodscore())) );  
									}
								} else if(mem.getGroupid().getId() == 2) {// 大众评委 
									if (dc.size() > 0) {
										md.setScore((Double.parseDouble(dt.get(0).getScore())
												+ Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore()))+"");// 获取大众评委点赞分数
										docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore())) );  
									}
								}else{//游客
									if (dc.size() > 0) {
										md.setScore((Double.parseDouble(dt.get(0).getScore())
												+ Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore()))+"");// 获取游客用户点赞分数
										docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())) );  
									}
								}
								md.setScoretime(time);
								tre_menberdocscoreService.update(md);// 修改用户评分历史表
							} else {
								Tre_menberdocscore md = new Tre_menberdocscore();
								if (mem.getGroupid().getId() == 3) {// 如果会员是专家
									if (dc.size() > 0) {
										md.setScore(dc.get(0).getContest_id().getExpertsgoodscore());// 获取专家点赞分数，新增评分数
										List<Tb_groupdocgood> gdg=null;
										String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
												+ tb_docgood.getDocid().getId()
												+ "' and d.member_id='"
												+ tb_docgood.getMemberid().getId() + "' ";
										gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
										if(null!=gdg && gdg.size()>0){
											Tb_groupdocgood good=new Tb_groupdocgood();
											good.setId(gdg.get(0).getId());
											good.setGoodcound(gdg.get(0).getGoodcound()+1);
											good.setTodaytime(new Date());
											tb_groupdocgoodService.update(good);
										}else{
											Tb_groupdocgood good=new Tb_groupdocgood();
											good.setMember_id(tb_docgood.getMemberid());
											good.setDoc_id(tb_docgood.getDocid());
											good.setGoodcound(1);
											good.setContest_id(con.get(0).getContest_id());
											good.setContesttheme_id(con.get(0).getContesttheme_id());
											good.setTodaytime(new Date());
											tb_groupdocgoodService.save(good);
										}
									}
								} else if(mem.getGroupid().getId() == 1){// 普通会员
									if (dc.size() > 0) {
										md.setScore(dc.get(0).getContest_id().getGoodscore());// 获取普通用户点赞分数
									}
								}else if(mem.getGroupid().getId() == 2) {// 大众评委 
									if (dc.size() > 0) {
										md.setScore(dc.get(0).getContest_id().getDazhonggoodscore());// 获取普通用户点赞分数
									}
								}else{//游客
									if (dc.size() > 0) {
										md.setScore(dc.get(0).getContest_id().getYoukegoodscore());// 获取游客用户点赞分数
									}
								}
								
								md.setMemberid(mem);
								md.setDocid(docresult);
								md.setScoretime(time);
								tre_menberdocscoreService.save(md);// 新增用户评分历史表
								docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore())+Double.parseDouble(md.getScore())) );  
							}
					}
					}
				}
				Map map = new HashMap();
				if(null!=docresult.getMemberid().getRandroidios() && docresult.getMemberid().getRandroidios()==0 ){
					  //ios
					if(null!=tb_docgood.getMemberid()){
						 JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),mem.getUsersname()+" 点赞了你的文件  ："+docresult.getDoctitle(), map, 0);
					}else{
						 JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),"游客点赞了你的文件  ："+docresult.getDoctitle(), map, 0);
					}
					  
				  }else if(null!=docresult.getMemberid().getRandroidios() && docresult.getMemberid().getRandroidios()==1){
						//安卓
					  if(null!=tb_docgood.getMemberid()){
					      JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),mem.getUsersname()+" 点赞了你的文件  ："+docresult.getDoctitle(), map, 1);
					  }else{
						  JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),"游客点赞了你的文件  ："+docresult.getDoctitle(), map, 1);
							 
					  }
				  }
				// 修改文件点赞数量
				if (null != docresult) {
					docresult.setFilegoodcount(docresult.getFilegoodcount() + 1);
				}
				if (null != mem) {
					// 修改会员积分
					List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
					if (null!=discuss && discuss.size() > 0) {
						Double i=discuss.get(0).getGood();
						mem.setId(tb_docgood.getMemberid().getId());
						mem.setLevelintegral(mem.getLevelintegral()+ i);
						List<Tb_level> level = tb_levelService.listlevel(mem
								.getLevelintegral() + "");
						if (null != level) {
							mem.setLevelid(level.get(0));
						}
						tb_memberService.update(mem);
					} 
				}
				List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(memb.getLevelid().getId());
				if (null!=discuss2 && discuss2.size() > 0) {
					Double i=discuss2.get(0).getUngood();
					memb.setLevelintegral(memb.getLevelintegral()+ i);
					List<Tb_level> level = tb_levelService.listlevel((memb
							.getLevelintegral()+ i )+ "");
					if (null != level) {
						memb.setLevelid(level.get(0));
					}
					tb_memberService.update(memb);
				} 
				tb_docService.update(docresult);
				// 新增点赞表
			 	tb_docgood.setTodaytime(new Date());
				tb_docgood.setGoodcound(1);
				tb_docgood.setDeviceid(tb_docgood.getDeviceid());
				tb_docgoodService.save(tb_docgood);
			 Tb_doc Tb_docresult =tb_docService.findByPrimaryKey(docresult.getId());
				jsonResult.setResult(Tb_docUtil.getControllerMap(Tb_docresult)); 
				jsonResult.setCode("200");
				jsonResult.setMessage("点赞成功！");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}

		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 更新 tb_docgood
	 * 
	 * @param tb_docgood
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/docgood/update")
	public String update(Tb_docgood tb_docgood, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			 
			tb_docgood.setTodaytime(new Date());
			tb_docgoodService.update(tb_docgood);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");

		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 *            数组
	 * @return json
	 */
	@RequestMapping(value = "/docgood/delete")
	public String delete(String[] ids, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (ids != null && ids.length > 0) {
				tb_docgoodService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				Log(request, "操作了点赞删除");
			} else {
				jsonResult.setMessage("请选择删除选项!");
				jsonResult.setCode("300");
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

}
