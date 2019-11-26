package com.sunnet.org.app;

import java.util.ArrayList;
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

import com.sunnet.org.system.model.Tb_siteconfig;
import com.sunnet.org.system.model.Tb_watermark;
import com.sunnet.org.system.service.ITb_siteconfigService;
import com.sunnet.org.system.service.ITb_watermarkService;
import com.sunnet.org.system.vo.Tb_siteconfigUtil;
import com.sunnet.org.system.vo.Tb_watermarkUtil;
import com.sunnet.org.util.OSSClientUtil2;
import com.sunnet.org.util.UserUtil;

/***
 * 站点设置
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_siteconfigController extends BaseController {

	@Autowired
	private ITb_siteconfigService tb_siteconfigService;
	@Autowired
	private ITb_watermarkService tb_watermarkService;

	/**
	 * 返回List列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/siteconfig/listAll")
	public String listAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		List<Tb_watermark> result2 =null;
		try {
			List<Tb_siteconfig> result = tb_siteconfigService.findAll();
			if(null!=result.get(0).getWatermarkswitch() && result.get(0).getWatermarkswitch()=="1" || "1".equals(result.get(0).getWatermarkswitch())){
				result2 = tb_watermarkService.findAll();
			}
			List item = new ArrayList();
			for (Tb_siteconfig obj : result) {
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("id", obj.getId());
				map.put("sitename", obj.getSitename());
				map.put("siteswitch", obj.getSiteswitch());
				map.put("logoimg", obj.getLogoimg());
				map.put("isupload", obj.getIsupload());
				map.put("uploadformat", obj.getUploadformat());
				map.put("watermarkswitch", obj.getWatermarkswitch());
				map.put("sietdescribe", obj.getSietdescribe());
				map.put("sitekeywords", obj.getSitekeywords());
				map.put("siterecord", obj.getSiterecord());
				map.put("sitecopyright", obj.getSitecopyright());
				if(null!=obj.getWatermarkswitch() && obj.getWatermarkswitch()=="1" || "1".equals(obj.getWatermarkswitch())){
					if(null!=result2){
						for (Tb_watermark objs : result2) {
							map.put("tb_watermark_id", obj.getId());
							map.put("tb_watermark_watermarktype", objs.getWatermarktype());
							map.put("tb_watermark_watermarktxt", objs.getWatermarktxt());
							map.put("tb_watermark_watermarkimg", objs.getWatermarkimg());
							map.put("tb_watermark_watermarkwidth", objs.getWatermarkwidth());
							map.put("tb_watermark_watermarkheight", objs.getWatermarkheight());
							map.put("tb_watermark_watermarkposition", objs.getWatermarkposition());
						}
					}
				}else{
					map.put("tb_watermark_id","");
					map.put("tb_watermark_watermarktype", "");
					map.put("tb_watermark_watermarktxt", "");
					map.put("tb_watermark_watermarkimg", "");
					map.put("tb_watermark_watermarkwidth", "");
					map.put("tb_watermark_watermarkheight","");
					map.put("tb_watermark_watermarkposition","");
				}
				item.add(map);
			}
			jsonResult.setResult(item);
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
