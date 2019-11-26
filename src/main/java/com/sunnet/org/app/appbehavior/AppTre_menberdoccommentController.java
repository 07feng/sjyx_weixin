package com.sunnet.org.app.appbehavior;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.framework.util.WriteError;
import com.sunnet.org.apppush.util.JPushAllUtil;
import com.sunnet.org.information.model.T_comment_fid;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.Tre_menberdoccomment;
import com.sunnet.org.information.service.IT_comment_fidService;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.information.service.ITre_menberdoccommentService;
import com.sunnet.org.information.vo.T_comment_fidUtil;
import com.sunnet.org.information.vo.Tre_menberdoccommentUtil;
import com.sunnet.org.member.model.*;
import com.sunnet.org.member.service.*;
import com.sunnet.org.member.vo.Tb_membermessagesteupUtil;
import com.sunnet.org.member.vo.Tre_docfilelabelUtil;
import com.sunnet.org.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/***
 * 评论管理
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTre_menberdoccommentController extends BaseController
{
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITre_menberdoccommentService tre_menberdoccommentService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private ITb_levelService tb_levelService;
	@Autowired
	private IT_comment_fidService t_comment_fidService;
	@Autowired
	private ITre_docfilelabelService tre_docfilelabelService;
	
	@Autowired
	private ITb_membermessagesteupService tb_membermessagesteupService;
	@Autowired
	private IT_comment_fidService iT_comment_fidService;
	/**
	 * 保存 tre_menberdoccomment
	 * fd_c_member_id 被评论人
	 * member_id 评论人
	 * fd_doccomment_id  评论表id
	 * @param comments 评论表
	 * @param request
	 *  @param docid 文件id
	 * @return json
	 */
	@RequestMapping(value = "/menberdoccomment/save")
	public String save(String docid,String comments,String member_id,String fd_c_member_id, Integer fd_doccomment_id, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			WriteError.writeLogFile("comments:", comments);
			String msg = StringUtils.isObject(
					new Object[] { docid,comments,member_id},
					new String[] { "被评论文件不能为空","评论内容不能为空","用户不能为空" });
			if (!msg.equals("")) {
				jsonResult.setCode("504");
				jsonResult.setMessage(msg);
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}else{
				//查询会员留言设置
				List<Tb_membermessagesteup> liuyan=tb_membermessagesteupService.findAll();
				if(liuyan!=null && liuyan.size()>0){
					String []jinyong=liuyan.get(0).getDisablewords().split("[,，]");
					for(String s:jinyong)
					{
						comments=comments.replaceAll(s, liuyan.get(0).getReplacewords());
					}
					//comments.indexOf(ch)	
				}
				Tb_doc doc=tb_docService.findByPrimaryKey(docid);
				Tb_member mem=tb_memberService.findByPrimaryKey(member_id);
				List<Tre_menberdoccomment> dttime =null;
				T_comment_fid cf=new T_comment_fid();
				Tb_member cmem=null;
				if(null!=fd_c_member_id){
					 cmem=tb_memberService.findByPrimaryKey(fd_c_member_id);
				}            
				
				Tre_menberdoccomment md= new Tre_menberdoccomment();
				md.setDocid(doc);
				md.setMember_id(mem);
				md.setComments(comments);
				md.setComment_time(new Date());
				md.setIs_public("1");
				if(fd_doccomment_id!=null){
					//查询评论id是否存在
					dttime = tre_menberdoccommentService.findByHQL(" from Tre_menberdoccomment  where 1=1 and is_public=1 and id = '"+ fd_doccomment_id+"'");
					if(dttime!=null && dttime.size()>0){
						cf.setFd_doccomment_id(dttime.get(0).getId());
						cf.setFd_member_id(mem);
						cf.setFd_c_member_id(cmem);
						cf.setFd_comment_note(comments);
						cf.setFd_comment_time(new Date());
						t_comment_fidService.save(cf);
						Map map = new HashMap();
						if(null!=cmem.getRandroidios() && cmem.getRandroidios()==0 ){
							  //ios
							  JPushAllUtil.jSend_notification(cmem.getRegistrationid(),mem.getUsersname()+"评论了你的回复  ："+comments, map, 0);
						  }else if(null!=cmem.getRandroidios() && cmem.getRandroidios()==1){
								//安卓
							  JPushAllUtil.jSend_notification(cmem.getRegistrationid(),mem.getUsersname()+"评论了你的回复  ："+comments, map, 1);
						  }
					}else{
						jsonResult.setCode("505");
						jsonResult.setMessage("您将评论的内容以被删除");
						return ajaxJson(JSONObject.toJSONString(jsonResult), response);
					}
				
				}else{ 
					tre_menberdoccommentService.save(md);
					Map map = new HashMap();
					if(null!=doc.getMemberid().getRandroidios() && doc.getMemberid().getRandroidios()==0 ){
						  //ios
						  JPushAllUtil.jSend_notification(doc.getMemberid().getRegistrationid(),mem.getUsersname()+"评论了你的文件  "+doc.getDoctitle()+"："+comments, map, 0);
					  }else if(null!=doc.getMemberid().getRandroidios() && doc.getMemberid().getRandroidios()==1){
							//安卓
						  JPushAllUtil.jSend_notification(doc.getMemberid().getRegistrationid(),mem.getUsersname()+"评论了你的文件  "+doc.getDoctitle()+"："+comments, map, 1);
					  }
				}
				
				// 修改文件表评论数量
				if (null != doc) {
					doc.setFilecommentscount(doc.getFilecommentscount()+1);
					tb_docService.update(doc);
				}
				
				// 查询 会员等级积分来源表点赞数量
				 
				List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
				List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(doc.getMemberid().getLevelid().getId());
				 
				if (null!=discuss && discuss.size() > 0) {
					//评论人
					mem.setId(member_id);
					mem.setLevelintegral(mem.getLevelintegral()
							+ discuss.get(0).getGood());
					tb_memberService.update(mem);
					List<Tb_level> level = tb_levelService
							.listlevel(mem.getLevelintegral() + "");
					if (null != level) {
						mem.setLevelid(level.get(0));
					}
					tb_memberService.update(mem);
				}  
				if(null!=discuss2 && discuss2.size() > 0){
					//被评论人------------------------------------------------
					Tb_member mm=new Tb_member();
					mm.setId(doc.getMemberid().getId());
					mm.setLevelintegral(doc.getMemberid().getLevelintegral()
							+ discuss2.get(0).getGood());
					List<Tb_level> level = tb_levelService
							.listlevel(doc.getMemberid().getLevelintegral() + "");
					if (null != level) {
						mm.setLevelid(level.get(0));
					}
					tb_memberService.update(mm);
				}
				List item = new ArrayList();
				System.out.println(md.getId());
				if(dttime!=null && dttime.size()>0){
					List<T_comment_fid> dttimes = t_comment_fidService.findByHQL(" from T_comment_fid  where 1=1  and id = "+ cf.getId()+"  order by fd_comment_time desc ");
					jsonResult.setResult(T_comment_fidUtil.getControllerList(dttimes));
					jsonResult.setCode("200");
					jsonResult.setMessage("执行成功！");
				
				}else{
					List<Tre_menberdoccomment> dttimes = tre_menberdoccommentService.findByHQL(" from Tre_menberdoccomment  where 1=1 and is_public=1 and id = "+ md.getId()+"  order by comment_time desc ");
					for (Tre_menberdoccomment obj : dttimes) {
						Map<Object, Object> map = new HashMap<Object, Object>();
						map.put("id", obj.getId());
						map.put("fid", obj.getFid());
						map.put("comments", obj.getComments());
						if (obj.getDocid() != null) {
							map.put("docid", obj.getDocid().getId());
							map.put("doctitle", obj.getDocid().getDoctitle());
						} else {
							map.put("docid", "");
							map.put("doctitle", "");
						}
						if (obj.getEdit_time() != null) {
							map.put("edit_time", obj.getEdit_time());
						} else {
							map.put("edit_time", "");
						}
						if (obj.getMember_id() != null) {
							map.put("member_id", obj.getMember_id().getId());
							map.put("usersname", obj.getMember_id().getUsersname());
							map.put("headimg", obj.getMember_id().getHeadimg());
						} else {
							map.put("usersname", "");
							map.put("member_id", "");
							map.put("headimg", "");
						}
						if (obj.getComment_time() != null) {
							map.put("comment_time", obj.getComment_time());
						} else {
							map.put("comment_time", "");
						}
						if (obj.getEdit_userid() != null) {
							map.put("edit_userid", obj.getEdit_userid().getFdUserName());
						} else {
							map.put("edit_userid", "");
						}
						if (obj.getIs_public() != null) {
							map.put("is_public", obj.getIs_public());
						} else {
							map.put("is_public", "");
						}
						  
						item.add(map);
					}
					jsonResult.setResult(item);
					jsonResult.setCode("200");
					jsonResult.setMessage("执行成功！");
				}
				
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
	/**
	 * 返回分页 tre_menberdoccomment
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/menberdoccomment/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tre_menberdoccomment> list=null;
			StringBuffer str = new StringBuffer();
			str.append(" where 1=1 and is_public=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("docid.id"))) {
				str.append("and docid = '" + request.getParameter("docid.id")+"' " );
			}
			QueryResult<Tre_menberdoccomment> result =
					tre_menberdoccommentService.getPage(pageBean, str.toString(), "Tre_menberdoccomment", "id","order by comment_time desc ", Tre_menberdoccomment.class);//tre_menberdoccommentService.list(pageBean,request);
			list = result.getResultList();
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
			map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
			map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
			item.add(map1);
			
			for (Tre_menberdoccomment obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj.getId());
				map.put("fid", obj.getFid());
				map.put("comments", obj.getComments());
				//map.put("member_id", obj.getMember_id());
				 if(obj.getMember_id()!=null){
					 map.put("memberid", obj.getMember_id().getId());
					 map.put("usersname", obj.getMember_id().getUsersname());
					 if(obj.getMember_id().getHeadimg()!=null){
						 map.put("headimg", obj.getMember_id().getHeadimg());
					 }else{
						 map.put("headimg","");
					 }
					 if(obj.getMember_id().getLevelid()!=null){
						 map.put("levelid", obj.getMember_id().getLevelid().getId());
						 map.put("levelname", obj.getMember_id().getLevelid().getLevelname());
					 }else{
						 map.put("levelid", "");
						 map.put("levelname", "");
					 }
				 }else{
					 map.put("memberid", "");
					 map.put("usersname","");
					 map.put("headimg","");
					 map.put("levelid", "");
					 map.put("levelname", "");
				 }
				if (obj.getDocid() != null) {
					map.put("docid", obj.getDocid().getId());
					map.put("doctitle", obj.getDocid().getDoctitle());
					map.put("filegoodcount", obj.getDocid().getFilegoodcount());
					map.put("filekeepcount", obj.getDocid().getFilekeepcount());
					map.put("filepaycount", obj.getDocid().getFilepaycount());
					map.put("fileviewcount", obj.getDocid().getFileviewcount());
					map.put("filecommentscount", obj.getDocid()
							.getFilecommentscount());
					map.put("filepath", obj.getDocid().getFilepath());
				} else {
					map.put("docid", "");
					map.put("doctitle", "");
					map.put("filegoodcount", "");
					map.put("filekeepcount", "");
					map.put("filepaycount", "");
					map.put("fileviewcount", "");
					map.put("filecommentscount", "");
					map.put("filepath", "");
				}
				if (obj.getEdit_time() != null) {
					map.put("edit_time", obj.getEdit_time());
				} else {
					map.put("edit_time", "");
				}
				if (obj.getComment_time() != null) {
					map.put("comment_time", obj.getComment_time());
				} else {
					map.put("comment_time", "");
				}
				if (obj.getEdit_userid() != null) {
					map.put("edit_userid", obj.getEdit_userid().getFdUserName());
				} else {
					map.put("edit_userid", "");
				}
				if (obj.getIs_public() != null) {
					map.put("is_public", obj.getIs_public());
				} else {
					map.put("is_public", "");
				}
				// 查询标签表
				List<Tre_docfilelabel> listdoclabel = tre_docfilelabelService
						.findByHQL("from Tre_docfilelabel where docid.id=? ", obj.getDocid().getId());
				map.put("listdoclabel", Tre_docfilelabelUtil.getControllerList(listdoclabel));
				
				List<T_comment_fid> listdoc = t_comment_fidService
						.findByHQL("from T_comment_fid where fd_doccomment_id=?  order by fd_comment_time desc ",
								obj.getId());
				 
				map.put("listdoc",T_comment_fidUtil.getControllerList(listdoc));
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
	
	
	/**
	 * 返回List列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/menberdoccomment/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tre_menberdoccomment> result = tre_menberdoccommentService.findAll();
			jsonResult.setResult(Tre_menberdoccommentUtil.getControllerList(result));
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
	@RequestMapping(value = "/menberdoccomment/selectKey")
	public String selectKey(Integer fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Tre_menberdoccomment result = tre_menberdoccommentService.findByPrimaryKey(fdId);
				jsonResult.setResult(Tre_menberdoccommentUtil.getControllerMap(result));
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
	 * 更新 tre_menberdoccomment
	 * 
	 * @param tre_menberdoccomment
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/menberdoccomment/update")
	public String update(Tre_menberdoccomment tre_menberdoccomment, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			tre_menberdoccomment.setEdit_time(new Date());
			tre_menberdoccomment.setEdit_userid(UserUtil.getSession(request));
			tre_menberdoccommentService.update(tre_menberdoccomment);
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
	@RequestMapping(value = "/menberdoccomment/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				for (String stringfid : ids) {
					Integer fids=Integer.parseInt(stringfid);
					Tre_menberdoccomment m=tre_menberdoccommentService.findByPrimaryKey(fids);
					if(m!=null){
						Tb_doc doc=tb_docService.findByPrimaryKey(m.getDocid().getId());
						doc.setFilecommentscount(doc.getFilecommentscount()-1);
						tb_docService.update(doc);
					
					List<T_comment_fid> fid=t_comment_fidService.findByHQL(" from T_comment_fid  where 1=1  and fd_doccomment_id = "+ fids);
				   if(fid!=null && fid.size()>0){
					   for (T_comment_fid t_comment_fid : fid) {
						   t_comment_fidService.remove(t_comment_fid.getId());
								doc.setFilecommentscount(doc.getFilecommentscount()-1);
								tb_docService.update(doc);
							 
					}
				   }
					  
				   }
				}
				tre_menberdoccommentService.delete(ids);
				
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
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
	/**
	 * 删除子评论
	 * 
	 * @param ids
	 *            数组
	 * @return json
	 */
	@RequestMapping(value = "/t_comment_fid/delete_fid")
	public String deletefid(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				for (String stringfid : ids) {
					Integer fids=Integer.parseInt(stringfid);
					T_comment_fid f=t_comment_fidService.findByPrimaryKey(fids);
					Tre_menberdoccomment m=tre_menberdoccommentService.findByPrimaryKey(f.getFd_doccomment_id());
					if(m!=null){
						Tb_doc doc = tb_docService.findByPrimaryKey(m.getDocid().getId());
						doc.setFilecommentscount(doc.getFilecommentscount() - 1);
						tb_docService.update(doc);
					}
				}
				t_comment_fidService.delete(ids);
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
	/**
	 * 查询是否可以留言
	 */
	@RequestMapping(value = "/membermessagesteup/listAllisok")
	public String listAllisok(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Tb_membermessagesteup> result = tb_membermessagesteupService.findAll();
			jsonResult.setResult(Tb_membermessagesteupUtil.getControllerList(result));
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
	 * author jinhao
	 * @param docid
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWorksComment")
	public String getWorksComment(String docid,String page, HttpServletResponse response, HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		try
		{	//页码问题
			int p = Integer.parseInt(page);
			int startRow = 15*(p-1)+1;
			int endRow = 15*p;
			Map<String,Object> result = new HashMap<>();
			result.put("doc_id", docid);
			List<Object[]> listComment = tre_menberdoccommentService.getAllByDocId(docid, startRow,endRow);
			List comment = new ArrayList();
			result.put("comment_num", listComment.size());
			for (int i =0; i<listComment.size();i++) {
				Map<String, Object> mapComment = new HashMap<>();
				Object[] obj = listComment.get(i);
				mapComment.put("user_id", obj[0].toString());
				mapComment.put("user_name", obj[1].toString());
				mapComment.put("portrait", obj[2].toString() + Constants.DOC_PATH_END1);

				mapComment.put("comment_context", URLDecoder.decode(obj[3].toString(),"utf-8"));	//读取后解码
				mapComment.put("comment_id",obj[4].toString());
				mapComment.put("comment_time",DateUtil.getDatePoor(new Date(),(Date)obj[5]));
				//二级评论
				List<Object[]> comment_fid = iT_comment_fidService.findByCommentId(Integer.parseInt(obj[4].toString()));
				List<Map<String, String>> levels = new ArrayList<>();
				for (int j= 0; j<comment_fid.size();j++) {
					Map<String, String> temp = new HashMap<>();
					Object[] o = comment_fid.get(j);
					temp.put("auth_id1", o[0].toString());
					temp.put("auth_id2", o[1].toString());
					temp.put("auth_name1", o[2].toString());
					temp.put("auth_name2", o[3].toString());
					temp.put("content", URLDecoder.decode(o[4].toString(),"utf-8"));
					temp.put("comment_time",DateUtil.getDatePoor(new Date(),(Date)o[5]));
					levels.add(temp);
				}
				mapComment.put("level", levels);
				comment.add(mapComment);
			}
			result.put("comment", comment);

			jsonResult.setData(result);
			jsonResult.setCode("0");
			jsonResult.setMsg("success");
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("执行错误！");
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 用户评论操作
	 * @param docId
	 * @param comment
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/session/comment")
	public String comment(String docId ,String comment, HttpServletResponse response, HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();

		String access_token = WeixinSign.getAccess_token();
		Boolean isSafe = SenInfoCheckUtil.cotentFilter(access_token, comment);
		if (isSafe == false){
			jsonResult.setCode("3");
			jsonResult.setMsg("评论含有违法违规内容");
			return ajaxJson(JSONObject.toJSONString(jsonResult), response);
		}

		try {
			String memberId = getMemberId(request,response);
			if(memberId != null) {
				List<Object[]> mems = tb_memberService.getMemLevel(memberId);
				List<Object[]> docs = tb_docService.getDocMemLevel(docId);
				Object[] mem = mems.get(0);
				Object[] mem1 = docs.get(0);
				Tb_member member = new Tb_member();	//评论人
				member.setId(memberId);
				Tb_doc doc = new Tb_doc();	//被评论的作品
				doc.setId(docId);
				Tre_menberdoccomment comment1 = new Tre_menberdoccomment();
//				int id = tre_menberdoccommentService.getmaxId();
//				comment1.setId(id+1);
//				String com = FilterEmoji.filterEmoji(comment," ");
				String com = URLEncoder.encode(comment,"utf-8"); //保存前编码
				comment1.setComments(com);
				comment1.setIs_public("1");
				comment1.setComment_time(DateUtil.getDate());
				comment1.setMember_id(member);
				comment1.setDocid(doc);
				Integer result = tre_menberdoccommentService.addComment(comment1,mem,mem1,docId,memberId);
				jsonResult.setData(result);
				jsonResult.setCode("0");
				jsonResult.setMsg("success");
			}else{
				jsonResult.setCode("-1");
				jsonResult.setMsg("请先登录!");
			}
		} catch (Exception e) {
			jsonResult.setCode("1");
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 二级评论
	 * @param userId
	 * @param commentId
	 * @param comment
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/userComment")
	public String userComment(String userId,String commentId ,String comment,HttpServletResponse response, HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();

		String access_token = WeixinSign.getAccess_token();
		Boolean isSafe = SenInfoCheckUtil.cotentFilter(access_token, comment);
		if (isSafe == false){
			jsonResult.setCode("3");
			jsonResult.setMsg("内容含有违法违规内容");
			return ajaxJson(JSONObject.toJSONString(jsonResult), response);
		}

		try {
			String memberId = getMemberId(request,response);
			if(memberId != null) {
                T_comment_fid com_two = new T_comment_fid();
                Tb_member mem = new Tb_member();
                mem.setId(memberId);
                Tb_member mem2 = new Tb_member();
                mem2.setId(userId);
                com_two.setFd_doccomment_id(Integer.parseInt(commentId));
//                String com = FilterEmoji.filterEmoji(comment," ");
				String com = URLEncoder.encode(comment,"utf-8"); //保存前编码
                com_two.setFd_comment_note(com);
                com_two.setFd_comment_time(DateUtil.getDate());
                com_two.setFd_c_member_id(mem2);
                com_two.setFd_member_id(mem);
//				int id = iT_comment_fidService.getMaxId() + 1;
//				com_two.setId(id);
                iT_comment_fidService.save(com_two);
//				iT_comment_fidService.sqlSave(Integer.parseInt(commentId),memberId,userId,comment);
				jsonResult.setCode("0");
				jsonResult.setMsg("success");
			}else{
				jsonResult.setCode("-1");
				jsonResult.setMsg("请先登录!");
			}
			} catch (Exception e) {
			jsonResult.setCode("1");
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
}
