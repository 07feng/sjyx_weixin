package com.sunnet.org.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.doc.service.ITb_docpayService;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.vie_Tb_DocInfo;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.information.vo.Tb_docUtil;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.member.service.ITre_docfilelabelService;
import com.sunnet.org.member.vo.Tre_docfilelabelUtil;
import com.sunnet.org.system.model.Tb_package;
import com.sunnet.org.system.model.Tb_siteconfig;
import com.sunnet.org.system.service.ITb_packageService;
import com.sunnet.org.system.service.ITb_siteconfigService;
import com.sunnet.org.system.vo.Tb_siteconfigUtil;

@Controller
@RequestMapping("/app")
public class AppMyController extends BaseController {
	@Autowired
	private ITb_docpayService tb_docpayService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITre_docfilelabelService tre_docfilelabelService;
	@Autowired
	private ITb_packageService tb_packageService;
	@Autowired
	private ITb_siteconfigService tb_siteconfigService;
	
	@RequestMapping(value = "/package/list")
	public String list(PageBean pageBean, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			QueryResult<Tb_package> result = tb_packageService.list(pageBean,request);
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
	 *我的荣誉（获奖）
	 * 
	 * @param memberid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/doc/selectContestdoc")
	public String selectContestdoc(String memberid,PageBean pagebean, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		 
		try {
			if (memberid != null && !(memberid.equals(""))) {
				List<Tb_doc> list = null;
				String str = " where  MemberId='"+memberid+"' ";
				QueryResult<Tb_doc> result = tb_docService.getPage(pagebean, str, "Vie_DocContest", "id", " ORDER BY uploadtime desc ",Tb_doc.class);
				 
				list = result.getResultList();
				List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
				for (Tb_doc obj : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", obj.getId());

					if (obj.getFid() != null) {
						map.put("fid", obj.getFid());
					} else {
						map.put("fid", "");
					}
					if (obj.getIsboutique() > 0) {
						map.put("isboutique", obj.getIsboutique());
					} else {
						map.put("isboutique", 0);
					}
					if (obj.getDoctitle() != null) {
						map.put("doctitle", obj.getDoctitle());
					} else {
						map.put("doctitle", "");
					}
					if (obj.getFiledescribe() != null) {
						map.put("filedescribe", obj.getFiledescribe());
					} else {
						map.put("filedescribe", "");
					}
					map.put("filetype", obj.getFiletype());
					map.put("uploadtime", obj.getUploadtime());
					map.put("shootingtime", obj.getShootingtime());
					map.put("publictime", obj.getPublictime());
					map.put("devicenumber", obj.getDevicenumber());
					map.put("iwidht", obj.getIwidht());
					map.put("iheight", obj.getIheight());
					map.put("exposuretime", obj.getExposuretime());
					map.put("exposureprogram", obj.getExposureprogram());
					map.put("exposurecompensation",
							obj.getExposurecompensation());
					map.put("exposuremodel", obj.getExposuremodel());
					map.put("isorate", obj.getIsorate());
					map.put("shutterspeed", obj.getShutterspeed());
					map.put("aperture", obj.getAperture());
					map.put("lensaperture", obj.getLensaperture());
					map.put("largestaperture", obj.getLargestaperture());
					map.put("flash", obj.getFlash());
					map.put("focallength", obj.getFocallength());
					map.put("whitebalance", obj.getWhitebalance());
					map.put("filelength", obj.getFilelength());
					map.put("gpslat", obj.getGpslat());
					map.put("gpslon", obj.getGpslon());
					map.put("pcthumbnailpathimg", obj.getPcthumbnailpathimg());
					map.put("phonethumbnailpathimg",
							obj.getPhonethumbnailpathimg());
					map.put("docstatus", obj.getDocstatus());
					map.put("padthumbnailpathimg", obj.getPadthumbnailpathimg());
					map.put("filepath", obj.getFilepath());
					map.put("filescore", obj.getFilescore());
					map.put("nsfw", obj.getNsfw());
					map.put("ispublic", obj.getIspublic());
					map.put("isdelete", obj.getIsdelete());
					map.put("isparticipating", obj.getIsparticipating());
					map.put("isportrait", obj.getIsportrait());
					map.put("isdouble", obj.getIsdouble());
					if(obj.getFileviewcount()>0){
						map.put("fileviewcount", obj.getFileviewcount());
					}else{
						map.put("fileviewcount", 0);
					}
					if(obj.getFilegoodcount()>0){
						map.put("filegoodcount", obj.getFilegoodcount());
					}else{
						map.put("filegoodcount", 0);
					}
					if(obj.getFilekeepcount()>0){
						map.put("filekeepcount", obj.getFilekeepcount());
					}else{
						map.put("filekeepcount", 0);
					}
					if(obj.getFilecommentscount()>0){
						map.put("filecommentscount", obj.getFilecommentscount());
					}else{
						map.put("filecommentscount", 0);
					}
					if(obj.getFilepaycount()>0){
						map.put("filepaycount", obj.getFilepaycount());
					}else{
						map.put("filepaycount", 0);
					}
					if(obj.getActityforwarcount()>0){
						map.put("actityforwarcount", obj.getActityforwarcount());
					}else{
						map.put("actityforwarcount", 0);
					} 
					map.put("storageformat", obj.getStorageformat());
				
					if (obj.getMemberid() != null) {
						map.put("memberid", obj.getMemberid().getId());
						map.put("usersname", obj.getMemberid().getUsersname());
						if (obj.getMemberid().getHeadimg() != null) {
							map.put("headimg", obj.getMemberid().getHeadimg());
						} else {
							map.put("headimg", "");
						}

						if (obj.getMemberid().getLevelid() != null) {
							map.put("levelid", obj.getMemberid().getLevelid()
									.getId());
							map.put("levelname", obj.getMemberid().getLevelid()
									.getLevelname());
						} else {
							map.put("levelid", "");
							map.put("levelname", "");
						}
					} else {
						map.put("memberid", "");
						map.put("usersname", "");
					}

					if (obj.getPhotoalbumid() != null) {
						map.put("photoalbumid", obj.getPhotoalbumid().getId());
						map.put("photoalbumname", obj.getPhotoalbumid()
								.getPhotoalbumname());
					} else {
						map.put("photoalbumid", "");
						map.put("photoalbumname", "");
					}

					if (obj.getFiletypeid() != null) {
						map.put("filetypeid", obj.getFiletypeid().getId());
						map.put("typeName", obj.getFiletypeid().getTypeName());
					} else {
						map.put("filetypeid", "");
						map.put("typeName", "");
					}
					// 查询标签表
					List<Tre_docfilelabel> listdoc = tre_docfilelabelService
							.findByHQL(
									"from Tre_docfilelabel where docid.id=? ",
									obj.getId());
					map.put("listdoc",
							Tre_docfilelabelUtil.getControllerList(listdoc));
					item.add(map);
				 
				}
				jsonResult.setResult(item);
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}

		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
	/**
	 * 我的（统计查询）
	 * 
	 * @param memberid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/doc/selectMemberid")
	public String selectTypeId(String memberid, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (memberid != null && !(memberid.equals(""))) {

				// 根据用户查询总浏览量
				String fileviewcount = "select SUM(fileviewcount) as fileviewcount from TB_Doc d where d.memberid='"
						+ memberid + "'";
				int fileviewcounts = tb_docService
						.findConutBySql(fileviewcount);
				// 根据用户查询被关注量
				String memberidcount = "select count(memberid) as memberidcount from tre_friendscircle f where f.circlememberid='"
						+ memberid + "'";
				int memberidcounts = tb_docService
						.findConutBySql(memberidcount);
				// 查询我关注的人
				String circlememberidcount = "select count(circlememberid) as circlememberidcount from tre_friendscircle f where f.memberid='"
						+ memberid + "'";
				int circlememberidcounts = tb_docService
						.findConutBySql(circlememberidcount);
				// 根据会员查询打赏人数
				List memberCount = tb_docpayService
						.findConutBySqllist("select tdp.memberid"
								+ " from Tb_DocPay tdp "
								+ "LEFT JOIN TB_Doc td on tdp.docid=td.id "
								+ "where td.memberid='" + memberid + "' "
								+ "GROUP BY tdp.memberid");
				map.put("fileviewcount", fileviewcounts);
				map.put("memberidcount", memberidcounts);
				map.put("circlememberidcount", circlememberidcounts);
				map.put("memberCount", null != memberCount ? memberCount.size()
						: 0);
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功!");
				jsonResult.setResult(map);
			} else {
				jsonResult.setCode("300");
				jsonResult.setMessage("获取出错!请检查memberid参数是否传入!");
			}
		} catch (Exception e) {
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 好友动态
	 * 
	 * @param memberid
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/doc/selectfrienddoc")
	public String selectfrienddoc(String memberid,PageBean pagebean, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		List<Tb_doc> list = null;
		
		try {
			if (memberid != null && !(memberid.equals(""))) {
			/*	String str = "select td.* from Tre_friendscircle f "
						+ "LEFT JOIN Tb_doc td on f.CircleMemberId=td.memberid "
						+ "where td.ispublic=0 and td.isdelete=0 and f.MemberId='" + memberid + "' "
						+ "ORDER BY td.uploadtime desc";
				List<Tb_doc> memberCount = tb_docService.findlistBySql(Tb_doc.class,str);*/
				QueryResult<Tb_doc> result = new QueryResult<Tb_doc>();
				result =tb_docService.getDoc(pagebean,memberid);
			    list=result.getResultList();
				
				List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pagebean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
				 
				if(list!=null && list.size()>0){
					for (Tb_doc obj : list) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", obj.getId());
						if (obj.getFid() != null) {
							map.put("fid", obj.getFid());
						} else {
							map.put("fid", "");
						}
						if (obj.getDoctitle() != null) {
							map.put("doctitle", obj.getDoctitle());
						} else {
							map.put("doctitle", "");
						}
						map.put("filetype", obj.getFiletype());
						map.put("uploadtime", obj.getUploadtime());
						map.put("devicenumber", obj.getDevicenumber());
						map.put("iwidht", obj.getIwidht());
						map.put("iheight", obj.getIheight());
						map.put("exposuretime", obj.getExposuretime());
						map.put("exposureprogram", obj.getExposureprogram());
						map.put("exposurecompensation",
								obj.getExposurecompensation());
						map.put("exposuremodel", obj.getExposuremodel());
						map.put("isorate", obj.getIsorate());
						map.put("shutterspeed", obj.getShutterspeed());
						map.put("aperture", obj.getAperture());
						map.put("lensaperture", obj.getLensaperture());
						map.put("largestaperture", obj.getLargestaperture());
						map.put("flash", obj.getFlash());
						map.put("focallength", obj.getFocallength());
						map.put("whitebalance", obj.getWhitebalance());
						map.put("filelength", obj.getFilelength());
						map.put("gpslat", obj.getGpslat());
						map.put("gpslon", obj.getGpslon());
						map.put("pcthumbnailpathimg", obj.getPcthumbnailpathimg());
						map.put("phonethumbnailpathimg",
								obj.getPhonethumbnailpathimg());
						map.put("docstatus", obj.getDocstatus());
						map.put("padthumbnailpathimg", obj.getPadthumbnailpathimg());
						map.put("filepath", obj.getFilepath());
						map.put("filescore", obj.getFilescore());
						map.put("nsfw", obj.getNsfw());
						map.put("ispublic", obj.getIspublic());
						map.put("isdelete", obj.getIsdelete());
						map.put("isparticipating", obj.getIsparticipating());
						map.put("isportrait", obj.getIsportrait());
						map.put("isdouble", obj.getIsdouble());
						map.put("fileviewcount", obj.getFileviewcount());
						map.put("filegoodcount", obj.getFilegoodcount());
						map.put("filekeepcount", obj.getFilekeepcount());
						map.put("filecommentscount", obj.getFilecommentscount());
						map.put("filepaycount", obj.getFilepaycount());
						map.put("storageformat", obj.getStorageformat());
						map.put("actityforwarcount", obj.getActityforwarcount());

						if (obj.getMemberid() != null) {
							map.put("memberid", obj.getMemberid().getId());
							map.put("usersname", obj.getMemberid().getUsersname());
							if (obj.getMemberid().getHeadimg() != null) {
								map.put("headimg", obj.getMemberid().getHeadimg());
							} else {
								map.put("headimg", "");
							}

							if (obj.getMemberid().getLevelid() != null) {
								map.put("levelid", obj.getMemberid().getLevelid()
										.getId());
								map.put("levelname", obj.getMemberid().getLevelid()
										.getLevelname());
							}
						} else {
							map.put("memberid", "");
						}
						if (obj.getPhotoalbumid() != null) {
							map.put("photoalbumid", obj.getPhotoalbumid().getId());
							map.put("photoalbumname", obj.getPhotoalbumid()
									.getPhotoalbumname());
						} else {
							map.put("photoalbumid", "");
						}

						if (obj.getFiletypeid() != null) {
							map.put("filetypeid", obj.getFiletypeid().getfId());
							map.put("typeName", obj.getFiletypeid().getTypeName());
						} else {
							map.put("filetypeid", "");
							map.put("typeName", "");
						}

						item.add(map);
						jsonResult.setResult(item);
						jsonResult.setCode("200");
						jsonResult.setMessage("执行成功！");
					}
				}else{
					jsonResult.setCode("201");
					jsonResult.setMessage("目前没有好友动态！");
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}

		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
	/**
	 * 是否允许上传
	 */
	@RequestMapping(value = "/siteconfig/listAllisok")
	public String listAllisok(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tb_siteconfig> result = tb_siteconfigService.findAll();
			jsonResult.setResult(Tb_siteconfigUtil.getControllerList(result));
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
