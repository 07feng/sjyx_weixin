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

import com.sunnet.org.member.model.Tb_contesttype;
import com.sunnet.org.member.service.ITb_contesttypeService;
import com.sunnet.org.member.vo.Tb_contesttypeUtil;

/***
 * 赛事分类
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_contesttypeController extends BaseController {

	@Autowired
	private ITb_contesttypeService tb_contesttypeService;
 

	/**
	 * 返回分页 tb_contesttype
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contesttype/list")
	public String list(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			QueryResult<Tb_contesttype> result = tb_contesttypeService.list(
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
	@RequestMapping(value = "/contesttype/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tb_contesttype> result = tb_contesttypeService.findAll();
			jsonResult.setResult(Tb_contesttypeUtil.getControllerList(result));
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
	@RequestMapping(value = "/contesttype/selectKey")
	public String selectKey(Integer fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Tb_contesttype result = tb_contesttypeService
						.findByPrimaryKey(fdId);
				jsonResult.setResult(Tb_contesttypeUtil
						.getControllerMap(result));
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
	 * 保存 tb_contesttype
	 * 
	 * @param tb_contesttype
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contesttype/save")
	public String save(Tb_contesttype tb_contesttype,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			Log(request, "新增了" + tb_contesttype.getName());
			tb_contesttypeService.save(tb_contesttype);
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
	 * 更新 tb_contesttype
	 * 
	 * @param tb_contesttype
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/contesttype/update")
	public String update(Tb_contesttype tb_contesttype,
			HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			Log(request, "修改了" + tb_contesttype.getName());
			tb_contesttypeService.update(tb_contesttype);
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
	@RequestMapping(value = "/contesttype/delete")
	public String delete(String[] ids, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (ids != null && ids.length > 0) {
				Log(request, "操作了${title}删除");
				tb_contesttypeService.delete(ids);
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
