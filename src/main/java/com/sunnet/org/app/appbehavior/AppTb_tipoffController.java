package com.sunnet.org.app.appbehavior;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;
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

import com.sunnet.org.doc.model.Tb_tipoff;
import com.sunnet.org.doc.service.ITb_tipoffService;
import com.sunnet.org.doc.vo.Tb_tipoffUtil;
import com.sunnet.org.util.UserUtil;

/***
 * 举报记录
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_tipoffController extends BaseController
{

	@Autowired
	private ITb_tipoffService tb_tipoffService;

	 

	/**
	 * 返回分页 tb_tipoff
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/tb_tipoff/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Tb_tipoff> result = tb_tipoffService.list(pageBean,request);
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
	@RequestMapping(value = "/     /listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tb_tipoff> result = tb_tipoffService.findAll();
			jsonResult.setResult(Tb_tipoffUtil.getControllerList(result));
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
	@RequestMapping(value = "/tb_tipoff/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Tb_tipoff result = tb_tipoffService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_tipoffUtil.getControllerMap(result));
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
	 * 保存 tb_tipoff
	 * 
	 * @param tb_tipoff
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/tipoff/save")
	public String save(Tb_tipoff tb_tipoff, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tb_tipoff.setCausetime(new Date());
			tb_tipoffService.save(tb_tipoff);
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
	 * 举报
	 * aurthod :caixaing
	 * @param response
	 * @param request
	 * @param msg
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/session/complaint")
	public String complaint(HttpServletResponse response, HttpServletRequest request ,String msg,String docId){
		String memberId=getMemberId(request,response);
		JsonResult jsonResult = new JsonResult();
		Tb_tipoff tb_tipoff = new Tb_tipoff();
		Tb_doc tb_doc = new Tb_doc();
		Tb_member tb_member = new Tb_member();
		tb_doc.setId(docId);
		tb_member.setId(memberId);
		tb_tipoff.setMember_id(tb_member);
		tb_tipoff.setDoc_id(tb_doc);
		tb_tipoff.setCausetime(new Date());
		tb_tipoff.setCause(msg);
		StringBuffer hql = new StringBuffer();
//		int id=tb_tipoffService.getMaxId(Tb_tipoff.class)+1;
		Integer isExist = tb_tipoffService.findByHQL("from Tb_tipoff t where t.doc_id.id=? and t.member_id.id=?",docId,memberId).size();
		System.out.println("isExist="+isExist);
		try {
				//这条记录是否存在
			if(isExist==null||isExist <= 0) {
//					tb_tipoff.setId(id);
					tb_tipoffService.save(tb_tipoff);
			}else{
					jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
					jsonResult.setMsg("您已经举报过一次了");
					return ajaxJson(JSONObject.toJSONString(jsonResult), response);
				}
		}catch (Exception e){
				jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
				jsonResult.setMessage("执行错误！");
				log.debug("异常:"+e);
		}
		jsonResult.setCode(Constants.SUCCESS_CODE);
		jsonResult.setMsg(Constants.SUCCESS_DATA);
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 更新 tb_tipoff
	 * 
	 * @param tb_tipoff
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/tb_tipoff/update")
	public String update(Tb_tipoff tb_tipoff, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tb_tipoff.setUsertime(new Date());
			tb_tipoff.setUser_id(UserUtil.getSession(request));
			tb_tipoffService.update(tb_tipoff);
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
	@RequestMapping(value = "/tb_tipoff/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				tb_tipoffService.delete(ids);
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
