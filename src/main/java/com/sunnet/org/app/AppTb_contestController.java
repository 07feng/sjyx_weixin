package com.sunnet.org.app;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.service.ITb_contestService;
import com.sunnet.org.competition.service.Ivie_doccontestmemberService;
import com.sunnet.org.doc.service.ITre_menberdocscoreService;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/***
 * 赛事列表
 * 
 * @author caixiang
 */
@Controller
@RequestMapping("/app")
public class AppTb_contestController extends BaseController {

	@Autowired
	private ITb_contestService tb_contestService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITre_menberdocscoreService tre_menberdocscoreService;
	@Autowired
	private Ivie_doccontestmemberService vie_doccontestmemberService;

	/**
	 * 获取赛事列表
	 * @param matchStatus 1进行2结束
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMatchList")
	public String selectMacthBytype(String matchStatus ,String page ,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		PageBean pageBean=new PageBean();
		try{
			pageBean.setCurrentPage(Integer.parseInt(page));
			QueryResult result = tb_contestService.getList(Integer.parseInt(page), Integer.parseInt(matchStatus));
			if(null == result){
				jsonResult.setCode(Constants.SUCCESS_CODE);
				jsonResult.setMsg(Constants.SUCCESS_DATA);
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			jsonResult.setData(result.getResultList());
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}catch (Exception e){
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 获取赛事详情
	 * authrd caixiang
	 * @param matchId
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/getMatchInfo")
	public String selectKey(String matchId , HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		Map<String,Object> map;
		try{
			map = tb_contestService.getTb_contest(Integer.parseInt(matchId));
			jsonResult.setData(map);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}catch (Exception e){
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}


	/**
	 * 获取赛事作品列表
	 * aurthod:caixiang
	 * @param matchId
	 * @param page
	 * @param code
	 * code =1（最新参赛作品）
	 * code =2 （专家点赞作品）
	 * code =3 （人气作品）
	 * code =4  （获奖作品）
	 * code =5 (入围作品)
	 * code = 0  ( 全部作品)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMatchImgList")
	public String getMatchImgList(String matchId ,String page,String code,HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
//		PageBean pageBean = new PageBean();
//		pageBean.setCurrentPage(Integer.parseInt(page));
		Map<String,Object> map;
		try{
			map = tb_contestService.getTb_contest(Integer.parseInt(matchId),Integer.parseInt(page),Integer.parseInt(code));
			jsonResult.setData(map);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}catch (Exception e){
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 获取赛事获奖名单
	 * aurthod : caixiang
	 * @param matchId
	 * @param page
	 * @param code
	 * code = 1(入围名单)
	 * code = 2(获奖名单)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMatchUserList")
	public String getMatchUserList(String matchId ,String page,String code,HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		PageBean pageBean = new PageBean();
		pageBean.setCurrentPage(Integer.parseInt(page));
		List list = new LinkedList();
		String memberId=getMemberId(request,response);
//		String memberId="";
		try{
			if(null == memberId){
				memberId ="";
			}
			list = tb_contestService.getPrizeMassage(Integer.parseInt(matchId),Integer.parseInt(code),Integer.parseInt(page),memberId);
			if(null == list || list.size()==0){
				jsonResult.setData("");
			}else {
				jsonResult.setData(list);
			}
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}catch (Exception e){
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

}
