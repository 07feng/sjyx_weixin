package com.sunnet.org.app;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.framework.util.Util;
import com.sunnet.framework.util.VideoUtil;
import com.sunnet.org.app.oss.httpClientHelp;
import com.sunnet.org.app.oss.util.ResultValue;
import com.sunnet.org.app.oss.util.getHttppostResult;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.competition.service.ITb_contestService;
import com.sunnet.org.competition.service.ITb_contestthemeService;
import com.sunnet.org.competition.service.ITre_doccontestService;
import com.sunnet.org.competition.service.Ivie_doccontestmemberService;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.doc.model.Tb_groupdocgood;
import com.sunnet.org.doc.model.Tre_menberdocscore;
import com.sunnet.org.doc.service.*;
import com.sunnet.org.feedback.model.Tb_feedback;
import com.sunnet.org.feedback.service.ITb_feedbackService;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.information.model.Tb_photoalbum;
import com.sunnet.org.information.service.*;
import com.sunnet.org.information.vo.Tb_filetypeUtil;
import com.sunnet.org.member.model.*;
import com.sunnet.org.member.service.*;
import com.sunnet.org.util.*;
import com.sunnet.org.view.model.Vie_Info;
import com.sunnet.org.view.service.IVie_InfoService;
import com.sunnet.org.view.service.Ivie_shouyeService;
import com.sunnet.org.visitors.model.T_topsearchterm;
import com.sunnet.org.visitors.service.IT_topsearchtermService;
import org.apache.xmlbeans.impl.schema.StscChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/***
 * 影像管理
 *
 * @author jing
 */
@Controller
@RequestMapping("/app")
public class AppTb_docController extends BaseController {
    @Autowired
    private ITb_feedbackService iTb_feedbackService;
    @Autowired
    private IVie_InfoService vie_InfoService;
    @Autowired
    private IT_comment_fidService iT_comment_fidService;
    @Autowired
    private ITb_memberService tb_memberService;
    @Autowired
    private ITb_docService tb_docService;
    @Autowired
    private ITb_docpayService tb_docpayService;
    @Autowired
    private ITre_docfilelabelService tre_docfilelabelService;
    @Autowired
    private ITb_filelabelService filelabel;
    @Autowired
    private ITb_contestService tb_contestService;
    @Autowired
    private ITre_docfilelabelService filetolabel;
    @Autowired
    private Ivie_Tb_DocInfoService vie_Tb_DocInfoService;
    @Autowired
    private ITb_filetypeService tb_filetypeService;
    @Autowired
    private ITre_doccontestService tre_doccontestService;
    @Autowired
    private ITb_photoalbumService tb_photoalbumService;
    @Autowired
    private ITb_dockeepService tb_dockeepService;
    @Autowired
    private ITb_docgoodService tb_docgoodService;
    @Autowired
    private ITb_contestthemeService tb_contestthemeService;
    @Autowired
    private ITb_memberparameterService tb_memberparameterService;// 查询会员参数表设置表
    @Autowired
    private ITb_membermessagesteupService tb_membermessagesteupService;
    @Autowired
    private ITb_levelintegralsourceService tb_levelintegralsourceService;

    @Autowired
    private Ivie_shouyeService vie_shouyeService;

    @Autowired
    private ITre_menberdoccommentService iTre_menberdoccommentService;

    @Autowired
    private ITre_friendscircleService iTre_friendscircleService;

    @Autowired
    private IT_topsearchtermService t_topsearchtermService;
    @Autowired
    private ITre_menberdocscoreService tre_menberdocscoreService;
    @Autowired
    private ITb_groupdocgoodService tb_groupdocgoodService;
    @Autowired
    private ITb_levelService tb_levelService;
    @Autowired
    private ITb_photoalbumService iTb_photoalbumService;
    @Autowired
    private Ivie_doccontestmemberService vie_doccontestmemberService;
    @Autowired
    private ITb_docService iTb_docService;
    @Autowired
    private ITre_doccontestService iTre_doccontestService;
    @Autowired
    private ITb_sendmessageService iTb_sendmessageService;

