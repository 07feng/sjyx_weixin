package com.sunnet.org.app.visitors;

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

import com.sunnet.org.visitors.model.T_topsearchterm;
import com.sunnet.org.visitors.service.IT_topsearchtermService;
import com.sunnet.org.visitors.vo.T_topsearchtermUtil;

/***
 * 热门搜索词
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppT_topsearchtermController extends BaseController
{

	@Autowired
	private IT_topsearchtermService t_topsearchtermService;
 

	/**
	 * 返回分页 t_topsearchterm
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/t_topsearchterm/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<T_topsearchterm> result = t_topsearchtermService.list(pageBean,request);
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
	@RequestMapping(value = "/t_topsearchterm/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<T_topsearchterm> result = t_topsearchtermService.findAll();
			jsonResult.setResult(T_topsearchtermUtil.getControllerList(result));
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
	@RequestMapping(value = "/t_topsearchterm/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				T_topsearchterm result = t_topsearchtermService.findByPrimaryKey(fdId);
				jsonResult.setResult(T_topsearchtermUtil.getControllerMap(result));
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
	 * 保存 t_topsearchterm
	 * 
	 * @param t_topsearchterm
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/t_topsearchterm/save")
	public String save(T_topsearchterm t_topsearchterm, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			t_topsearchtermService.save(t_topsearchterm);
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
	 * 更新 t_topsearchterm
	 * 
	 * @param t_topsearchterm
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/t_topsearchterm/update")
	public String update(T_topsearchterm t_topsearchterm, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			t_topsearchtermService.update(t_topsearchterm);
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
	 * 删除
	 * 
	 * @param ids
	 *            数组
	 * @return json
	 */
	@RequestMapping(value = "/t_topsearchterm/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				t_topsearchtermService.delete(ids);
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
