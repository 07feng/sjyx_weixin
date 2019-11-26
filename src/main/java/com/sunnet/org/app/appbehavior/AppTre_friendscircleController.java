package com.sunnet.org.app.appbehavior;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.sunnet.org.apppush.util.JPushAllUtil;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.doc.vo.Tre_friendscircleUtil;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.model.vie_docpay;

/***
 * 关注
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTre_friendscircleController extends BaseController {

	@Autowired
	private ITre_friendscircleService tre_friendscircleService;

	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private ITb_memberService tb_memberService;
 	@Autowired
	private ITb_levelService tb_levelService; 
	/**
	 * 返回分页 tre_friendscircle
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/friendscircle/list")
	public String list(PageBean pageBean, HttpServletRequest request,String memberid_id,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tre_friendscircle> list = null;
			StringBuffer str = new StringBuffer();
			str.append(" where 1=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("circlememberid.id"))) {
				str.append(" and circlememberid = '" + request.getParameter("circlememberid.id") + "'");
			}
			if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
				str.append(" and memberid = '" + request.getParameter("memberid.id") + "'");
			}
			QueryResult<Tre_friendscircle> result = new QueryResult<Tre_friendscircle>();
			String order = " order by addtime desc ";
			 result = tre_friendscircleService.getPage(pageBean, str.toString(), "Tre_friendscircle", "id", order, Tre_friendscircle.class);
			 list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord",list.size());// 总记录数// result.getPageBean().getTotalRecord()
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
				for (Tre_friendscircle obj : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("circlememberid", obj.getCirclememberid().getId());
					map.put("id", obj.getId());
				 	if (obj.getCirclememberid() != null) {
				 		if (StringUtils.isStringNull(request.getParameter("mycirclememberid_id"))) {
							 //查询别人的关注，我有没有关注
								StringBuffer strs = new StringBuffer();
								strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
								strs.append(" and o.circlememberid = '" +obj.getCirclememberid().getId() + "' ");
								strs.append(" and o.memberid = '" + request.getParameter("mycirclememberid_id") + "'");
								List<Tre_friendscircle> listdoc=tre_friendscircleService.findByHQL(strs.toString());
								if(null!=listdoc && listdoc.size()>0){
									map.put("gz_isfriends", listdoc.get(0).getIsfriends());
									map.put("gz_isfriendto", "yes");
								}else{
									map.put("gz_isfriendto", "no");
									map.put("gz_isfriends", "");
							} 
						}
						map.put("circlememberid", obj.getCirclememberid().getId());
						map.put("circleusersname", obj.getCirclememberid().getUsersname());
						if(obj.getCirclememberid().getLevelid()!=null){
							map.put("circlelevelid", obj.getCirclememberid().getLevelid().getId());
							map.put("circlelevelname", obj.getCirclememberid().getLevelid().getLevelname());
						}else{
							map.put("circlelevelid", "");
							map.put("circlelevelname","");
						}
						if (obj.getCirclememberid().getHeadimg() != null) {
							map.put("circleheadimg", obj.getCirclememberid()
									.getHeadimg());
						} else {
							map.put("circleheadimg", "");
						}
						if (obj.getCirclememberid().getIntroduction() != null) {// 个人简介
							map.put("circleintroduction", obj.getCirclememberid()
									.getIntroduction());
						} else {
							map.put("circleintroduction", "");
						}
					} else {
						map.put("circlememberid", "");
						map.put("circleusersname", "");
						map.put("circleheadimg", "");
						map.put("circleintroduction", "");
						map.put("circlelevelid","");
						map.put("circlelevelname", "");
					} 
					if (obj.getMemberid() != null) {
						map.put("memberid", obj.getMemberid().getId());
						map.put("usersname", obj.getMemberid().getUsersname());
						 if (StringUtils.isStringNull(memberid_id)) {
							 //查询别人的粉丝，我有没有关注
								StringBuffer strs = new StringBuffer();
								strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
								strs.append(" and o.circlememberid = '" +obj.getMemberid().getId() + "' ");
								strs.append(" and o.memberid = '" + memberid_id + "'");
								List<Tre_friendscircle> listdoc=tre_friendscircleService.findByHQL(strs.toString());
								if(null!=listdoc && listdoc.size()>0){
									map.put("bf_isfriends", listdoc.get(0).getIsfriends());
									map.put("f_isfriendto", "yes");
								}else{
									map.put("f_isfriendto", "no");
									map.put("bf_isfriends", "");
							} 
						}
						if(obj.getMemberid().getLevelid()!=null){
							map.put("levelid", obj.getMemberid().getLevelid().getId());
							map.put("levelname", obj.getMemberid().getLevelid().getLevelname());
						}else{
							map.put("levelid", "");
							map.put("levelname","");
						}
						
						if (obj.getMemberid().getHeadimg() != null) {
							map.put("headimg", obj.getMemberid().getHeadimg());
						} else {
							map.put("headimg", "");
						}
						if (obj.getCirclememberid().getIntroduction() != null) { // 个人简介
							map.put("introduction", obj.getMemberid().getIntroduction());
						} else {
							map.put("introduction", "");
						}
					} else {
						map.put("memberid", "");
						map.put("usersname", "");
						map.put("headimg", "");
						map.put("introduction", "");
						map.put("levelid","");
						map.put("levelname", "");
					}

					map.put("addtime", obj.getAddtime());
					map.put("isfriends",obj.getIsfriends());
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
	@RequestMapping(value = "/friendscircle/listAll")
	public String listAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tre_friendscircle> result = tre_friendscircleService.findAll();
			jsonResult.setResult(Tre_friendscircleUtil
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
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/friendscircle/selectKey")
	public String selectKey(String fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Tre_friendscircle result = tre_friendscircleService
						.findByPrimaryKey(fdId);
				jsonResult.setResult(Tre_friendscircleUtil
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
	 * 保存 tre_friendscircle
	 * 
	 * @param tre_friendscircle
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/friendscircle/save")
	public String save(Tre_friendscircle tre_friendscircle,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			String msg = StringUtils.isObject(
					new Object[] { tre_friendscircle.getCirclememberid(),
							tre_friendscircle.getMemberid() }, new String[] {
							"被关注用户不能为空", "用户不能为空" });
			if (!msg.equals("")) {
				jsonResult.setCode("504");
				jsonResult.setMessage(msg);
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			// 查询 会员等级积分来源表点赞数量
			List<Tre_friendscircle> result = tre_friendscircleService
					.listfriend(tre_friendscircle.getCirclememberid().getId(),
							tre_friendscircle.getMemberid().getId());
			//查询是否是互相关注，如果results存在则是
			List<Tre_friendscircle> results = tre_friendscircleService
					.listfriend(tre_friendscircle.getMemberid().getId(),
							tre_friendscircle.getCirclememberid().getId());
			Tb_member mem = tb_memberService.findByPrimaryKey(tre_friendscircle.getCirclememberid().getId());
			List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
			if (null != mem) {
				Tb_member amem = tb_memberService.findByPrimaryKey(tre_friendscircle.getMemberid().getId());
				List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(amem.getLevelid().getId());
				//关注会员存在 修改关注会员积分
				if (null != amem) {
					if (null!=result) {
						//如果是互相关注，取消了一方的关注，另一方修改
						if(null!=results){
							Tre_friendscircle fd=new Tre_friendscircle();
							fd.setId(results.get(0).getId());
							fd.setIsfriends(0);
							tre_friendscircleService.update(fd);
						} 
						//discuss  amem 关注人
						if (null!=discuss && discuss.size() > 0) {
							/*amem.setId(tre_friendscircle.getCirclememberid().getId());*/
							amem.setLevelintegral(amem.getLevelintegral() - discuss.get(0).getFocus());
							List<Tb_level> level2=tb_levelService.listlevel(amem.getLevelintegral()+"");
							if(null!=level2){
								amem.setLevelid(level2.get(0));
							}
							tb_memberService.update(amem);
						} 
						if(null!=discuss2 && discuss2.size() > 0){
							/*mem.setId(tre_friendscircle.getCirclememberid().getId());*/
							mem.setLevelintegral(mem.getLevelintegral()
									- discuss.get(0).getUnfocus());
							List<Tb_level> level=tb_levelService.listlevel(mem.getLevelintegral()+"");
							if(null!=level){
								mem.setLevelid(level.get(0));
							}
							tb_memberService.update(mem);
						}
						tre_friendscircleService.remove(result.get(0).getId());
						jsonResult.setMessage("取消关注执行成功！");
						jsonResult.setCode("200");
						return ajaxJson(JSONObject.toJSONString(jsonResult),
								response);
					} else {
						if(null!=results){
							Tre_friendscircle fd=new Tre_friendscircle();
							fd.setId(results.get(0).getId());
							fd.setIsfriends(1);
							tre_friendscircleService.update(fd);
							tre_friendscircle.setIsfriends(1);
						}else {
							tre_friendscircle.setIsfriends(0);
						}
						tre_friendscircle.setAddtime(new Date());
						tre_friendscircleService.save(tre_friendscircle);
						/* App推送消息给被关注人  circlememberid*/
						Map map = new HashMap();
						if(null!=mem.getRandroidios() && mem.getRandroidios()==0 ){
							  //ios
							  JPushAllUtil.jSend_notification(mem.getRegistrationid(),amem.getUsersname()+"关注了你  !", map, 0);
						  }else if(null!=mem.getRandroidios() && mem.getRandroidios()==1){
								//安卓
							  JPushAllUtil.jSend_notification(mem.getRegistrationid(),amem.getUsersname()+"关注了你  !", map, 1);
						  }
						/*推送结束-------------*/
						amem.setId(tre_friendscircle.getMemberid().getId());
						//discuss  amem 关注人
						if (null!=discuss && discuss.size() > 0) {
							/*amem.setId(tre_friendscircle.getCirclememberid().getId());*/
							amem.setLevelintegral(amem.getLevelintegral()
									+ discuss.get(0).getFocus());
							List<Tb_level> level2=tb_levelService.listlevel(amem.getLevelintegral()+"");
							if(null!=level2){
								amem.setLevelid(level2.get(0));
							}
							tb_memberService.update(amem);
						} 
						//被关注人
						if(null!=discuss2 && discuss2.size() > 0){
							/*mem.setId(tre_friendscircle.getCirclememberid().getId());*/
							mem.setLevelintegral(mem.getLevelintegral()
									+ discuss.get(0).getUnfocus());
							List<Tb_level> level=tb_levelService.listlevel(mem.getLevelintegral()+"");
							if(null!=level){
								mem.setLevelid(level.get(0));
							}
							tb_memberService.update(mem);
						}
						jsonResult.setCode("200");
						jsonResult.setMessage("执行成功！");
					}
				} else {
					jsonResult.setCode("505"); 
					jsonResult.setMessage("Oh！ no~~用户不存在");
					return ajaxJson(JSONObject.toJSONString(jsonResult), response);
				}
			} else {
				jsonResult.setCode("505");
				jsonResult.setMessage("Oh！ no~~您将关注的用户不存在");
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
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
	 * 更新 tre_friendscircle
	 * 
	 * @param tre_friendscircle
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/friendscircle/update")
	public String update(Tre_friendscircle tre_friendscircle,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			tre_friendscircleService.update(tre_friendscircle);
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
	@RequestMapping(value = "/friendscircle/delete")
	public String delete(String[] ids, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (ids != null && ids.length > 0) {
				tre_friendscircleService.delete(ids);
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
