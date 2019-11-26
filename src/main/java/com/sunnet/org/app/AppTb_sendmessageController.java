package com.sunnet.org.app;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.sunnet.org.member.model.Tb_sendmessage;
import com.sunnet.org.member.service.ITb_sendmessageService;
import com.sunnet.org.member.vo.Tb_sendmessageUtil;

/***
 * 系统消息
 * 
 * @author caixiang
 */
@Controller
@RequestMapping("/app")
public class AppTb_sendmessageController extends BaseController
{

	@Autowired
	private ITb_sendmessageService tb_sendmessageService;

 



	/**
	 * 获取系统消息列表
	 * author : caixaing
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/systemMessageList")
//	@RequestMapping(value = "/systemMessageList")
	public String systemMessageList(String page,HttpServletRequest request,HttpServletResponse response){
		String memberId=getMemberId(request,response);
//		String memberId="92CF8CB5-4D3D-4DAE-A102-FB17544DCB97";
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(Integer.parseInt(page));
		JsonResult jsonResult = new JsonResult();
		QueryResult result = new QueryResult();
		result = tb_sendmessageService.watchList(pageBean,memberId);
		jsonResult.setCode(Constants.SUCCESS_CODE);
		jsonResult.setMessage(Constants.SUCCESS_DATA);
		if(null != result) {
			jsonResult.setData(result.getResultList());
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
	
	 

}
