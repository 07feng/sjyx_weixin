package com.sunnet.org.app.pay;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.sunnet.framework.util.EncryptionMd5;
import com.sunnet.framework.util.HttpClientUtil;
import com.sunnet.framework.util.RandomStringGenerator;
import com.sunnet.org.app.oss.httpClientHelp;
import com.sunnet.org.app.pay.common.Configure;
import com.sunnet.org.app.pay.common.HttpRequest;
import com.sunnet.org.app.pay.common.Signature;
import com.sunnet.org.app.pay.model.OrderInfo;
import com.sunnet.org.app.pay.model.OrderReturnInfo;
import com.sunnet.org.app.pay.model.SignInfo;
import com.sunnet.org.pay.model.Tb_rechargerecord;
import com.sunnet.org.pay.service.ITb_rechargerecordService;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.SignUtil;
import com.thoughtworks.xstream.XStream;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.doc.service.ITb_docpayService;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.model.Filmfestivalvipopentime;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipopentimeService;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.information.vo.Tb_docUtil;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_memberconsumption;
import com.sunnet.org.member.model.Tb_memberextract;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_memberconsumptionService;
import com.sunnet.org.member.service.ITb_memberextractService;
import com.sunnet.org.member.service.ITre_docfilelabelService;
import com.sunnet.org.member.vo.Tre_docfilelabelUtil;

