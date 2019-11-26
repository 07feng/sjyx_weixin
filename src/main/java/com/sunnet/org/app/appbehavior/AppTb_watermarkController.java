package com.sunnet.org.app.appbehavior;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.sunnet.org.system.model.Tb_watermark;
import com.sunnet.org.system.service.ITb_watermarkService;
import com.sunnet.org.system.vo.Tb_watermarkUtil;

/***
 * 水印设置
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_watermarkController extends BaseController {

	@Autowired
	private ITb_watermarkService tb_watermarkService;

	/**
	 * 返回分页 tb_watermark
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/tbwatermark/list")
	public String list(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			QueryResult<Tb_watermark> result = tb_watermarkService.list(
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
	@RequestMapping(method=RequestMethod.POST,value = "/tbwatermark/listAll")
	public String listAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tb_watermark> result = tb_watermarkService.findAll();
			jsonResult.setResult(Tb_watermarkUtil.getControllerList(result));
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
	@RequestMapping(method=RequestMethod.POST,value = "/tbwatermark/selectKey")
	public String selectKey(String fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Tb_watermark result = tb_watermarkService
						.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_watermarkUtil.getControllerMap(result));
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
	 * 保存 tb_watermark
	 * 
	 * @param tb_watermark
	 * @param request
	 * @return json
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/tbwatermark/save")
	public String save(Tb_watermark tb_watermark, HttpServletResponse response,
			HttpServletRequest request, MultipartFile watermarkimg_file) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (watermarkimg_file != null) {
				String img = FileUploadUtil.upload(watermarkimg_file, request,
						"tb_watermark");
				tb_watermark.setWatermarkimg(img);
			}
			tb_watermarkService.save(tb_watermark);
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
	 * 更新 tb_watermark
	 * 
	 * @param tb_watermark
	 * @param request
	 * @return json
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/tbwatermark/update")
	public String update(Tb_watermark tb_watermark,
			HttpServletResponse response, HttpServletRequest request,
			MultipartFile watermarkimg_file) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (watermarkimg_file != null) {
				String img = FileUploadUtil.upload(watermarkimg_file, request,
						"tb_watermark");
				tb_watermark.setWatermarkimg(img);
			}
			tb_watermarkService.update(tb_watermark);
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
	 * 删除
	 * 
	 * @param ids
	 *            数组
	 * @return json
	 */
	@RequestMapping(method=RequestMethod.POST,value = "/tbwatermark/delete")
	public String delete(String[] ids, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (ids != null && ids.length > 0) {
				tb_watermarkService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
			} else {
				jsonResult.setMessage("请选择删除选项!");
				jsonResult.setCode("300");
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

}
