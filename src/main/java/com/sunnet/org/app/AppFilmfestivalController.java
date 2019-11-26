package com.sunnet.org.app;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunnet.framework.util.Util;
import com.sunnet.framework.util.VideoUtil;
import com.sunnet.org.app.entity.AddFilm;
import com.sunnet.org.app.oss.httpClientHelp;
import com.sunnet.org.app.oss.util.ResultValue;
import com.sunnet.org.cover.model.TbFilmfestivalvipCover;
import com.sunnet.org.cover.service.TbCoverMusicService;
import com.sunnet.org.cover.service.TbCoverService;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.information.model.Tb_photoalbum;
import com.sunnet.org.util.*;
import net.sf.json.JSONArray;
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
import org.apache.commons.lang.text.StrBuilder;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.sunnet.org.app.entity.Content;
import com.sunnet.org.app.entity.FilmfestivalIds;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.filmfestival.model.Filmfestivalvipopentime;
import com.sunnet.org.filmfestival.service.IFilmfestivalService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipopentimeService;
import com.sunnet.org.filmfestival.vo.FilmfestivalUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_membermessagesteup;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.service.ITb_membermessagesteupService;

/***
 * 个人影展作品
 * 
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppFilmfestivalController extends BaseController {

	@Autowired
	private IFilmfestivalService filmfestivalService;
	@Autowired
	private ITb_docService tb_docService;
	@Autowired
	private ITb_memberService tb_memberService;
	@Autowired
	private IFilmfestivalvipopentimeService filmfestivalvipopentimeService;
	@Autowired
	private IFilmfestivalvipService filmfestivalvipService;

	@Autowired
	private ITb_membermessagesteupService tb_membermessagesteupService;
	@Autowired
	private ITre_friendscircleService iTre_friendscircleService;

	@Autowired
    private TbCoverMusicService tbCoverMusicService;
	@Autowired
    private TbCoverService tbCoverService;



	/**
	 * 我的影展列表
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/worksShowList")
	public String myfestivalList(String code,String page,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String membetId = getMemberId(request,response);
			if (null != membetId) {
				Integer p = Integer.parseInt(page);    //页码
				int startRow = 20 * (p - 1) + 1;
				int endRow = 20 * p;
				if (null == code)
					code = "2";
				List<Object[]> result = filmfestivalService.festivalListForMine(membetId, startRow, endRow,code);
				List data = new ArrayList();
				if (result.size() > 0 && null != result.get(0)) {
					for (Object[] obj : result) {
						Map map = new HashMap();
						List<Object[]> list = filmfestivalService.festivalCover(obj[0].toString());
						if (list.size() > 0 && null != list.get(0)) {
							map.put("image", list.get(0)[1].toString() + Constants.DOC_PATH_END4);
//						map.put("iwidht", Integer.parseInt(list.get(0)[2].toString()));
//						map.put("iheight", Integer.parseInt(list.get(0)[3].toString()));
							map.put("worksShowTitle", URLDecoder.decode(obj[4].toString(),"utf-8"));
							map.put("worksShowId", Integer.parseInt(obj[0].toString()));
							if (null != obj[2])
								map.put("good_num", Integer.parseInt(obj[2].toString()));
							else
								map.put("good_num", 0);
//						if (null != obj[3])
//							map.put("commentCount", Integer.parseInt(obj[3].toString()));
//						else
//							map.put("commentCount", 0);
							if (null != obj[6])
								map.put("view_num", Integer.parseInt(obj[6].toString()));
							else
								map.put("view_num", 0);
							if ("0".equals(obj[5].toString()))
								map.put("status", 0);
							else
								map.put("status", 1);
							map.put("code",code);
							data.add(map);
						}
					}
				}
				jsonResult.setData(data);
				jsonResult.setCode(Constants.SUCCESS_CODE);
				jsonResult.setMsg(Constants.SUCCESS_DATA);
			}else {
				jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
				jsonResult.setMsg("请先登录");
			}
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 个人影展作品列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/worksShowData")
	public String worksShowData(String worksShowId,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try {
			Map data = new HashMap();
			List<Object[]> result = filmfestivalService.festivalDetail(worksShowId);
			Object[] o = result.get(0);
			data.put("works_id",worksShowId);
			data.put("title",URLDecoder.decode(o[0].toString(),"utf-8"));
			data.put("explain", URLDecoder.decode(o[13].toString(),"utf-8"));
			data.put("user_id",o[4].toString());
			data.put("user_name",o[5].toString());
//			data.put("HeadImg",o[6].toString());
			String date = DateUtil.DateToString((Date)o[1],"YYYY-MM-DD");
			data.put("time",date.toString());
			if (null != o[18])
				data.put("musicId",Integer.parseInt(o[18].toString()));
			else
				data.put("musicId",0);
			if (null != o[19])
				data.put("musicName",o[19].toString());
			else
				data.put("musicName","");
			if (null != o[20])
				data.put("musicPath",o[20].toString());
			else
				data.put("musicPath","");
            if (null == o[21]) {
                data.put("code", "1");
            }else {
                if ("1".equals(o[21].toString()))
                    data.put("code","2");
                else
                    data.put("code","1");
            }
            if (null != o[23]){
                data.put("coverurl",o[23].toString());
            }else {
                data.put("coverurl","http://image.91sjyx.com/sjyx/worksData/defaultCover.jpg");
            }
			List docs = new ArrayList();
			for (Object[] obj:result){
				Map map = new HashMap();
				map.put("id",obj[7].toString());
				map.put("image",obj[8].toString()+Constants.DOC_PATH_END);
				if (null != obj[9])
					map.put("desc",URLDecoder.decode(obj[9].toString(),"utf-8"));
				else
					map.put("desc","");
				map.put("iwidht",obj[10].toString());
				map.put("iheight",obj[11].toString());
				docs.add(map);
			}
			data.put("imgList",docs);
			jsonResult.setData(data);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}


	/**
	 * author jinhao
	 * 发布影展
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/worksShowMake")
	public String worksShowMake(String worksShowId,String code,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String memberId = getMemberId(request, response);
			filmfestivalvipService.publishShow(Integer.parseInt(worksShowId),Integer.parseInt(code));  //修改影展开通时间
//			jsonResult.setData(worksShowId);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * author jinhao
	 * 删除影展
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/worksShowDel")
	public String worksShowDel(String worksShowId,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
//			String memberId = getMemberId(request, response);
			List<Object[]> result = filmfestivalService.festivalDetail(worksShowId);    //影展作品列表信息
			filmfestivalvipService.delShow(result,worksShowId);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

    /**
     * 影展封面列表
     * @param response
     * @return
     */
	@RequestMapping(value = "/session/coverlist")
	public String coverlist(HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try {
            List<Object[]> result = tbCoverService.findBySql("select id,covertitle,coverurl from tb_filmfestivalvip_cover where issoldout = 0 order by sort desc");
            ArrayList data = new ArrayList();
            if (result.size()>0 && result.get(0).length>0){
                for (Object[] obj: result){
                    Map map = new HashMap();
                    map.put("id",obj[0].toString());
                    map.put("covertitle",obj[1].toString());
                    map.put("coverurl",obj[2].toString());
                    data.add(map);
                }
            }
            jsonResult.setData(data);
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
        }
        catch (Exception e)
        {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

	/**
	 * author jinhao
	 * 影展音乐列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/musicList")
	public String musicList(String code,String page,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Object[]> result = tbCoverMusicService.findBySql("select id,musicname,musicurl,imgurl from tb_filmfestivalvip_music where issoldout = 0 order by sort desc");    //音乐信息列表
			List data = new ArrayList();
			if (result.size()>0 && result.get(0).length>0){
//				System.out.println("2222222222222222");
				for (Object[] obj: result){
					Map map = new HashMap();
					map.put("id",obj[0].toString());
					map.put("musicname",obj[1].toString());
					map.put("musicurl",obj[2].toString());
					if (null != obj[3])
						map.put("imgurl",obj[3].toString());
					else
						map.put("imgurl","http://image.91sjyx.com/sjyx/worksData/defaultCover.jpg");
					data.add(map);
				}
			}
			jsonResult.setData(data);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 影展点赞用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/FilmGoodList")
	public String FilmGoodList(String worksShowId,String page,HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String memberId = getMemberId(request, response);
			int p = Integer.parseInt(page);
			int startRow = 16*(p-1)+1;
			int endRow = 16*p;
			List data = new ArrayList();
			List<Object[]> goodList = filmfestivalService.goodList(Integer.parseInt(worksShowId),startRow,endRow);
			for (int i = 0; i <goodList.size();i++){
				Map map = new HashMap();
				Object[] os = goodList.get(i);
				map.put("user_id", os[0].toString());
				map.put("user_name", os[1].toString());
				map.put("user_rank", os[2].toString());
				map.put("portrait", os[3].toString() + Constants.DOC_PATH_END1);
				map.put("isfollow", iTre_friendscircleService.idFucos(memberId, os[0].toString()));
				data.add(map);
			}
			jsonResult.setData(data);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 添加影展 从个人作品里面选择作品 保存影展
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/worksSave")
	public String worksSave(String code,String isPublic,String worksShowId,String musicId,String isChange,String imgList,String worksDesc,
							String worksTitle,String spotId, String coverurl, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();

        String access_token = WeixinSign.getAccess_token();
        Boolean titleSafe = SenInfoCheckUtil.cotentFilter(access_token, worksTitle);
        if (titleSafe == false){
            jsonResult.setCode("3");
            jsonResult.setMsg("作品标题含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        Boolean descSafe = SenInfoCheckUtil.cotentFilter(access_token, worksDesc);
        if (descSafe == false){
            jsonResult.setCode("4");
            jsonResult.setMsg("作品描述含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        Boolean listSafe = SenInfoCheckUtil.cotentFilter(access_token, imgList);
        if (listSafe == false){
            jsonResult.setCode("4");
            jsonResult.setMsg("作品介绍含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }

		try
		{
			if (null == spotId) //不是旅游景点文章
				spotId = "0";
			String memberId = getMemberId(request, response);
			Object[] res = tb_memberService.getMemInfo(memberId); //  用户，作者
			List<AddFilm> list= (List<AddFilm>)JSONArray.toList(JSONArray.fromObject(imgList), AddFilm.class);		//解析json数据
			if (null == code)
				code = "1";
			if ("0".equals(isChange))
				worksShowId = filmfestivalService.addFilm(null,res,list,worksDesc,worksTitle,memberId,-1,"",null,isPublic,musicId,code,spotId,coverurl);
			if ("1".equals(isChange)) {
				filmfestivalService.updateFilm(list,null,Integer.parseInt(worksShowId),-1,memberId,worksDesc,worksTitle,null,isPublic,musicId,code,coverurl);
			}
			Map data = new HashMap();
			data.put("worksShowId",worksShowId);
			jsonResult.setData(data);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

    /**
     * 上传图文封面
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/coverUpload")
	public String coverUpload(MultipartFile file,HttpServletRequest request,HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();

        String access_token = WeixinSign.getAccess_token();
        Boolean isSafe = SenInfoCheckUtil.imgFilter(access_token, file);
        if (isSafe == false){
            jsonResult.setCode("2");
            jsonResult.setMsg("图片不合法");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }

        try {
            String fileName = file.getOriginalFilename();	//文件原名
            OSSClientUtil oss = new OSSClientUtil();    //上传到阿里云
            String time = DateUtil.DateToString(DateUtil.getDate(),"YYYY-MM-dd")+"/";   //存储时间文件夹
            oss.setFOLDER(time);

            //上传作品到云库
            //生成随机文件名
            String fn = oss.fileName(fileName);
            String key = oss.uploadFile2OSS(file.getInputStream(),fn);
            if ("".equals(key)){
                jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                jsonResult.setMsg("上传服务器失败");
                return ajaxJson(JSONObject.toJSONString(jsonResult), response);
            }
            String coverurl = oss.getReplace()+ time + fn;
            Map data = new HashMap();
            data.put("coverurl",coverurl);
            jsonResult.setData(data);
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult),response);

    }

	/**
	 * 添加影展 附带上传作品  保存影展
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/worksShowUpload")
	public String worksShowUpload(String code,String isPublic,String isChange,String musicId,String count,String imgList,String worksDesc,String worksTitle,String worksShowId,
								  MultipartFile file,String spotId, String coverurl, HttpServletRequest request,HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();

		String access_token = WeixinSign.getAccess_token();
		Boolean isSafe = SenInfoCheckUtil.imgFilter(access_token, file);
		if (isSafe == false){
			jsonResult.setCode("2");
			jsonResult.setMsg("图片不合法");
			//修改的时候不删
			if(org.apache.commons.lang.StringUtils.isNotEmpty(worksShowId) && Integer.parseInt(isChange) != 1){
				List<Object[]> result = filmfestivalService.festivalDetail(worksShowId);    //影展作品列表信息
				filmfestivalvipService.delShow(result,worksShowId);
			}
			return ajaxJson(JSONObject.toJSONString(jsonResult), response);
		}
		Boolean titleSafe = SenInfoCheckUtil.cotentFilter(access_token, worksTitle);
		if (titleSafe == false){
			jsonResult.setCode("3");
			jsonResult.setMsg("作品标题含有违法违规内容");
			return ajaxJson(JSONObject.toJSONString(jsonResult), response);
		}
		Boolean descSafe = SenInfoCheckUtil.cotentFilter(access_token, worksDesc);
		if (descSafe == false){
			jsonResult.setCode("4");
			jsonResult.setMsg("作品描述含有违法违规内容");
			return ajaxJson(JSONObject.toJSONString(jsonResult), response);
		}
        Boolean listSafe = SenInfoCheckUtil.cotentFilter(access_token, imgList);
        if (listSafe == false){
            jsonResult.setCode("4");
            jsonResult.setMsg("作品介绍含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }

		try
		{
			if (null == spotId)
				spotId = "0";
			String memberId = getMemberId(request, response);
			Object[] res = tb_memberService.getMemInfo(memberId); //  用户，作者
			List<AddFilm> list= (List<AddFilm>)JSONArray.toList(JSONArray.fromObject(imgList), AddFilm.class);		//解析json数据
			Tb_doc tb_doc = new Tb_doc();   //上传作品
			Long len = file.getSize();  //文件大小
			BigDecimal space = null;

			//得到当前文件大小，单位GB	比较用户剩余存储容量
			BigDecimal fileLen = new BigDecimal(len);
			BigDecimal ls = new BigDecimal((1024 * 1024));
			fileLen = fileLen.divide(ls);
			ls = new BigDecimal(1024);
			fileLen = fileLen.divide(ls);//divide除
			//拿到剩余所有空间
			space = new BigDecimal(res[0].toString());
			if (space.compareTo(fileLen) == -1) {
				if ("1".equals(count)) {
					jsonResult.setCode("1");
					jsonResult.setMessage("您的空间容量不足！");
					return ajaxJson(JSONObject.toJSONString(jsonResult), response);
				}else {
					space = new BigDecimal(0);
				}
			}else {
				//计算剩余空间
				space = space.subtract(fileLen);//subtract减
			}

			String fileName = file.getOriginalFilename();	//文件原名
			OSSClientUtil oss = new OSSClientUtil();    //上传到阿里云
			String time = DateUtil.DateToString(DateUtil.getDate(),"YYYY-MM-dd")+"/";   //存储时间文件夹
			oss.setFOLDER(time);

			//上传作品到云库
			//生成随机文件名
			String fn = oss.fileName(fileName);
			String key = oss.uploadFile2OSS(file.getInputStream(),fn);
			if ("".equals(key)){
				jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
				jsonResult.setMsg("上传服务器失败");
				return ajaxJson(JSONObject.toJSONString(jsonResult), response);
			}
			//图片鉴黄
			String str=oss.getReplace()+time+fn;
//			String newStr=str.substring(str.length()-3);
//			String tags=null;
//			if(newStr!="GIF" && !"GIF".equals(newStr)){
//				JSONObject json=JSONObject.parseObject(AppAliyunImg.CheckImage(str));
//				tags= json.getJSONObject("result").getJSONObject("porn").get("body").toString();
//			}
//			if (!Util.isnull(tags)) {
//				if (tags.indexOf("色情") != -1)
//					tb_doc.setDocstatus(0);
//				else
//					tb_doc.setDocstatus(1);
//			} else
//				tb_doc.setDocstatus(0);
			tb_doc.setDocstatus(1);

			//获取图片参数
			net.sf.json.JSONObject jso = httpClientHelp.httpGet(str + "?x-oss-process=image/info");
			Object apertureValue = jso.get("ApertureValue");// 镜头光圈
			Object whitebalance = jso.get("WhiteBalance");// 白平衡
			Object shootingtime = jso.get("DateTimeOriginal");// 拍摄时间 原始时间
			Object focallength = jso.get("FocalLength");// 焦距
			Object flash = jso.get("Flash");// 闪光灯
			Object largestaperture = jso.get("MaxApertureValue");// 最大光圈值
			Object isorate = jso.get("ISOSpeedRatings");// ISO速率
			Object gpslat = jso.get("GPSLatitude");// 纬度
			Object gpslon = jso.get("GPSLongitude");// 经度
			Object storageformat = jso.get("Format");// 存储格式
			Object exposuretime = jso.get("ExposureTime");// 曝光时间
			Object devicenumber = jso.get("Model");// 设备型号
			Object shutterspeed = jso.get("ShutterSpeedValue"); // 快门速度
			Object exposureprogram = jso.get("ExposureProgram");// 曝光程序
			Object aperture = jso.get("FNumber");// FNumber 光圈值
			Object exposuremodel = jso.get("ExposureMode");// 曝光模式
			Object fileSize = jso.get("FileSize");// 文件大小
			Object iwidht = jso.get("ImageWidth"); // kuandu
			Object iheight = jso.get("ImageHeight"); // gaodu
			net.sf.json.JSONObject jsoniwidht = net.sf.json.JSONObject.fromObject(iwidht);
			net.sf.json.JSONObject jsoniheight = net.sf.json.JSONObject.fromObject(iheight);
			net.sf.json.JSONObject jsonapertureValue = net.sf.json.JSONObject.fromObject(apertureValue);
			net.sf.json.JSONObject jsonwhitebalance = net.sf.json.JSONObject.fromObject(whitebalance);
			net.sf.json.JSONObject jsonshootingtime = net.sf.json.JSONObject.fromObject(shootingtime);
			net.sf.json.JSONObject jsonfocallength = net.sf.json.JSONObject.fromObject(focallength);
			net.sf.json.JSONObject jsonflash = net.sf.json.JSONObject.fromObject(flash);
			net.sf.json.JSONObject jsonlargestaperture = net.sf.json.JSONObject.fromObject(largestaperture);
			net.sf.json.JSONObject jsonisorate = net.sf.json.JSONObject.fromObject(isorate);
			net.sf.json.JSONObject jsongpslat = net.sf.json.JSONObject.fromObject(gpslat);
			net.sf.json.JSONObject jsongpslon = net.sf.json.JSONObject.fromObject(gpslon);
			net.sf.json.JSONObject jsonstorageformat = net.sf.json.JSONObject.fromObject(storageformat);
			net.sf.json.JSONObject jsonexposuretime = net.sf.json.JSONObject.fromObject(exposuretime);
			net.sf.json.JSONObject jsondevicenumber = net.sf.json.JSONObject.fromObject(devicenumber);
			net.sf.json.JSONObject jsonshutterspeed = net.sf.json.JSONObject.fromObject(shutterspeed);
			net.sf.json.JSONObject jsonexposureprogram = net.sf.json.JSONObject.fromObject(exposureprogram);
			net.sf.json.JSONObject jsonaperture = net.sf.json.JSONObject.fromObject(aperture);
			net.sf.json.JSONObject jsonfileSize = net.sf.json.JSONObject.fromObject(fileSize);
			net.sf.json.JSONObject jsonexposuremodel = net.sf.json.JSONObject.fromObject(exposuremodel);
			ResultValue drtiwidht = (ResultValue) net.sf.json.JSONObject.toBean(jsoniwidht, ResultValue.class);
			ResultValue drtiheight = (ResultValue) net.sf.json.JSONObject.toBean(jsoniheight, ResultValue.class);
			ResultValue drtapertureValue = (ResultValue) net.sf.json.JSONObject.toBean(jsonapertureValue,
					ResultValue.class);
			ResultValue drtwhitebalance = (ResultValue) net.sf.json.JSONObject.toBean(jsonwhitebalance,
					ResultValue.class);
			ResultValue drtshootingtime = (ResultValue) net.sf.json.JSONObject.toBean(jsonshootingtime,
					ResultValue.class);
			ResultValue drtfocallength = (ResultValue) net.sf.json.JSONObject.toBean(jsonfocallength,
					ResultValue.class);
			ResultValue drtflash = (ResultValue) net.sf.json.JSONObject.toBean(jsonflash, ResultValue.class);
			ResultValue drtlargestaperture = (ResultValue) net.sf.json.JSONObject.toBean(jsonlargestaperture,
					ResultValue.class);
			ResultValue drtisorate = (ResultValue) net.sf.json.JSONObject.toBean(jsonisorate, ResultValue.class);
			ResultValue drtgpslat = (ResultValue) net.sf.json.JSONObject.toBean(jsongpslat, ResultValue.class);
			ResultValue drtgpslon = (ResultValue) net.sf.json.JSONObject.toBean(jsongpslon, ResultValue.class);
			ResultValue drtstorageformat = (ResultValue) net.sf.json.JSONObject.toBean(jsonstorageformat,
					ResultValue.class);
			ResultValue drtexposuretime = (ResultValue) net.sf.json.JSONObject.toBean(jsonexposuretime,
					ResultValue.class);
			ResultValue drtdevicenumber = (ResultValue) net.sf.json.JSONObject.toBean(jsondevicenumber,
					ResultValue.class);
			ResultValue drtshutterspeed = (ResultValue) net.sf.json.JSONObject.toBean(jsonshutterspeed,
					ResultValue.class);
			ResultValue drtexposureprogram = (ResultValue) net.sf.json.JSONObject.toBean(jsonexposureprogram,
					ResultValue.class);
			ResultValue drtaperture = (ResultValue) net.sf.json.JSONObject.toBean(jsonaperture, ResultValue.class);
			ResultValue drtfileSize = (ResultValue) net.sf.json.JSONObject.toBean(jsonfileSize, ResultValue.class);
			ResultValue drtexposuremodel = (ResultValue) net.sf.json.JSONObject.toBean(jsonexposuremodel,
					ResultValue.class);

			if (null != apertureValue) {
				tb_doc.setLensaperture(drtapertureValue.getValue());
			}
			if (null != whitebalance) {
				tb_doc.setWhitebalance(drtwhitebalance.getValue());
			}
			if (null != shootingtime) {
				tb_doc.setShootingtime(drtshootingtime.getValue());
			}
			if (null != focallength) {
				tb_doc.setFocallength(drtfocallength.getValue());
			}
			if (null != flash) {
				tb_doc.setFlash(drtflash.getValue());
			}
			if (null != largestaperture) {
				tb_doc.setLargestaperture(drtlargestaperture.getValue());
			}
			if (null != isorate) {
				tb_doc.setIsorate(drtisorate.getValue());
			}
			if (null != gpslat) {
				tb_doc.setGpslat(drtgpslat.getValue());
			}
			if(null!=drtiwidht){
				tb_doc.setIwidht(Integer.parseInt(drtiwidht.getValue()));
			}
			if(null!=drtiheight){
				tb_doc.setIheight(Integer.parseInt(drtiheight.getValue()));
			}
			if (null != gpslon) {
				tb_doc.setGpslon(drtgpslon.getValue());
			}
			if (null != storageformat) {
				tb_doc.setStorageformat(drtstorageformat.getValue());
			}
			if (null != exposuretime) {
				tb_doc.setExposuretime(drtexposuretime.getValue());
			}
			if (null != devicenumber) {
				tb_doc.setDevicenumber(drtdevicenumber.getValue());
			}
			if (null != shutterspeed) {
				tb_doc.setShutterspeed(drtshutterspeed.getValue());
			}
			if (null != exposureprogram) {
				tb_doc.setExposureprogram(drtexposureprogram.getValue());
			}
			if (null != aperture) {
				tb_doc.setAperture(drtaperture.getValue());

			}
			if (null != exposuremodel) {

				tb_doc.setExposuremodel(drtexposuremodel.getValue());
			}

			tb_doc.setFilepath(str);    //保存文件远程路径
			oss.destory();  //关闭资源

			tb_doc.setId(fn.substring(0,fn.lastIndexOf(".")));  //作品id
			tb_doc.setFid(fn.substring(0,fn.lastIndexOf(".")));
			tb_doc.setFilelength(len.intValue());
			tb_doc.setStorageformat(fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase());
			//作品其他信息
			Date d = DateUtil.getDate();
			tb_doc.setFiletype(0);
			tb_doc.setUploadtime(d);
			Tb_member result = new Tb_member();
			result.setId(memberId);
			tb_doc.setMemberid(result);
			tb_doc.setCreatedate(new Date().getTime());
			tb_doc.setIsdelete(0);
			tb_doc.setIsparticipating(0);
			tb_doc.setIsportrait(0);
			tb_doc.setIsdouble(0);
			tb_doc.setFilescore("0");
			tb_doc.setFileviewcount(0);
			tb_doc.setFilegoodcount(0);
			tb_doc.setFilekeepcount(0);
			tb_doc.setFilecommentscount(0);
			tb_doc.setFilepaycount(0);
			tb_doc.setFileserverid(0);
			tb_doc.setActityforwarcount(0);
			tb_doc.setIsboutique(0);
			tb_doc.setIspublic(0);
			tb_doc.setPublictime(d);
			tb_doc.setDoctitle("影集图");
			tb_doc.setDevicetype((byte)0);
			Tb_photoalbum photoalbum = new Tb_photoalbum(); //作品所属相册
			photoalbum.setId(17);
			tb_doc.setPhotoalbumid(photoalbum);

			Tb_filetype filetypeId = new Tb_filetype();   //作品所属类别
			filetypeId.setId(1);
			tb_doc.setFiletypeid(filetypeId);

			System.out.println("isChange==="+isChange);
			System.out.println("count==="+count);
			if (null == code)
				code = "1";
			if ("0".equals(isChange))
				worksShowId = filmfestivalService.addFilm(tb_doc,res,list,worksDesc,worksTitle,memberId,Integer.parseInt(count),worksShowId,space.toString(),isPublic,musicId,code,spotId,coverurl);
			if ("1".equals(isChange)) {
				filmfestivalService.updateFilm(list,tb_doc,Integer.parseInt(worksShowId),Integer.parseInt(count),memberId,worksDesc,worksTitle,space.toString(),isPublic,musicId,code,coverurl);
			}
			Map data = new HashMap();
			data.put("worksShowId",worksShowId);
			jsonResult.setData(data);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMsg(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 返回分页 filmfestival
	 * 
	 * @param pageBean
	 *            -> currentPage 页数 pageSize 每页个数
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/film_festival/lists")
	public String lists(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			StringBuffer str = new StringBuffer();
			str.append(" from Filmfestival o where 1=1 "); //初始化语句
			if (StringUtils.isStringNull(request.getParameter("vip_id"))) {
				str.append(" and o.vip_id  like '%" + request.getParameter("vip_id") + "%' ");
			}
			if (StringUtils.isStringNull(request.getParameter("doc_id"))) {
				str.append(" and o.doc_id.doctitle  like '%" + request.getParameter("doc_id") + "%' ");
			}
			if (StringUtils.isStringNull(request.getParameter("usersname"))) {
				str.append(" and o.member_id.usersname  like '%" + request.getParameter("usersname") + "%' ");
			}
			pageBean.setHql(str.toString());
			int totalRecord = filmfestivalService.findByHQLCount(str.toString());
			str.append(" order by o.sort,o.doc_id.uploadtime desc ");
			pageBean.setHql(str.toString());
			List<Filmfestival> list = filmfestivalService.findByHQL(str.toString(), pageBean);
		 
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("startPage", pageBean.getStartPage());// 当前页
			map1.put("pageSize", pageBean.getPageSize());// 一页多少条
			map1.put("totalRecord", totalRecord);// 总记录数
			map1.put("totalPage", ((totalRecord + pageBean.getPageSize() - 1) / pageBean.getPageSize()));// 一共有多少页
			item.add(map1);
			for (Filmfestival obj : list) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("id", obj.getId());
				 
				if(obj.getVip_id()!=null){
					map.put("vip_id", obj.getVip_id().getId());
					map.put("titel", obj.getVip_id().getTitel());
					map.put("titelnote", obj.getVip_id().getTitelnote());
					map.put("vip_goodcount", obj.getVip_id().getFilegoodcount());
					map.put("vip_paycount", obj.getVip_id().getFilepaycount());
					map.put("vip_commentscount", obj.getVip_id().getFilecommentscount());
				}else{
					map.put("vip_id","");
					map.put("titel", "");
					map.put("titelnote","");
					map.put("vip_goodcount","0");
					map.put("vip_paycount","0");
					map.put("vip_commentscount", "0");
				}
				if (obj.getDoc_id() != null) {
					map.put("doc_fdId", obj.getDoc_id().getId());
					map.put("doctitle", obj.getDoc_id().getDoctitle());
					map.put("filedescribe", obj.getDoc_id().getFiledescribe());
					map.put("filegoodcount", obj.getDoc_id().getFilegoodcount());
					map.put("filekeepcount", obj.getDoc_id().getFilekeepcount());
					map.put("filepaycount", obj.getDoc_id().getFilepaycount());
					map.put("fileviewcount", obj.getDoc_id().getFileviewcount());
					map.put("filecommentscount", obj.getDoc_id()
							.getFilecommentscount());
					map.put("filepath", obj.getDoc_id().getFilepath());
					map.put("iwidht", obj.getDoc_id().getIwidht());
					map.put("iheight", obj.getDoc_id().getIheight());
				} else {
					map.put("doc_fdId", "");
					map.put("doctitle", "");
				 	map.put("filegoodcount", "");   
					map.put("filekeepcount", "");
					map.put("filepaycount", "");
					map.put("fileviewcount", "");
					map.put("filecommentscount", ""); 
					map.put("filepath", "");
					map.put("filedescribe", "");
					map.put("iwidht","");
					map.put("iheight", "");
				}
			
				map.put("sort", obj.getSort());
				if (obj.getMember_id() != null) {
					map.put("member_id", obj.getMember_id().getId());
					if(obj.getMember_id().getUsersname()!=null){
						map.put("usersname", obj.getMember_id().getUsersname());
					}else{
						map.put("usersname", "");
					}
					if(obj.getMember_id().getLevelid() != null){
						map.put("levelname", obj.getMember_id().getLevelid().getLevelname());
					}else{
						map.put("levelname", "");
					}
					if(obj.getMember_id().getHeadimg()!=null){
						map.put("headimg", obj.getMember_id().getHeadimg());
					}else{
						map.put("headimg", "");
					}
					
				} else {
					map.put("member_id", "");
					map.put("usersname", "");
					map.put("headimg", "");
					map.put("levelname", "");
				}
				 
				item.add(map);
			}
			 
			/*QueryResult<Filmfestival> result = filmfestivalService.list(
					pageBean, request);*/
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

	@RequestMapping(value = "/film_festival/list")
	public String list(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			QueryResult<Filmfestival> result = filmfestivalService.list(
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
	
	@RequestMapping(value = "/film_festival_doc/list")
	public String listfestival_m(PageBean pageBean, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			 QueryResult<Filmfestival> result = filmfestivalService.findval(pageBean);
			 List<Filmfestival>	list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
			for (Filmfestival obj : list) { 
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj.getId());
				if(obj.getVip_id()!=null){
					map.put("vip_id", obj.getVip_id().getId());
					map.put("titel", obj.getVip_id().getTitel());
					map.put("titelnote", obj.getVip_id().getTitelnote());
				}else{
					map.put("vip_id","");
					map.put("titel", "");
					map.put("titelnote","");
				}
				if (obj.getDoc_id() != null) {
					map.put("doc_fdId", obj.getDoc_id().getId());
					map.put("doctitle", obj.getDoc_id().getDoctitle());
					map.put("filepath", obj.getDoc_id().getFilepath());
					map.put("iwidht", obj.getDoc_id().getIwidht());
					map.put("iheight", obj.getDoc_id().getIheight());
				} else {
					map.put("doc_fdId", "");
					map.put("doctitle", "");
					map.put("filepath", "");
				}
				map.put("sort", obj.getSort());
				if (obj.getMember_id() != null) {
					map.put("member_id", obj.getMember_id().getId());
					if (obj.getMember_id().getUsersname() != null) {
						map.put("usersname", obj.getMember_id().getUsersname());
					} else {
						map.put("usersname", "");
					}
					if (obj.getMember_id().getIntroduction() != null) {
						map.put("introduction", obj.getMember_id().getIntroduction());
					} else {
						map.put("introduction", "");
					}
					if(obj.getMember_id().getLevelid() != null){
						map.put("levelname", obj.getMember_id().getLevelid().getLevelname());
					}else{
						map.put("levelname", "");
					}
					if (obj.getMember_id().getHeadimg() != null) {
						map.put("headimg", obj.getMember_id().getHeadimg());
					} else {
						map.put("headimg", "");
					}

				} else {
					map.put("member_id", "");
					map.put("usersname", "");
					map.put("headimg", "");
					map.put("levelname", "");
					map.put("introduction", "");
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
	
	@RequestMapping(value = "/film_festival_doc_mycaogao/list")
	public String listfestival_mycaogao(PageBean pageBean, HttpServletRequest request,String memberid,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			 QueryResult<Filmfestival> result = filmfestivalService.findmemberval_caogao(pageBean, memberid);
			 List<Filmfestival>	list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
			for (Filmfestival obj : list) { 
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj.getId());
				if(obj.getVip_id()!=null){
					map.put("vip_id", obj.getVip_id().getId());
					map.put("titel", obj.getVip_id().getTitel());
				}else{
					map.put("vip_id","");
					map.put("titel", "");
				}
				if (obj.getDoc_id() != null) {
					map.put("doc_fdId", obj.getDoc_id().getId());
					map.put("filepath", obj.getDoc_id().getFilepath());
					map.put("iwidht", obj.getDoc_id().getIwidht());
					map.put("iheight", obj.getDoc_id().getIheight());
				} else {
					map.put("doc_fdId", "");
					map.put("filepath", "");
				}
				map.put("sort", obj.getSort());
				if (obj.getMember_id() != null) {
					map.put("member_id", obj.getMember_id().getId());
					if (obj.getMember_id().getUsersname() != null) {
						map.put("usersname", obj.getMember_id().getUsersname());
					} else {
						map.put("usersname", "");
					}

				} else {
					map.put("member_id", "");
					map.put("usersname", "");
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
	@RequestMapping(value = "/film_festival_doc_my/list")
	public String listfestival_my(PageBean pageBean, HttpServletRequest request,String memberid,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			 QueryResult<Filmfestival> result = filmfestivalService.findmemberval(pageBean, memberid);
			 List<Filmfestival>	list = result.getResultList();
			 List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("startPage", pageBean.getStartPage());// 当前页
				map1.put("pageSize", result.getPageBean().getPageSize());// 一页多少条
				map1.put("totalRecord", result.getPageBean().getTotalRecord());// 总记录数
				map1.put("totalPage", result.getPageBean().getTotalPage());// 一共有多少页
				item.add(map1);
			for (Filmfestival obj : list) { 
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", obj.getId());
				if(obj.getVip_id()!=null){
					map.put("vip_id", obj.getVip_id().getId());
					map.put("titel", obj.getVip_id().getTitel());
				}else{
					map.put("vip_id","");
					map.put("titel", "");
				}
				if (obj.getDoc_id() != null) {
					map.put("doc_fdId", obj.getDoc_id().getId());
					map.put("filepath", obj.getDoc_id().getFilepath());
					map.put("iwidht", obj.getDoc_id().getIwidht());
					map.put("iheight", obj.getDoc_id().getIheight());
				} else {
					map.put("doc_fdId", "");
					map.put("filepath", "");
				}
				map.put("sort", obj.getSort());
				if (obj.getMember_id() != null) {
					map.put("member_id", obj.getMember_id().getId());
					if (obj.getMember_id().getUsersname() != null) {
						map.put("usersname", obj.getMember_id().getUsersname());
					} else {
						map.put("usersname", "");
					}

				} else {
					map.put("member_id", "");
					map.put("usersname", "");
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

	/**
	 * 返回List列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/film_festival/listAll")
	public String listAll(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Filmfestival> result = filmfestivalService.findAll();
			jsonResult.setResult(FilmfestivalUtil.getControllerList(result));
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
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/film_festival/selectKey")
	public String selectKey(String fdId, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (fdId != null && !(fdId.equals(""))) {
				Filmfestival result = filmfestivalService
						.findByPrimaryKey(fdId);
				jsonResult.setResult(FilmfestivalUtil.getControllerMap(result));
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
	 * 保存个人影展
	 * 
	 * @param filmfestival
	 * @param ids
	 * @param ispayto
	 * @param response
	 * @param request
	 *            jiuu="{hhh:lll}"
	 * @return
	 */
	@RequestMapping(value = "/film_festival/save")
	public String save(Filmfestival filmfestival, String ids,Integer ispayto,
			HttpServletResponse response, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/upload/");
		File newFile = new File(realPath);
		try {
			 if (!newFile.exists()) {
				if (!newFile.mkdirs()) {
					try {
						throw new Exception("创建保存目录失败");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			LogMsg.writeEror_to_txt(realPath+"ids.txt", ids);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JsonResult jsonResult = new JsonResult();
		try {
			/* String idss = "{\"content\": [{\"doctitle\": \"大千世界无奇不有\",\"id\": \"1508836220982TATKW9\",\"sort\":\"1\"},"
			    +"{\"doctitle\": \"大千世界无奇不有2222\",\"id\": \"1508836259310SRYJ3E\"}],"
					+ "\"member_id\": \"45F86EE9-1D3D-471E-BAA0-3270CD80C666\","
					+ "\"titel\": \"1111111\","
					+ "\"titelnote\": \"111111\"}"; */
			/*String ii="{\"member_id\":\"32B9BF11-C9B4-4CFC-B6B6-800DAD30BBC6\",\"content\":[{\"id\":\"150487453198766PY6Y\",\"doctitle\":\"123\",\"sort\":\"1\"},{\"id\":\"1504870228566C3W1CY\",\"doctitle\":\"456\",\"sort\":\"1\"},{\"id\":\"1504869616993TUWIFR\",\"doctitle\":\"789\",\"sort\":\"1\"}],\"titel\":\"adgj\",\"titelnote\":\"128\"}";*/
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(ids);
			Map map = new HashMap();
			map.put("content", Content.class);
			FilmfestivalIds f = (FilmfestivalIds) net.sf.json.JSONObject
					.toBean(json, FilmfestivalIds.class, map);

			Tb_member tbm = tb_memberService.findByPrimaryKey(f.getMember_id());
			
			//已经付费
			if(null!=ispayto && ispayto!=0){
			// 影展会员表
			/*List<Filmfestivalvip> findvip = filmfestivalvipService
					.findlistBySql(
							Filmfestivalvip.class,
							"select * from Filmfestivalvip where GETDATE()-open_time <=  time_length and open_time>0 and member_id='"
									+ f.getMember_id() + "'");*/
			
				Filmfestivalvip vip = new Filmfestivalvip();
				String title=f.getTitel();
				String note=f.getTitelnote();
				//查询会员留言设置
				List<Tb_membermessagesteup> liuyan=tb_membermessagesteupService.findAll();
				if(liuyan!=null && liuyan.size()>0){
					String []jinyong=liuyan.get(0).getDisablewords().split("[,，]");
					for(String s:jinyong)
					{
						if(null!=title){
							title=title.replaceAll(s, liuyan.get(0).getReplacewords());
						}
						if(null!=note){
							note=note.replaceAll(s, liuyan.get(0).getReplacewords());
						}
					}
					//comments.indexOf(ch)	
				}
				vip.setTitel(title);
				vip.setTitelnote(note);
				vip.setMember_id(tbm);
				vip.setFilegoodcount(0);
				vip.setFilecommentscount(0);
				vip.setFilepaycount(0);
					// 影展VIP开通时限表
					List<Filmfestivalvipopentime> fvip = filmfestivalvipopentimeService
							.findAll();
					if (fvip != null && fvip.size() > 0) {
						vip.setTime_length(fvip.get(0).getTime_length());
					} else {
						vip.setTime_length(30);
					}
				vip.setOpen_time(new Date());
				filmfestivalvipService.save(vip);
				jsonResult.setResult(vip.getId());
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
				try {
					StrBuilder sq = new StrBuilder();
					if (null != f.getContent() && f.getContent().size() > 0) {
						for (Content c : f.getContent()) {
							sq.append("update Tb_doc set filedescribe='"
									+ c.getDoctitle() + "' , ispublic=1 where id='" + c.getId()
									+ "';");
							Tb_doc doc = tb_docService.findByPrimaryKey(c.getId());
							filmfestival.setSort(c.getSort());
							filmfestival.setDoc_id(doc);
							filmfestival.setMember_id(tbm);
  							filmfestival.setVip_id(vip);
							filmfestivalService.save(filmfestival);
						}
						filmfestivalService.updatedoc(sq.toString());
					} else {
						jsonResult.setCode("501");
						jsonResult.setMessage("请选择图片！");
					}
				} catch (Exception e) {
					jsonResult.setCode("502");
					jsonResult.setMessage("错误提示：" + e.getMessage());
				}
		 
			
			}else{//未付费
				Filmfestivalvip vip = new Filmfestivalvip();
				String title=f.getTitel();
				String note=f.getTitelnote();
				//查询会员留言设置
				List<Tb_membermessagesteup> liuyan=tb_membermessagesteupService.findAll();
				if(liuyan!=null && liuyan.size()>0){
					String []jinyong=liuyan.get(0).getDisablewords().split("[,，]");
					for(String s:jinyong)
					{
						title=title.replaceAll(s, liuyan.get(0).getReplacewords());
						note=note.replaceAll(s, liuyan.get(0).getReplacewords());
					}
					//comments.indexOf(ch)	
				}
				vip.setTitel(title);
				vip.setTitelnote(note);
				vip.setMember_id(tbm);
				vip.setTime_length(0);//天数为0
				vip.setOpen_time(new Date());
				filmfestivalvipService.save(vip);
				jsonResult.setResult(vip.getId());
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
				try {
					StrBuilder sq = new StrBuilder();
					if (null != f.getContent() && f.getContent().size() > 0) {
						for (Content c : f.getContent()) {
							sq.append("update Tb_doc set filedescribe='"
									+ c.getDoctitle() + "' , ispublic=1  where id='" + c.getId()
									+ "';");
							Tb_doc doc = tb_docService.findByPrimaryKey(c.getId());
							filmfestival.setSort(c.getSort());
							filmfestival.setDoc_id(doc);
							filmfestival.setMember_id(tbm);
							filmfestival.setVip_id(vip);
							filmfestivalService.save(filmfestival);
						}
						filmfestivalService.updatedoc(sq.toString());
					} else {
						jsonResult.setCode("501");
						jsonResult.setMessage("请选择图片！");
					}
				} catch (Exception e) {
					jsonResult.setCode("502");
					jsonResult.setMessage("错误提示：" + e.getMessage());
				}
			
			}
		} catch (Exception e) {
			
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 更新 filmfestival
	 * 
	 * @param filmfestival
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/film_festival/update")
	public String update(Filmfestival filmfestival,String ids,Integer ispayto,Integer vipid,
			HttpServletResponse response, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/upload/");
		File newFile = new File(realPath);
	 /*String idss = "{\"content\": [{\"doctitle\": \"大千世界无奇不有\",\"id\": \"1508836610328N6A8KO\",\"sort\":\"1\"},"
	    +"{\"doctitle\": \"大千世界无奇不有2222\",\"id\": \"15088363608110W224F\",\"sort\":\"2\"}],"
			+ "\"member_id\": \"45F86EE9-1D3D-471E-BAA0-3270CD80C666\","
			+ "\"titel\": \"shijiemori\","
			+ "\"titelnote\": \"shijiemori\"}"; */
		try {
			 if (!newFile.exists()) {
				if (!newFile.mkdirs()) {
					try {
						throw new Exception("创建保存目录失败");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			LogMsg.writeEror_to_txt(realPath+"ids.txt", ids);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JsonResult jsonResult = new JsonResult();
		try {
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(ids);
			Map map = new HashMap();
			map.put("content", Content.class);
			FilmfestivalIds f = (FilmfestivalIds) net.sf.json.JSONObject
					.toBean(json, FilmfestivalIds.class, map);
			Tb_member tbm = tb_memberService.findByPrimaryKey(f.getMember_id());
		
			if(null!=ispayto && ispayto!=0){//已付费
				Filmfestivalvip vip =  filmfestivalvipService.findByPrimaryKey(vipid);
				if(null!=vip && vip.getTime_length()<=0){
				String title=f.getTitel();
				String note=f.getTitelnote();
				//查询敏感词
				List<Tb_membermessagesteup> liuyan=tb_membermessagesteupService.findAll();
				if(liuyan!=null && liuyan.size()>0){
					String []jinyong=liuyan.get(0).getDisablewords().split("[,，]");
					for(String s:jinyong)
					{
						if(null!=title){
							title=title.replaceAll(s, liuyan.get(0).getReplacewords());
						}
						if(null!=note){
							note=note.replaceAll(s, liuyan.get(0).getReplacewords());
						}
					}
				}
				vip.setId(vipid);
				vip.setTitel(title);
				vip.setTitelnote(note);
				vip.setMember_id(tbm);
					// 影展VIP开通时限表
					List<Filmfestivalvipopentime> fvip = filmfestivalvipopentimeService
							.findAll();
					if (fvip != null && fvip.size() > 0) {
						vip.setTime_length(fvip.get(0).getTime_length());
					} else {
						vip.setTime_length(30);
					}
				vip.setOpen_time(new Date());
				filmfestivalvipService.update(vip);
				if(vipid != null && vipid > 0){
					filmfestivalService.deletevipid(vipid);
				}
				jsonResult.setResult(vip.getId());
				jsonResult.setCode("200");
				jsonResult.setMessage("执行成功！");
				try {
					StrBuilder sq = new StrBuilder();
					if (null != f.getContent() && f.getContent().size() > 0) {
						for (Content c : f.getContent()) {
							sq.append("update Tb_doc set filedescribe='"
									+ c.getDoctitle() + "' , ispublic=1  where id='" + c.getId()
									+ "';");
							Tb_doc doc = tb_docService.findByPrimaryKey(c.getId());
							filmfestival.setSort(c.getSort());
							filmfestival.setDoc_id(doc);
							filmfestival.setMember_id(tbm);
							filmfestival.setVip_id(vip);
							filmfestivalService.save(filmfestival);
						}
						filmfestivalService.updatedoc(sq.toString());
					} else {
						jsonResult.setCode("501");
						jsonResult.setMessage("请选择图片！");
					}
				} catch (Exception e) {
					jsonResult.setCode("502");
					jsonResult.setMessage("错误提示：" + e.getMessage());
				}
				}else{
					jsonResult.setCode("201");
					jsonResult.setMessage("已经参展的不能再修改咯！");
				}
			}else{//未付费 
				Filmfestivalvip vip =  filmfestivalvipService.findByPrimaryKey(vipid);
				if(null!=vip && vip.getTime_length()<=0){
					String title=f.getTitel();
					String note=f.getTitelnote();
					//查询敏感词
					List<Tb_membermessagesteup> liuyan=tb_membermessagesteupService.findAll();
					if(liuyan!=null && liuyan.size()>0){
						String []jinyong=liuyan.get(0).getDisablewords().split("[,，]");
						for(String s:jinyong)
						{
							if(null!=title){
								title=title.replaceAll(s, liuyan.get(0).getReplacewords());
							}
							if(null!=note){
								note=note.replaceAll(s, liuyan.get(0).getReplacewords());
							}
						}
					}
					vip.setId(vipid);
					vip.setTitel(title);
					vip.setTitelnote(note);
					vip.setMember_id(tbm);
					filmfestivalvipService.update(vip);
					if(vipid != null && vipid > 0){
						filmfestivalService.deletevipid(vipid);
					}
					jsonResult.setResult(vip.getId());
					jsonResult.setCode("200");
					jsonResult.setMessage("执行成功！");
					try {
						StrBuilder sq = new StrBuilder();
						if (null != f.getContent() && f.getContent().size() > 0) {
							for (Content c : f.getContent()) {
								sq.append("update Tb_doc set filedescribe='"
										+ c.getDoctitle() + "' , ispublic=1  where id='" + c.getId()
										+ "';");
								Tb_doc doc = tb_docService.findByPrimaryKey(c.getId());
								filmfestival.setSort(c.getSort());
								filmfestival.setDoc_id(doc);
								filmfestival.setMember_id(tbm);
								filmfestival.setVip_id(vip);
								filmfestivalService.save(filmfestival);
							}
							filmfestivalService.updatedoc(sq.toString());
						} else {
							jsonResult.setCode("501");
							jsonResult.setMessage("请选择图片！");
						}
					} catch (Exception e) {
						jsonResult.setCode("502");
						jsonResult.setMessage("错误提示：" + e.getMessage());
					} 
				}else{
					jsonResult.setCode("201");
					jsonResult.setMessage("已经参展的不能再修改咯！");
				}
				 
			} 
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
	@RequestMapping(value = "/film_festival/delete")
	public String delete(String[] ids, HttpServletResponse response,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
			if (ids != null && ids.length > 0) {
				filmfestivalService.delete(ids);
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
