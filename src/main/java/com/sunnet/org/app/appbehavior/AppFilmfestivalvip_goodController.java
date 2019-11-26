package com.sunnet.org.app.appbehavior;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;

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
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_good;
import com.sunnet.org.filmfestival.service.IFilmfestivalService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_goodService;
import com.sunnet.org.filmfestival.vo.FilmfestivalvipUtil;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_goodUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.vo.Tb_docUtil;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.util.StringUtils;

/***
 * 影展点赞
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppFilmfestivalvip_goodController extends BaseController {

	@Autowired
	private IFilmfestivalvip_goodService filmfestivalvip_goodService;
	@Autowired
	private ITre_friendscircleService tre_friendscircleService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private IFilmfestivalService filmfestivalService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_levelService tb_levelService;
	@Autowired
	private IFilmfestivalvipService filmfestivalvipService;

	/**
	 * 点赞
	 * author jinhao
	 * @param worksShowId
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/session/worksShowGood")
	public String worksShowGood(String worksShowId, HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request, response);
			String opId = getOpenId(request,response);
			int vip_id = Integer.parseInt(worksShowId);		//影展id
			List<Object[]> mem = null;
			List<Object[]> memc = filmfestivalvipService.getFilmMemLevel(vip_id);
			if (null != memberId){
				mem = tb_memberService.getMemLevel(memberId);	//点赞用户信息
			}
			//查询用户点赞记录，没有就新创建,如果点过赞，在原有的点赞数量上修改
			StringBuffer str = new StringBuffer("SELECT Id,vipid,Memberid,Goodcound,deviceid,todaytime FROM Filmfestivalvip_Good WHERE vipid=?");
			if (null != memberId) {
				str.append(" and Memberid ='").append(memberId).append("'");
			}else {
				str.append(" and deviceid ='").append(opId).append("'");
			}
			List<Object[]> isGood = filmfestivalvip_goodService.findBySql(str.toString(),vip_id);
			int back = 0;
			if (null != mem)
				back = filmfestivalvip_goodService.doGood(opId,mem.get(0),memc.get(0),isGood,vip_id);
			else
				back = filmfestivalvip_goodService.doGood(opId,null,memc.get(0),isGood,vip_id);
			if (0== back){
				// 这里判断今天是否点过赞
				jsonResult.setCode("1");
				jsonResult.setMsg("您今天已点过赞！请明天再来！");
			}else {
				jsonResult.setCode(Constants.SUCCESS_CODE);
				jsonResult.setMsg(Constants.SUCCESS_DATA);
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}


	/**
	 * 返回分页 filmfestivalvip_good
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvipgood/list")
	public String list(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Filmfestivalvip_good> list = null;
			StringBuffer str = new StringBuffer();
			str.append("  where 1=1 "); // 初始化语句
			if (StringUtils.isStringNull(request.getParameter("vipid"))) {
				str.append(" and vipid = '" + request.getParameter("vipid")
						+ "'");
			}
			if (StringUtils.isStringNull(request.getParameter("memberid"))) {
				str.append(" and memberid = '" + request.getParameter("memberid") + "'");
			}else{
				str.append(" and memberid is not null  ");
			}
		 
			QueryResult<Filmfestivalvip_good> result = filmfestivalvip_goodService
					.getPage(pageBean, str.toString(), "Filmfestivalvip_good",
							"id", " order by goodcound desc ",
							Filmfestivalvip_good.class);
			list = result.getResultList();
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("startPage", pageBean.getStartPage());// 当前页
			map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
			map1.put("totalRecord", list.size());// 总记录数
			map1.put("totalPage",
					(int) Math.ceil(list.size() / pageBean.getPageSize()));// 一共有多少页
			item.add(map1);
			for (Filmfestivalvip_good obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj.getId());
				map.put("vipid", obj.getVipid());
				if (obj.getMemberid() != null) {
					map.put("memberid", obj.getMemberid().getId());
					map.put("usersname", obj.getMemberid().getUsersname());
					map.put("headimg", obj.getMemberid().getHeadimg());
					if (StringUtils.isStringNull(request
							.getParameter("memberid_id"))) {
						StringBuffer strs = new StringBuffer();
						strs.append(" from Tre_friendscircle o where 1=1 "); // 初始化语句
						strs.append(" and o.circlememberid = '"
								+ obj.getMemberid().getId() + "' ");
						strs.append(" and o.memberid = '"
								+ request.getParameter("memberid_id") + "'");
						List<Tre_friendscircle> listdoc = tre_friendscircleService
								.findByHQL(strs.toString());
						if (null != listdoc && listdoc.size() > 0) {
							map.put("isfriends", listdoc.get(0).getIsfriends());
							map.put("isfriendto", "yes");
						} else {
							map.put("isfriends", "");
							map.put("isfriendto", "no");
						}
					} else {
						map.put("isfriendto", "no");
					}
				} else {
					map.put("memberid", "");
					map.put("usersname", "");
					map.put("headimg", "");
					map.put("isfriends", "");
					map.put("isfriendto", "no");
				}
				
				map.put("goodcound", obj.getGoodcound());
				map.put("deviceid", obj.getDeviceid());
				map.put("todaytime", obj.getTodaytime());
				
				item.add(map);
			}
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
	@RequestMapping(value = "/filmfestivalvipgood/listAll")
	public String listAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Filmfestivalvip_good> result = filmfestivalvip_goodService
					.findAll();
			jsonResult.setResult(Filmfestivalvip_goodUtil
					.getControllerList(result));
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
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/filmfestivalvipgood/selectKey")
	public String selectKey(String fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Filmfestivalvip_good result = filmfestivalvip_goodService
						.findByPrimaryKey(fdId);
				jsonResult.setResult(Filmfestivalvip_goodUtil
						.getControllerMap(result));
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
	 * 保存 filmfestivalvip_good
	 * 
	 * @param filmfestivalvip_good
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvipgood/save")
	public String save(Filmfestivalvip_good filmfestivalvip_good,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			String msg = StringUtils.isObject(
					new Object[] { filmfestivalvip_good.getVipid() },
					new String[] { "点赞的影展不能为空" });
			if (!msg.equals("")) {
				jsonResult.setCode("504");
				jsonResult.setMessage(msg);
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			if (null == filmfestivalvip_good.getMemberid()
					&& null == filmfestivalvip_good.getDeviceid()) {
				jsonResult.setCode("504");
				jsonResult.setMessage("设备id不能为空或者会员不能为空");
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			List<Filmfestivalvip_good> result = null;
			if (null != filmfestivalvip_good.getMemberid()) {
				// 查询今天是否点过赞
				result = filmfestivalvip_goodService.listgood(
						filmfestivalvip_good.getVipid(), filmfestivalvip_good
								.getMemberid().getId(), filmfestivalvip_good
								.getDeviceid());
			} else {
				result = filmfestivalvip_goodService.listgood(
						filmfestivalvip_good.getVipid(), null,
						filmfestivalvip_good.getDeviceid());
			}
			// 查询点赞人与点赞影展是不是存在，存在则修改点赞数，不存在则新增数据
			StringBuffer str = new StringBuffer();
			str.append(" SELECT d from Filmfestivalvip_good d where 1=1 and d.vipid = '"
					+ filmfestivalvip_good.getVipid() + "' "); // 初始化语句
			if (null != filmfestivalvip_good.getMemberid()) {
				str.append(" and d.memberid = '"
						+ filmfestivalvip_good.getMemberid().getId() + "'");
			}
			if (null != filmfestivalvip_good.getDeviceid()) {
				str.append(" and d.deviceid ='"
						+ filmfestivalvip_good.getDeviceid() + "'");
			}
			List<Filmfestivalvip_good> listdocgood = filmfestivalvip_goodService
					.findByHQL(str.toString());
			// 查询影展信息
			Filmfestivalvip docresult = filmfestivalvipService.findByPrimaryKey(filmfestivalvip_good.getVipid());
			Tb_member mem = null;
			if (null != filmfestivalvip_good.getMemberid()) {
				mem = tb_memberService.findByPrimaryKey(filmfestivalvip_good
						.getMemberid().getId());
			}
			//如果点过赞，在原有的点赞数量上修改
			if (listdocgood.size() > 0) {
				if (null != result) {
					// 这里判断今天是否点过赞
					jsonResult.setCode("201");
					jsonResult.setMessage("您今天已点过赞！请明天再来！");
				} else {
					filmfestivalvip_good.setId(listdocgood.get(0).getId());
					filmfestivalvip_good.setTodaytime(new Date());
					filmfestivalvip_good.setGoodcound(listdocgood.get(0).getGoodcound() + 1);
					filmfestivalvip_goodService.update(filmfestivalvip_good);
					Map map = new HashMap();
					if(null!=docresult.getMember_id().getRandroidios() && docresult.getMember_id().getRandroidios()==0 ){
						  //ios
						  JPushAllUtil.jSend_notification(docresult.getMember_id().getRegistrationid(),mem.getUsersname()+" 点赞了你的影展 ："+docresult.getTitel(), map, 0);
					  }else if(null!=docresult.getMember_id().getRandroidios() && docresult.getMember_id().getRandroidios()==1){
							//安卓
						  JPushAllUtil.jSend_notification(docresult.getMember_id().getRegistrationid(),mem.getUsersname()+" 点赞了你的影展  ："+docresult.getTitel(), map, 1);
					  }
					if (null != mem) {
						// 修改会员积分
						List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
						Tb_member cmember=tb_memberService.findByPrimaryKey(docresult.getMember_id().getId());
						List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(cmember.getLevelid().getId());
						if (null != discuss && discuss.size() > 0) {
							// 点赞人加积分
							mem.setId(filmfestivalvip_good.getMemberid().getId());
							mem.setLevelintegral(mem.getLevelintegral() + discuss.get(0).getGood());
							// 查询等级积分
							List<Tb_level> level = tb_levelService.listlevel(mem.getLevelintegral() + discuss.get(0).getGood() + "");
							if (null != level) {
								mem.setLevelid(level.get(0));
							}
							tb_memberService.update(mem);

						}
						if(null!=discuss2 && discuss2.size()>0){
							//被点赞人加积分
							cmember.setLevelintegral(cmember.getLevelintegral()+discuss2.get(0).getUngood());
							List<Tb_level> clevel = tb_levelService.listlevel(cmember.getLevelintegral()+discuss2.get(0).getUngood() + "");
							if (null != clevel) {
								cmember.setLevelid(clevel.get(0));
							}
							tb_memberService.update(cmember);
						}
					}
					// 修改文件表点赞数量
					if (null != docresult) {
						docresult.setId(filmfestivalvip_good.getVipid());
						if(null!=docresult.getFilegoodcount()){
							docresult.setFilegoodcount(docresult.getFilegoodcount() + 1);
						}else{
							docresult.setFilegoodcount(1);
						}
					}
					filmfestivalvipService.update(docresult);
					jsonResult.setCode("200");
					jsonResult.setMessage("点赞成功！");
					Filmfestivalvip Tb_docresult =filmfestivalvipService.findByPrimaryKey(docresult.getId());
					jsonResult.setResult(FilmfestivalvipUtil.getControllerMap(Tb_docresult));  
				}
				
			}else{//从未点过赞，则新增数据
				Map map = new HashMap();
				if(null!=docresult.getMember_id().getRandroidios() && docresult.getMember_id().getRandroidios()==0 ){
					  //ios
					if(null!=filmfestivalvip_good.getMemberid()){
						 JPushAllUtil.jSend_notification(docresult.getMember_id().getRegistrationid(),mem.getUsersname()+" 点赞了你的影展  ："+docresult.getTitel(), map, 0);
					}else{
						 JPushAllUtil.jSend_notification(docresult.getMember_id().getRegistrationid(),"游客点赞了你的影展  ："+docresult.getTitel(), map, 0);
					}
					  
				  }else if(null!=docresult.getMember_id().getRandroidios() && docresult.getMember_id().getRandroidios()==1){
						//安卓
					  if(null!=filmfestivalvip_good.getMemberid()){
					      JPushAllUtil.jSend_notification(docresult.getMember_id().getRegistrationid(),mem.getUsersname()+" 点赞了你的影展  ："+docresult.getTitel(), map, 1);
					  }else{
						  JPushAllUtil.jSend_notification(docresult.getMember_id().getRegistrationid(),"游客点赞了你的影展  ："+docresult.getTitel(), map, 1);
							 
					  }
				  }
				// 修改文件点赞数量
				if (null != docresult) {
					if(null!=docresult.getFilegoodcount()){
						docresult.setFilegoodcount(docresult.getFilegoodcount() + 1);
					}else{
						docresult.setFilegoodcount(1);
					}
					
				}
				if (null != mem) {
					// 修改会员积分
					List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
					if (discuss.size() > 0) {
						mem.setId(filmfestivalvip_good.getMemberid().getId());
						mem.setLevelintegral(mem.getLevelintegral()
								+ discuss.get(0).getGood());
						tb_memberService.update(mem);
						List<Tb_level> level = tb_levelService.listlevel(mem
								.getLevelintegral() + "");
						if (null != level) {
							mem.setId(filmfestivalvip_good.getMemberid().getId());
							mem.setLevelid(level.get(0));
							tb_memberService.update(mem);
						}
					} 
				}
				Tb_member cmember=tb_memberService.findByPrimaryKey(docresult.getMember_id().getId());
				List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(cmember.getLevelid().getId());
				if(null!=discuss2 && discuss2.size()>0){
					//被点赞人加积分
					cmember.setLevelintegral(cmember.getLevelintegral()+discuss2.get(0).getUngood());
					List<Tb_level> clevel = tb_levelService.listlevel(cmember.getLevelintegral()+discuss2.get(0).getUngood() + "");
					if (null != clevel) {
						cmember.setLevelid(clevel.get(0));
					}
					tb_memberService.update(cmember);
				}
				//修改影展点赞数
				filmfestivalvipService.update(docresult);
				// 新增点赞影展记录
				filmfestivalvip_good.setTodaytime(new Date());
			 	filmfestivalvip_good.setGoodcound(1);
				filmfestivalvip_good.setDeviceid(filmfestivalvip_good.getDeviceid());
				filmfestivalvip_goodService.save(filmfestivalvip_good);
				Filmfestivalvip Tb_docresult =filmfestivalvipService.findByPrimaryKey(docresult.getId());
				jsonResult.setResult(FilmfestivalvipUtil.getControllerMap(Tb_docresult)); 
				jsonResult.setCode("200");
				jsonResult.setMessage("点赞成功！"); 
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 更新 filmfestivalvip_good
	 * 
	 * @param filmfestivalvip_good
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvipgood/update")
	public String update(Filmfestivalvip_good filmfestivalvip_good,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			filmfestivalvip_goodService.update(filmfestivalvip_good);
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
	 * 删除
	 * 
	 * @param ids
	 *            数组
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvipgood/delete")
	public String delete(String[] ids, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (ids != null && ids.length > 0) {
				filmfestivalvip_goodService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
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
