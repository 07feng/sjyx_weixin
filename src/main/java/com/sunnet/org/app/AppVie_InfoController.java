package com.sunnet.org.app;

import java.util.List;
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

import com.sunnet.org.view.model.Vie_Info;
import com.sunnet.org.view.service.IVie_InfoService;
import com.sunnet.org.view.vo.Vie_InfoUtil;

/***
 * 我的消息
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppVie_InfoController extends BaseController
{

	@Autowired
	private IVie_InfoService vie_InfoService;

	/**
	 * 获取社交消息列表
	 *
	 * @param info_type
	 * @param page
	 * @param response
	 * @param request
	 * @return
	 * @aurthod caixiang
	 */
//    @RequestMapping(value = "/session/messageList")
//    public String messageList(String info_type, String page, HttpServletResponse response, HttpServletRequest request) {
//        String memberId = getMemberId(request, response);
//        PageBean pageBean = new PageBean();
//        pageBean.setCurrentPage(Integer.parseInt(page));
//        int code = Integer.parseInt(info_type);
//        StringBuffer hql = new StringBuffer();
//        JsonResult jsonResult = new JsonResult();
//        try {
//            switch (code) {
//                case 1:
//                    QueryResult<Tre_friendscircle> result1 = new QueryResult<Tre_friendscircle>();
//                    hql.append("from Tre_friendscircle o where o.circlememberid='" + memberId + "'");
//                    pageBean.setHql(hql.toString());
//                    result1 = iTre_friendscircleService.findByHQLPage(pageBean);
//                    List list1 = new ArrayList();
//                    if (null == result1.getResultList() || result1.getPageBean().getTotalPage() < Integer.parseInt(page)) {
//                        break;
//                    }
//                    for (Tre_friendscircle f : result1.getResultList()) {
//                        Map<String, Object> map = new HashMap();
//                        String time = DateUtil.getDatePoor(new Date(), f.getAddtime());
//                        map.put("time", time);
//                        map.put("doc_id", "-1");
//                        map.put("doc_name", "-1");
//                        map.put("user_id", f.getMemberid().getId());
//                        map.put("user_name", f.getMemberid().getUsersname());
//                        map.put("info_type", "1");
//                        map.put("info_content", "关注了你");
//                        list1.add(map);
//                    }
//                    result1.setResultList(list1);
//                    jsonResult.setData(result1.getResultList());
//                    break;
//                case 2:
//                    QueryResult<Tb_dockeep> result2 = new QueryResult<Tb_dockeep>();
//                    hql.append("from Tb_dockeep o where o.docid.memberid='" + memberId + "'");
//                    pageBean.setHql(hql.toString());
//                    result2 = tb_dockeepService.findByHQLPage(pageBean);
//                    if (null == result2.getResultList() || result2.getPageBean().getTotalPage() < Integer.parseInt(page)) {
//                        break;
//                    }
//                    List list2 = new ArrayList();
//                    for (Tb_dockeep f : result2.getResultList()) {
//                        Map<String, Object> map = new HashMap();
//                        if (null == f.getMemberid()) {
//                            continue;
//                        } else {
//                            map.put("user_id", f.getMemberid().getId());
//                            map.put("user_name", f.getMemberid().getUsersname());
//                        }
//                        String time = DateUtil.getDatePoor(new Date(), f.getAddtime());
//                        map.put("time", time);
//                        map.put("doc_id", f.getDocid().getId());
//                        map.put("doc_name", f.getDocid().getDoctitle());
//                        map.put("info_type", "2");
//                        map.put("info_content", "收藏了你的作品");
//                        list2.add(map);
//                    }
//                    result2.setResultList(list2);
//                    jsonResult.setData(result2.getResultList());
//                    break;
//                case 3:
//                    QueryResult<Tb_docpay> result3 = new QueryResult<Tb_docpay>();
//                    hql.append("from Tb_docpay o where o.docid.memberid='" + memberId + "'");
//                    pageBean.setHql(hql.toString());
//                    result3 = tb_docpayService.findByHQLPage(pageBean);
//                    if (result3.getResultList().size() == 0 || result3.getPageBean().getTotalPage() < Integer.parseInt(page)) {
//                        break;
//                    }
//                    List list3 = new ArrayList();
//                    for (Tb_docpay f : result3.getResultList()) {
//                        Map<String, Object> map = new HashMap();
//                        String time = DateUtil.getDatePoor(new Date(), f.getAddtime());
//                        map.put("time", time);
//                        map.put("doc_id", f.getDocid().getId());
//                        map.put("doc_name", f.getDocid().getDoctitle());
//                        map.put("user_id", f.getMemberid().getId());
//                        map.put("user_name", f.getMemberid().getUsersname());
//                        map.put("info_type", "3");
//                        map.put("info_content", "打赏了你的作品");
//                        list3.add(map);
//                    }
//                    result3.setResultList(list3);
//                    jsonResult.setData(result3.getResultList());
//                    break;
//                case 4:
//                    QueryResult<Tb_docgood> result4 = new QueryResult<Tb_docgood>();
//                    hql.append("from Tb_docgood o where o.docid.memberid='" + memberId + "'");
//                    pageBean.setHql(hql.toString());
//                    result4 = tb_docgoodService.findByHQLPage(pageBean);
//                    if (result4.getResultList().size() == 0 || result4.getPageBean().getTotalPage() < Integer.parseInt(page)) {
//                        break;
//                    }
//                    List list4 = new ArrayList();
//                    for (Tb_docgood f : result4.getResultList()) {
//                        Map<String, Object> map = new HashMap();
//                        if (null == f.getMemberid()) {
//                            continue;
//                        } else {
//                            map.put("user_id", f.getMemberid().getId());
//                            map.put("user_name", f.getMemberid().getUsersname());
//                        }
//                        String time = DateUtil.getDatePoor(new Date(), f.getTodaytime());
//                        map.put("time", time);
//                        map.put("doc_id", f.getDocid().getId());
//                        map.put("doc_name", f.getDocid().getDoctitle());
//                        map.put("info_type", "4");
//                        map.put("info_content", "赞了你的作品");
//                        list4.add(map);
//                    }
//                    result4.setResultList(list4);
//                    jsonResult.setData(result4.getResultList());
//                    break;
//                case 5:
//                    QueryResult<Tre_menberdoccomment> result5 = new QueryResult<Tre_menberdoccomment>();
//                    hql.append("from Tre_menberdoccomment o where o.docid.memberid='" + memberId + "'");
//                    pageBean.setHql(hql.toString());
//                    result5 = iTre_menberdoccommentService.findByHQLPage(pageBean);
//                    int totlePage1 = result5.getPageBean().getTotalPage();
//                    List list5 = new ArrayList();
//                    if (result5.getResultList().size() != 0 && totlePage1 >= Integer.parseInt(page)) {
//                        for (Tre_menberdoccomment f : result5.getResultList()) {
//                            Map<String, Object> map = new HashMap();
//                            if (null == f.getMember_id()) {
//                                continue;
//                            } else {
//                                map.put("user_id", f.getMember_id().getId());
//                                map.put("user_name", f.getMember_id().getUsersname());
//                            }
//                            if (null == f.getDocid()) {
//                                continue;
//                            } else {
//                                map.put("doc_id", f.getDocid().getId());
//                                map.put("doc_name", f.getDocid().getDoctitle());
//                            }
//                            String time = DateUtil.getDatePoor(new Date(), f.getComment_time());
//                            map.put("time", time);
//                            map.put("info_type", "5");
//                            map.put("info_content", "评论了你的作品");
//                            list5.add(map);
//                        }
//                    }
//                    QueryResult<T_comment_fid> result52 = new QueryResult<T_comment_fid>();
//                    if (totlePage1 < Integer.parseInt(page)) {
//                        pageBean.setCurrentPage(Integer.parseInt(page) - totlePage1);
//                        pageBean.setHql("from T_comment_fid o where o.fd_c_member_id='" + memberId + "'");
//                        result52 = iT_comment_fidService.findByHQLPage(pageBean);
//                        if (Integer.parseInt(page) - totlePage1 > result52.getPageBean().getTotalPage()) {
//                            break;
//                        }
//                        if (result52.getResultList().size() != 0) {
//                            for (T_comment_fid f : result52.getResultList()) {
//                                Map<String, Object> map = new HashMap();
//                                if (null == f.getFd_member_id()) {
//                                    continue;
//                                } else {
//                                    map.put("user_id", f.getFd_member_id().getId());
//                                    map.put("user_name", f.getFd_member_id().getUsersname());
//                                }
//                                String time = DateUtil.getDatePoor(new Date(), f.getFd_comment_time());
//                                map.put("time", time);
//                                map.put("doc_id", "-1");
//                                map.put("doc_name", "-1");
//                                map.put("info_type", "5");
//                                map.put("info_content", "回复了你的评论");
//                                list5.add(map);
//                            }
//                        }
//                    }
//                    result5.setResultList(list5);
//                    jsonResult.setData(result5.getResultList());
//                    break;
//                case 6:
//                    QueryResult<Filmfestivalvip_comment> result6 = new QueryResult<Filmfestivalvip_comment>();
//                    hql.append("from Filmfestivalvip_comment o where o.vipid.member_id='" + memberId + "'");
//                    pageBean.setHql(hql.toString());
//                    result6 = iFilmfestivalvip_commentService.findByHQLPage(pageBean);
//                    int totlePage61 = result6.getPageBean().getTotalPage();
//                    List list6 = new ArrayList();
//                    if (result6.getResultList().size() != 0 && totlePage61 >= Integer.parseInt(page)) {
//                        for (Filmfestivalvip_comment f : result6.getResultList()) {
//                            Map<String, Object> map = new HashMap();
//                            if (null == f.getMember_id()) {
//                                continue;
//                            } else {
//                                map.put("user_id", f.getMember_id().getId());
//                                map.put("user_name", f.getMember_id().getUsersname());
//                            }
//                            String time = DateUtil.getDatePoor(new Date(), f.getComment_time());
//                            map.put("time", time);
//                            map.put("doc_id", f.getVipid().getId());
//                            map.put("doc_name", f.getVipid().getTitel());
//                            map.put("info_type", "6");
//                            map.put("info_content", "评论了你的影展");
//                            list6.add(map);
//                        }
//                    }
//                    QueryResult<Filmfestivalvip_comment_fid> result62 = new QueryResult<Filmfestivalvip_comment_fid>();
//                    if (totlePage61 < Integer.parseInt(page)) {
//                        pageBean.setCurrentPage(Integer.parseInt(page) - totlePage61);
//                        pageBean.setHql("from Filmfestivalvip_comment_fid o where o.fd_c_member_id='" + memberId + "'");
//                        result62 = iFilmfestivalvip_comment_fidService.findByHQLPage(pageBean);
//                        if (Integer.parseInt(page) - totlePage61 > result62.getPageBean().getTotalPage()) {
//                            break;
//                        }
//                        if (result62.getResultList().size() != 0) {
//                            for (Filmfestivalvip_comment_fid f : result62.getResultList()) {
//                                Map<String, Object> map = new HashMap();
//                                if (null == f.getFd_member_id()) {
//                                    continue;
//                                } else {
//                                    map.put("user_id", f.getFd_member_id().getId());
//                                    map.put("user_name", f.getFd_member_id().getUsersname());
//                                }
//                                String time = DateUtil.getDatePoor(new Date(), f.getFd_comment_time());
//                                map.put("time", time);
//                                map.put("doc_id", "-1");
//                                map.put("doc_name", "-1");
//                                map.put("info_type", "6");
//                                map.put("info_content", "回复了你的评论");
//                                list6.add(map);
//                            }
//                        }
//                    }
//                    result6.setResultList(list6);
//                    jsonResult.setData(result6.getResultList());
//                    break;
//                case 7:
//                    QueryResult result7 = new QueryResult();
//                    result7 = ifilmfestivalvip_goodService.getList(pageBean, memberId);
//                    if (null == result7.getResultList() || result7.getPageBean().getTotalPage() < Integer.parseInt(page)) {
//                        break;
//                    }
//                    jsonResult.setData(result7.getResultList());
//                    break;
//                case 8:
//                    QueryResult<Filmfestivalvip_pay> result8 = new QueryResult<Filmfestivalvip_pay>();
//                    hql.append("from Filmfestivalvip_pay o where o.vipid.member_id='" + memberId + "'");
//                    pageBean.setHql(hql.toString());
//                    result8 = iFilmfestivalvip_payService.findByHQLPage(pageBean);
//                    if (result8.getResultList().size() == 0 || result8.getPageBean().getTotalPage() < Integer.parseInt(page)) {
//                        break;
//                    }
//                    List list8 = new ArrayList();
//                    for (Filmfestivalvip_pay f : result8.getResultList()) {
//                        Map<String, Object> map = new HashMap();
//                        if (null == f.getMemberid()) {
//                            continue;
//                        } else {
//                            map.put("user_id", f.getMemberid().getId());
//                            map.put("user_name", f.getMemberid().getUsersname());
//                        }
//                        if (null == f.getVipid()) {
//                            continue;
//                        } else {
//                            map.put("doc_id", f.getVipid().getId());
//                            map.put("doc_name", f.getVipid().getTitel());
//                        }
//                        String time = DateUtil.getDatePoor(new Date(), f.getAddtime());
//                        map.put("time", time);
//                        map.put("info_type", "8");
//                        map.put("info_content", "打赏了你的影展");
//                        list8.add(map);
//                    }
//                    result8.setResultList(list8);
//                    jsonResult.setData(result8.getResultList());
//                    break;
//                default:
//                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
//                    jsonResult.setMsg("参数错误");
//                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
////               	break;
//            }
//        } catch (Exception e) {
//            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
//            jsonResult.setMsg("系统异常");
//            log.debug(e.getMessage());
//            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
//        }
//        jsonResult.setCode(Constants.SUCCESS_CODE);
//        jsonResult.setMsg(Constants.SUCCESS_DATA);
//        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
//    }

	/**
	 * 返回分页 vie_Info
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/vieInfo/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Vie_Info> result = vie_InfoService.list(pageBean,request);
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
	@RequestMapping(value = "/vieInfo/listAll")
	@RequiresPermissions("sys_vie_Info")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Vie_Info> result = vie_InfoService.findAll();
			jsonResult.setResult(Vie_InfoUtil.getControllerList(result));
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
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/vieInfo/selectKey")
	@RequiresPermissions("sys_vie_Info")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Vie_Info result = vie_InfoService.findByPrimaryKey(fdId);
				jsonResult.setResult(Vie_InfoUtil.getControllerMap(result));
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
	 * 保存 vie_Info
	 * 
	 * @param vie_Info
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/vieInfo/save")
	@RequiresPermissions("sys_vie_Info")
	public String save(Vie_Info vie_Info, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			vie_InfoService.save(vie_Info);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了我的消息保存");
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
	 * 更新 vie_Info
	 * 
	 * @param vie_Info
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/vieInfo/update")
	@RequiresPermissions("sys_vie_Info")
	public String update(Vie_Info vie_Info, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			vie_InfoService.update(vie_Info);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
			Log(request,"操作了我的消息更新");
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
	@RequestMapping(value = "/vieInfo/delete")
	@RequiresPermissions("sys_vie_Info")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				vie_InfoService.delete(ids);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
				Log(request,"操作了我的消息删除");
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
