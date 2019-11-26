package com.sunnet.org.app.appbehavior;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_pay;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_payService;
import com.sunnet.org.util.Constants;
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
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.service.ITb_docpayService;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.doc.vo.Tb_docpayUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_memberconsumption;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_memberconsumptionService;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.model.vie_docpay;
import com.sunnet.org.view.model.vip_doc_pay;
import com.sunnet.org.view.service.Ivie_docpayService;
import com.sunnet.org.view.service.Ivip_doc_payService;

/***
 * 打赏
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_docpayController extends BaseController
{
	@Autowired
	private ITb_memberconsumptionService tb_memberconsumptionService;
	@Autowired
	private ITb_docpayService tb_docpayService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_levelService tb_levelService; 
	@Autowired
	private ITre_friendscircleService tre_friendscircleService;
	@Autowired
	private Ivip_doc_payService vip_doc_payService;
	@Autowired
	private Ivie_docpayService vie_docpayService;
	@Autowired
	private IFilmfestivalvipService iFilmfestivalvipService;
	@Autowired
	private IFilmfestivalvip_payService iFilmfestivalvip_payService;
	/**
	 * 保存 tb_docpay
	 * 
	 * @param docId
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/session/reward")
	public String save(String docId,String kb,String code, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String memberId = getMemberId(request, response);
			//被打赏人
			Tb_member docmember = null;
			//打赏人
			Tb_member member = tb_memberService.getByKey(memberId);
			Double amount = new Double(kb)/10;
			if(member.getWallet() >= amount){
				if ("0".equals(code)) {	//打赏作品
					Tb_doc doc = tb_docService.getByKey(docId);
					//被打赏人
					docmember = doc.getMemberid();

					if (member.getId().equals(docmember.getId())){
						jsonResult.setCode("-1");
						jsonResult.setMsg("您不能打赏自己的作品！");
						return ajaxJson(JSONObject.toJSONString(jsonResult), response);
					}
					//存记录
					Tb_docpay tb_docpay = new Tb_docpay();
					tb_docpay.setAddtime(new Date());
					tb_docpay.setDocid(doc);
					tb_docpay.setMemberid(member);
					System.out.println("--------acount==="+kb);
					tb_docpay.setPayamount(amount);
					tb_docpayService.save(tb_docpay);

					//作品被打赏次数
					doc.setFilepaycount(doc.getFilepaycount()+1);
					tb_docService.update(doc);
				}

				if ("1".equals(code)) {	//打赏影集
					Filmfestivalvip ffv = iFilmfestivalvipService.getByKey(docId);
					//被打赏人
					docmember = ffv.getMember_id();

					if (member.getId().equals(docmember.getId())){
						jsonResult.setCode("-1");
						jsonResult.setMsg("您不能打赏自己的作品！");
						return ajaxJson(JSONObject.toJSONString(jsonResult), response);
					}
					//存记录
					Filmfestivalvip_pay ffvPay = new Filmfestivalvip_pay();
					ffvPay.setAddtime(new Date());
					ffvPay.setVipid(ffv);
					ffvPay.setMemberid(member);
					ffvPay.setPayamount(amount);
					iFilmfestivalvip_payService.save(ffvPay);

					//作品被打赏次数
					ffvPay.setPayamount(ffvPay.getPayamount()+1);
					iFilmfestivalvipService.update(ffv);
				}
				//查询 会员等级积分来源表点赞数量
				List<Tb_levelintegralsource> discuss = null;
//				tb_levelintegralsourceService.listlevel(member.getLevelid().getId());
				List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(docmember.getLevelid().getId());

				if(discuss!=null && discuss.size()>0){
					member.setLevelintegral(discuss.get(0).getPay()+member.getLevelintegral());
				}
				if(discuss2!=null && discuss2.size()>0){
					docmember.setLevelintegral(discuss2.get(0).getPay()+docmember.getLevelintegral());
				}
				Double money = new Double(kb);
				member.setWallet(member.getWallet()-amount);
				System.out.println("-----money/10="+money/10);
				tb_memberService.update(member);

				if (null != docmember.getIncome())
					docmember.setIncome(docmember.getIncome()+amount);
				else
					docmember.setIncome(amount);
				tb_memberService.update(docmember);


				//记录会员资金明细-打赏人
				Tb_memberconsumption consumption= new Tb_memberconsumption();
				consumption.setMemberid(member);//tb_docpay.getMemberid()
				consumption.setAmount(amount);
				consumption.setOperationtype(1);//打赏图片视频
				consumption.setOverage(member.getWallet());//余额
				consumption.setOperationtime(new Date());
				tb_memberconsumptionService.save(consumption);
				//被打赏人
				Tb_memberconsumption me= new Tb_memberconsumption();
				me.setMemberid(docmember);
				me.setAmount(amount);
				me.setOperationtype(2);//状态
				me.setOverage(docmember.getWallet());//余额
				me.setOperationtime(new Date());
				tb_memberconsumptionService.save(me);

				Map map = new HashMap();
				map.put("myWallet",member.getWallet());
				jsonResult.setData(map);
				jsonResult.setCode(Constants.SUCCESS_CODE);
				jsonResult.setMsg(Constants.SUCCESS_DATA);
			}else{
				jsonResult.setCode("-1");
				jsonResult.setMsg("您的余额不足！请充值！");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 钱包余额
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/session/memberWallet")
	public String memberWallet(HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String memberId = getMemberId(request, response);
			Tb_member mem = tb_memberService.getByKey(memberId);
			DecimalFormat df = new DecimalFormat("0.0");
			Map map = new HashMap();
			String money = df.format(mem.getWallet()*10);
			map.put("myWallet",money.substring(0,money.indexOf(".")));
			jsonResult.setData(map);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 返回分页 tb_docpay
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/tb_docpay/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			 List<vip_doc_pay> list = null;
			StringBuffer str = new StringBuffer();
			str.append("where 1=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("memberid"))) {
			str.append(" and paymemberid = '" + request.getParameter("memberid") + "'");
		    }
			if (StringUtils.isStringNull(request.getParameter("docid"))) {
				str.append(" and docid = '" + request.getParameter("docid") + "'");
			}
			if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
				str.append(" and docmemberid = '" + request.getParameter("memberid_id") + "'");
			}
			String order = " order by addtime desc ";
			QueryResult<vip_doc_pay> result =new QueryResult<vip_doc_pay>();
			 result = vip_doc_payService.getPage(pageBean, str.toString(), "vip_doc_pay", "id", order, vip_doc_pay.class);
			 list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
				for (vip_doc_pay obj : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", obj.getId());
					map.put("docid", obj.getDocid());
					map.put("paymemberid", obj.getPaymemberid());
					map.put("payusersname", obj.getPayusersname());
					map.put("docmemberid", obj.getDocmemberid());
					map.put("docmembername", obj.getDocmembername());
					map.put("payamount", obj.getPayamount());
					map.put("addtime", obj.getAddtime());
					map.put("infotype", obj.getInfotype());
					map.put("doctitle", obj.getDoctitle());
					map.put("filetype", obj.getFiletype());
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
	/**
	 * 根据文件编号查打赏人
	 * @param pageBean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tb_docpay/listuser")
	public String listuser(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tb_docpay> list = null;
			StringBuffer str = new StringBuffer();
			str.append("where 1=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("memberid"))) {
				str.append(" and memberid = '" + request.getParameter("memberid") + "'");
			}
			if (StringUtils.isStringNull(request.getParameter("docid"))) {
				str.append(" and docid = '" + request.getParameter("docid") + "'");
			}
			String order = " order by addtime desc ";
			QueryResult<Tb_docpay> result =new QueryResult<Tb_docpay>();
			 result = tb_docpayService.getPages(pageBean, str.toString(), "Tb_docpay", "id", order, Tb_docpay.class);
			 list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
				for (Tb_docpay obj : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", obj.getId());
					map.put("payamount", obj.getPayamount());
					map.put("addtime", obj.getAddtime());
					if (obj.getMemberid() != null) {
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
						map.put("memberid", obj.getMemberid().getId());
						map.put("usersname", obj.getMemberid().getUsersname());
						map.put("headimg", obj.getMemberid().getHeadimg());
						map.put("Levelname", obj.getMemberid().getLevelid().getLevelname());
					} else {
						map.put("memberid", "");
						map.put("usersname", "");
						map.put("headimg", "");
						map.put("Levelname", "");
					}
					if (obj.getDocid() != null) {
						map.put("docid", obj.getDocid().getId());
						map.put("doctitle", obj.getDocid().getDoctitle());
						map.put("filepath", obj.getDocid().getFilepath());
						map.put("doc_member_id", obj.getDocid().getMemberid().getId());
						map.put("doc_member_name", obj.getDocid().getMemberid().getUsersname());
						map.put("doc_member_headimg", obj.getDocid().getMemberid().getHeadimg());
					} else {
						map.put("docid", "");
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
	/**
	 * （我的赏客）
	 * @param pageBean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/tb_docpay/listmember")
	public String listmember(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<vie_docpay> list = null;
			StringBuffer str = new StringBuffer();
			str.append("  where 1=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
				str.append(" and memberid = '" + request.getParameter("memberid_id") + "'");
			}
			QueryResult<vie_docpay> result = new QueryResult<vie_docpay>();
			String order = " order by cpaynum desc ";
			 result = vie_docpayService.getPage(pageBean, str.toString(), "vie_docpay", "id", order, vie_docpay.class);
			 list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
				for (vie_docpay obj : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", obj.getId());
					map.put("usersname", obj.getUsersname());
					map.put("headimg", obj.getHeadimg());
					map.put("levelid", obj.getLevelid());
					map.put("levelname", obj.getLevelname());
					map.put("memberid", obj.getMemberid());
					map.put("cpaynum", obj.getCpaynum());
					//我有没有关注我的赏客
					 if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
							StringBuffer strs = new StringBuffer();
							strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
							strs.append(" and o.circlememberid = '" + obj.getId() + "' ");
							strs.append(" and o.memberid = '" + request.getParameter("memberid_id") + "'");
							List<Tre_friendscircle> listdoc=tre_friendscircleService.findByHQL(strs.toString());
							if(null!=listdoc && listdoc.size()>0){
								//我关注了我的赏客
								map.put("isfriends", listdoc.get(0).getIsfriends());
								map.put("isfriendto", "yes");
							}else{
								//我没有关注我的赏客
								map.put("isfriendto", "no");
						} 
					}else{
						map.put("isfriendto", "no");
					}
					 if (StringUtils.isStringNull(request.getParameter("my_memberid_id"))) {
							StringBuffer strs = new StringBuffer();
							strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
							strs.append(" and o.circlememberid = '" + obj.getId() + "' ");
							strs.append(" and o.memberid = '" + request.getParameter("my_memberid_id") + "'");
							List<Tre_friendscircle> listdoc=tre_friendscircleService.findByHQL(strs.toString());
							if(null!=listdoc && listdoc.size()>0){
								//我关注了别人的赏客   my_isfriends==1  与别人的商客互相关注了
								map.put("my_isfriends", listdoc.get(0).getIsfriends());
								map.put("my_isfriendto", "yes");
							}else{
								//我没有关注别人的赏客
								map.put("my_isfriendto", "no");
						} 
					}else{
						map.put("my_isfriendto", "no");
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
	
	
	/**
	 * 返回List列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tb_docpay/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tb_docpay> result = tb_docpayService.findAll();
			jsonResult.setResult(Tb_docpayUtil.getControllerList(result));
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

	
	/**
	 * 返回单个对象
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tb_docpay/selectKey")
	public String selectKey(Integer fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Tb_docpay result = tb_docpayService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_docpayUtil.getControllerMap(result));
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
			}else{
				jsonResult.setCode("300");
				jsonResult.setMessage("获取出错!请检查fdId参数是否传入!");
			}
		}
		catch (Exception e)
		{
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
	



	/**
	 * 更新 tb_docpay
	 * 
	 * @param tb_docpay
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/tb_docpay/update")
	public String update(Tb_docpay tb_docpay, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tb_docpayService.update(tb_docpay);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了打赏更新");
		}
		catch (Exception e)
		{
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:"+e);
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
	@RequestMapping(value = "/tb_docpay/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				tb_docpayService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				Log(request,"操作了打赏删除");
			}else{
				jsonResult.setMessage("请选择删除选项!");
				jsonResult.setCode("300");
			}
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
