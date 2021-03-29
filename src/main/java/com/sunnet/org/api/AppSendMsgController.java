package com.sunnet.org.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.sunnet.org.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.apppush.util.JPushAllUtil;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_messagetemplate;
import com.sunnet.org.member.model.Tb_sendmessage;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_messagetemplateService;
import com.sunnet.org.member.service.ITb_sendmessageService;

/***
 * 发送验证码，短信推送
 * 
 * @author caixiang
 */
@Controller
@RequestMapping("/app")
public class AppSendMsgController extends BaseController {

	@Autowired 
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_sendmessageService tb_sendmessageService;
	@Autowired
	private ITb_messagetemplateService tb_messagetemplateService;

	/**
	 *  获取验证码,短信推送
	 *  aurthod : caixiang
	 * @param phonenumber 手机号（验证的时候用）
	 * @param code 判断获取哪个code
	 * code=1用户注册*
	 * code=2修改密码
	 * code=3登录确认
	 * code=4登录异常
	 * code=5信息变更
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 */
	@RequestMapping(value = "/getVerify")
	public String getVerify(String phonenumber, int code,
							HttpServletRequest request, HttpServletResponse response)
			throws ClientException {
//		System.out.println("123<<<<<<<<<<<");
		JsonResult jsonResult = new JsonResult();
		SendSmsResponse responseMsg =null;
		//发送短信
		responseMsg = SendMsgUtil.sendSms(phonenumber, code,"","","",
				request);
//		System.out.println("getCode="+responseMsg.getCode());
//		System.out.println("getMessage="+responseMsg.getMessage());
//		System.out.println("getRequestId="+responseMsg.getRequestId());
		String lcode = responseMsg.getCode();
		List<Tb_member> list = tb_memberService.getByPhone(phonenumber);
		Map map=new HashMap();
		if(list.size() == 0){
			map.put("isRegister",false);
		}else{
			map.put("isRegister",true);
		}
		if (lcode != null && !(lcode.equals(""))) {
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setData(map);
			jsonResult.setMsg("发送成功！");
		} else {
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("发送失败！");
		}
//		System.out.println("jsonResult="+responseMsg.getMessage()+","+responseMsg.getCode());
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

 /**
  * 获取验证码,短信推送
  * @param phonenumber 手机号（验证的时候用）
  * @param lcode 判断获取哪个code
  * @param membername 推送人id
  * @param notesid 推送内容id
  * @param request
  * @param response
  * @return
  * @throws ClientException
  */
	@RequestMapping(value = "/sendMsg")
	public String selectKey(String phonenumber, int lcode,String membername,String contesttypeid,Integer notesid,String templatetype,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException {
		
		JsonResult jsonResult = new JsonResult();
		SendSmsResponse responseMsg =null;
		if(lcode==1) {
			List list = tb_memberService.findByHQL("from Tb_member where MobileNumber =?", phonenumber);
			if (null != list && list.size() != 0) {
				jsonResult.setCode("100");
				jsonResult.setMessage("该手机号已注册！");
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
		}
		if(membername != null ){
			String []membernames=membername.replace("ids=", "").split("&");
		     if(membernames.length > 0){
		    	 for (String id : membernames) {
						id=id.trim();
						Tb_member result = tb_memberService.findByPrimaryKey(id);
						Tb_messagetemplate p=tb_messagetemplateService.findByPrimaryKey(notesid);
						if(result!=null){
							String app ="App推送";
							Map map = new HashMap();
							  //app推送
							  if(app==templatetype || app.equals(templatetype)){
								  if(null!=result.getRandroidios() && result.getRandroidios()==0 ){
									  //ios
									  JPushAllUtil.jSend_notification(result.getRegistrationid(),p.getTemplateinfo(), map, 0);
									  Tb_sendmessage send=new Tb_sendmessage();
										send.setMessagetype("App推送");
										send.setMemberid(result);
										send.setMessageinfo(p.getTemplateinfo());
										send.setEdittime(new Date());
										tb_sendmessageService.save(send);
										jsonResult.setResult(responseMsg);
										jsonResult.setMessage("发送成功！");
								  }else if(null!=result.getRandroidios() && result.getRandroidios()==1){
										//安卓
									  JPushAllUtil.jSend_notification(result.getRegistrationid(),p.getTemplateinfo(), map, 1);
									  Tb_sendmessage send=new Tb_sendmessage();
										send.setMessagetype("App推送");
										send.setMemberid(result);
										send.setMessageinfo(p.getTemplateinfo());
										send.setEdittime(new Date());
										tb_sendmessageService.save(send);
										jsonResult.setResult(responseMsg);
										jsonResult.setMessage("发送成功！");
								  }
									
							  }else{
								  //发送短信
									 responseMsg = SendMsgUtil.sendSms(result.getMobilenumber(), lcode,p.getTemplateparam(),result.getUsersname(),p.getTemplateinfo(),
												request);
									 String code = responseMsg.getCode();
										if (code != null && !(code.equals(""))) {
										
											Tb_sendmessage ts=new Tb_sendmessage();
											ts.setMemberid(result);
											ts.setMessagetype("短信推送");
											ts.setEdittime(new Date());
											ts.setMessageinfo(p.getTemplateinfo());
											tb_sendmessageService.save(ts);
											jsonResult.setResult(responseMsg);
											jsonResult.setMessage("发送成功！");
										} else {
											jsonResult.setCode("100");
											jsonResult.setMessage("发送失败！");
										}
							  }
						}else{
							jsonResult.setCode("101");
							jsonResult.setMessage("用户不存在！");
						}
					}
		     }
			
		}else{
			 responseMsg = SendMsgUtil.sendSms(phonenumber, lcode,"","","", 
					request);
			 String code = responseMsg.getCode();
				if (code != null && !(code.equals(""))) {
					jsonResult.setResult(responseMsg);
			// jsonResult.setCode(checkCode);
			jsonResult.setMessage("发送成功！");
		} else {
			jsonResult.setCode("100");
			jsonResult.setMessage("发送失败！");
		}
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	  * 获取验证码,短信推送
	  * @param phonenumber 手机号（验证的时候用）
	  * @param lcode 判断获取哪个code
	  * @param notesid 推送内容id
	  * @param request
	  * @param response
	  * @return
	  * @throws ClientException
	  */
		@RequestMapping(value = "/sendMsgall")
		public String selectall(String phonenumber, int lcode,String contesttypeid,Integer notesid,String templatetype,
				HttpServletRequest request, HttpServletResponse response)
				throws ClientException {
			JsonResult jsonResult = new JsonResult();
			SendSmsResponse responseMsg =null;
		    List<Tb_member> memberlist=tb_memberService.findAll();
			    	 for (Tb_member result : memberlist) {
							Tb_messagetemplate p=tb_messagetemplateService.findByPrimaryKey(notesid);
								String app ="App推送";
								Map map = new HashMap();
								  //app推送
								  if(app==templatetype || app.equals(templatetype)){
									  if(null!=result.getRandroidios() && result.getRandroidios()==0 ){
										  //ios
										  JPushAllUtil.jSend_notification(result.getRegistrationid(),p.getTemplateinfo(), map, 0);
										  Tb_sendmessage send=new Tb_sendmessage();
											send.setMessagetype("App推送");
											send.setMemberid(result);
											send.setMessageinfo(p.getTemplateinfo());
											send.setEdittime(new Date());
											tb_sendmessageService.save(send);
											jsonResult.setResult(responseMsg);
											jsonResult.setMessage("发送成功！");
									  }else if(null!=result.getRandroidios() && result.getRandroidios()==1){
											//安卓
										  JPushAllUtil.jSend_notification(result.getRegistrationid(),p.getTemplateinfo(), map, 1); 
										  Tb_sendmessage send=new Tb_sendmessage();
											send.setMessagetype("App推送");
											send.setMemberid(result);
											send.setMessageinfo(p.getTemplateinfo());
											send.setEdittime(new Date());
											tb_sendmessageService.save(send);
											jsonResult.setData(responseMsg);
											jsonResult.setMsg("发送成功！");
									  }
										
								  }else{
									  //发送短信
										 responseMsg = SendMsgUtil.sendSms(result.getMobilenumber(), lcode,p.getTemplateparam(),result.getUsersname(),p.getTemplateinfo(),
													request);
										 String code = responseMsg.getCode();
											if (code != null && !(code.equals(""))) {
											
												Tb_sendmessage ts=new Tb_sendmessage();
												ts.setMemberid(result);
												ts.setMessagetype("短信推送");
												ts.setEdittime(new Date());
												ts.setMessageinfo(p.getTemplateinfo());
												tb_sendmessageService.save(ts);
												jsonResult.setData(responseMsg);
												jsonResult.setMsg("发送成功！");
											} else {
												jsonResult.setCode("100");
												jsonResult.setMsg("发送失败！");
											}
								  }
						}
			return ajaxJson(JSONObject.toJSONString(jsonResult), response);
		}





	/**
	 * 
	 * @param memberid
	 * @param notesid
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 */
	@RequestMapping(value = "/sendApp")
	public String sendApp(String memberid,Integer notesid,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException {
		JsonResult jsonResult = new JsonResult();
		
		
		
		
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
}