    /**
     * 删除文件
     *
     * @param code     0删除文件
     *                 1还原文件
     *                 2清空回收站
     * @param imgId
     * @param response
     * @param request
     * @return
     */
    @Transactional
    @RequestMapping(value = "/session/recovery")
    public String recovery(String code, String imgId, HttpServletResponse response, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        //回收站还原文件
        String[] strArray;
        try {
            if (code.equals("1")) {
                strArray = imgId.split(",");
                System.out.println("strArray="+strArray.length);
                if(strArray.length != 0) {
                    for (int i = 0; i < strArray.length; i++) {
                        tb_docService.updateHql("update Tb_doc set isdelete=0 where id='" + strArray[i] + "'");
                    }
                }
            }
            if (code.equals("2")) {
                //清空回收站
                String memberId = getMemberId(request, response);
//                System.out.println("memberId="+memberId);
                List<Tb_doc> list = tb_docService.findByHQL("from Tb_doc o where o.memberid.id='" + memberId + "' and o.isdelete=1");
                if (list.size() == 0) {
                    jsonResult.setCode(Constants.SUCCESS_CODE);
                    jsonResult.setMsg("回收站已清空");
                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                }
                strArray = new String[list.size()];
//                System.out.println("list="+list.size());
                for (int i = 0; i < list.size(); i++) {
                    strArray[i] = (list.get(i).getId());
//                    System.out.println("strArray="+strArray[i]);
                }
                tb_docService.deletebyArr(strArray);
            }
            //删除文件（物理删除）
            if (code.equals("0")) {
                strArray = imgId.split(",");
                if(strArray.length != 0) {
                    tb_docService.deletebyArr(strArray);
                }
            }
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("系统异常！");
            log.debug(e.getMessage());
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        jsonResult.setCode(Constants.SUCCESS_CODE);
        jsonResult.setMsg(Constants.SUCCESS_DATA);
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 意见反馈
     *
     * @param content
     * @param response
     * @param request
     * @return
     * @aurthod : caixiang
     */
    @RequestMapping(value = "/session/feedback")
    public String feedback(String content, HttpServletResponse response, HttpServletRequest request) {
        String memberId = getMemberId(request, response);
        Tb_feedback tb_feedback = new Tb_feedback();
        JsonResult jsonResult = new JsonResult();
        Tb_member tb_member = new Tb_member();
        tb_member.setId(memberId);
//        int id=iTb_feedbackService.getMaxId(Tb_feedback.class)+1;
        try {
//            tb_feedback.setId(id);
            tb_feedback.setAddtime(new Date());
            tb_feedback.setMemberid(tb_member);
            tb_feedback.setFeedbackinfo(content);
            tb_feedback.setFeedbackstatus("0");
            iTb_feedbackService.save(tb_feedback);
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("系统异常！");
            log.debug(e.getMessage());
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        jsonResult.setCode(Constants.SUCCESS_CODE);
        jsonResult.setMsg(Constants.SUCCESS_DATA);
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 获取社交消息列表
     *
     * @param page
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/messageList")
    public String messageList(String page, HttpServletRequest request, HttpServletResponse response) {
        String memberId = getMemberId(request, response);
        JsonResult jsonResult = new JsonResult();
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(Integer.parseInt(page));
        try {
            List infoList = vie_InfoService.getList(pageBean, memberId);
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
            if (infoList.size() == 0) {
                jsonResult.setData("");
            } else {
                jsonResult.setData(infoList);
            }
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 收藏和取消收藏
     *
     * @param docId
     * @param collect  收藏 :1,取消收藏：0
     * @param response
     * @param request
     * @return
     * @author : caixiang
     */
    @RequestMapping(value = "/session/collect")
    public String collect(String docId, String collect, HttpServletResponse response, HttpServletRequest request) {
//		System.out.println("str="+docId+","+memberId+","+collect);
        String memberId = getMemberId(request, response);
        JsonResult jsonResult = new JsonResult();
        try {
            Integer isHaving = tb_dockeepService.findByHQL("from Tb_doc d where d.id=?", docId).size();
            if (isHaving == null || isHaving <= 0) {
                jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                jsonResult.setMsg("收藏失败");
                return ajaxJson(JSONObject.toJSONString(jsonResult), response);
            }
            tb_dockeepService.collect(docId,Integer.parseInt(collect), memberId);
            List list = tb_docService.findBySql("select d.filekeepcount from Tb_doc d where d.id=?", docId);
            Map<String, Object> map = new HashMap<>();
            map.put("num", list.get(0).toString());
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
            jsonResult.setData(map);
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMessage("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 模糊查询list数据
     *
     * @param page
     * @param keyword
     * @param request
     * @param response authod: caixiang
     * @return
     */
    @RequestMapping(value = "/serach")
    public String list(String page, String keyword, HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("result="+page+","+keyword);
        JsonResult jsonResult = new JsonResult();
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(Integer.parseInt(page));
        String addressIp = getClientIpAddr();
        String memberId = getMemberId(request, response);
        if (null == memberId) {
            memberId = "";
        }
        try {
            QueryResult result = vie_doccontestmemberService.searchList(pageBean, keyword, addressIp, memberId);
            if (null == result.getResultList()) {
                jsonResult.setCode(Constants.SUCCESS_CODE);
                jsonResult.setMsg(Constants.SUCCESS_DATA);
            } else {
//				System.out.println("list=" + result.getResultList().size());
                jsonResult.setData(result.getResultList());
                jsonResult.setCode(Constants.SUCCESS_CODE);
                jsonResult.setMsg(Constants.SUCCESS_DATA);
            }
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }
/**
 * 分割线<-------------------------------------------------------------------------------------------------------------------------------------------------------></------------------------------------------------------------------------------------------------------------------------------------------------------->
 */
    /**
     * author jinhao
     * 修改主页背景图片
     *
     * @param imgId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/changeBackground")
    public String changeBackground(String imgId, HttpServletRequest request, HttpServletResponse response) {
        String memberId = getMemberId(request, response);
        JsonResult jsonResult = new JsonResult();
        try {
            List doc = tb_docService.findBySql("select d.FilePath from Tb_doc as d where d.id=?",imgId);
            if (null != doc && null != doc.get(0)){
                tb_memberService.updateBg(doc.get(0).toString(),memberId);
                jsonResult.setCode(Constants.SUCCESS_CODE);
                jsonResult.setMsg(Constants.SUCCESS_DATA);
            }else{
                jsonResult.setCode("-1");
                jsonResult.setMsg("图片不存在");
            }
        }
        catch (Exception e)
        {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("fail");
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 上传作品
     * @param desc
     * @param albumId
     * @param categoryId
     * @param isPublic
     * @param label
     * @param matchId
     * @param title
     * @param docType
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/upload" )
    public String upload(String id,String desc, String albumId , String categoryId, String isPublic, String label, String matchId,
                         String title, String docType, MultipartFile file,String spotId,byte deviceType, HttpServletRequest request, HttpServletResponse response)
    {
        String memberId = getMemberId(request, response);
        JsonResult jsonResult = new JsonResult();
        Object[] res = tb_memberService.getMemInfo(memberId); //  用户，作者
        Tb_doc tb_doc = new Tb_doc();   //上传作品
        Long len = file.getSize();  //文件大小
        BigDecimal space = null;

        //获取access_token
        String access_token = WeixinSign.getAccess_token();
        Boolean descSafe = SenInfoCheckUtil.cotentFilter(access_token, desc);
        if (descSafe == false){
            jsonResult.setCode("3");
            jsonResult.setMsg("作品描述含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        Boolean titleSafe = SenInfoCheckUtil.cotentFilter(access_token, title);
        if (titleSafe == false){
            jsonResult.setCode("4");
            jsonResult.setMsg("作品标题含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }

        if (null == spotId) //不是旅游景点作品
            spotId = "0";
        try {
            //查询会员留言设置
            List<Tb_membermessagesteup> liuyan = tb_membermessagesteupService.findAll();
            if (liuyan != null && liuyan.size() > 0) {
                String[] jinyong = liuyan.get(0).getDisablewords().split("[,，]");
                for (String s : jinyong) {
                    if (null != title)
                        title = title.replaceAll(s, liuyan.get(0).getReplacewords());
                    if (null != desc)
                        desc = desc.replaceAll(s, liuyan.get(0).getReplacewords());
                }
            }
            //得到当前文件大小，单位GB	比较用户剩余存储容量
            BigDecimal fileLen = new BigDecimal(len);
            BigDecimal ls = new BigDecimal((1024 * 1024));
            fileLen = fileLen.divide(ls);
            ls = new BigDecimal(1024);
            fileLen = fileLen.divide(ls);//divide除
            //拿到剩余所有空间
            space = new BigDecimal(res[0].toString());
            if (space.compareTo(fileLen) == -1) {
                jsonResult.setCode("1");
                jsonResult.setMessage("您的空间容量不足！");
                return ajaxJson(JSONObject.toJSONString(jsonResult), response);
            }
            //计算剩余空间
            space = space.subtract(fileLen);//subtract减
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
        }
        catch (Exception e)
        {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("fail");
            log.debug("异常:"+e);
        }

        //上传图片到服务器临时保存的名字为文件原名
        try{
            String fileName = file.getOriginalFilename();
            //获取文件需要上传到的路径
            String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
            String webappRoot = classpath.replaceAll("file/", "");
            // 设置格式,图片

            //保存文件到临时目录
            String savePath = webappRoot + file.getOriginalFilename();      //文件本地临时存储位置
            File saveFile = new File(savePath);
            if ("1".equals(docType))
                file.transferTo(saveFile);
            else {
                //图片超过20M不能上传
                if (len>=(20*1024*1024)){
                    jsonResult.setCode("110");
                    jsonResult.setMessage("图片超过20M，作品上传失败");
                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                }
                OutputStream out = null;
                try {
                    out = new FileOutputStream(saveFile);
                    InputStream in = file.getInputStream();
                    int length = 0;
                    byte[] buf = new byte[1024*1024*20];
                    // in.read(buf) 每次读到的数据存放在buf 数组中
                    while ((length = in.read(buf)) != -1) {
                        //在buf数组中取出数据写到（输出流）磁盘上
                        out.write(buf, 0, length);
                    }
                    in.close();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Integer width = null;
            Integer height = null; //作品高
            OSSClientUtil oss = new OSSClientUtil();    //上传到阿里云
            String time = DateUtil.DateToString(DateUtil.getDate(),"YYYY-MM-dd")+"/";   //存储时间文件夹
            oss.setFOLDER(time);

            //读取文件信息，上传OSS,删除临时文件,返回文件远程路径
            String videoOnePath = null; //第一帧图片的路径
            String videoOne = null;
            File upOne = null;
            InputStream inputStream = null;
            if("1".equals(docType)){   //视频类型，获取第一帧图片
                videoOne = "videoOne";	//第一帧图片文件名
                Map m = VideoUtil.randomGrabberFFmpegImage(savePath,webappRoot,videoOne);	//返回图片路径，高宽
                if (null != m){
                    videoOnePath = (String)m.get("filePath"); //获取第一帧图片的路径
                    width = (Integer)m.get("width");
                    height = (Integer)m.get("height");
                }
                //上传作品和第一帧图片 ，返回远程路径
                upOne = new File(videoOnePath);
                inputStream = new FileInputStream(upOne);
                //生成随机文件名
                String videoOneName = oss.fileName(videoOnePath);
                String key1 = oss.uploadFile2OSS(inputStream,videoOneName);
                if ("".equals(key1)){
                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                    jsonResult.setMsg("上传服务器失败");
                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                }
                //获取远程url
                String url1 = oss.getImgUrl(videoOneName);
                tb_doc.setPhonethumbnailpathimg(oss.getReplace()+time+videoOneName);  //保存视频第一帧图远程路径
                //删除本地第一帧图片文件
                upOne.delete();
            }
            if ("0".equals(docType)){
                File picture = new File(savePath);
                FileInputStream fi = new FileInputStream(picture);
                BufferedImage sourceImg = null;
                try {
                    sourceImg = ImageIO.read(fi);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    width = sourceImg.getWidth();
                    height = sourceImg.getHeight();
                }catch (Exception e){
                    fi.close();
                    oss.destory();
                    picture.delete();
                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                    jsonResult.setMsg("fail!");
                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                }finally {
                    fi.close();
                }
            }
            //------------------------------------------------------------------------------------------
            //上传作品到云库
            File up = new File(savePath);
            inputStream = new FileInputStream(up);

            //生成随机文件名
            String fn = oss.fileName(fileName);
            String key = oss.uploadFile2OSS(inputStream,fn);
            if ("".equals(key)){
                jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                jsonResult.setMsg("上传服务器失败");
                return ajaxJson(JSONObject.toJSONString(jsonResult), response);
            }

            //获取远程url
            String url = oss.getImgUrl(fn);
            //图片鉴黄
            String str=oss.getReplace()+time+fn;
            if ("0".equals(docType)) {
                Boolean isSafe = SenInfoCheckUtil.imgFilter(access_token, file);
                if (isSafe == false){
                    jsonResult.setCode("2");
                    jsonResult.setMsg("图片不合法");
                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                }
//                String newStr=str.substring(str.length()-3);
//                String tags=null;
//                if(newStr!="GIF" && !"GIF".equals(newStr)){
//                    JSONObject json=JSONObject.parseObject(AppAliyunImg.CheckImage(str));
//                    //String detect= json.getJSONObject("result").getJSONObject("detect").get("body").toString();
//                    tags= json.getJSONObject("result").getJSONObject("porn").get("body").toString();
//                }
//                if (!Util.isnull(tags)) {
//                    if (tags.indexOf("色情") != -1)
//                        tb_doc.setDocstatus(0);
//                    else
//                        tb_doc.setDocstatus(1);
//                } else
//                    tb_doc.setDocstatus(0);
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
            }else
                tb_doc.setDocstatus(1);

            tb_doc.setFilepath(str);    //保存文件远程路径

            inputStream.close();	//关闭资源
            oss.destory();  //关闭资源
            //删除本地文件
            if(null != upOne)
                upOne.delete();
            up.delete();

            tb_doc.setId(fn.substring(0,fn.lastIndexOf(".")));  //作品id

            tb_doc.setFilelength(len.intValue());
            tb_doc.setStorageformat(fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase());
            //图片宽高
            tb_doc.setIwidht(width);
            tb_doc.setIheight(height);
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
        }
        catch (Exception e)
        {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("fail");
            log.debug("异常:"+e);
        }

        //作品其他信息
        try{
            tb_doc.setDevicetype(deviceType);       //设备类型
            if(null != title)
                tb_doc.setDoctitle(title);
            if(null != docType) {
                tb_doc.setFiletype(Integer.parseInt(docType));
            }
            if(null != desc)
                tb_doc.setFiledescribe(URLEncoder.encode(desc,"utf-8"));
            tb_doc.setUploadtime(DateUtil.getDate());
            if("0".equals(docType)) {
                if ("0".equals(id)) {     //不是组图
                    tb_doc.setFid(tb_doc.getId());
                    tb_doc.setIsdouble(0);
                } else {     //是组图
                    if ("1".equals(id)) {    //第一张标记
                        tb_doc.setFid(tb_doc.getId());
                        tb_doc.setIsdouble(1);
                    } else {     //后面的
                        System.out.println("不是第一次的组图id=" + id);
                        tb_doc.setFid(id);
                        tb_doc.setIsdouble(1);
                    }
                }
            }else {
                tb_doc.setFid(tb_doc.getId());
                tb_doc.setIsdouble(0);
            }

            Tb_member result = new Tb_member();
            result.setId(memberId);
            tb_doc.setMemberid(result);
            tb_doc.setCreatedate(new Date().getTime());
            tb_doc.setIsdelete(0);
            tb_doc.setIsparticipating(0);
            tb_doc.setIsportrait(0);
            tb_doc.setFilescore("0");
            tb_doc.setFileviewcount(0);
            tb_doc.setFilegoodcount(0);
            tb_doc.setFilekeepcount(0);
            tb_doc.setFilecommentscount(0);
            tb_doc.setFilepaycount(0);
            tb_doc.setFileserverid(0);
            tb_doc.setActityforwarcount(0);
            tb_doc.setIsboutique(0);
            if(null !=isPublic) {
                tb_doc.setIspublic(Integer.parseInt(isPublic));    //公开状态
                if ("1".equals(isPublic)) {
                    tb_doc.setPublictime(DateUtil.getDate());
                }
            }else {
                tb_doc.setIspublic(1);
                tb_doc.setPublictime(DateUtil.getDate());
            }
            Tb_photoalbum photoalbum = new Tb_photoalbum(); //作品所属相册
            if(null != albumId) {
                if ("0".equals(albumId))
                    photoalbum.setId(1);
                else
                    photoalbum.setId(Integer.parseInt(albumId));
            }else
                photoalbum.setId(1);
            tb_doc.setPhotoalbumid(photoalbum);

            Tb_filetype filetypeId = new Tb_filetype();   //作品所属类别
            if(null != categoryId){
                if("0".equals(categoryId))
                    filetypeId.setId(1);
                else
                    filetypeId.setId(Integer.parseInt(categoryId));
            }else {
                    filetypeId.setId(14);
            }
            if (!"0".equals(spotId))
                filetypeId.setId(16);
            tb_doc.setFiletypeid(filetypeId);

            iTb_docService.addDoc(tb_doc,res,label,matchId,memberId,space.toString(),spotId);


//            System.out.println("返回Fid==="+tb_doc.getFid());
            Map data = new HashMap();
            data.put("id",tb_doc.getFid());
            jsonResult.setData(data);
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("fail");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }


    /**
     * author jinhao
     * 获取上传相关数据
     *
     * @return
     */
    @RequestMapping(value = "/session/uploadData")
    public String uploadData(HttpServletResponse response, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request, response);
            if (null != memberId) {
                //赛事列表
                Date date = DateUtil.getDate();
                List<Object[]> cons = tb_contestService.curContest();
                List<Map> match = new ArrayList<>();
                for (int i = 0; i < cons.size(); i++) {
                    Map map = new HashMap();
                    Object[] ob = cons.get(i);
                    map.put("id", Integer.parseInt(ob[0].toString()));
                    map.put("name", ob[1].toString());
                    match.add(map);
                }
                //类别列表
                List<Object[]> result = tb_filetypeService.ListFileType();
                //相册列表
                List<Object[]> photoalbums = tb_photoalbumService.memAlbum(memberId);
                List<Map> album = new ArrayList<>();
                for (int i = 0; i < photoalbums.size(); i++) {
                    Map mapp = new HashMap();
                    mapp.put("id", photoalbums.get(i)[0]);
                    mapp.put("name", photoalbums.get(i)[1]);
                    album.add(mapp);
                }

                Map data = new HashMap();
                data.put("album", album);
                data.put("match", match);
                data.put("type", Tb_filetypeUtil.getControllerListH(result));
//				data.put("album", album);
                jsonResult.setData(data);
                jsonResult.setCode("0");
                jsonResult.setMessage("执行成功！");
            } else {
                jsonResult.setCode("-1");
                jsonResult.setMessage("请先授权登录");
            }
        } catch (Exception e) {
            jsonResult.setCode("1");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * author jinhao
     * 根据作品ID获取作品详情
     *
     * @param docid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getWorksDetail")
    public String getWorksDetail(String docid, HttpServletRequest request, HttpServletResponse response) {
        JsonResult jsonResult = new JsonResult();
        try {
            String memberid = getMemberId(request, response);
            String opId = getOpenId(request, response);
            //作品信息
            List<Object[]> docl = tb_docService.getByDocId(docid);
            if (null != docl) {
                Object[] ob = docl.get(0);
                //是否关注作者
                boolean isFocus = false;
                if (null != memberid)
                    isFocus = iTre_friendscircleService.idFucos(memberid, ob[0].toString());
                //控制返回数据格式
                Map<Object, Object> map = new HashMap<Object, Object>();
                map.put("member_id", ob[0].toString());
                map.put("user_name", ob[1].toString());
                if (null != ob[2]) {
                    map.put("type_id", Integer.parseInt(ob[2].toString()));
                    map.put("type_name", ob[3].toString());
                }
                map.put("rank", Integer.parseInt(ob[4].toString()));
                map.put("user_portrait", ob[5].toString() + Constants.DOC_PATH_END1);
                map.put("fileType", Integer.parseInt(ob[6].toString()));
                if ("1".equals(ob[6].toString())) {
                    map.put("images", ob[7].toString());
                    map.put("type_id", -1);
                } else
                    map.put("images", ob[7].toString() + Constants.DOC_PATH_END2);
                if (null != ob[8])
                    map.put("title", ob[8].toString());
                else
                    map.put("title","");
                if (null != ob[9])
                    map.put("describe", URLDecoder.decode(ob[9].toString(),"utf-8"));
                if (null != ob[10])
                    map.put("view_num", Integer.parseInt(ob[10].toString()) + 1);
                map.put("good_num", ob[11].toString());
                map.put("collect_num", ob[12].toString());
                map.put("height", 0);
                map.put("iheight", ob[13].toString());
                map.put("iwidht", ob[14].toString());
                //查询今天是否点过赞
                if (null != opId) {
                    int result = tb_docgoodService.isgood(docid, opId);
                    if (result > 0)
                        map.put("isGood", 1);
                    else
                        map.put("isGood", 0);
                } else
                    map.put("isGood", 0);

                //点赞用户
                List<Object[]> list = tb_docgoodService.goodDocList(docid);
                List<Map> goodList = new ArrayList<>();
                if (null != list) {
                    for (int i = 0; i < list.size(); i++) {
                        Map ms = new HashMap();
                        Object[] d = (Object[]) list.get(i);
                        ms.put("userId", d[0].toString());
                        ms.put("userHeading", d[1].toString() + Constants.DOC_PATH_END1);
                        goodList.add(ms);
                    }
                }
                map.put("goodList", goodList);
                //是否收藏该作品
                if (null != memberid) {
                    int keeps = tb_dockeepService.isKeepDocId(docid, memberid);
                    if (keeps > 0)
                        map.put("isKeep", 1);
                    else
                        map.put("isKeep", 0);
                } else
                    map.put("isKeep", 0);
                //上传时间
                String uploadtime = ob[15].toString();
                Date upload = null;
                if (-1 != uploadtime.lastIndexOf("."))
                    upload = DateUtil.StringToDate(uploadtime.substring(0, uploadtime.lastIndexOf(".")), "yyyy-MM-dd HH:mm:ss");
                else
                    upload = DateUtil.StringToDate(uploadtime, "yyyy-MM-dd HH:mm:ss");
                String diffDate = DateUtil.getDatePoor(DateUtil.getDate(), upload);
                map.put("upload_time", diffDate);
                //是否关注作者
                if (isFocus)
                    map.put("is_follow", 1);
                else
                    map.put("is_follow", 0);

                //作品标签
                List doclabels = filetolabel.findByDocId(docid);
                List<String> labelList = new ArrayList<>();
                for (int i = 0; i < doclabels.size(); i++) {
                    labelList.add(doclabels.get(i).toString());
                }
                map.put("label", labelList);
                //赛事信息
                List<Object[]> doccontests = tre_doccontestService.getByDocid(docid);
                List matchL = new ArrayList();
                for (int i = 0; i < doccontests.size(); i++) {
                    Map<Object, Object> mapContest = new HashMap<Object, Object>();
                    Object[] o = doccontests.get(i);
                    mapContest.put("match_id", Integer.parseInt(o[0].toString()));
                    mapContest.put("match_name", o[1].toString());
                    mapContest.put("match_status",o[2].toString());
                    matchL.add(mapContest);
                }
                //修改图片被浏览次数
                tb_docService.updateViewNum(docid, Integer.parseInt(ob[10].toString()) + 1);
                map.put("match", matchL);
                jsonResult.setData(map);
                jsonResult.setCode("0");
                jsonResult.setMsg("success");
            } else {
                jsonResult.setCode("1");
                jsonResult.setMsg("作品不存在");
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
     * 根据作品ID获取作品详情
     *
     * @param docid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/groupDetail")
    public String groupDetail(String docid, HttpServletRequest request, HttpServletResponse response) {
        JsonResult jsonResult = new JsonResult();
        try {
            String memberid = getMemberId(request, response);
            String opId = getOpenId(request, response);
            //作品信息
            List<Object[]> docl = tb_docService.getByDocId(docid);
            Object[] ob = docl.get(0);
            if (null != ob[16]) {
                //控制返回数据格式
                Map<Object, Object> map = new HashMap<Object, Object>();
                //是否关注作者
                boolean isFocus = false;
                if (null != memberid)
                    isFocus = iTre_friendscircleService.idFucos(memberid, ob[0].toString());
                //是否关注作者
                if (isFocus)
                    map.put("is_follow", 1);
                else
                    map.put("is_follow", 0);
                map.put("member_id", ob[0].toString());
                map.put("user_name", ob[1].toString());
                if (null != ob[2]) {
                    map.put("type_id", Integer.parseInt(ob[2].toString()));
                    map.put("type_name", ob[3].toString());
                }
                map.put("rank", Integer.parseInt(ob[4].toString()));
                map.put("user_portrait", ob[5].toString() + Constants.DOC_PATH_END1);

                if ("1".equals(ob[6].toString())) {
                    map.put("images", ob[7].toString());
                    map.put("type_id", -1);
                } else
                    map.put("images", ob[7].toString() + Constants.DOC_PATH_END2);

                //本张图不是是组图第一张
                if (null != ob[16]) {
                    if (!docid.equals(ob[16].toString())) {
                        List<Object[]> docs = tb_docService.getByDocId(ob[16].toString());
                        ob = docs.get(0);
                    }
                }
                map.put("fileType", Integer.parseInt(ob[6].toString()));
                if (null != ob[8])
                    map.put("title", ob[8].toString());
                else
                    map.put("title", "");
                if (null != ob[9])
                    map.put("describe", URLDecoder.decode(ob[9].toString(),"utf-8"));
                if (null != ob[10])
                    map.put("view_num", Integer.parseInt(ob[10].toString()) + 1);
                map.put("good_num", ob[11].toString());
                map.put("collect_num", ob[12].toString());
                map.put("height", 0);
                map.put("iheight", ob[13].toString());
                map.put("iwidht", ob[14].toString());
                //查询今天是否点过赞
                if (null != opId) {
                    int result = tb_docgoodService.isgood(docid, opId);
                    if (result > 0)
                        map.put("isGood", 1);
                    else
                        map.put("isGood", 0);
                } else
                    map.put("isGood", 0);

                //点赞用户
                List<Object[]> list = tb_docgoodService.goodDocList(docid);
                List<Map> goodList = new ArrayList<>();
                if (null != list) {
                    for (int i = 0; i < list.size(); i++) {
                        Map ms = new HashMap();
                        Object[] d = (Object[]) list.get(i);
                        ms.put("userId", d[0].toString());
                        ms.put("userHeading", d[1].toString() + Constants.DOC_PATH_END1);
                        goodList.add(ms);
                    }
                }
                map.put("goodList",goodList);

                //查询组图列表
                List<Map> lm = new ArrayList<>();
                List<Object[]> groupList = tb_docgoodService.groupList(ob[16].toString());
                for (Object[] o : groupList) {
                    Map m = new HashMap();
                    m.put("iwidht", o[1].toString());
                    m.put("iheight", o[2].toString());
                    m.put("images", o[3].toString() + Constants.DOC_PATH_END2);
                    lm.add(m);
                }
                map.put("groupList",lm);

                //是否收藏该作品
                if (null != memberid) {
                    int keeps = tb_dockeepService.isKeepDocId(ob[16].toString(), memberid);
                    if (keeps > 0)
                        map.put("isKeep", 1);
                    else
                        map.put("isKeep", 0);
                } else
                    map.put("isKeep", 0);
                //上传时间
                String uploadtime = ob[15].toString();
                Date upload = null;
                if (-1 != uploadtime.lastIndexOf("."))
                    upload = DateUtil.StringToDate(uploadtime.substring(0, uploadtime.lastIndexOf(".")), "yyyy-MM-dd HH:mm:ss");
                else
                    upload = DateUtil.StringToDate(uploadtime, "yyyy-MM-dd HH:mm:ss");
                String diffDate = DateUtil.getDatePoor(DateUtil.getDate(), upload);
                map.put("upload_time", diffDate);

                //作品标签
                List doclabels = filetolabel.findByDocId(ob[16].toString());
                List<String> labelList = new ArrayList<>();
                for (int i = 0; i < doclabels.size(); i++) {
                    labelList.add(doclabels.get(i).toString());
                }
                map.put("label", labelList);
                //赛事信息
                List<Object[]> doccontests = tre_doccontestService.getByDocid(ob[16].toString());
                List matchL = new ArrayList();
                for (int i = 0; i < doccontests.size(); i++) {
                    Map<Object, Object> mapContest = new HashMap<Object, Object>();
                    Object[] o = doccontests.get(i);
                    mapContest.put("match_id", Integer.parseInt(o[0].toString()));
                    mapContest.put("match_name", o[1].toString());
                    matchL.add(mapContest);
                }
                //修改图片被浏览次数
                tb_docService.updateViewNum(ob[16].toString(), Integer.parseInt(ob[10].toString()) + 1);
                map.put("match", matchL);
                jsonResult.setData(map);
                jsonResult.setCode("0");
                jsonResult.setMsg("success");
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
     * 获取最近30天搜索次数最多的热门搜索词
     *
     * @param page
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTopKeyword")
    public String getkeywordList(String page, HttpServletResponse response, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        try {
            List<T_topsearchterm> list = t_topsearchtermService.getByDate(page);
            List result = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map value = new HashMap();
                value.put("id", list.get(i).getFd_searchnumber());
                value.put("keyword", list.get(i).getFdSearchterm());
                result.add(value);
            }
            jsonResult.setData(result);
            jsonResult.setCode("0");
            jsonResult.setMsg("success");
        } catch (Exception e) {
            jsonResult.setCode("500");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * author jinhao
     * 获取作品信息
     *
     * @param imgId
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/session/getWorksInfo")
    public String getWorksInfo(String imgId, HttpServletResponse response, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request, response);
            List<Object[]> doc = tb_docService.getWorksInfo(imgId);
            if (null != doc.get(0)) {
                Object[] obj = doc.get(0);
                if (memberId.equals(obj[0].toString())) {
                    Map map = new HashMap();
                    map.put("doc_id",imgId);
                    if (null != obj[1])
                        map.put("doc_title",obj[1].toString());
                    else
                        map.put("doc_title","");
                    map.put("ispublic",Integer.parseInt(obj[2].toString()));
                    map.put("iheight",Integer.parseInt(obj[3].toString()));
                    map.put("iwidht",Integer.parseInt(obj[4].toString()));
                    if(null != obj[5])
                        map.put("isMatch",Integer.parseInt(obj[5].toString()));
                    else
                        map.put("isMatch",0);
                    if (null != obj[8]) {
                        map.put("type_id", Integer.parseInt(obj[7].toString()));
                        map.put("type_name", obj[8].toString());
                    }else{
                        map.put("type_id", 0);
                        map.put("type_name", "未分类");
                    }
                    if(null != obj[11]) {
                        map.put("album_name", obj[12].toString());
                        map.put("album_id", Integer.parseInt(obj[11].toString()));
                    }
                    if(null != obj[6])
                        map.put("describe",URLDecoder.decode(obj[6].toString(),"utf-8"));
                    else
                        map.put("describe","");
                    List doclabels = filetolabel.findByDocId(imgId);
                    StringBuffer labelList = new StringBuffer();
                    for(int i = 0; i<doclabels.size(); i++){
                        labelList.append(doclabels.get(i).toString());
                        if (i != doclabels.size()-1)
                            labelList.append(",");
                    }
                    map.put("label",labelList.toString());
                    if (Integer.parseInt(obj[9].toString())==1)
                        map.put("img_path",obj[10].toString());
                    if (Integer.parseInt(obj[9].toString())==0)
                        map.put("img_path",obj[10].toString()+Constants.DOC_PATH_END2);
                    List<Object[]> type_list = tb_filetypeService.ListFileType();
                    List item = new ArrayList();
                    for (Object[] ob : type_list) {
                        Map<Object, Object> p = new HashMap<Object, Object>();
                        if((Integer)ob[0] == 14)
                            p.put("id", -1);
                        else
                            p.put("id", ob[0]);
                        p.put("type", ob[1]);
                        item.add(p);
                    }
                    map.put("type_list",item);
                    List<Object[]> albums = iTb_photoalbumService.findBySql("SELECT Id,PhotoAlbumName FROM [dbo].[TB_PhotoAlbum] where MemberId = ?",obj[0].toString());
                    List<Map> albumList = new ArrayList<>();
                    for (Object[] o:albums) {
                        Map m = new HashMap();
                        m.put("album_id",o[0]);
                        m.put("album_name",o[1]);
                        albumList.add(m);
                    }
                    map.put("albumList",albumList);
                    jsonResult.setData(map);
                    jsonResult.setCode("0");
                    jsonResult.setMsg("success");
                } else {
                    jsonResult.setCode("-2");
                    jsonResult.setMsg("非本人作品");
                }
            } else {
                jsonResult.setCode(Constants.DATA_NOTEXIST);
                jsonResult.setMsg("文件不存在");
            }
        } catch (Exception e) {
            jsonResult.setCode("1");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * author jinhao
     * 修改作品信息
     *
     * @param imgId
     * @param ispublic
     * @param albumId
     * @param categoryId
     * @param desc
     * @param title
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/session/changeWorksInfo")
    public String changeWorksInfo(String imgId, String ispublic, String albumId, String categoryId,
                                  String desc, String title, String label, HttpServletResponse response, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        String memberId = getMemberId(request, response);
        try {
            Tb_doc doc = tb_docService.getByKey(imgId);
            if (null != doc) {
                if (memberId.equals(doc.getMemberid().getId())) {
                    doc.setIspublic(Integer.parseInt(ispublic));
                    doc.setDoctitle(title);
                    doc.setFiledescribe(URLEncoder.encode(desc,"utf-8"));
                    if (null != albumId && albumId.length() > 0) {
                        Tb_photoalbum tb_photoalbum = new Tb_photoalbum();
                        tb_photoalbum.setId(Integer.parseInt(albumId));
                        doc.setPhotoalbumid(tb_photoalbum);
                    }
                    if (null != categoryId && categoryId.length() > 0) {
                        Tb_filetype tb_filetype = new Tb_filetype();
                        tb_filetype.setId(Integer.parseInt(categoryId));
                        doc.setFiletypeid(tb_filetype);
                    }
                    //添加标签
                    if (null != label && label.length() > 0) {
                        //删除原来的标签，添加新的标签
                        String whereHql = "docid.id='" + imgId + "'";
                        tre_docfilelabelService.delete(whereHql);
                        String[] strs = label.split("[,;，；、]");
                        for (String str : strs) {
                            List<Tb_filelabel> count = filelabel.findByHQL("from Tb_filelabel where name = ?", str);
                            //int macid1 = tre_docfilelabelService.getMaxId(Tre_docfilelabel.class);
                            Tb_filelabel l = null;
                            if (count.size() == 0) {    //添加标签表，作品标签表数据
                                //int maxid2 = filelabel.getMaxId(Tb_filelabel.class);
                                l = new Tb_filelabel();
                                //l.setId(maxid2 + 1);
                                l.setName(str);
                                filelabel.save(l);
                            } else
                                l = count.get(0);
                            //作品标签表数据
                            Tre_docfilelabel dl = new Tre_docfilelabel();
                            //dl.setId(macid1 + 1);
                            dl.setLabelid(l);
                            dl.setDocid(doc);
                            tre_docfilelabelService.save(dl);
                        }
                    }
                    tb_docService.update(doc);
                    jsonResult.setCode("0");
                    jsonResult.setMsg("success");
                } else {
                    jsonResult.setCode("-2");
                    jsonResult.setMsg("不能修改他人作品信息");
                }
            } else {
                jsonResult.setCode(Constants.DATA_NOTEXIST);
                jsonResult.setMsg("文件不存在");
            }
        } catch (Exception e) {
            jsonResult.setCode("500");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 公开图片
     *
     * @param memberId
     * @param docid
     * @param ispublic
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/session/ispublic")
    public String ispublic(String memberId, String docid, String ispublic, HttpServletResponse response, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        try {
            if (null != docid) {
                tb_docService.updateIspublic(docid, Integer.parseInt(ispublic));
            } else {
                jsonResult.setCode(Constants.DATA_NOTEXIST);
                jsonResult.setMsg("文件不存在");
            }
        } catch (Exception e) {
            jsonResult.setCode("500");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * author jinhao
     * 用户点赞操作
     *
     * @param docid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/good")
    public String good(String docid, HttpServletRequest request, HttpServletResponse response) {
        JsonResult jsonResult = new JsonResult();
        try {
            String opid = getOpenId(request, response);
            String memberId = getMemberId(request, response);
            Tb_docgood tb_docgood = new Tb_docgood();
            // 查询文件信息
            Tb_doc docresult = tb_docService.getByKey(docid);
            if (null != memberId) {
                Tb_member member = tb_memberService.getByKey(memberId);
                tb_docgood.setMemberid(member);
            }
            tb_docgood.setDocid(docresult);
            //将OpenId存入  设备id
            tb_docgood.setDeviceid(opid);

            String msg = StringUtils.isObject(
                    new Object[]{tb_docgood.getDocid()},
                    new String[]{"点赞文件不能为空"});
            if (!msg.equals("")) {
                jsonResult.setCode("1");
                jsonResult.setMsg(msg);
                return ajaxJson(JSONObject.toJSONString(jsonResult), response);
            }
            //List<Tb_docgood> result = tb_docgoodService.listgood(tb_docgood.getDocid().getId(), null, tb_docgood.getDeviceid());

            // 查询文件id与会员id是不是存在，存在则修改点赞数，不存在则新增数据
            StringBuffer str = new StringBuffer();
            str.append(" SELECT d from Tb_docgood d where 1=1 and d.docid.id = '"
                    + tb_docgood.getDocid().getId() + "' "); // 初始化语句
            if (null != memberId) {
                str.append(" and d.memberid.id ='" + memberId
                        + "'");
            }
            List<Tb_docgood> listdocgood = tb_docgoodService.findByHQL(str.toString());

            Tb_member memb = docresult.getMemberid();
//			String time = (new Date()).toString();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = f.format(new Date()).toString();

            if (listdocgood.size() > 0) {
                if (DateUtil.areSameDay(DateUtil.getDate(), listdocgood.get(0).getTodaytime())) {
                    // 这里判断今天是否点过赞
                    jsonResult.setCode("1");
                    jsonResult.setMsg("您今天已点过赞！请明天再来！");
                } else {
                    //------------------参赛---------------------------------------------------------------
                    // 如果参赛，需要修改TRE_MenberDocScore用户评分历史表，修改文件表的评分数
                    // 如果会员是专家或者普通用户，点赞分数获取从TB_Contest赛事表获取 TRE_DocContest参赛作品表

                    if (docresult.getIsparticipating() == 1) {
                        String docstr = "SELECT d from Tre_doccontest d where 1=1 and d.doc_id = '" + tb_docgood.getDocid().getId() + "' ";
                        List<Tre_doccontest> dc = tre_doccontestService.findByHQL(docstr.toString());
                        if (null != dc && dc.get(0).getContest_id().getConteststatus() == 0) {    //参赛的状态为征集中
                            List<Tre_menberdocscore> dt = null;
                            if (null != tb_docgood.getMemberid()) {
                                String sdt = "SELECT d from Tre_menberdocscore d where 1=1 and d.docid = '"
                                        + tb_docgood.getDocid().getId()
                                        + "' and d.memberid='"
                                        + tb_docgood.getMemberid().getId() + "'";
                                dt = tre_menberdocscoreService
                                        .findByHQL(sdt.toString());
                            }
                            if (null != tb_docgood.getDeviceid()) {
                                if (dc.size() > 0)
                                    docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())));
                            }
                            if (null != tb_docgood.getMemberid()) {// 如果用户存在
                                if (dt.size() > 0) {
                                    Tre_menberdocscore md = tre_menberdocscoreService
                                            .findByPrimaryKey(dt.get(0).getId());// 用户评分历史表
                                    md.setId(dt.get(0).getId());
                                    if (tb_docgood.getMemberid().getGroupid().getId() == 3) {// 如果会员是专家，修改评分
                                        if (dc.size() > 0) {
                                            md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                    + Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore())) + "");// 获取专家点赞分数，新增评分数
                                            docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore())));
                                            List<Tb_groupdocgood> gdg = null;
                                            String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
                                                    + tb_docgood.getDocid().getId()
                                                    + "' and d.member_id='"
                                                    + tb_docgood.getMemberid().getId() + "' ";
                                            gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
                                            Tb_groupdocgood good = null;
                                            if (null != gdg && gdg.size() > 0) {
                                                good = gdg.get(0);
                                                good.setGoodcound(gdg.get(0).getGoodcound() + 1);
                                                good.setTodaytime(new Date());
                                                tb_groupdocgoodService.update(good);
                                            } else {
                                                good = new Tb_groupdocgood();
                                                good.setMember_id(tb_docgood.getMemberid());
                                                good.setDoc_id(tb_docgood.getDocid());
                                                good.setGoodcound(1);
                                                good.setContest_id(dc.get(0).getContest_id());
                                                good.setContesttheme_id(dc.get(0).getContesttheme_id());
                                                good.setTodaytime(new Date());
                                                tb_groupdocgoodService.save(good);
                                            }
                                        }
                                    } else if (tb_docgood.getMemberid().getGroupid().getId() == 1) {// 普通会员
                                        if (dc.size() > 0) {
                                            md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                    + Double.parseDouble(dc.get(0).getContest_id().getGoodscore())) + "");// 获取普通用户点赞分数
                                            docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getGoodscore())));
                                        }
                                    } else if (tb_docgood.getMemberid().getGroupid().getId() == 2) {// 大众评委
                                        if (dc.size() > 0) {
                                            md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                    + Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore())) + "");// 获取大众评委点赞分数
                                            docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore())));
                                        }
                                    } else {//游客
                                        if (dc.size() > 0) {
                                            md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                    + Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())) + "");// 获取游客用户点赞分数
                                            docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())));
                                        }
                                    }
                                    md.setScoretime(time);
                                    tre_menberdocscoreService.update(md);// 修改用户评分历史表
                                } else {
                                    Tre_menberdocscore md = new Tre_menberdocscore();
                                    if (tb_docgood.getMemberid().getGroupid().getId() == 3) {// 如果会员是专家
                                        if (dc.size() > 0) {
                                            md.setScore(dc.get(0).getContest_id().getExpertsgoodscore());// 获取专家点赞分数，新增评分数
                                            List<Tb_groupdocgood> gdg = null;
                                            String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
                                                    + tb_docgood.getDocid().getId()
                                                    + "' and d.member_id='"
                                                    + tb_docgood.getMemberid().getId() + "' ";
                                            gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
                                            Tb_groupdocgood good = null;
                                            if (null != gdg && gdg.size() > 0) {
                                                good = new Tb_groupdocgood();
                                                good.setId(gdg.get(0).getId());
                                                good.setGoodcound(gdg.get(0).getGoodcound() + 1);
                                                good.setTodaytime(new Date());
                                                tb_groupdocgoodService.update(good);
                                            } else {
                                                good = new Tb_groupdocgood();
                                                good.setMember_id(tb_docgood.getMemberid());
                                                good.setDoc_id(tb_docgood.getDocid());
                                                good.setGoodcound(1);
                                                good.setContest_id(dc.get(0).getContest_id());
                                                good.setContesttheme_id(dc.get(0).getContesttheme_id());
                                                good.setTodaytime(new Date());
                                                tb_groupdocgoodService.save(good);
                                            }
                                        }
                                    } else if (tb_docgood.getMemberid().getGroupid().getId() == 1) {// 普通会员
                                        if (dc.size() > 0) {
                                            md.setScore(dc.get(0).getContest_id().getGoodscore());// 获取普通用户点赞分数
                                        }
                                    } else if (tb_docgood.getMemberid().getGroupid().getId() == 2) {// 大众评委
                                        if (dc.size() > 0) {
                                            md.setScore(dc.get(0).getContest_id().getDazhonggoodscore());// 获取普通用户点赞分数
                                        }
                                    } else {//游客
                                        if (dc.size() > 0) {
                                            md.setScore(dc.get(0).getContest_id().getYoukegoodscore());// 获取普通用户点赞分数
                                        }
                                    }
                                    md.setMemberid(tb_docgood.getMemberid());
                                    md.setDocid(docresult);
                                    md.setScoretime(time);
                                    tre_menberdocscoreService.save(md);// 新增用户评分历史表
                                    docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(md.getScore())));
                                }
                            }
                        }
                    }
                    //--------------------------------------------------------------------------------------------
                    // 修改点赞表
                    tb_docgood.setId(listdocgood.get(0).getId());
                    tb_docgood.setTodaytime(new Date());
                    tb_docgood.setGoodcound(listdocgood.get(0).getGoodcound() + 1);
                    tb_docgoodService.update(tb_docgood);

                    if (null != tb_docgood.getMemberid()) {
                        // 修改会员积分
                        // 查询 会员等级积分来源表点赞数量
                        Double levelIntegral = tb_docgood.getMemberid().getLevelintegral() + Integer.parseInt(tb_docgood.getMemberid().getLevelid().getLevelname());
                        tb_docgood.getMemberid().setLevelintegral(levelIntegral);
                        //判断 两位用户是否满足升级条件
                        if (levelIntegral > Double.valueOf(tb_docgood.getMemberid().getLevelid().getMaxexp()) && tb_docgood.getMemberid().getLevelid().getId() < 4) {
                            Tb_level tb_level = new Tb_level();
                            tb_level.setId(tb_docgood.getMemberid().getLevelid().getId() + 1);
                            tb_docgood.getMemberid().setLevelid(tb_level);
                        }
                        tb_memberService.update(tb_docgood.getMemberid());
                    }
                    // 修改文件表点赞数量
                    if (null != docresult) {
                        docresult.setId(tb_docgood.getDocid().getId());
                        if (tb_docgood.getMemberid().getGroupid().getId() == 2)     //用户是大众评委，可以当作10次点赞
                            docresult.setFilegoodcount(docresult.getFilegoodcount() + 10);
                        else
                            docresult.setFilegoodcount(docresult.getFilegoodcount() + 1);
                    }
                    //被点赞人加积分
                    Double levelIntegral2 = memb.getLevelintegral() + Integer.parseInt(memb.getLevelid().getLevelname());
                    memb.setLevelintegral(levelIntegral2);
                    //判断 两位用户是否满足升级条件
                    if (levelIntegral2 > Double.valueOf(memb.getLevelid().getMaxexp()) && memb.getLevelid().getId() < 4) {
                        Tb_level tb_level = new Tb_level();
                        tb_level.setId(memb.getLevelid().getId() + 1);
                        memb.setLevelid(tb_level);
                    }
                    tb_memberService.update(memb);
                    tb_docService.update(docresult);
                    Map r = new HashMap();
                    r.put("num", docresult.getFilegoodcount());
                    jsonResult.setCode("0");
                    jsonResult.setMsg("success");
                    jsonResult.setData(r);
                }
            } else {

                //------------------参赛---------------------------------------------------------------
                // 如果参赛，需要修改TRE_MenberDocScore用户评分历史表，修改文件表的评分数
                // 如果会员是专家或者普通用户，点赞分数获取从TB_Contest赛事表获取 TRE_DocContest参赛作品表
                String docstr = "SELECT d from Tre_doccontest d where 1=1 and d.doc_id = '"
                        + tb_docgood.getDocid().getId() + "' ";
                List<Tre_doccontest> dc = tre_doccontestService.findByHQL(docstr.toString());

                if (docresult.getIsparticipating() == 1) {
                    String sql = "select * from Tre_doccontest where doc_id='" + tb_docgood.getDocid().getId() + "'";
                    List<Tre_doccontest> con = tre_doccontestService.findlistBySql(Tre_doccontest.class, sql);
                    if (null != con && con.get(0).getContest_id().getConteststatus() == 0) {//参赛的状态为征集中
                        if (null != tb_docgood.getDeviceid()) {
                            if (dc.size() > 0) {
                                docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())));
                            }
                        }
                        if (null != tb_docgood.getMemberid()) {// 如果用户存在
                            List<Tre_menberdocscore> dt = null;
                            if (null != tb_docgood.getMemberid()) {
                                String sdt = "SELECT d from Tre_menberdocscore d where 1=1 and d.docid = '"
                                        + tb_docgood.getDocid().getId()
                                        + "' and d.memberid='"
                                        + tb_docgood.getMemberid().getId() + "' ";
                                dt = tre_menberdocscoreService
                                        .findByHQL(sdt.toString());
                            }
                            if (null != dt && dt.size() > 0) {
                                Tre_menberdocscore md = tre_menberdocscoreService
                                        .findByPrimaryKey(dt.get(0).getId());// 用户评分历史表

                                md.setId(dt.get(0).getId());

                                if (tb_docgood.getMemberid().getGroupid().getId() == 3) {// 如果会员是专家，修改评分
                                    if (dc.size() > 0) {
                                        md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                + Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore())) + "");// 获取专家点赞分数，新增评分数
                                        docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getExpertsgoodscore())));
                                        List<Tb_groupdocgood> gdg = null;
                                        String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
                                                + tb_docgood.getDocid().getId()
                                                + "' and d.member_id='"
                                                + tb_docgood.getMemberid().getId() + "' ";
                                        gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
                                        if (null != gdg && gdg.size() > 0) {
                                            Tb_groupdocgood good = new Tb_groupdocgood();
                                            good.setId(gdg.get(0).getId());
                                            good.setGoodcound(gdg.get(0).getGoodcound() + 1);
                                            good.setTodaytime(new Date());
                                            tb_groupdocgoodService.update(good);
                                        } else {
                                            Tb_groupdocgood good = new Tb_groupdocgood();
                                            good.setMember_id(tb_docgood.getMemberid());
                                            good.setDoc_id(tb_docgood.getDocid());
                                            good.setGoodcound(1);
                                            good.setContest_id(con.get(0).getContest_id());
                                            good.setContesttheme_id(con.get(0).getContesttheme_id());
                                            good.setTodaytime(new Date());
                                            tb_groupdocgoodService.save(good);
                                        }
                                    }
                                } else if (tb_docgood.getMemberid().getGroupid().getId() == 1) {// 普通会员
                                    if (dc.size() > 0) {
                                        md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                + Double.parseDouble(dc.get(0).getContest_id().getGoodscore())) + "");// 获取普通用户点赞分数
                                        docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getGoodscore())));
                                    }
                                } else if (tb_docgood.getMemberid().getGroupid().getId() == 2) {// 大众评委
                                    if (dc.size() > 0) {
                                        md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                + Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore())) + "");// 获取大众评委点赞分数
                                        docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getDazhonggoodscore())));
                                    }
                                } else {//游客
                                    if (dc.size() > 0) {
                                        md.setScore((Double.parseDouble(dt.get(0).getScore())
                                                + Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())) + "");// 获取游客用户点赞分数
                                        docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(dc.get(0).getContest_id().getYoukegoodscore())));
                                    }
                                }
                                md.setScoretime(time);
                                tre_menberdocscoreService.update(md);// 修改用户评分历史表
                            } else {
                                Tre_menberdocscore md = new Tre_menberdocscore();
                                if (tb_docgood.getMemberid().getGroupid().getId() == 3) {// 如果会员是专家
                                    if (dc.size() > 0) {
                                        md.setScore(dc.get(0).getContest_id().getExpertsgoodscore());// 获取专家点赞分数，新增评分数
                                        List<Tb_groupdocgood> gdg = null;
                                        String sdt = "SELECT d from Tb_groupdocgood d where 1=1 and d.doc_id = '"
                                                + tb_docgood.getDocid().getId()
                                                + "' and d.member_id='"
                                                + tb_docgood.getMemberid().getId() + "' ";
                                        gdg = tb_groupdocgoodService.findByHQL(sdt.toString());
                                        if (null != gdg && gdg.size() > 0) {
                                            Tb_groupdocgood good = new Tb_groupdocgood();
                                            good.setId(gdg.get(0).getId());
                                            good.setGoodcound(gdg.get(0).getGoodcound() + 1);
                                            good.setTodaytime(new Date());
                                            tb_groupdocgoodService.update(good);
                                        } else {
                                            Tb_groupdocgood good = new Tb_groupdocgood();
                                            good.setMember_id(tb_docgood.getMemberid());
                                            good.setDoc_id(tb_docgood.getDocid());
                                            good.setGoodcound(1);
                                            good.setContest_id(con.get(0).getContest_id());
                                            good.setContesttheme_id(con.get(0).getContesttheme_id());
                                            good.setTodaytime(new Date());
                                            tb_groupdocgoodService.save(good);
                                        }
                                    }
                                } else if (tb_docgood.getMemberid().getGroupid().getId() == 1) {// 普通会员
                                    if (dc.size() > 0) {
                                        md.setScore(dc.get(0).getContest_id().getGoodscore());// 获取普通用户点赞分数
                                    }
                                } else if (tb_docgood.getMemberid().getGroupid().getId() == 2) {// 大众评委
                                    if (dc.size() > 0) {
                                        md.setScore(dc.get(0).getContest_id().getDazhonggoodscore());// 获取普通用户点赞分数
                                    }
                                } else {//游客
                                    if (dc.size() > 0) {
                                        md.setScore(dc.get(0).getContest_id().getYoukegoodscore());// 获取游客用户点赞分数
                                    }
                                }

                                md.setMemberid(tb_docgood.getMemberid());
                                md.setDocid(docresult);
                                md.setScoretime(time);
                                tre_menberdocscoreService.save(md);// 新增用户评分历史表
                                docresult.setFilescore(Double.toString(Double.parseDouble(docresult.getFilescore()) + Double.parseDouble(md.getScore())));
                            }
                        }
                    }
                }
                // 修改文件点赞数量
                if (null != docresult) {
                    if (tb_docgood.getMemberid().getGroupid().getId() == 2)     //用户是大众评委，可以当作10次点赞
                        docresult.setFilegoodcount(docresult.getFilegoodcount() + 10);
                    else
                        docresult.setFilegoodcount(docresult.getFilegoodcount() + 1);
                }
                if (null != tb_docgood.getMemberid()) {
                    // 修改会员积分
                    // 查询 会员等级积分来源表点赞数量
                    Double levelIntegral = tb_docgood.getMemberid().getLevelintegral() + Integer.parseInt(tb_docgood.getMemberid().getLevelid().getLevelname());
                    tb_docgood.getMemberid().setLevelintegral(levelIntegral);
                    //判断 两位用户是否满足升级条件
                    if (levelIntegral > Double.valueOf(tb_docgood.getMemberid().getLevelid().getMaxexp()) && tb_docgood.getMemberid().getLevelid().getId() < 4) {
                        Tb_level tb_level = new Tb_level();
                        tb_level.setId(tb_docgood.getMemberid().getLevelid().getId() + 1);
                        tb_docgood.getMemberid().setLevelid(tb_level);
                    }
                    tb_memberService.update(tb_docgood.getMemberid());
                }
                //被点赞人加积分
                Double levelIntegral2 = memb.getLevelintegral() + Integer.parseInt(memb.getLevelid().getLevelname());
                memb.setLevelintegral(levelIntegral2);
                //判断 两位用户是否满足升级条件
                if (levelIntegral2 > Double.valueOf(memb.getLevelid().getMaxexp()) && memb.getLevelid().getId() < 4) {
                    Tb_level tb_level = new Tb_level();
                    tb_level.setId(memb.getLevelid().getId() + 1);
                    memb.setLevelid(tb_level);
                }
                tb_memberService.update(memb);
                tb_docService.update(docresult);
                // 新增点赞表
                //int macid = tb_docgoodService.getmaxId();
                //tb_docgood.setId(macid+1);
                tb_docgood.setTodaytime(new Date());
                tb_docgood.setGoodcound(1);
                tb_docgood.setDeviceid(tb_docgood.getDeviceid());
                tb_docgoodService.save(tb_docgood);
                Map r = new HashMap();
                r.put("num", docresult.getFilegoodcount());
                jsonResult.setCode("0");
                jsonResult.setMsg("success");
                jsonResult.setData(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setCode("1");
            jsonResult.setMsg("fail");
            log.debug("异常:" + e);
        }

        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }
}
