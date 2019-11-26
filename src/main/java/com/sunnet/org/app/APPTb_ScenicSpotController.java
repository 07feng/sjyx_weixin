package com.sunnet.org.app;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.framework.util.Util;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.scenicSpot.model.TB_Area;
import com.sunnet.org.scenicSpot.model.TB_City;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpot;
import com.sunnet.org.scenicSpot.service.ITB_ScenicSpotService;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.OSSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游管理
 * author jinhao
 * 时间：2018.6/21
 */
@Controller
@RequestMapping("/app")
public class APPTb_ScenicSpotController extends BaseController {
    @Autowired
    private ITB_ScenicSpotService itb_scenicSpotService;

    /**
     * 获取景点信息
     * @param spotId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/getSpotBgk")
    public String getSpotBgk(String spotId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberId = getMemberId(request, response);
            Object[] obj = itb_scenicSpotService.spotInfo(Integer.parseInt(spotId));
            if (null != obj[0] && memberId.equals(obj[0].toString())){
                Map data = new HashMap();
                data.put("spotId",spotId);
                data.put("desc",obj[4].toString());
                data.put("image",obj[5].toString()+Constants.DOC_PATH_END2);
                jsonResult.setData(data);
                jsonResult.setCode("0");
                jsonResult.setMessage("success");
            }else {
                jsonResult.setCode("-1");
                jsonResult.setMessage("您不是发现者，没有权限编辑！");
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
     * 获取景点信息
     * @param spotId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/toTopFile")
    public String toTopFile(String spotId,String spotFileId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberId = getMemberId(request, response);
            Object[] obj = itb_scenicSpotService.spotInfo(Integer.parseInt(spotId));
            if (null != obj[0] && memberId.equals(obj[0].toString())){
                itb_scenicSpotService.updateSpotFileTop(spotId,spotFileId);
                jsonResult.setCode("0");
                jsonResult.setMessage("success");
            }else {
                jsonResult.setCode("-1");
                jsonResult.setMessage("您不是发现者，没有权限置顶作品！");
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
     * 景点发现者编辑景点信息
     * 推荐美景地
     * @param desc
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/spotBgkChange")
    public String spotBgkChange(String spotId,String desc,String spotName,String cityId, String spotDesc, MultipartFile file, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try {
            String memberid = getMemberId(request, response);
            String str = null;
            Map map = new HashMap();    //返回数据
            if (null != file) {
                String fileName = file.getOriginalFilename();
                OSSClientUtil oss = new OSSClientUtil();    //上传到阿里云
                String time = DateUtil.DateToString(DateUtil.getDate(), "YYYY-MM-dd") + "/";   //存储时间文件夹
                oss.setFOLDER(time);
                //生成随机文件名
                String fn = oss.fileName(fileName);
                String key = oss.uploadFile2OSS(file.getInputStream(), fn);
                if ("".equals(key)) {
                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                    jsonResult.setMsg("上传服务器失败");
                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                }

                //获取远程url
                String url = oss.getImgUrl(fn);
                //图片鉴黄
                str = oss.getReplace() + time + fn;
                String newStr = str.substring(str.length() - 3);
                String tags = null;
                if (newStr != "GIF" && !"GIF".equals(newStr)) {
                    JSONObject json = JSONObject.parseObject(AppAliyunImg.CheckImage(str));
                    //String detect= json.getJSONObject("result").getJSONObject("detect").get("body").toString();
                    tags = json.getJSONObject("result").getJSONObject("porn").get("body").toString();
                }
                if (!Util.isnull(tags)) {
                    if (tags.indexOf("色情") != -1) {
                        jsonResult.setCode("1");
                        jsonResult.setMsg("图片内容不通过！");
                        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                    }
                }
                oss.destory();  //关闭资源
            }
            if ("0".equals(spotId)) {
                //获取景点所在地区id
//                List<Object[]> list = itb_scenicSpotService.findBySql("SELECT a.* FROM TB_City AS c LEFT JOIN TB_Area AS a ON c.AreaId = a.id WHERE c.id =?", Integer.parseInt(cityId));
                TB_ScenicSpot ss = new TB_ScenicSpot();
                ss.setAdddate(DateUtil.getDate());
                ss.setIsclose(0);
                ss.setPhotonum(0);
                ss.setVideonum(0);
                ss.setArticlenum(0);
                ss.setUpdatetime(DateUtil.getDate());
                ss.setSort(0);
                ss.setScenicspotname(spotName);
                ss.setIntro(spotDesc);
                Tb_member mem = new Tb_member();
                mem.setId(memberid);
                ss.setRecommemberid(mem);
                TB_City c = new TB_City();
                c.setId(Integer.parseInt(cityId));
                ss.setCityid(c);
                if (null != str)
                    ss.setCoverpicurl(str);
                itb_scenicSpotService.save(ss);
                System.out.println("添加景区成功！");
                spotId = ss.getId() + "";
                map.put("spotId", spotId);
                jsonResult.setData(map);
                jsonResult.setCode("0");
                jsonResult.setMessage("success");
            }
            else {
//                Object[] spot = itb_scenicSpotService.spotInfo(Integer.parseInt(spotId));
                //修改景点信息
                StringBuffer sb = new StringBuffer("Intro = ?");
                if (null != str)
                    sb.append(",CoverPicUrl='").append(str).append("'");
//                if (null == spot[0])
//                    sb.append(",AdminMemberId='").append(memberid).append("'");
                sb.append(",updateTime = GETDATE()");
                itb_scenicSpotService.updateSpotInfo(sb.toString(), desc, spotId);
                System.out.println("编辑景区成功！");
                map.put("spotId",spotId);
                jsonResult.setData(map);
                jsonResult.setCode("0");
                jsonResult.setMessage("success");
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
     * 成为景点发现在权限
     * @param spotId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/spotFindStatus")
    public String spotFindStatus(String spotId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberId = getMemberId(request, response);
            Map data = itb_scenicSpotService.adminSpotStatus(Integer.parseInt(spotId),memberId);
            jsonResult.setData(data);
            jsonResult.setCode("0");
            jsonResult.setMessage("success");
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
     * 成为景点发现在权限
     * @param spotId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/spotFind")
    public String spotFind(String spotId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberId = getMemberId(request, response);
            String sb = "AdminMemberId=?,updateTime = GETDATE()";
            itb_scenicSpotService.updateSpotInfo(sb.toString(),memberId, spotId);
            jsonResult.setCode("0");
            jsonResult.setMessage("success");
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
