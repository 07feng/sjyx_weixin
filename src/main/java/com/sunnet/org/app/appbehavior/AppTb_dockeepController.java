package com.sunnet.org.app.appbehavior;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sunnet.org.apppush.util.JPushAllUtil;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.service.ITb_dockeepService;
import com.sunnet.org.doc.vo.Tb_dockeepUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_sendmessage;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_sendmessageService;
import com.sunnet.org.util.StringUtils;

/***
 * 收藏
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_dockeepController extends BaseController
{

	@Autowired
	private ITb_dockeepService tb_dockeepService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_levelService tb_levelService; 
	@Autowired
	private ITb_sendmessageService tb_sendmessageService;
	/**
	 * 返回分页 tb_dockeep
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/dockeep/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Tb_dockeep> result = tb_dockeepService.list(pageBean,request);
			 
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
	@RequestMapping(value = "/dockeep/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tb_dockeep> result = tb_dockeepService.findAll();
			jsonResult.setResult(Tb_dockeepUtil.getControllerList(result));
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
	@RequestMapping(value = "/dockeep/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Tb_dockeep result = tb_dockeepService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_dockeepUtil.getControllerMap(result));
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
	 * 保存 tb_dockeep
	 * 
	 * @param tb_dockeep
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/dockeep/save")
	public String save(Tb_dockeep tb_dockeep, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			String msg = StringUtils.isObject(
					new Object[] { tb_dockeep.getDocid(),tb_dockeep.getMemberid()},
					new String[] { "收藏文件不能为空","用户不能为空" });
			if (!msg.equals("")) {
				jsonResult.setCode("504");
				jsonResult.setMessage(msg);
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			//查询收藏的人的信息
			Tb_member mem = tb_memberService.findByPrimaryKey(tb_dockeep.getMemberid().getId());
			
			List<Tb_dockeep> result = tb_dockeepService.listkeep(tb_dockeep.getDocid().getId(), tb_dockeep.getMemberid().getId());
			Tb_doc docresult = tb_docService.findByPrimaryKey(tb_dockeep.getDocid().getId());
			if(null !=result){
				tb_dockeepService.remove(result.get(0).getId());
				if(null!=docresult){
					docresult.setFilekeepcount(docresult.getFilekeepcount()-1);
					
				}
				tb_docService.update(docresult);
				jsonResult.setCode("200");
				jsonResult.setMessage("取消收藏成功！");
			}else {
				
				if(null!=docresult){
					docresult.setFilekeepcount(docresult.getFilekeepcount()+1);
					tb_docService.update(docresult);
				}
				tb_dockeep.setAddtime(new Date());
				tb_dockeepService.save(tb_dockeep);
				Map map = new HashMap();
				if(null!=docresult.getMemberid().getRandroidios() && docresult.getMemberid().getRandroidios()==0 ){
					  //ios
					  JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),mem.getUsersname()+" 收藏了你的文件  ："+docresult.getDoctitle(), map, 0);
				  }else if(null!=docresult.getMemberid().getRandroidios() && docresult.getMemberid().getRandroidios()==1){
						//安卓
					  JPushAllUtil.jSend_notification(docresult.getMemberid().getRegistrationid(),mem.getUsersname()+" 收藏了你的文件  ："+docresult.getDoctitle(), map, 1);
				  }
			
				jsonResult.setCode("201");
				jsonResult.setMessage("收藏成功！");
				if(null!=mem){
					//查询 会员等级积分来源表点赞数量
					List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
					if(discuss.size()>0){
						 mem.setId(tb_dockeep.getMemberid().getId());
						 mem.setLevelintegral(mem.getLevelintegral()+discuss.get(0).getGood());
						 List<Tb_level> level=tb_levelService.listlevel(mem.getLevelintegral()+"");
							if(null!=level){
								mem.setLevelid(level.get(0));
							}
							tb_memberService.update(mem);
					} 
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 更新 tb_dockeep
	 * 
	 * @param tb_dockeep
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/dockeep/update")
	public String update(Tb_dockeep tb_dockeep, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tb_dockeepService.update(tb_dockeep);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了收藏更新");
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
	@RequestMapping(value = "/dockeep/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				tb_dockeepService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				Log(request,"操作了收藏删除");
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
