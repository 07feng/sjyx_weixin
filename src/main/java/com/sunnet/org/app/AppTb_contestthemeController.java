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

import com.sunnet.org.competition.model.Tb_contesttheme;
import com.sunnet.org.competition.service.ITb_contestthemeService;
import com.sunnet.org.competition.vo.Tb_contestthemeUtil;

/***
 * 赛事主題
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_contestthemeController extends BaseController
{

	@Autowired
	private ITb_contestthemeService tb_contestthemeService;

	 

	/**
	 * 返回分页 tb_contesttheme
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contesttheme/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Tb_contesttheme> result = tb_contestthemeService.list(pageBean,request);
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
	@RequestMapping(value = "/contesttheme/listAll")
	@RequiresPermissions("sys_tb_contesttheme")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tb_contesttheme> result = tb_contestthemeService.findAll();
			jsonResult.setResult(Tb_contestthemeUtil.getControllerList(result));
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
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contesttheme/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Tb_contesttheme result = tb_contestthemeService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_contestthemeUtil.getControllerMap(result));
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
	 * 保存 tb_contesttheme
	 * 
	 * @param tb_contesttheme
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contesttheme/save")
	public String save(Tb_contesttheme tb_contesttheme, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tb_contestthemeService.save(tb_contesttheme);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了赛事主題保存");
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
	 * 更新 tb_contesttheme
	 * 
	 * @param tb_contesttheme
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contesttheme/update")
	public String update(Tb_contesttheme tb_contesttheme, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tb_contestthemeService.update(tb_contesttheme);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了赛事主題更新");
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
	@RequestMapping(value = "/contesttheme/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				tb_contestthemeService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				Log(request,"操作了赛事主題删除");
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
