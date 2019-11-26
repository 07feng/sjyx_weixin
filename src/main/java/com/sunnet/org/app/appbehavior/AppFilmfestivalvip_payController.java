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
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.sunnet.org.apppush.util.JPushAllUtil;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_pay;
import com.sunnet.org.filmfestival.service.IFilmfestivalService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_payService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_payUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_memberconsumption;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_memberconsumptionService;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.model.vie_docpay;
import com.sunnet.org.view.model.vie_vippay;
import com.sunnet.org.view.service.Ivie_vippayService;

/***
 * 影展打赏
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppFilmfestivalvip_payController extends BaseController {
	@Autowired
	private Ivie_vippayService vie_vippayService;
	@Autowired
	private IFilmfestivalvip_payService filmfestivalvip_payService;
	@Autowired
	private ITb_memberconsumptionService tb_memberconsumptionService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_levelService tb_levelService;
	@Autowired
	private ITre_friendscircleService tre_friendscircleService;
	@Autowired
	private IFilmfestivalService filmfestivalService;
	@Autowired
	private IFilmfestivalvipService filmfestivalvipService;

	 /**
	  * 谁打赏了我的影展，我打赏了谁的影展
	  * @param pageBean
	  * @param request
	  * @param response
	  * @return
	  */
	@RequestMapping(value = "/filmfestivalvippay/list")
	public String list(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			QueryResult<Filmfestivalvip_pay> result = filmfestivalvip_payService
					.list(pageBean, request);
			jsonResult.setResult(result);
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
	@RequestMapping(value = "/filmfestivalvippay/listAll")
	public String listAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Filmfestivalvip_pay> result = filmfestivalvip_payService
					.findAll();
			jsonResult.setResult(Filmfestivalvip_payUtil
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
	@RequestMapping(value = "/filmfestivalvippay/selectKey")
	public String selectKey(String fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Filmfestivalvip_pay result = filmfestivalvip_payService
						.findByPrimaryKey(fdId);
				jsonResult.setResult(Filmfestivalvip_payUtil
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
	 * 保存 filmfestivalvip_pay
	 * 
	 * @param filmfestivalvip_pay
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvippay/save")
	public String save(Filmfestivalvip_pay filmfestivalvip_pay,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			Filmfestivalvip doc = filmfestivalvipService
					.findByPrimaryKey(filmfestivalvip_pay.getVipid().getId());
			// 被打赏人
			Tb_member docmember = tb_memberService.findByPrimaryKey(doc
					.getMember_id().getId());
			// 打赏人
			Tb_member member = tb_memberService
					.findByPrimaryKey(filmfestivalvip_pay.getMemberid().getId());
			// 查询 会员等级积分来源表点赞数量
			List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(member.getLevelid().getId());
			List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(docmember.getLevelid().getId());
						
			if (member.getWallet() >= filmfestivalvip_pay.getPayamount()) {
				if (discuss != null && discuss.size() > 0) {
					member.setLevelintegral(discuss.get(0).getPay() + member.getLevelintegral());
				}  
				if(discuss2 != null && discuss2.size() > 0){
					docmember.setLevelintegral(discuss2.get(0)
							.getPay()+ docmember.getLevelintegral());
				}
				member.setWallet(member.getWallet()
						- filmfestivalvip_pay.getPayamount());
				tb_memberService.update(member);

				docmember.setWallet(docmember.getWallet()
						+ filmfestivalvip_pay.getPayamount());
				tb_memberService.update(docmember);
				filmfestivalvip_pay.setAddtime(new Date());
				filmfestivalvip_payService.save(filmfestivalvip_pay);
				if(null!=doc.getFilepaycount()){
					doc.setFilepaycount(doc.getFilepaycount() + 1);
				}else{
					doc.setFilepaycount(1);
				}
				filmfestivalvipService.update(doc);
				// 记录会员资金明细
				Tb_memberconsumption consumption = new Tb_memberconsumption();
				consumption.setMemberid(member);// tb_docpay.getMemberid()
				consumption.setAmount(filmfestivalvip_pay.getPayamount());
				consumption.setOperationtype(103);// 状态影展打赏
				consumption.setOverage(member.getWallet());// 余额
				consumption.setOperationtime(new Date());
				tb_memberconsumptionService.save(consumption);
				Tb_memberconsumption me = new Tb_memberconsumption();
				me.setMemberid(docmember);
				me.setAmount(filmfestivalvip_pay.getPayamount());
				me.setOperationtype(2);// 状态
				me.setOverage(docmember.getWallet());// 余额
				me.setOperationtime(new Date());
				tb_memberconsumptionService.save(me);
				// App推送
				Map map = new HashMap();
				if (null != doc.getMember_id().getRandroidios()
						&& doc.getMember_id().getRandroidios() == 0) {
					// ios
					JPushAllUtil.jSend_notification(doc.getMember_id()
							.getRegistrationid(), member.getUsersname()
							+ " 打赏了你的影展  ：" + doc.getTitel() + ":"
							+ filmfestivalvip_pay.getPayamount() + "元", map, 0);
				} else if (null != doc.getMember_id().getRandroidios()
						&& doc.getMember_id().getRandroidios() == 1) {
					// 安卓
					JPushAllUtil.jSend_notification(doc.getMember_id()
							.getRegistrationid(), member.getUsersname()
							+ " 打赏了你的影展  ：" + doc.getTitel() + ":"
							+ filmfestivalvip_pay.getPayamount() + "元", map, 1);
				}
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
			} else {
				jsonResult.setCode("201");
				jsonResult.setMessage("您的余额不总！请充值！");
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 更新 filmfestivalvip_pay
	 * 
	 * @param filmfestivalvip_pay
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvippay/update")
	public String update(Filmfestivalvip_pay filmfestivalvip_pay,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			filmfestivalvip_payService.update(filmfestivalvip_pay);
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
	@RequestMapping(value = "/filmfestivalvippay/delete")
	public String delete(String[] ids, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (ids != null && ids.length > 0) {
				filmfestivalvip_payService.delete(ids);
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

	/**
	 * （我的影展赏客）
	 * 
	 * @param pageBean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/filmfestivalvippay/listmember")
	public String listmember(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<vie_vippay> list = null;
			StringBuffer str = new StringBuffer();
			str.append("  where 1=1 "); // 初始化语句
			// 根据被打赏用户编号查询（我的影展赏客）
			if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
				str.append(" and memberid = '"
						+ request.getParameter("memberid_id") + "'");
			}
			QueryResult<vie_vippay> result = new QueryResult<vie_vippay>();
			String order = " order by cpaynum desc ";
			result = vie_vippayService.getPage(pageBean, str.toString(),
					"vie_vippay", "id", order, vie_vippay.class);
			list = result.getResultList();
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("startPage", pageBean.getStartPage());// 当前页
			map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
			map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
			map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
			item.add(map1);
			for (vie_vippay obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj.getId());
				map.put("usersname", obj.getUsersname());
				map.put("headimg", obj.getHeadimg());
				map.put("levelid", obj.getLevelid());
				map.put("levelname", obj.getLevelname());
				map.put("memberid", obj.getMemberid());
				map.put("cpaynum", obj.getCpaynum());
				if (StringUtils.isStringNull(request
						.getParameter("memberid_id"))) {
					StringBuffer strs = new StringBuffer();
					strs.append(" from Tre_friendscircle o where 1=1 "); // 初始化语句
					strs.append(" and o.circlememberid = '" + obj.getId()
							+ "' ");
					strs.append(" and o.memberid = '"
							+ request.getParameter("memberid_id") + "'");
					List<Tre_friendscircle> listdoc = tre_friendscircleService
							.findByHQL(strs.toString());
					if (null != listdoc && listdoc.size() > 0) {
						map.put("isfriends", listdoc.get(0).getIsfriends());
						map.put("isfriendto", "yes");
					} else {
						map.put("isfriendto", "no");
					}
				} else {
					map.put("isfriendto", "no");
				}
				if (StringUtils.isStringNull(request
						.getParameter("my_memberid_id"))) {
					StringBuffer strs = new StringBuffer();
					strs.append(" from Tre_friendscircle o where 1=1 "); // 初始化语句
					strs.append(" and o.circlememberid = '" + obj.getId()
							+ "' ");
					strs.append(" and o.memberid = '"
							+ request.getParameter("my_memberid_id") + "'");
					List<Tre_friendscircle> listdoc = tre_friendscircleService
							.findByHQL(strs.toString());
					if (null != listdoc && listdoc.size() > 0) {
						map.put("my_isfriendto", "yes");
					} else {
						map.put("my_isfriendto", "no");
					}
				} else {
					map.put("my_isfriendto", "no");
				}

				map.put("my_isfriendto", "no");
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
	 * 根据文件编号查打赏人
	 * @param pageBean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/filmfestivalvippay/listuser")
	public String listuser(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Filmfestivalvip_pay> list = null;
			StringBuffer str = new StringBuffer();
			str.append("where 1=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("memberid"))) {
				str.append(" and memberid = '" + request.getParameter("memberid") + "'");
			}
			if (StringUtils.isStringNull(request.getParameter("vipid"))) {
				str.append(" and vipid = '" + request.getParameter("vipid") + "'");
			}
			String order = " order by addtime desc ";
			QueryResult<Filmfestivalvip_pay> result =new QueryResult<Filmfestivalvip_pay>();
			 result = filmfestivalvip_payService.getPages(pageBean, str.toString(), "Filmfestivalvip_pay", "id", order, Filmfestivalvip_pay.class);
			 list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
				for (Filmfestivalvip_pay obj : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", obj.getId());
					map.put("payamount", obj.getPayamount());
					map.put("addtime", obj.getAddtime());
					if (obj.getMemberid() != null) {
						map.put("memberid", obj.getMemberid().getId());
						map.put("usersname", obj.getMemberid().getUsersname());
						map.put("headimg", obj.getMemberid().getHeadimg());
						map.put("Levelname", obj.getMemberid().getLevelid().getLevelname());
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
						map.put("Levelname", "");
					}
					if (obj.getVipid() != null) {
						map.put("vipid", obj.getVipid().getId());
						map.put("viptitle", obj.getVipid().getTitel());
						map.put("vip_member_id", obj.getVipid().getMember_id().getId());
						map.put("vip_member_name", obj.getVipid().getMember_id().getUsersname());
						map.put("vip_member_headimg", obj.getVipid().getMember_id().getHeadimg());
					} else {
						map.put("vipid", "");
					}
					item.add(map);
				}
			jsonResult.setResult(item);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
		}
		catch (Exception e)
		{
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
}
