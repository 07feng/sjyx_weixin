package com.sunnet.org.app.visitors;

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

import com.sunnet.org.visitors.model.T_searchrecords;
import com.sunnet.org.visitors.model.T_topsearchterm;
import com.sunnet.org.visitors.service.IT_searchrecordsService;
import com.sunnet.org.visitors.service.IT_topsearchtermService;
import com.sunnet.org.visitors.vo.T_searchrecordsUtil;

/***
 * 搜索记录
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppT_searchrecordsController extends BaseController
{

	@Autowired
	private IT_searchrecordsService t_searchrecordsService;
	@Autowired
	private IT_topsearchtermService t_topsearchtermService;
	 
    
	/**
	 * 返回分页 t_searchrecords
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/searchrecords/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<T_searchrecords> result = t_searchrecordsService.list(pageBean,request);
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
	@RequestMapping(value = "/searchrecords/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<T_searchrecords> result = t_searchrecordsService.findAll();
			jsonResult.setResult(T_searchrecordsUtil.getControllerList(result));
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
	@RequestMapping(value = "/searchrecords/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				T_searchrecords result = t_searchrecordsService.findByPrimaryKey(fdId);
				jsonResult.setResult(T_searchrecordsUtil.getControllerMap(result));
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
	 * 保存 t_searchrecords
	 * 
	 * @param t_searchrecords
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/searchrecords/save")
	public String save(T_searchrecords t_searchrecords, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			t_searchrecords.setFdsearchtime(new Date());
			t_searchrecordsService.save(t_searchrecords);
			T_topsearchterm top=new T_topsearchterm();
			T_topsearchterm trem=t_topsearchtermService.findByPrimaryKey(t_searchrecords.getFdsearchterm());
			if(trem!=null){
				top.setFdSearchterm(trem.getFdSearchterm());
				top.setFd_lastsearchtime(new Date());
				top.setFd_searchnumber(trem.getFd_searchnumber()+1);
				t_topsearchtermService.update(top);
			}else{
				top.setFdSearchterm(t_searchrecords.getFdsearchterm());
				top.setFd_searchnumber(1);
				top.setFd_lastsearchtime(new Date());
				t_topsearchtermService.save(top);
			}
			
			
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
	 * 更新 t_searchrecords
	 * 
	 * @param t_searchrecords
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/searchrecords/update")
	public String update(T_searchrecords t_searchrecords, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			t_searchrecordsService.update(t_searchrecords);
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
	@RequestMapping(value = "/searchrecords/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				t_searchrecordsService.delete(ids);
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
