package com.sunnet.org.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.sunnet.org.albumcard.model.Tb_albumcard;
import com.sunnet.org.albumcard.model.Tb_albumcardrecord;
import com.sunnet.org.albumcard.service.ITb_albumcardService;
import com.sunnet.org.albumcard.service.ITb_albumcardrecordService;
import com.sunnet.org.albumcard.vo.Tb_albumcardUtil;

/***
 * 相册贺卡模板
 * 
 * @author caixiang
 */
@Controller
@RequestMapping("/app")
public class AppTb_albumcardController extends BaseController {

	@Autowired
	private ITb_albumcardService tb_albumcardService;
	@Autowired
	private ITb_albumcardrecordService tb_albumcardrecordService;

//	/**
////	 * 获取模板列表
////	 * @param type
////	 * @param page
////	 * @param request
////	 * @param response
////	 * @return
////	 */
//	@RequestMapping(value = "/getTemplateList")
//	public String getTemplateList(String type,String page, HttpServletRequest request,
//					   HttpServletResponse response) {
//		JsonResult jsonResult = new JsonResult();
//		PageBean pageBean = new PageBean();
//		pageBean.setCurrentPage(Integer.parseInt(page));
//		try {
//			QueryResult<Tb_albumcard> result = tb_albumcardService.Tb_albumcardList(
//					pageBean, type);
//			jsonResult.setCode(Constants.SUCCESS_CODE);
//			jsonResult.setMsg(Constants.SUCCESS_DATA);
//			jsonResult.setData(result.getResultList());
//		} catch (Exception e) {
//			jsonResult.setCode("500");
//			jsonResult.setMessage("执行错误！");
//			log.debug("异常:" + e);
//		}
//		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
//

}
