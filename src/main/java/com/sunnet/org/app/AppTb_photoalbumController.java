package com.sunnet.org.app;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.Tb_photoalbum;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.information.service.ITb_photoalbumService;
import com.sunnet.org.information.vo.Tb_photoalbumUtil;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_membermessagesteup;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_membermessagesteupService;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/***
 * 影像管理
 *
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_photoalbumController extends BaseController {

	@Autowired
	private ITb_photoalbumService tb_photoalbumService;

	@Autowired
	private ITb_membermessagesteupService tb_membermessagesteupService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITb_memberService iTb_memberService;

	/**
	 * author jinhao
	 * 获取云库相册列表
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/getCloudBaseList")
	public String getCloudBaseList(HttpServletRequest request, HttpServletResponse response){
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			if(null != memberId) {
				//获取用户上传的所有作品
				List<Object[]> docs = tb_docService.findBySql("SELECT id,Filetype,Ispublic,Isdelete,Isparticipating FROM TB_Doc WHERE DocStatus =1 and memberid =?", memberId);
				Map sys = new HashMap();    //系统相册
				//相册照片数量
				int count1 = 0;
				int count2 = 0;
				int count3 = 0;
				int count4 = 0;
				int count5 = 0;
				int count6 = 0;
				if (null != docs || docs.size()>0) {
					for (int i = 0; i < docs.size(); i++) {
						Object[] o = docs.get(i);
						if ("0".equals(o[1].toString()) && "0".equals(o[3].toString()))
							count1++;
						if ("1".equals(o[4].toString()) && "0".equals(o[1].toString()) && "0".equals(o[3].toString()))
							count2++;
						if ("1".equals(o[1].toString()) && "0".equals(o[3].toString()))
							count3++;
						if ("1".equals(o[2].toString()) && "0".equals(o[1].toString()) && "0".equals(o[3].toString()))
							count4++;
						if ("0".equals(o[2].toString()) && "0".equals(o[1].toString()) && "0".equals(o[3].toString()))
							count5++;
						if ("1".equals(o[3].toString()))
							count6++;
					}
				}
				Map map1 = new HashMap();
				map1.put("album_id", Constants.PHOTOALBUM_ALL);
				map1.put("album_name", "全部");
				map1.put("img_num", count1);
				Map map2 = new HashMap();
				map2.put("album_id", Constants.PHOTOALBUM_CON);
				map2.put("album_name", "参赛");
				map2.put("img_num", count2);
				Map map3 = new HashMap();
				map3.put("album_id", Constants.PHOTOALBUM_VIDEO);
				map3.put("album_name", "视频");
				map3.put("img_num", count3);
				Map map4 = new HashMap();
				map4.put("album_id", Constants.PHOTOALBUM_PUBLIC);
				map4.put("album_name", "公开");
				map4.put("img_num", count4);
				Map map5 = new HashMap();
				map5.put("album_id", Constants.PHOTOALBUM_NOPUBLIC);
				map5.put("album_name", "不公开");
				map5.put("img_num", count5);
				Map map6 = new HashMap();
				map6.put("album_id", Constants.PHOTOALBUM_DELETE);
				map6.put("album_name", "回收站");
				map6.put("img_num", count6);
				sys.put("all",map1);
				sys.put("match",map2);
				sys.put("video",map3);
				sys.put("public",map4);
				sys.put("nopublic",map5);
				sys.put("delete",map6);
				Map result = new HashMap();
				List<Object[]> mem = iTb_memberService.findBySql("SELECT Capacity,RemainingCapacity FROM Tb_member WHERE id =?",memberId);

				List<Map> per = new ArrayList<>();    //个人相册
				List<Object[]> photoalbums = tb_photoalbumService.findBySql("SELECT p.id,p.PhotoAlbumName,d.FilePath FROM TB_PhotoAlbum AS p LEFT JOIN TB_Doc AS d ON d.id = p.frontcover_docid WHERE p.memberid =?", memberId);
				for (int i = 0; i < photoalbums.size(); i++) {
					Map mapp = new HashMap();
					int temp =  tb_docService.getAllNum("select count(*) from Tb_doc where DocStatus =1 and memberid = ? and photoalbumid = ? and isdelete = 0 and filetype = 0",memberId,photoalbums.get(i)[0]);
					mapp.put("album_id", photoalbums.get(i)[0]);
					mapp.put("album_name", photoalbums.get(i)[1].toString());
					if (null != photoalbums.get(i)[2]) {
						mapp.put("background_img", photoalbums.get(i)[2].toString() + Constants.DOC_PATH_END);
					}else
						mapp.put("background_img", "");
					mapp.put("img_num", temp);
					per.add(mapp);
				}
				result.put("system_album", sys);
				result.put("personal", per);
				result.put("total_capacity", mem.get(0)[0].toString() + "G");
				result.put("surplus_capacity", mem.get(0)[1].toString().substring(0, mem.get(0)[1].toString().indexOf(".") + 2) + "G");

				jsonResult.setData(result);
				jsonResult.setMsg("success");
				jsonResult.setCode(Constants.SUCCESS_CODE);
			}else {
				jsonResult.setMsg("请先登录");
				jsonResult.setCode("-1");
			}
		} catch (Exception e) {
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 创建相册
	 * @param albumName
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/session/addphotoAlbum")
	public String addphotoAlbum(String albumName, HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			if(null != memberId) {
				String sql = " from Tb_photoalbum where memberid='"
						+ memberId + "' and photoalbumname = '"
						+ albumName + "' ";
				List<Tb_photoalbum> result = tb_photoalbumService.findByHQL(sql);
				// 根据上传时间排序
				if (result != null && result.size() > 0) {
					jsonResult.setCode("100");
					jsonResult.setMsg("该相册名称已存在，请重新输入");
				} else {
					//查询会员留言设置
					String comments = albumName;
					List<Tb_membermessagesteup> liuyan = tb_membermessagesteupService.findAll();
					if (liuyan != null && liuyan.size() > 0) {
						String[] jinyong = liuyan.get(0).getDisablewords().split("[,，]");
						boolean state = false;
						for (String s : jinyong) {
							if (albumName.equals(s)) {
								state = true;
								break;
							}
						}
						if (state == true) {
							jsonResult.setCode("110");
							jsonResult.setMsg("相册名称包含非法字段，请重新输入");
						} else {
							//Integer id = tb_photoalbumService.getMaxId(Tb_photoalbum.class) + 1;
							Tb_photoalbum photoalbum = new Tb_photoalbum();
							photoalbum.setMemberid(memberId);
							photoalbum.setPhotoalbumname(albumName);
							photoalbum.setCreatetime(DateUtil.getDate());
							tb_photoalbumService.save(photoalbum);
							Map map = new HashMap();
							map.put("id",photoalbum.getId());
							map.put("albumName",albumName);
							jsonResult.setData(map);
							//jsonResult.setData(id);
							jsonResult.setCode(Constants.SUCCESS_CODE);
							jsonResult.setMsg("success");
						}
					}
				}
			}else {
				jsonResult.setMsg("请先登录");
				jsonResult.setCode("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 获取相册作品列表
	 * @param albumId
	 * @param page
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/getCloudBaseWorksList")
	public String getCloudBaseWorksList(String albumId , String page, HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			Integer p = Integer.parseInt(page);
			int startRow = 20*(p-1)+1;
			int endRow = 20*p;
			List<Object[]> docs = null;
			if(null != memberId) {
				switch (Integer.parseInt(albumId)){
					case -1:
					    docs = tb_docService.findBySql("SELECT * FROM (select id,Doctitle,Filegoodcount,Fileviewcount,Filecommentscount,Filekeepcount,Filepaycount,Filetype,Phonethumbnailpathimg,Filepath,ROW_NUMBER() over(order by UploadTime desc)T from TB_Doc WHERE DocStatus =1 and memberid=? and filetype = 0 and isdelete = 0)TT WHERE TT.T between ? and ?",memberId,startRow,endRow);
						break;
					case -2:
						docs = tb_docService.findBySql("SELECT * FROM (select id,Doctitle,Filegoodcount,Fileviewcount,Filecommentscount,Filekeepcount,Filepaycount,Filetype,Phonethumbnailpathimg,Filepath,ROW_NUMBER() over(order by UploadTime desc)T from TB_Doc WHERE DocStatus =1 and memberid=? and isparticipating = 1 and filetype = 0 and isdelete = 0)TT WHERE TT.T between ? and ?",memberId,startRow,endRow);
						break;
					case -3:
						docs = tb_docService.findBySql("SELECT * FROM (select id,Doctitle,Filegoodcount,Fileviewcount,Filecommentscount,Filekeepcount,Filepaycount,Filetype,Phonethumbnailpathimg,Filepath,ROW_NUMBER() over(order by UploadTime desc)T from TB_Doc WHERE DocStatus =1 and memberid=?  and isdelete = 0 and filetype = 1 )TT WHERE TT.T between ? and ?",memberId,startRow,endRow);
						break;
					case -4:
						docs = tb_docService.findBySql("SELECT * FROM (select id,Doctitle,Filegoodcount,Fileviewcount,Filecommentscount,Filekeepcount,Filepaycount,Filetype,Phonethumbnailpathimg,Filepath,ROW_NUMBER() over(order by UploadTime desc)T from TB_Doc WHERE DocStatus =1 and memberid=?  and isdelete = 0 and ispublic = 1 and filetype = 0)TT WHERE TT.T between ? and ?",memberId,startRow,endRow);
						break;
					case -5:
						docs = tb_docService.findBySql("SELECT * FROM (select id,Doctitle,Filegoodcount,Fileviewcount,Filecommentscount,Filekeepcount,Filepaycount,Filetype,Phonethumbnailpathimg,Filepath,ROW_NUMBER() over(order by UploadTime desc)T from TB_Doc WHERE DocStatus =1 and memberid=?  and isdelete = 0 and ispublic = 0 and filetype = 0)TT WHERE TT.T between ? and ?",memberId,startRow,endRow);
						break;
					case -6:
						docs = tb_docService.findBySql("SELECT * FROM (select id,Doctitle,Filegoodcount,Fileviewcount,Filecommentscount,Filekeepcount,Filepaycount,Filetype,Phonethumbnailpathimg,Filepath,ROW_NUMBER() over(order by UploadTime desc)T from TB_Doc WHERE DocStatus =1 and memberid=?  and isdelete = 1)TT WHERE TT.T between ? and ?",memberId,startRow,endRow);
						break;
					default:
						docs = tb_docService.findBySql("SELECT * FROM (select id,Doctitle,Filegoodcount,Fileviewcount,Filecommentscount,Filekeepcount,Filepaycount,Filetype,Phonethumbnailpathimg,Filepath,ROW_NUMBER() over(order by UploadTime desc)T from TB_Doc WHERE DocStatus =1 and memberid=?  and isdelete = 0 and Photoalbumid =?)TT WHERE TT.T between ? and ?",memberId,Integer.parseInt(albumId),startRow,endRow);
						break;
				}
				Map result = new HashMap();
				result.put("img_count", docs.size());
				List<Map> data = new ArrayList<>();
				if (docs.size()>0 && docs.get(0).length>0) {
					for (int i=0; i<docs.size(); i++) {
						Map map = new HashMap();
						Object[] obj = docs.get(i);
						map.put("doc_id", obj[0].toString());
						if (null != obj[1])
						    map.put("doc_title", obj[1].toString());
						else
                            map.put("doc_title","");
						map.put("good_num", obj[2].toString());
						map.put("view_num", obj[3].toString());
						map.put("comment_num", obj[4].toString());
						map.put("collect_num", obj[5].toString());
						map.put("reward_num", obj[6].toString());
						if ("1".equals(obj[7].toString()))
							map.put("image", obj[8].toString() + Constants.DOC_PATH_END);
						if ("0".equals(obj[7].toString()))
							map.put("image", obj[9].toString() + Constants.DOC_PATH_END);
						data.add(map);
					}
				}
				result.put("data", data);

				jsonResult.setData(result);
				jsonResult.setCode(Constants.SUCCESS_CODE);
				jsonResult.setMsg("success");
			}else {
				jsonResult.setMsg("请先登录");
				jsonResult.setCode("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 删除图片
	 * @param imgId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/delImg")
	public String delImg(String imgId,HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			if(null != memberId) {
				Tb_doc doc = tb_docService.getByKey(imgId);
				if (null != doc) {
					//是否参赛
					if(doc.getIsparticipating() == null || doc.getIsparticipating() ==0) {
						doc.setIsdelete(1);
						tb_docService.update(doc);
						jsonResult.setCode(Constants.SUCCESS_CODE);
						jsonResult.setMsg("success");
					}else{
						jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
						jsonResult.setMsg("失败，作品已参赛！");
					}
				} else {
					jsonResult.setCode("-1");
					jsonResult.setMsg("作品不存在！");
				}
			}else {
				jsonResult.setMsg("请先登录");
				jsonResult.setCode("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 删除相册
	 * @param albumId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/delPhotoAlbum")
	public String delPhotoAlbum(String albumId,HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			if(null != memberId) {
				List<Tb_photoalbum> photoalbums = tb_photoalbumService.findByHQL("from Tb_photoalbum where id = ?", Integer.parseInt(albumId));
				if (null != photoalbums && photoalbums.size() > 0) {
					Integer id = photoalbums.get(0).getId();
					tb_photoalbumService.delete(new Object[]{id});	//删除相册

					List<Tb_doc> docs = tb_docService.findByHQL("from Tb_doc where photoalbumid = ? and memberid.id = ? order by uploadtime desc",photoalbums.get(0),memberId);
					for (Tb_doc d : docs) {
						Tb_photoalbum p = new Tb_photoalbum();
						p.setId(1);
						d.setPhotoalbumid(p);
						tb_docService.update(d);
					}
					jsonResult.setCode(Constants.SUCCESS_CODE);
					jsonResult.setMsg(Constants.SUCCESS_DATA);
				} else {
					jsonResult.setCode(Constants.DATA_NOTEXIST);
					jsonResult.setMsg("相册不存在");
				}
			}else {
				jsonResult.setMsg("请先登录");
				jsonResult.setCode("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}


	/**
	 * author jinhao
	 * 设置相册封面
	 * @param albumId
	 * @param imgId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/setalbum")
	public String setalbum(String albumId, String imgId,HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			if(null != memberId) {
				List<Tb_photoalbum> photoalbums = tb_photoalbumService.findByHQL("from Tb_photoalbum where id = ?", Integer.parseInt(albumId));
				if (null != photoalbums && photoalbums.size() > 0) {
					Tb_photoalbum photoalbum = photoalbums.get(0);
					Tb_doc doc = tb_docService.getByKey(imgId);
					photoalbum.setDocid(doc);
					tb_photoalbumService.update(photoalbum);
					jsonResult.setCode(Constants.SUCCESS_CODE);
					jsonResult.setMsg(Constants.SUCCESS_DATA);
				} else {
					jsonResult.setCode(Constants.DATA_NOTEXIST);
					jsonResult.setMsg("相册不存在");
				}
			}else {
				jsonResult.setMsg("请先登录");
				jsonResult.setCode("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("fail");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 移动图片到相册
	 * @param albumId
	 * @param imgId
	 * @return
	 */
	@RequestMapping(value = "/session/moveAlbum")
	public String moveAlbum(String albumId , String imgId, HttpServletRequest request, HttpServletResponse response){
		JsonResult jsonResult = new JsonResult();
		try {
			String memberId = getMemberId(request,response);
			if(null != memberId) {
				String[] strs = imgId.split(",");
				List<Tb_doc> docs = tb_docService.getByManyIds(strs);
				List<Tb_photoalbum> photoalbums = tb_photoalbumService.findByHQL("from Tb_photoalbum where id = ?", Integer.parseInt(albumId));
				if (null != docs) {
					if (null != photoalbums && photoalbums.size() > 0) {
						for (Tb_doc d : docs) {
//							System.out.println("原来相册ID："+d.getPhotoalbumid().getId());
							d.setPhotoalbumid(photoalbums.get(0));
							System.out.println("新的相册ID："+d.getPhotoalbumid().getId());
							tb_docService.update(d);
						}
						jsonResult.setMsg(Constants.SUCCESS_DATA);
						jsonResult.setCode(Constants.SUCCESS_CODE);
					} else {
						jsonResult.setMsg("相册不存在！");
						jsonResult.setCode("-2");
					}
				} else {
					jsonResult.setMsg("图片不存在！");
					jsonResult.setCode("-1");
				}
			}else {
				jsonResult.setMsg("请先登录");
				jsonResult.setCode("-1");
			}
		} catch (Exception e) {
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
}
