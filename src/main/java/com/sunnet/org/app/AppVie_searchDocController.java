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

import com.sunnet.org.competition.vo.T_contest_themeUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.information.service.ITb_filetypeService;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.member.service.ITb_filelabelService;
import com.sunnet.org.system.model.SysLogItem;
import com.sunnet.org.view.model.Vie_searchdoc;
import com.sunnet.org.view.service.IVie_searchDocService;
import com.sunnet.org.view.vo.Vie_searchDocUtil;

/***
 * app模糊搜索
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppVie_searchDocController extends BaseController
{

	@Autowired
	private IVie_searchDocService vie_searchDocService;
	@Autowired
	private ITb_filetypeService tb_filetypeService;
	@Autowired
	private ITb_filelabelService tb_filelabelService;
	
	@RequestMapping(value = "/viesearchDoc/list")
	public String listfind(PageBean pageBean,String create,HttpServletRequest request,HttpServletResponse response)
	{
		List<Vie_searchdoc> result = null;
		JsonResult jsonResult = new JsonResult();
		QueryResult<Vie_searchdoc> resultsys = new QueryResult<Vie_searchdoc>();
		int totalRecord=0;
		try
		{
			List<Tb_filetype> filetype = tb_filetypeService.findAll();
			//List<Tb_filelabel> filelabel = tb_filelabelService.findAll();
			String str=" from Vie_searchdoc v where 1=1 and v.ispublic=1 and v.docstatus=1 and v.isdelete=0 "+ vie_searchDocService.selectCreate(create, filetype);
			result = vie_searchDocService.findByHQL(str, pageBean); 
			 
			if (null != result) {
				totalRecord = vie_searchDocService.findByHQLCount(str);
				resultsys.setResultList(Vie_searchDocUtil.getControllerList(result));
				pageBean.setTotalRecord(totalRecord);
				resultsys.setPageBean(pageBean);
				jsonResult.setResult(resultsys);
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
			}else{
				jsonResult.setCode("300");                 
				jsonResult.setMessage("亲！您查询的数据不存在哟!");
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
	 * 返回分页 vie_searchDoc
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/vie_searchDoc/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Vie_searchdoc> result = vie_searchDocService.list(pageBean,request);
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
	@RequestMapping(value = "/vie_searchDoc/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Vie_searchdoc> result = vie_searchDocService.findAll();
			jsonResult.setResult(Vie_searchDocUtil.getControllerList(result));
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

	 