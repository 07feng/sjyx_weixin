package com.sunnet.org.app.appbehavior;

import java.util.Date;
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

import com.sunnet.org.filmfestival.model.Filmfestivalvip_tipoff;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_tipoffService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_tipoffUtil;
import com.sunnet.org.util.UserUtil;

/***
 * 影展举报
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppFilmfestivalvip_tipoffController extends BaseController
{

	@Autowired
	private IFilmfestivalvip_tipoffService filmfestivalvip_tipoffService;

	 

	/**
	 * 返回分页 filmfestivalvip_tipoff
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalviptipoff/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Filmfestivalvip_tipoff> result = filmfestivalvip_tipoffService.list(pageBean,request);
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
	@RequestMapping(value = "/filmfestivalviptipoff/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Filmfestivalvip_tipoff> result = filmfestivalvip_tipoffService.findAll();
			jsonResult.setResult(Filmfestivalvip_tipoffUtil.getControllerList(result));
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
	@RequestMapping(value = "/filmfestivalviptipoff/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Filmfestivalvip_tipoff result = filmfestivalvip_tipoffService.findByPrimaryKey(fdId);
				jsonResult.setResult(Filmfestivalvip_tipoffUtil.getControllerMap(result));
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
	 * 保存 filmfestivalvip_tipoff
	 * 
	 * @param filmfestivalvip_tipoff
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalviptipoff/save")
	public String save(Filmfestivalvip_tipoff filmfestivalvip_tipoff, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			filmfestivalvip_tipoff.setCausetime(new Date());
			filmfestivalvip_tipoffService.save(filmfestivalvip_tipoff);
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
	 * 更新 filmfestivalvip_tipoff
	 * 
	 * @param filmfestivalvip_tipoff
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalviptipoff/update")
	public String update(Filmfestivalvip_tipoff filmfestivalvip_tipoff, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			filmfestivalvip_tipoff.setCausetime(new Date());
			filmfestivalvip_tipoff.setUser_id(UserUtil.getSession(request));
			filmfestivalvip_tipoffService.update(filmfestivalvip_tipoff);
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
	@RequestMapping(value = "/filmfestivalviptipoff/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				filmfestivalvip_tipoffService.delete(ids);
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