@Controller
@RequestMapping("/app")
public class AppPay extends BaseController {
	@Autowired
	private ITb_docpayService tb_docpayService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITre_docfilelabelService tre_docfilelabelService;
	@Autowired
	private ITb_memberconsumptionService tb_memberconsumptionService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private IFilmfestivalvipopentimeService filmfestivalvipopentimeService;
	@Autowired
	private IFilmfestivalvipService filmfestivalvipService;
	@Autowired
	private ITb_memberextractService tb_memberextractService;
	@Autowired
	private ITb_rechargerecordService tb_rechargerecordService;
	/**
	 * 我的钱包
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/myWallet")
	public String myWallet(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String memberId  = getMemberId(request, response);
			Tb_member tb_member=tb_memberService.getByKey(memberId);
			DecimalFormat df = new DecimalFormat("0.0");
			Map<String,Object> result =new HashMap();
			String money = df.format(tb_member.getWallet()*10);
			result.put("k_coin",money.substring(0,money.indexOf(".")));
			if (tb_member.getIncome() != null)
				result.put("income",df.format(tb_member.getIncome()));
			else
				result.put("income","0");
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


	/**
	 * 会员资金明细
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/capitalDetail")
//	@RequestMapping(value = "/capitalDetail")
	public String list(String page, String operationType,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			PageBean pageBean = new PageBean();
			pageBean.setCurrentPage(Integer.parseInt(page));
//			System.out.println("pageBean.getStartRow()="+pageBean.getStartRow());
			String memberId = getMemberId(request, response);
//			String memberId = "9C696C98-C282-4606-8803-6EAD540A9453";
			if ("0".equals(operationType)) {
				QueryResult<Tb_rechargerecord> result = tb_rechargerecordService.memberList(pageBean, memberId);
				jsonResult.setData(result.getResultList());
			}
			if ("1".equals(operationType) || "2".equals(operationType)) {
				List result = tb_docpayService.payList(pageBean,memberId,Integer.parseInt(operationType));
				jsonResult.setData(result);
			}
			if ("3".equals(operationType)) {
				QueryResult<Tb_memberextract> result = tb_memberextractService.memberList(pageBean,memberId);
				jsonResult.setData(result.getResultList());
			}
			System.out.println("pageBean.getTotalRecord()="+pageBean.getTotalRecord());
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMessage(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 资金详情
	 * @param operationType
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/moneyDetail")
	public String moneyDetail(String operationType, String id,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
//			String memberId = getMemberId(request, response);
			DecimalFormat df = new DecimalFormat("0.00");
			Map map = new HashMap();
			if ("0".equals(operationType)) {
				List<Object[]> result = tb_rechargerecordService.findBySql("select aliNo,reamount,rrstatus,finishtime from Tb_rechargerecord where rrid = ?",id);
				map.put("orderId",result.get(0)[0].toString());
				map.put("amount",df.format(result.get(0)[1]));
				map.put("status",result.get(0)[2].toString());
				map.put("finishTime",DateUtil.DateToString((Date)result.get(0)[3],"YYYY-MM-dd hh:mm:ss"));
				map.put("typeName","充值");
			}
//			if ("1".equals(operationType) || "2".equals(operationType)) {
//				Tb_docpay result = tb_docpayService.findByPrimaryKey(id);
//				map.put("",result);
//				map.put("",result);
//				map.put("",result);
//				map.put("typeName","充值");
//			}
			if ("3".equals(operationType)) {
				List<Object[]> result = tb_memberextractService.findBySql("select Account,ExtractAmount,ExtractStatus,ReturnTime,ApplyTime from Tb_memberextract where id = ?",id);

				map.put("amount",df.format(result.get(0)[1]));
				map.put("account",result.get(0)[0].toString());
				map.put("status",result.get(0)[2].toString());
				if (null != result.get(0)[3])
					map.put("finishTime",DateUtil.DateToString((Date)result.get(0)[3],"YYYY-MM-dd hh:mm:ss"));
				else
					map.put("finishTime",DateUtil.DateToString((Date)result.get(0)[4],"YYYY-MM-dd hh:mm:ss"));
				map.put("typeName","提现");
			}
			jsonResult.setData(map);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMessage(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	//提现
	@RequestMapping(value = "/memberextract/save")
	public String save(Tb_memberextract tb_memberextract, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			tb_memberextractService.save(tb_memberextract);
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
	 * 小程序预支付充值
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/session/wxPay")
	public String wxPay(String rAmount,HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String openid = getOpenId(request, response);
			String memberId  = getMemberId(request, response);
			String ipAddress = request.getRemoteAddr();	//获取IP


			//添加充值记录
			String http="http://localhost/reachPay/getorederno";
			Map jso =  httpClientHelp.HttpURLConnectionPost(http, "", "");
			System.out.println("-----orderno="+jso);
			String orderno=net.sf.json.JSONObject.fromObject(jso.get("results")).get("result").toString();
			System.out.println("-----orderno="+orderno);

			OrderInfo order = new OrderInfo();
			order.setAppid(Configure.getAppID());
			order.setMch_id(Configure.getMch_id());
			order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
			order.setBody("wxPay");
			order.setOut_trade_no(orderno);
			order.setTotal_fee(Integer.parseInt(Signature.changeY2F(rAmount)));
			order.setSpbill_create_ip("39.108.216.108");
			order.setNotify_url("http://39.108.216.108/reachPay/wxquery");
			order.setTrade_type("JSAPI");
			order.setOpenid(openid);
			order.setSign_type("MD5");
			//生成签名
			String sign = Signature.getSign(order);
			order.setSign(sign);


			String result = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order);
			log.info("---------下单返回:"+result);
			XStream xStream = new XStream();
			xStream.alias("xml", OrderReturnInfo.class);

			OrderReturnInfo returnInfo = (OrderReturnInfo)xStream.fromXML(result);
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Configure.getAppID());
			long time = System.currentTimeMillis()/1000;
			signInfo.setTimeStamp(String.valueOf(time));
			signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
			signInfo.setRepay_id("prepay_id="+returnInfo.getPrepay_id());
			signInfo.setSignType("MD5");
			//生成签名
			String signAgain = Signature.getSign(signInfo);
			Map map = new HashMap();
			map.put("timeStamp", signInfo.getTimeStamp());
			map.put("nonceStr", signInfo.getNonceStr());
			map.put("prePayId", signInfo.getRepay_id());
			map.put("signType", signInfo.getSignType());
			map.put("paySign", signAgain);

			Tb_rechargerecord tb_rechargerecord =new Tb_rechargerecord();
			tb_rechargerecord.setOrno(orderno);
			tb_rechargerecord.setAlino(returnInfo.getPrepay_id());
			tb_rechargerecord.setFinishtime(new Date());//getorederno
			tb_rechargerecord.setRramount(new BigDecimal(rAmount));
			Tb_member docmember = new Tb_member();
			docmember.setId(memberId);
			tb_rechargerecord.setMemberid(docmember);
			tb_rechargerecord.setIpaddress(ipAddress);
			tb_rechargerecord.setWay(1);
			tb_rechargerecord.setRrstatus("WAIT_CONFIRM");
			tb_rechargerecord.setReamount(new BigDecimal(0));
			tb_rechargerecord.setRechargetime(new Date());
			tb_rechargerecordService.save(tb_rechargerecord);

			map.put("orNo",tb_rechargerecord.getOrno());
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
	 *
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/session/successPay")
	public String successPay(String orNo,HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String memberId = getMemberId(request, response);
			Map<String, String> reqMap = new HashMap<>();
			reqMap.put("requestType", "JSAPI");
			reqMap.put("orderno", orNo); //商户系统内部的订单号,

			String json= JSON.toJSONString(reqMap);
			String encode = Base64.getEncoder().encodeToString(json.getBytes("UTF-8"));
			Map retStr = httpClientHelp.HttpURLConnectionPost("http://39.108.216.108/reachPay/wxquery?basedatas="+encode,"","");
			String result=net.sf.json.JSONObject.fromObject(retStr.get("results")).get("result").toString();

			Map maps = (Map)JSON.parse(result);
			if ("SUCCESS".equals(maps.get("result_code"))){
				if (maps.containsKey("trade_state") && "SUCCESS".equals(maps.get("trade_state"))){
					System.out.println("transaction_id="+maps.get("transaction_id").toString());
					String total_fee = maps.get("total_fee").toString();
					System.out.println("------total_fee=="+total_fee);
					Double amount = new Double(Signature.changeF2Y(total_fee));
					System.out.println("------amount=="+amount);
					List<Object[]> record = tb_rechargerecordService.findBySql("select orNo,rrStatus from Tb_rechargerecord where orNo =?",orNo);
					if (!"TRADE_SUCCESS".equals(record.get(0)[1].toString())) {
						tb_rechargerecordService.updateRecord(maps.get("transaction_id").toString(), amount, orNo);

						/*用户钱包金额*/
						List<Object[]> memWallet = tb_memberService.findBySql("select id,wallet from TB_member where id =?",memberId);
						System.out.println("--memWallet.get(0)[1].toString()="+memWallet.get(0)[1].toString());
						Double money = new Double(memWallet.get(0)[1].toString())+amount*10;
						System.out.println("-------money="+money);
						tb_memberService.updateMemberInfo("update TB_member set wallet=? where id =?",money,memberId);
						System.out.println("-----添加钱包成功！");
						//记录会员充值明细
						Tb_member member = new Tb_member();
						member.setId(memberId);
						Tb_memberconsumption consumption = new Tb_memberconsumption();
						consumption.setMemberid(member);//tb_docpay.getMemberid()
						consumption.setAmount(amount);
						consumption.setOperationtype(0);//操作类别为0
						consumption.setOverage(money);//余额
						consumption.setOperationtime(new Date());
						tb_memberconsumptionService.save(consumption);
					}else
						System.out.println("支付已经记录了！");
				}else
					System.out.println("支付失败");
			}else
				System.out.println("fail-----");

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
	 * 支付
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apppay/pay")
	public String selectContestdoc(Double paymoney,Integer operationtype,Tb_memberextract tb_memberextract,Integer vipid,
								   PageBean pagebean, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();

		try {
			if (tb_memberextract.getMemberid().getId() != null && !(tb_memberextract.getMemberid().getId().equals(""))) {
				Tb_member member=tb_memberService.findByPrimaryKey(tb_memberextract.getMemberid().getId());
				if(null!=paymoney && paymoney>=0){

					if(null!=member.getWallet() && member.getWallet()>=paymoney){

						if(null!=operationtype && operationtype==0){ //充值
							member.setWallet(member.getWallet()+paymoney);
						}else if(null!=operationtype && operationtype==1){//支出

							//个人影展付费
							if(null!=vipid && vipid>0){
            			   /* List<Filmfestivalvip> findvip = filmfestivalvipService
            						.findlistBySql(
            								Filmfestivalvip.class,
            								"select * from Filmfestivalvip where GETDATE()-open_time <=  time_length and member_id='"
            										+ member.getId() + "'");
            			   if (findvip != null && findvip.size() > 0) {
            					jsonResult.setCode("300");
            					jsonResult.setMessage("您参加的个人影展还未过期！");
            					return ajaxJson(JSONObject.toJSONString(jsonResult), response);
            				} else { */
								operationtype = 101;//影展付款
								Filmfestivalvip vip = new Filmfestivalvip();
								vip.setId(vipid);
								List<Filmfestivalvipopentime> fvip = filmfestivalvipopentimeService
										.findAll();
								if (fvip != null && fvip.size() > 0) {
									vip.setTime_length(fvip.get(0).getTime_length());
								} else {
									vip.setTime_length(30);
								}
								vip.setOpen_time(new Date());
								filmfestivalvipService.update(vip);
								if( null!=Double.valueOf(fvip.get(0).getPay_money()) && Double.valueOf(fvip.get(0).getPay_money())<=member.getWallet()){
									member.setWallet(member.getWallet()-Double.valueOf(fvip.get(0).getPay_money()));
								}else{
									jsonResult.setCode("300");
									jsonResult.setMessage("亲！您的余额已不足！请充值！");
									return ajaxJson(JSONObject.toJSONString(jsonResult), response);
								}


							}else{
								operationtype = 102;//相册贺卡付款
								member.setWallet(member.getWallet()-paymoney);
							}


						}else if(null!=operationtype && operationtype==2){//收入
							member.setWallet(member.getWallet()+paymoney);

						}else if(null!=operationtype && operationtype==3){//提现
							tb_memberextract.setApplytime(new Date());
							tb_memberextract.setExtractstatus("0");
							tb_memberextractService.save(tb_memberextract);
							member.setWallet(member.getWallet()-paymoney);

						}
						tb_memberService.update(member);
						// 记录会员资金明细
						Tb_memberconsumption consumption = new Tb_memberconsumption();
						consumption.setMemberid(member);
						consumption.setAmount(paymoney);//操作金额
						consumption.setOperationtype(operationtype);// 状态
						consumption.setOverage(member.getWallet());// 余额
						consumption.setOperationtime(new Date());
						consumption.setWithdrawalstate(0);
						tb_memberconsumptionService.save(consumption);
						jsonResult.setCode("200");
						jsonResult.setMessage("执行成功！");
					}else{
						jsonResult.setCode("300");
						jsonResult.setMessage("亲！您的余额已不足！请充值！");
					}
				}else{
					jsonResult.setCode("301");
					jsonResult.setMessage("亲！请输入金额！");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}

		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

}
