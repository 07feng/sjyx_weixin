package com.sunnet.org.app.updatepackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.information.vo.Tb_docUtil;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.member.service.ITre_docfilelabelService;
import com.sunnet.org.member.vo.Tre_docfilelabelUtil;
import com.sunnet.org.system.model.Tb_package;
import com.sunnet.org.system.service.ITb_packageService;

@Controller
@RequestMapping("/app")
public class AppMyUpdatePackage extends BaseController {
	@Autowired
	private ITb_docpayService tb_docpayService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITre_docfilelabelService tre_docfilelabelService;
	@Autowired
	private ITb_packageService tb_packageService;
	/**
	 * 
	 * 
	 * @param memberid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tb_package/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Tb_package> result = tb_packageService.list(pageBean,request);
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
	 
}
