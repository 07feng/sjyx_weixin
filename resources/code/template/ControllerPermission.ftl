package ${com}.${keyType}.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import com.sunnet.framework.util.FileUploadUtil;

import com.alibaba.fastjson.JSONObject;
import ${framework}.controller.BaseController;
import ${framework}.pagenation.PageBean;
import ${framework}.util.JsonResult;
import ${framework}.pagenation.QueryResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import ${com}.${keyType}.model.${bigName};
import ${com}.${keyType}.service.I${bigName}Service;
import ${com}.${keyType}.vo.${bigName}Util;

/***
 * ${title}
 * 
 * @author ${auto}
 */
@Controller
@RequestMapping("/admin")
public class ${bigName}Controller extends BaseController
{

	@Autowired
	private I${bigName}Service ${minName}Service;

	@RequestMapping(value = "/${minName}/index")
	${permissions_select}		
	public String index(HttpSession session, HttpServletRequest request)
	{
		return "view/${keyType}/${minName}_list";
	}

	@RequestMapping(value = "/${minName}/add")
	${permissions_add}
	public String add(String fdId, HttpServletRequest request)
	{
		${bigName} ${minName}=null;
		if(fdId != null && !(fdId.equals(""))){
			 ${minName} = ${minName}Service.findByPrimaryKey(fdId);
		}
		request.setAttribute("${minName}", ${minName});
		return "view/${keyType}/${minName}_add";
	}
	
	@RequestMapping(value = "/${minName}/select")
	${permissions_select}
	public String select(String fdId, HttpServletRequest request)
	{
		${bigName} ${minName}=null;
		if(fdId != null && !(fdId.equals(""))){
			 ${minName} = ${minName}Service.findByPrimaryKey(fdId);
		}
		request.setAttribute("${minName}", ${minName});
		return "view/${keyType}/${minName}_select";
	}

	/**
	 * 返回分页 ${minName}
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/${minName}/list")
	${permissions_select}
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<${bigName}> result = ${minName}Service.list(pageBean,request);
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
	@RequestMapping(value = "/${minName}/listAll")
	${permissions_select}
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<${bigName}> result = ${minName}Service.findAll();
			jsonResult.setResult(${bigName}Util.getControllerList(result));
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
	@RequestMapping(value = "/${minName}/selectKey")
	${permissions_select}
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				${bigName} result = ${minName}Service.findByPrimaryKey(fdId);
				jsonResult.setResult(${bigName}Util.getControllerMap(result));
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
	 * 保存 ${minName}
	 * 
	 * @param ${minName}
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/${minName}/save")
	${permissions_add}
	public String save(${bigName} ${minName}, HttpServletResponse response, HttpServletRequest request ${ConllerImg})
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	${ConllerImgD}
			${minName}Service.save(${minName});
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了${title}保存");
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
	 * 更新 ${minName}
	 * 
	 * @param ${minName}
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/${minName}/update")
	${permissions_update}
	public String update(${bigName} ${minName}, HttpServletResponse response, HttpServletRequest request ${ConllerImg})
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	${ConllerImgD}
			${minName}Service.update(${minName});
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了${title}更新");
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
	@RequestMapping(value = "/${minName}/delete")
	${permissions_del}
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				${minName}Service.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				Log(request,"操作了${title}删除");
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
