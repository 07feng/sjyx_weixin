package com.sunnet.org.app.appbehavior;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.FilterEmoji;
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
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment_fid;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_commentService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_comment_fidService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_commentUtil;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_comment_fidUtil;
import com.sunnet.org.information.model.T_comment_fid;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.Tre_menberdoccomment;
import com.sunnet.org.information.vo.T_comment_fidUtil;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_levelintegralsource;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_membermessagesteup;
import com.sunnet.org.member.service.ITb_levelService;
import com.sunnet.org.member.service.ITb_levelintegralsourceService;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_membermessagesteupService;
import com.sunnet.org.member.service.ITre_docfilelabelService;
import com.sunnet.org.util.StringUtils;

/***
 * 影展评论
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppFilmfestivalvip_commentController extends BaseController
{

	@Autowired
	private IFilmfestivalvip_commentService filmfestivalvip_commentService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private ITb_levelintegralsourceService tb_levelintegralsourceService;
	@Autowired
	private ITb_levelService tb_levelService;
	@Autowired
	private ITre_docfilelabelService tre_docfilelabelService;
	@Autowired
	private ITb_membermessagesteupService tb_membermessagesteupService;
	@Autowired
	private IFilmfestivalvip_comment_fidService filmfestivalvip_comment_fidService;
	@Autowired
	private IFilmfestivalvipService filmfestivalvipService;

	/**
	 * author jinhao
     * 影展评论列表
	 * @param worksShowId
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWorksShowComment")
	public String getWorksShowComment(String worksShowId,String page, HttpServletResponse response, HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		try
		{	//页码问题
			int p = Integer.parseInt(page);
			int startRow = 12*(p-1)+1;
			int endRow = 12*p;
			Map<String,Object> result = new HashMap<>();
			result.put("worksShowId", worksShowId);
			List<Object[]> listComment = filmfestivalvipService.getByVip_id(worksShowId, startRow,endRow);
			List comment = new ArrayList();
			result.put("comment_num", listComment.size());
			for (int i =0; i<listComment.size();i++) {
				Map<String, Object> mapComment = new HashMap<>();
				Object[] obj = listComment.get(i);
				mapComment.put("user_id", obj[0].toString());
				mapComment.put("user_name", obj[1].toString());
				mapComment.put("portrait", obj[2].toString() + Constants.DOC_PATH_END1);
				mapComment.put("comment_context", URLDecoder.decode(obj[3].toString(),"utf-8"));
				mapComment.put("comment_id",obj[4].toString());
				mapComment.put("comment_time",DateUtil.getDatePoor(new Date(),(Date)obj[5]));
				//二级评论
				List<Object[]> comment_fid = filmfestivalvipService.findByCommentId(Integer.parseInt(obj[4].toString()));
				List<Map<String, String>> levels = new ArrayList<>();
				for (int j= 0; j<comment_fid.size();j++) {
					Map<String, String> temp = new HashMap<>();
					Object[] o = comment_fid.get(j);
					temp.put("auth_id1", o[0].toString());
					temp.put("auth_id2", o[1].toString());
					temp.put("auth_name1", o[2].toString());
					temp.put("auth_name2", o[3].toString());
					temp.put("content", URLDecoder.decode(o[4].toString(),"utf-8"));
					temp.put("comment_time", DateUtil.getDatePoor(new Date(),(Date)o[5]));
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
     * @param worksShowId
     * @param comment
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/session/worksShowComment")
    public String worksShowComment(String worksShowId ,String comment, HttpServletResponse response, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request,response);
            if(memberId != null) {
                Integer vip_id = Integer.parseInt(worksShowId);
                List<Object[]> mems = tb_memberService.getMemLevel(memberId);
                List<Object[]> docs = filmfestivalvipService.getFilmMemLevel(vip_id);
                Object[] mem = mems.get(0);
                Object[] mem1 = docs.get(0);
                Tb_member member = new Tb_member();	//评论人
                member.setId(memberId);
                Filmfestivalvip ffv = new Filmfestivalvip();
                ffv.setId(vip_id);
                Filmfestivalvip_comment comment1 = new Filmfestivalvip_comment();
//				int id = filmfestivalvip_commentService.getMaxId(Filmfestivalvip_comment.class);
//				comment1.setId(id+1);
				String com = URLEncoder.encode(comment,"utf-8"); //保存前编码
                comment1.setComments(com);
                comment1.setIs_public("1");
                comment1.setComment_time(DateUtil.getDate());
                comment1.setMember_id(member);
                comment1.setVipid(ffv);
                Integer result = filmfestivalvipService.addComment(comment1,mem,mem1,vip_id,memberId);
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
	@RequestMapping(value = "/session/userShowComment")
	public String userShowComment(String userId,String commentId ,String comment,HttpServletResponse response, HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			if(memberId != null) {
				Filmfestivalvip_comment_fid com_two = new Filmfestivalvip_comment_fid();
				Tb_member mem = new Tb_member();
				mem.setId(memberId);
				Tb_member mem2 = new Tb_member();
				mem2.setId(userId);
				com_two.setFd_doccomment_id(Integer.parseInt(commentId));
				String com = URLEncoder.encode(comment,"utf-8"); //保存前编码
				com_two.setFd_comment_note(com);
				com_two.setFd_comment_time(DateUtil.getDate());
				com_two.setFd_c_member_id(mem2);
				com_two.setFd_member_id(mem);
//				int id = filmfestivalvip_comment_fidService.getMaxId(Filmfestivalvip_comment_fid.class) + 1;
//				com_two.setId(id);
				filmfestivalvip_comment_fidService.save(com_two);
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

	/**
	 * 影展点赞
	 * @param vipid
	 * @param comments
	 * @param member_id
	 * @param fd_c_member_id
	 * @param fd_doccomment_id
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/filmfestivalvipcomment/save")
	public String save(Integer vipid,String comments,String member_id,String fd_c_member_id, Integer fd_doccomment_id, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			String msg = StringUtils.isObject(
					new Object[] { vipid,comments,member_id},
					new String[] { "被评论影展不能为空","评论内容不能为空","用户不能为空" });
			if (!msg.equals("")) {
				jsonResult.setCode("504");
				jsonResult.setMessage(msg);
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}else{
				//查询会员留言敏感词设置
				List<Tb_membermessagesteup> liuyan=tb_membermessagesteupService.findAll();
				if(liuyan!=null && liuyan.size()>0){
					String []jinyong=liuyan.get(0).getDisablewords().split("[,，]");
					for(String s:jinyong)
					{
						comments=comments.replaceAll(s, liuyan.get(0).getReplacewords());
					}
				}
				Filmfestivalvip result = filmfestivalvipService.findByPrimaryKey(vipid);
				Tb_member mem=tb_memberService.findByPrimaryKey(member_id);
				List<Filmfestivalvip_comment> dttime =null;
				//评论子表
				Filmfestivalvip_comment_fid cf=new Filmfestivalvip_comment_fid();
				Tb_member cmem=null;
				if(null!=fd_c_member_id){
					 cmem=tb_memberService.findByPrimaryKey(fd_c_member_id);
				}            
				Filmfestivalvip_comment md= new Filmfestivalvip_comment();
				md.setVipid(result);
				md.setMember_id(mem);
				md.setComments(comments);
				md.setComment_time(new Date());
				md.setIs_public("1");
				if(fd_doccomment_id!=null){
					//查询评论id是否存在
					dttime = filmfestivalvip_commentService.findByHQL(" from Filmfestivalvip_comment  where 1=1 and is_public=1 and id = '"+ fd_doccomment_id+"'");
					if(dttime!=null && dttime.size()>0){
						cf.setFd_doccomment_id(dttime.get(0).getId());
						cf.setFd_member_id(mem);
						cf.setFd_c_member_id(cmem);
						cf.setFd_comment_note(comments);
						cf.setFd_comment_time(new Date());
						filmfestivalvip_comment_fidService.save(cf);
						Map map = new HashMap();
						if(null!=cmem.getRandroidios() && cmem.getRandroidios()==0 ){
							  //ios
							if(null!=result.getMember_id().getRegistrationid() && !"".equals(result.getMember_id().getRegistrationid())){
							  JPushAllUtil.jSend_notification(cmem.getRegistrationid(),mem.getUsersname()+"评论了你的回复  ："+comments, map, 0);
							}
							}else if(null!=cmem.getRandroidios() && cmem.getRandroidios()==1){
								//安卓
								if(null!=result.getMember_id().getRegistrationid() && !"".equals(result.getMember_id().getRegistrationid())){
							        JPushAllUtil.jSend_notification(cmem.getRegistrationid(),mem.getUsersname()+"评论了你的回复  ："+comments, map, 1);
						 
								}
							}
								
					}else{
						jsonResult.setCode("505");
						jsonResult.setMessage("您将评论的内容以被删除");
						return ajaxJson(JSONObject.toJSONString(jsonResult), response);
					}
				
				}else{ 
					filmfestivalvip_commentService.save(md);
					Map map = new HashMap();
					if(null!=result.getMember_id().getRandroidios() && result.getMember_id().getRandroidios()==0 ){
						  //ios
						if(null!=result.getMember_id().getRegistrationid() && !"".equals(result.getMember_id().getRegistrationid())){
							JPushAllUtil.jSend_notification(result.getMember_id().getRegistrationid(),mem.getUsersname()+"评论了你的文件  "+result.getTitel()+"："+comments, map, 0);
						}
					  }else if(null!=result.getMember_id().getRandroidios() && result.getMember_id().getRandroidios()==1){
							//安卓
						  if(null!=result.getMember_id().getRegistrationid() && !"".equals(result.getMember_id().getRegistrationid())){
						  JPushAllUtil.jSend_notification(result.getMember_id().getRegistrationid(),mem.getUsersname()+"评论了你的文件  "+result.getTitel()+"："+comments, map, 1);
						  }
					    }
				}
				// 修改影展表评论数量
				if (null != result) {
					if(null!=result.getFilecommentscount()){
						result.setFilecommentscount(result.getFilecommentscount()+1);
					}else{
						result.setFilecommentscount(1);
					}
					filmfestivalvipService.update(result);
				}
				// 查询 会员等级积分来源表点赞数量
				
				List<Tb_levelintegralsource> discuss = tb_levelintegralsourceService.listlevel(mem.getLevelid().getId());
				if (null!=discuss &&  discuss.size() > 0) {
					//评论人
					mem.setId(member_id);
					mem.setLevelintegral(mem.getLevelintegral()
							+ discuss.get(0).getGood());
					 
					List<Tb_level> level = tb_levelService
							.listlevel(mem.getLevelintegral() + "");
					if (null != level) {
						mem.setLevelid(level.get(0));
					}
					tb_memberService.update(mem);
				}  
				//被评论人------------------------------------------------
				List<Tb_levelintegralsource> discuss2 = tb_levelintegralsourceService.listlevel(result.getMember_id().getLevelid().getId());
				if (null!=discuss2 && discuss2.size() > 0) {
					//评论人
					Tb_member mm=new Tb_member();
					mm.setId(result.getMember_id().getId());
					mm.setLevelintegral(result.getMember_id().getLevelintegral()
							+ discuss2.get(0).getGood());
					List<Tb_level> level = tb_levelService
							.listlevel(result.getMember_id().getLevelintegral() + "");
					if (null != level) {
						mm.setLevelid(level.get(0));
					}
					tb_memberService.update(mm);
					
				}  
				
				
				
				List item = new ArrayList();
				System.out.println(md.getId());
				if(dttime!=null && dttime.size()>0){
					List<Filmfestivalvip_comment_fid> dttimes = filmfestivalvip_comment_fidService.findByHQL(" from Filmfestivalvip_comment_fid  where 1=1  and id = "+ cf.getId()+"  order by fd_comment_time desc ");
					jsonResult.setResult(Filmfestivalvip_comment_fidUtil.getControllerList(dttimes));
					jsonResult.setCode("200");
					jsonResult.setMessage("执行成功！");
				
				}else{
					List<Filmfestivalvip_comment> dttimes = filmfestivalvip_commentService.findByHQL(" from Filmfestivalvip_comment  where 1=1 and is_public=1 and id = "+ md.getId()+"  order by comment_time desc ");
					for (Filmfestivalvip_comment obj : dttimes) {
						Map<Object, Object> map = new HashMap<Object, Object>();
						map.put("id", obj.getId());
						map.put("fid", obj.getFid());
						if(obj.getVipid()!=null){
							map.put("vipid", obj.getVipid().getId());
							map.put("viptitel", obj.getVipid().getTitel());
						}else{
							map.put("vipid", "");
							map.put("viptitel","");
						}
						map.put("comments", obj.getComments());
						if (obj.getComment_time() != null) {
							map.put("comment_time", obj.getComment_time());
						} else {
							map.put("comment_time", "");
						}
						if (obj.getEdit_time() != null) {
							map.put("edit_time", obj.getEdit_time());
						} else {
							map.put("edit_time", "");
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
						if (obj.getMember_id() != null) {
							 map.put("memberid", obj.getMember_id().getId());
							 map.put("usersname", obj.getMember_id().getUsersname());
							 map.put("headimg", obj.getMember_id().getHeadimg());
						 }else{
							 map.put("memberid", "");
							 map.put("usersname","");
							 map.put("headimg", "");
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
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 返回分页 filmfestivalvip_comment
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvipcomment/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Filmfestivalvip_comment> list=null;
			StringBuffer str = new StringBuffer();
			str.append(" where 1=1  and is_public=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("vipid.id"))) {
				str.append("and vipid = '" + request.getParameter("vipid.id")+"' " );
			}
			QueryResult<Filmfestivalvip_comment> result =
					filmfestivalvip_commentService.getPage(pageBean, str.toString(), "Filmfestivalvip_comment", "id"," order by comment_time desc ", Filmfestivalvip_comment.class);
			list = result.getResultList();
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
			map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
			map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
			item.add(map1);
			for (Filmfestivalvip_comment obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj.getId());
				map.put("fid", obj.getFid());
				if(obj.getVipid()!=null){
					map.put("vipid", obj.getVipid().getId());
					map.put("viptitel", obj.getVipid().getTitel());
					map.put("filecommentscount", obj.getVipid().getFilecommentscount());
					map.put("filegoodcount", obj.getVipid().getFilegoodcount());
					map.put("filepaycount", obj.getVipid().getFilepaycount());
				}else{
					map.put("vipid", "");
					map.put("viptitel","");
					map.put("filecommentscount", "0");
					map.put("filegoodcount","0");
					map.put("filepaycount", "0");
				}
				map.put("comments", obj.getComments());
				if (obj.getComment_time() != null) {
					map.put("comment_time", obj.getComment_time());
				} else {
					map.put("comment_time", "");
				}
				if (obj.getEdit_time() != null) {
					map.put("edit_time", obj.getEdit_time());
				} else {
					map.put("edit_time", "");
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
				if (obj.getMember_id() != null) {
					 map.put("memberid", obj.getMember_id().getId());
					 map.put("usersname", obj.getMember_id().getUsersname());
					 map.put("headimg", obj.getMember_id().getHeadimg());
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
					 map.put("headimg", "");
					 map.put("levelid", "");
					 map.put("levelname", "");
				 }
				List<Filmfestivalvip_comment_fid> listdoc = filmfestivalvip_comment_fidService
						.findByHQL("from Filmfestivalvip_comment_fid where fd_doccomment_id=?  order by fd_comment_time desc ",
								obj.getId());
				
				map.put("listdoc",Filmfestivalvip_comment_fidUtil.getControllerList(listdoc));
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
	@RequestMapping(value = "/filmfestivalvipcomment/listAll")
	public String listAll(HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Filmfestivalvip_comment> result = filmfestivalvip_commentService.findAll();
			jsonResult.setResult(Filmfestivalvip_commentUtil.getControllerList(result));
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
	@RequestMapping(value = "/filmfestivalvipcomment/selectKey")
	public String selectKey(String fdId,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if(fdId != null && !(fdId.equals(""))){
				Filmfestivalvip_comment result = filmfestivalvip_commentService.findByPrimaryKey(fdId);
				jsonResult.setResult(Filmfestivalvip_commentUtil.getControllerMap(result));
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
	 * 更新 filmfestivalvip_comment
	 * 
	 * @param filmfestivalvip_comment
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/filmfestivalvipcomment/update")
	public String update(Filmfestivalvip_comment filmfestivalvip_comment, HttpServletResponse response, HttpServletRequest request )
	{
		JsonResult jsonResult = new JsonResult();
		try
		{	
			
			filmfestivalvip_commentService.update(filmfestivalvip_comment);
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
	@RequestMapping(value = "/filmfestivalvipcomment/delete")
	public String delete(String[] ids, HttpServletResponse response, HttpServletRequest request)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			if (ids != null && ids.length > 0)
			{
				for (String stringfid : ids) {
					Integer fids=Integer.parseInt(stringfid);
					Filmfestivalvip_comment m=filmfestivalvip_commentService.findByPrimaryKey(fids);
					if(m!=null){
						Filmfestivalvip doc=filmfestivalvipService.findByPrimaryKey(m.getVipid().getId());
						doc.setFilecommentscount(doc.getFilecommentscount()-1);
						filmfestivalvipService.update(doc);
					}
				}
				filmfestivalvip_commentService.delete(ids);
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
