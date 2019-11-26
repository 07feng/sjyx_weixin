package com.sunnet.org.app;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.framework.pagenation.QueryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.information.service.ITb_filetypeService;
import com.sunnet.org.information.vo.Tb_filetypeUtil;
import com.sunnet.org.util.UserUtil;

/***
 * 分类管理
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class
AppTb_filetypeController extends BaseController
{

	@Autowired
	private ITb_filetypeService tb_filetypeService;

	/**
	 * 返回类别列表
	 * @param request
	 * @param response
	 * @return
	 * author jinhao
	 */
	@RequestMapping(value = "/getCategoryList")
	public String getCategoryList(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Object[]> result = tb_filetypeService.ListFileType();
			jsonResult.setData(Tb_filetypeUtil.getControllerListJ(result));
			jsonResult.setCode("0");
			jsonResult.setMessage("success");
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
