package com.sunnet.org.app;

import java.util.List;
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

import com.sunnet.org.competition.model.Tre_contestaward;
import com.sunnet.org.competition.service.ITre_contestawardService;
import com.sunnet.org.competition.vo.Tre_contestawardUtil;

/***
 * 赛事奖项
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTre_contestawardController extends BaseController
{

	@Autowired
	private ITre_contestawardService tre_contestawardService;
 

	/**
	 * 返回分页 tre_contestaward
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contestaward/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Tre_contestaward> result = tre_contestawardService.list(pageBean,request);
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
	 * 返回List列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contestaward/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tre_contestaward> result = tre_contestawardService.findAll();
			jsonResult.setResult(Tre_contestawardUtil.getControllerList(result));
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
	 * @param fdId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contestaward/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Tre_contestaward result = tre_contestawardService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tre_contestawardUtil.getControllerMap(result));
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
	 * 保存 tre_contestaward
	 * 
	 * @param tre_contestaward
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contestaward/save")
	public String save(Tre_contestaward tre_contestaward, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tre_contestawardService.save(tre_contestaward);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了赛事奖项保存");
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
	 * 更新 tre_contestaward
	 * 
	 * @param tre_contestaward
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contestaward/update")
	public String update(Tre_contestaward tre_contestaward, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tre_contestawardService.update(tre_contestaward);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了赛事奖项更新");
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
	@RequestMapping(value = "/contestaward/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				tre_contestawardService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				Log(request,"操作了赛事奖项删除");
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
