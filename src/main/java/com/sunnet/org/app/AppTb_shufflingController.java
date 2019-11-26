package com.sunnet.org.app;

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

import com.sunnet.org.information.model.Tb_shuffling;
import com.sunnet.org.information.service.ITb_shufflingService;
import com.sunnet.org.information.vo.Tb_linkUtil;
import com.sunnet.org.information.vo.Tb_shufflingUtil;
import com.sunnet.org.util.UserUtil;

/***
 * 轮播图表
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_shufflingController extends BaseController {

	@Autowired
	private ITb_shufflingService tb_shufflingService;

	/**
	 * 返回分页 tb_shuffling
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/shuffling/applist")
	public String list(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			QueryResult<Tb_shuffling> result = tb_shufflingService.list(
					pageBean, request);
			jsonResult.setResult(result);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 返回List列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shuffling/applistAll")
	public String applistAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tb_shuffling> result = tb_shufflingService.findAll();
			jsonResult.setResult(Tb_shufflingUtil.getControllerList(result));
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 返回单个对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shuffling/selectKey")
	public String selectKey(Integer fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Tb_shuffling result = tb_shufflingService
						.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_shufflingUtil.getControllerMap(result));
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
			} else {
				jsonResult.setCode("300");
				jsonResult.setMessage("获取出错!请检查fdId参数是否传入!");
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 保存 tb_shuffling
	 * 
	 * @param tb_shuffling
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/shuffling/save")
	public String save(Tb_shuffling tb_shuffling, HttpServletResponse response,
			HttpServletRequest request, MultipartFile imgurl_file) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (imgurl_file != null) {
				String img = FileUploadUtil.upload(imgurl_file, request,
						"tb_shuffling");
				tb_shuffling.setImgurl(img);
			}
			tb_shuffling.setEdittime(new Date());
			tb_shuffling.setEdituserid(UserUtil.getSession(request));
			tb_shufflingService.save(tb_shuffling);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			 
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 更新 tb_shuffling
	 * 
	 * @param tb_shuffling
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/shuffling/update")
	public String update(Tb_shuffling tb_shuffling,
			HttpServletResponse response, HttpServletRequest request,
			MultipartFile imgurl_file) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (imgurl_file != null) {
				String img = FileUploadUtil.upload(imgurl_file, request,
						"tb_shuffling");
				tb_shuffling.setImgurl(img);
			}
			tb_shuffling.setEdittime(new Date());
			tb_shuffling.setEdituserid(UserUtil.getSession(request));
			tb_shufflingService.update(tb_shuffling);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
		 
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	 

}
