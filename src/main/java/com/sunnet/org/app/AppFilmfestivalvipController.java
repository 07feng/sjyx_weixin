package com.sunnet.org.app;

import java.util.ArrayList;
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

import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.vo.FilmfestivalvipUtil;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.view.model.vie_Friendscircle;
import com.sunnet.org.view.service.Ivie_FriendscircleService;

/***
 * 影展会员表
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppFilmfestivalvipController extends BaseController
{

	@Autowired
	private IFilmfestivalvipService filmfestivalvipService;
	 

	/**
	 * 返回分页 filmfestivalvip
	 * 展出名单
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/film_festivalvip/list")
	public String list(PageBean pageBean,String memberid, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<vie_Friendscircle> list = null;
			QueryResult<vie_Friendscircle> result = filmfestivalvipService.findFriendscircle(pageBean, memberid);
			list = result.getResultList();
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("startPage", pageBean.getStartPage());// 当前页
			map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
			map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
			map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
			item.add(map1);
			for (vie_Friendscircle obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("id", obj.getId());
				if(obj.getTitel()!=null){
					map.put("titel", obj.getTitel());
				}else{
					map.put("titel","");
				}
				 
				if (obj.getOpen_time() != null) {
					map.put("open_time", obj.getOpen_time());
				} else {
					map.put("open_time", "");
				}
				if (obj.getTime_length() >0) {
					map.put("time_length", obj.getTime_length());
				} else {
					map.put("time_length", "");
				}
				map.put("sort", obj.getSort());
				if(obj.getTitelnote()!=null){
					map.put("titelnote", obj.getTitelnote());
				}else{
					map.put("titelnote", "");
				}
				
				map.put("isfriends", obj.getIsfriends());
				if (obj.getMember_id() != null) {
					map.put("member_fdId", obj.getMember_id().getId());
					map.put("usersname", obj.getMember_id().getUsersname());
					map.put("levelid", obj.getMember_id().getLevelid().getId());
					map.put("levelname", obj.getMember_id().getLevelid().getLevelname());
					if(obj.getMember_id().getHeadimg()!=null){
						map.put("headimg", obj.getMember_id().getHeadimg());
					}else{
						map.put("headimg", "");
					}
				} else {
					map.put("member_fdId", "");
					map.put("usersname", "");
					map.put("levelid","");
					map.put("levelname", "");
					map.put("headimg", "");
				}
				item.add(map);
			}
			jsonResult.setResult(item);
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
	
	@RequestMapping(value = "/film_festivalvip_no/list")
	public String listfestivalvip(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
			try
			{
				QueryResult<Filmfestivalvip> result = filmfestivalvipService.list(pageBean,request);
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
	@RequestMapping(value = "/film_festivalvip/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Filmfestivalvip> result = filmfestivalvipService.findAll();
			jsonResult.setResult(FilmfestivalvipUtil.getControllerList(result));
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
	@RequestMapping(value = "/film_festivalvip/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Filmfestivalvip result = filmfestivalvipService.findByPrimaryKey(fdId);
				jsonResult.setResult(FilmfestivalvipUtil.getControllerMap(result));
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
	 * 保存 filmfestivalvip
	 * 
	 * @param filmfestivalvip
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/film_festivalvip/save")
	public String save(Filmfestivalvip filmfestivalvip, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			filmfestivalvipService.save(filmfestivalvip);
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
	 * 更新 filmfestivalvip
	 * 
	 * @param filmfestivalvip
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/film_festivalvip/update")
	public String update(Filmfestivalvip filmfestivalvip, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			filmfestivalvipService.update(filmfestivalvip);
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
	@RequestMapping(value = "/film_festivalvip/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				filmfestivalvipService.delete(ids);
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
