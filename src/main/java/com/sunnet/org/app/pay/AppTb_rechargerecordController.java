package com.sunnet.org.app.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.sunnet.org.app.oss.httpClientHelp;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_memberconsumption;
import com.sunnet.org.member.model.Tb_memberrecharge;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_memberconsumptionService;
import com.sunnet.org.pay.model.Tb_rechargerecord;
import com.sunnet.org.pay.service.ITb_rechargerecordService;
import com.sunnet.org.pay.vo.Tb_rechargerecordUtil;
import com.sunnet.org.util.ExcelExport;

/***
 * 充值记录
 *
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_rechargerecordController extends BaseController
{

	@Autowired
	private ITb_rechargerecordService tb_rechargerecordService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_memberconsumptionService tb_memberconsumptionService;
	/**
	 * 返回分页 tb_rechargerecord
	 *
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/rechargerecord/list",method = RequestMethod.POST)
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Tb_rechargerecord> result = tb_rechargerecordService.list(pageBean,request);
			jsonResult.setResult(result);
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

	@RequestMapping(value = "/tbrechargerecord/tbrechargerecordresult",method = RequestMethod.POST)
	public String tbrechargerecordresult(HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode("200");
		jsonResult.setMessage("执行成功！");
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	@RequestMapping(value = "/tbrechargerecord/save",method = RequestMethod.POST)
	public String save(String rramount,String memberid,String ipaddress, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			BigDecimal bd=new BigDecimal(rramount);
			Tb_member docmember = tb_memberService.findByPrimaryKey(memberid);
			Tb_rechargerecord tb_rechargerecord =new Tb_rechargerecord();
			String http="http://39.108.216.108/reachPay/getorederno";
			Map jso =  httpClientHelp.HttpURLConnectionPost(http, "", "");
			String orderno=net.sf.json.JSONObject.fromObject(jso.get("results")).get("result").toString();
			tb_rechargerecord.setOrno(orderno);
			tb_rechargerecord.setFinishtime(new Date());//getorederno
			tb_rechargerecord.setRramount(bd);
			tb_rechargerecord.setMemberid(docmember);
			tb_rechargerecord.setIpaddress(ipaddress);
			tb_rechargerecord.setWay(2);
			tb_rechargerecord.setRrstatus("TRADE_SUCCESS");
			tb_rechargerecord.setReamount(bd);
			tb_rechargerecord.setRechargetime(new Date());
			tb_rechargerecordService.save(tb_rechargerecord);

			docmember.setWallet(docmember.getWallet()+Double.parseDouble(rramount));
			tb_memberService.update(docmember);
			//记录会员资金明细
			Tb_memberconsumption consumption= new Tb_memberconsumption();
			consumption.setMemberid(docmember);
			consumption.setAmount(Double.parseDouble(rramount));
			consumption.setOperationtype(0);//状态r
			consumption.setOverage(docmember.getWallet());//余额
			consumption.setOperationtime(new Date());
			tb_memberconsumptionService.save(consumption);
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
	@RequestMapping(value = "/rechargerecord/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tb_rechargerecord> result = tb_rechargerecordService.findAll();
			jsonResult.setResult(Tb_rechargerecordUtil.getControllerList(result));
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
	@RequestMapping(value = "/rechargerecord/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Tb_rechargerecord result = tb_rechargerecordService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_rechargerecordUtil.getControllerMap(result));
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
	 * 删除
	 *
	 * @param ids
	 *            数组
	 * @return json
	 */
	@RequestMapping(value = "/rechargerecord/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				tb_rechargerecordService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
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
