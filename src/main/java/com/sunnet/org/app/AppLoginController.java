package com.sunnet.org.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.adv.model.Tb_adv_show;
import com.sunnet.org.adv.service.ITb_adv_showService;
import com.sunnet.org.competition.service.ITb_contestService;
import com.sunnet.org.competition.service.ITb_contestthemeService;
import com.sunnet.org.competition.service.ITre_doccontestService;
import com.sunnet.org.competition.service.Ivie_doccontestmemberService;
import com.sunnet.org.doc.service.*;
import com.sunnet.org.feedback.service.ITb_feedbackService;
import com.sunnet.org.filmfestival.service.IFilmfestivalService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.information.service.*;
import com.sunnet.org.information.vo.Tb_filetypeUtil;
import com.sunnet.org.member.service.*;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpot;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpotFile;
import com.sunnet.org.scenicSpot.service.ITB_ScenicSpotService;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.util.WeixinSign;
import com.sunnet.org.view.service.IVie_InfoService;
import com.sunnet.org.view.service.Ivie_shouyeService;
import com.sunnet.org.visitors.service.IT_topsearchtermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

@Controller
public class AppLoginController extends BaseController {

    @Autowired
    private ITb_memberService tb_memberService;
    @Autowired
    private ITb_docService tb_docService;
    @Autowired
    private ITb_contestService tb_contestService;
    @Autowired
    private ITre_docfilelabelService filetolabel;
    @Autowired
    private ITb_filetypeService tb_filetypeService;
    @Autowired
    private ITre_doccontestService tre_doccontestService;
    @Autowired
    private ITb_dockeepService tb_dockeepService;
    @Autowired
    private ITb_docgoodService tb_docgoodService;
    @Autowired
    private ITb_adv_showService tb_adv_showService;
    @Autowired
    private ITre_friendscircleService iTre_friendscircleService;
    @Autowired
    private IFilmfestivalService iFilmfestivalService;
    @Autowired
    private IFilmfestivalvipService iFilmfestivalvipService;
    @Autowired
    private ITB_ScenicSpotService itb_scenicSpotService;
    /**
     * 用户通過微信授權獲取openid
     * aurthod : caixaing
     * @param code
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOpenId",method = RequestMethod.GET)
    @ResponseBody
    public String authorize(String code, HttpServletResponse response, HttpServletRequest request) {
        //过期
        //or
        //不存在
//        RedisClientTemplate redisClientTemplate = new RedisClientTemplate();
        JsonResult jsonResult = new JsonResult();
        boolean check = (StringUtils.isStringNull(code)) ?  false:true;
        if (check) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("参数为空");
            return ajaxJson(JSONObject.toJSONString(jsonResult),
                    response);
        }
//        String sessionKey = UUID.randomUUID().toString();
        String sessionKey=Constants.SESSIONKEY;
//        System.out.println("code="+code);
        String data = WeixinSign.getOpenId(code); // 請求微信服務器
        System.out.println("请求结果：" + data);
        JSONObject object = JSON.parseObject(data);
        String openId = object.getString("openid");
        String session_key = object.getString("session_key");
//        System.out.println("获得openId: " + openId);
        List list = new ArrayList();
        Map map = new HashMap();
        try {
            list = tb_memberService.findBySql(
                    "select id from Tb_member where openid ='"+openId+"'");
            System.out.println("size="+list.size());
            if (list.size() == 0) {
                //不存在先把session存儲在redis中
                Map sessionMap=new HashMap();
                sessionMap.put("openId",openId);
                sessionMap.put("session_key",session_key);
//                System.out.println("sessionKey="+sessionKey);
                System.out.println("sessionMap.toString()="+sessionMap.toString());
                request.getSession().setAttribute(sessionKey,sessionMap.toString());
                request.getSession().setMaxInactiveInterval(Constants.USER_SESSION_TIME*2);
                map.put("isLogin",false);
//              request.getSession().setAttribute(sessionKey,"fdsg");
//                String session =(String) request.getSession().getAttribute("abc");
//                if(null == session || session.equals("")){
//                    System.out.println("session=null");
//                }else{
//                    System.out.println("session="+session);
//                }
                jsonResult.setCode(Constants.SUCCESS_CODE);
                jsonResult.setMsg("您還未註冊賬號");
            } else {
                //
                map.put("isLogin",true);
                String memberId=list.get(0).toString();
                Map sessionMap=new HashMap();
                sessionMap.put("openId",openId);
                sessionMap.put("session_key",session_key);
                sessionMap.put("memberId",memberId);
                request.getSession().setAttribute(sessionKey,sessionMap.toString());
                request.getSession().setMaxInactiveInterval(Constants.USER_SESSION_TIME*2);
                jsonResult.setCode(Constants.SUCCESS_CODE);
                jsonResult.setMsg("此用户已注册");
            }
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("查找openId錯誤");
            log.debug("异常:" + e);
            return ajaxJson(JSONObject.toJSONString(jsonResult),
                    response);
        }
        String sessionId = request.getSession().getId();
        map.put("sessionId",sessionId);
        jsonResult.setData(map);
        return ajaxJson(JSONObject.toJSONString(jsonResult),
                response);
    }

    /**
     * 首页顶部显示列表-20190304
     * @author jinhao
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/advShow")
    public String advShow(HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        Map back = new HashMap();
//        List<Object[]> result = null;
        List<Object[]> advs = null;
        try {
            advs = tb_adv_showService.findBySql("SELECT b.id,b.adv_title,b.adv_url,b.adv_type,b.linkpage_type,b.linkpage_path,b.app_id,a.sort from tb_adv_show AS a LEFT JOIN tb_adv AS b ON a.adv_id = b.id WHERE a.adv_place = ? AND b.status =1 ORDER BY a.sort",1);
            List<Map> adv = new ArrayList<>();
            if (null != advs && advs.size() > 0) {
                for (Object[] objects : advs) {
                    Map temp = new HashMap();
                    temp.put("advId",objects[0].toString());
                    temp.put("advTitle",objects[1].toString());
                    temp.put("advImg",objects[2].toString()+Constants.DOC_PATH_END2);
                    temp.put("advType",objects[3].toString());
                    temp.put("linkPageType",objects[4]);
                    temp.put("linkPagePath",objects[5]);
                    temp.put("appId",objects[6]);
                    temp.put("sort",objects[7]);
                    adv.add(temp);
                }
            }
//            back.put("adv",adv);

//            result = iFilmfestivalService.findBySql("select Id,ContestMinImg,2 as one,3 as two,ContestName,ContestStatus from Tb_contest where IsPublicHome = 1 and IsRelease =1 ORDER BY EditTime DESC");
//            List<Map> contest = new ArrayList<>();
//            if (null != result && result.size() > 0){
//                for (int i = 0; i< result.size() ; i++){
//                    Map temp = new HashMap();
//                    temp.put("contestId",result.get(i)[0].toString());
//                    temp.put("contestImg",result.get(i)[1].toString());
//                    temp.put("contestName",result.get(i)[4].toString());
//                    temp.put("contestStatus",result.get(i)[5]);
//                    contest.add(temp);
//                }
//            }
//            back.put("contest",contest);

            jsonResult.setData(adv);
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMsg(Constants.SUCCESS_DATA);
        } catch (Exception e)
        {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg(Constants.SERVICE_EXCEPTION_DATA);
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * author jinhao
     * 根据类别ID获取作品列表
     * @param imgType
     * @param page
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getWorksList",produces = "text/html;charset=utf-8")
    public  String getWorksList(String workType,String imgType,String page,HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try {
            List<Object[]> list = null;
            //页码 行范围
            int p = Integer.parseInt(page);
            int startRow = 20*(p-1)+1;
            int endRow = 20*p;
            if("-1".equals(imgType)){
                System.out.println("=============================="+workType);
                if (org.apache.commons.lang.StringUtils.isEmpty(workType)){
                    list = tb_docService.getVideo(startRow,endRow);
                }else {
                    list = tb_docService.getVideo1(startRow,endRow);
                }
            }else{
                if("".equals(imgType)) {
                        list = tb_docService.getAll(startRow,endRow);
                }else{
                        list = tb_docService.getByFiletypeid(imgType,startRow,endRow);
                }
            }
            List<Map> listDoc = new ArrayList<>();
            if (null != list || list.size()>0) {
                for (int i=0 ; i<list.size(); i++) {
                    Map<String, Object> result = new HashMap();
                    Object[] ob = list.get(i);
                    result.put("imgId", ob[0].toString());
                    result.put("fileType",ob[1].toString());
                    result.put("iwidht",ob[2].toString());
                    result.put("iheight",ob[3].toString());
                    result.put("height",0);
                    if (0 == Integer.parseInt(ob[1].toString()))
                        result.put("pic", ob[4].toString()+ Constants.DOC_PATH_END);
                    else
                        result.put("pic", ob[5].toString() + Constants.DOC_PATH_END);
                    result.put("good_num", ob[6].toString());
                    result.put("comment", ob[7].toString());
                    result.put("isDouble",Integer.parseInt(ob[8].toString()));
                    if (null != ob[9])
                        result.put("isboutique",Integer.parseInt(ob[9].toString()));
                    else
                        result.put("isboutique",0);
                    listDoc.add(result);
                }
            }
            jsonResult.setData(listDoc);
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
     * 返回类别列表
     * @param request
     * @param response
     * @return
     * author jinhao
     */
    @RequestMapping(value = "/getCategoryList")
    public String getCategoryList(HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            List<Object[]> result = tb_filetypeService.ListFileType();
            jsonResult.setData(Tb_filetypeUtil.getControllerListJ(result));
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
//            String opId = getOpenId(request, response);
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
                    map.put("images", ob[7].toString());// + Constants.DOC_PATH_END2);
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
                map.put("devicetype",Byte.parseByte(ob[15].toString()));
                //查询今天是否点过赞
                if (null != memberid) {
                    int result = tb_docgoodService.isgood(docid, memberid);
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
                String uploadtime = ob[16].toString();
                Date upload = null;
                if (-1 != uploadtime.lastIndexOf("."))
                    upload = DateUtil.StringToDate(uploadtime.substring(0, uploadtime.lastIndexOf(".")), "yyyy-MM-dd HH:mm:ss");
                else
                    upload = DateUtil.StringToDate(uploadtime, "yyyy-MM-dd HH:mm:ss");
                String diffDate = DateUtil.getDatePoor(DateUtil.getDate(), upload);
                map.put("upload_time", diffDate);
                map.put("time", uploadtime);
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
     * 作品详情页左右滑动 根据作品上传时间获取 前后作品
     * 1 向右滑,前面5张  -1 向左滑 后面5张
     * @param time
     * @param code
     * @param type_id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/WorksDetailNext")
    public String WorksDetailNext(String time,String code,String type_id, HttpServletRequest request, HttpServletResponse response) {
        JsonResult jsonResult = new JsonResult();
        try {
            String memberid = getMemberId(request, response);
//            String memberid = null;
//            String memberid = "9C696C98-C282-4606-8803-6EAD540A9453";
            List<Map> back = tb_docService.getNextWorks(time,type_id,code,memberid);
                jsonResult.setData(back);
                jsonResult.setCode("0");
                jsonResult.setMsg("success");
        } catch (Exception e) {
            jsonResult.setCode("1");
            jsonResult.setMsg("fail");
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
     * majia  jinhao
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/isopen")
    public String isopen(HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            List result = tb_filetypeService.findBySql("SELECT Fid FROM TB_FileType WHERE id =1");
            jsonResult.setData(Integer.parseInt(result.get(0).toString()));
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
     * majia  jinhao
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/isOpenStatus")
    public String isopen1(String code,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            List result = tb_filetypeService.findBySql("SELECT status FROM t_switch WHERE id =?",Integer.parseInt(code));
            jsonResult.setData(Integer.parseInt(result.get(0).toString()));
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
     * 影展更多列表
     * @param page
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/showList")
    public String showList(String code,String page, HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        String memberId = getMemberId(request,response);
        try
        {
            Integer p = Integer.parseInt(page);	//页码
            int startRow = 20*(p-1)+1;
            int endRow = 20*p;
            if (null == code)
                code = "1";
            List<Object[]> result = iFilmfestivalService.festivalList(null,startRow,endRow,code);
            List data = new ArrayList();
            if (result.size()>0 && 0 != result.get(0).length) {
                    for (Object[] obj : result) {
                        Map map = new HashMap();
                        List<Object[]> list = iFilmfestivalService.festivalCover(obj[0].toString());
                        if (list.size()>0 && null != list.get(0)) {
                            map.put("author_id",obj[7].toString());
                            if (null != obj[8])
                                map.put("author_name",obj[8].toString());
                            else
                                map.put("author_name","游客甲");
                            if (null != obj[9])
                                map.put("author_portrait",obj[9].toString());
                            else
                                map.put("author_portrait","");
                            if (null != obj[10])
                                map.put("rank",obj[10].toString());
                            else
                                map.put("rank",1);
                            if (null != obj[4])
                                map.put("title",URLDecoder.decode(obj[4].toString(),"utf-8"));
                            else
                                map.put("title",1);
                            map.put("doc_path", list.get(0)[1].toString() + Constants.DOC_PATH_END);
                            map.put("iwidht", Integer.parseInt(list.get(0)[2].toString()));
                            map.put("iheight", Integer.parseInt(list.get(0)[3].toString()));
                            map.put("worksShowId", Integer.parseInt(obj[0].toString()));
                            map.put("upload_time", DateUtil.getDatePoor(DateUtil.getDate(), (Date)obj[11]));
                            if (null != obj[2])
                                map.put("goodCount", Integer.parseInt(obj[2].toString()));
                            else
                                map.put("goodCount", 0);
                            if (null != obj[3])
                                map.put("commentCount", Integer.parseInt(obj[3].toString()));
                            else
                                map.put("commentCount", 0);
                            if (null != obj[6])
                                map.put("view_num", Integer.parseInt(obj[6].toString()));
                            else
                                map.put("view_num", 0);
                            map.put("code",code);
                            //是否关注作者
                            boolean isFocus = false;
                            if (null != memberId) {
                                isFocus = iTre_friendscircleService.idFucos(memberId,obj[7].toString());
                                if (isFocus)
                                    map.put("is_follow", 1);
                                else
                                    map.put("is_follow", 0);
                            }else
                                map.put("is_follow", 0);
                            data.add(map);
                        }
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
     * 首页顶部显示列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/homeShowList")
    public String homeShowList(String code, HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            if (null == code)
                code = "1";
            List<Object[]> result = null;
            if ("3".equals(code)){
                result = iFilmfestivalService.findBySql("select Id,ContestMinImg,2 as one,3 as two,ContestName,ContestStatus,showOrder from Tb_contest where IsPublicHome = 1 and IsRelease =1 ORDER BY showOrder DESC");
            }else
                result = iFilmfestivalService.festivalList(null,1,6,code);
            List data = new ArrayList();
            if (result.size()>0 && null != result.get(0)) {
                for (int i =0; i<result.size(); i++) {
                    Object[] obj = result.get(i);
                    Map map = new HashMap();
                    List<Object[]> list = new ArrayList<>();
                    if ("3".equals(code))
                        list.add(obj);
                    else
                        list = iFilmfestivalService.festivalCover(obj[0].toString());

                    if (list.size()>0 && null != list.get(0)) {
                        map.put("doc_path", list.get(0)[1].toString() + Constants.DOC_PATH_END);
                        map.put("worksShowId", Integer.parseInt(obj[0].toString()));
                        if (!"3".equals(code)) {
                            map.put("isboutique", Integer.parseInt(obj[1].toString()));
                        }
                        if (null != obj[4])
                            map.put("title",URLDecoder.decode(obj[4].toString(),"utf-8"));
                        else
                            map.put("title",1);
                        if (null != obj[5])     //赛事状态
                            map.put("status",Integer.parseInt(obj[5].toString()));
                        else
                            map.put("status",0);
                        map.put("code",code);
                        if ("3".equals(code)) {
                            map.put("sort", Integer.parseInt(obj[6].toString()));
                        }
                        data.add(map);
                    }
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
     * 影展作品列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/worksShowDetail")
    public String worksShowDetail(String worksShowId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        String memberId = getMemberId(request,response);
        String opId = getOpenId(request,response);
        try {
            Map data = new HashMap();
            List<Object[]> result = iFilmfestivalService.festivalDetail(worksShowId);
            if(result.size()>0 && result.get(0).length>0) {
                Object[] o = result.get(0);
                data.put("works_id", worksShowId);
                if (null != o[0])
                    data.put("title", URLDecoder.decode(o[0].toString(),"utf-8"));
                else
                    data.put("title", "");
                if (null != o[13])
                    data.put("explain", URLDecoder.decode(o[13].toString(),"utf-8"));
                else
                    data.put("explain", "");
                data.put("user_id", o[4].toString());
                if (null != o[5])
                    data.put("user_name", o[5].toString());
                else
                    data.put("user_name", "");
                if (null != o[6])
                    data.put("HeadImg", o[6].toString());
                else
                    data.put("HeadImg", "");
                if (null != o[17])
                    data.put("rank", Integer.parseInt(o[17].toString()));
                else
                    data.put("rank", 1);
                if (null != o[18])
                    data.put("musicId", Integer.parseInt(o[18].toString()));
                else
                    data.put("musicId", 0);
                if (null != o[19])
                    data.put("musicName", o[19].toString());
                else
                    data.put("musicName", "");
                if (null != o[20])
                    data.put("musicPath", o[20].toString());
                else
                    data.put("musicPath", "");
                String code = "1";
                if (null == o[21]) {
                    data.put("code", "1");
                } else {
                    if ("1".equals(o[21].toString())) {
                        data.put("code", "2");
                        code = "2";
                    } else
                        data.put("code", "1");
                }
                if (null != o[23]){
                    data.put("coverurl",o[23].toString());
                }else {
                    data.put("coverurl","http://image.91sjyx.com/sjyx/worksData/defaultCover.jpg");
                }
                //修改浏览数量
                if (null != o[16]) {
                    data.put("viewnum", Integer.parseInt(o[16].toString()) + 1);
                    iFilmfestivalvipService.updateViewnum(Integer.parseInt(o[16].toString()) + 1, Integer.parseInt(worksShowId));
                } else {
                    data.put("viewnum", 1);
                    iFilmfestivalvipService.updateViewnum(1, Integer.parseInt(worksShowId));
                }

                data.put("time", DateUtil.getDatePoor(DateUtil.getDate(), (Date) o[1]));
                if (null != o[2])
                    data.put("good_num", Integer.parseInt(o[2].toString()));
                else
                    data.put("good_num", 0);
                if (null != o[3])
                    data.put("comment_num", Integer.parseInt(o[3].toString()));
                else
                    data.put("comment_num", 0);
                if (null != o[12])
                    data.put("payCount", Integer.parseInt(o[12].toString()));
                else
                    data.put("payCount", 0);
                List docs = new ArrayList();
                for (Object[] obj : result) {
                    Map map = new HashMap();
                    map.put("id", obj[7].toString());
                    map.put("image", obj[8].toString() + Constants.DOC_PATH_END2);
                    if (null != obj[9])
                        map.put("desc", URLDecoder.decode(obj[9].toString(),"utf-8"));
                    else
                        map.put("desc", "");
                    map.put("iwidht", obj[10].toString());
                    map.put("iheight", obj[11].toString());
                    docs.add(map);
                }
                data.put("imgList", docs);
                //作品点赞用户列表
                List<Object[]> list = iFilmfestivalService.findBySql("select * from(select m.id,m.HeadImg,ROW_NUMBER() over(order by dg.todaytime DESC)T FROM Filmfestivalvip_Good AS dg LEFT JOIN Tb_member AS m ON dg.MemberId=m.Id WHERE dg.MemberId is not null AND dg.vipid =?)TT WHERE TT.T between 1 and 6", Integer.parseInt(worksShowId));
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
                data.put("goodList", goodList);
                //查询用户是否点赞影展，是否关注作者
            if (null != memberId) {
                int isGood = iFilmfestivalService.isGood(worksShowId, memberId, opId);
                if (isGood > 0)
                    data.put("isGood", 1);
                else
                    data.put("isGood", 0);
            } else
                data.put("isGood", 0);
            //是否关注作者
            boolean isFocus = false;
            if (null != memberId) {
                isFocus = iTre_friendscircleService.idFucos(memberId, (result.get(0)[4]).toString());
                if (isFocus)
                    data.put("is_follow", 1);
                else
                    data.put("is_follow", 0);
            }else
                data.put("is_follow", 0);

            //平台推荐作品
            List<Object[]> temp = iFilmfestivalService.recommendFestivalList(1, 12, code);
            List recommend = new ArrayList();
            if (temp.size() > 0 && 0 != temp.get(0).length) {
                int count = 0;
                for (Object[] obj : temp) {
                    if(!worksShowId.equals(obj[0].toString())) {
                        Map map = new HashMap();
                        List<Object[]> listR = iFilmfestivalService.festivalCover(obj[0].toString());
                        if (listR.size() > 0 && null != listR.get(0)) {
                            map.put("id", obj[0].toString());
                            if (null != obj[1])
                                map.put("title", URLDecoder.decode(obj[1].toString(),"utf-8"));
                            else
                                map.put("title", "无题");
                            map.put("doc_path", listR.get(0)[1].toString() + Constants.DOC_PATH_END4);
                            recommend.add(map);
                            count++;
                            if (count >= 10)
                                break;
                        }
                    }
                }
            }
            data.put("recommend", recommend);
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
     * 景点推荐地
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/tourismRecommend")
    public String tourismRecommend(HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            List<Object[]> result = itb_scenicSpotService.tourismRecommend();
            List data = new ArrayList();
            for (Object[] obj:result){
                Map map = new HashMap();
                map.put("id",Integer.parseInt(obj[0].toString()));
                if (null != obj[1])
                    map.put("path",obj[1].toString()+Constants.DOC_PATH_END4);
                else
                    map.put("path","");
                map.put("name",obj[2].toString());
                data.add(map);
            }
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
     * 搜索景点
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/searchSpot")
    public String searchSpot(String cityId,String spotName,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            if (null == cityId)
                cityId ="0";
            if (null == spotName)
                spotName ="0";
            List<Map> result = itb_scenicSpotService.searchSpot(cityId,spotName);
            jsonResult.setData(result);
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
     * 旅游景点详细信息
     * @param spotId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getSpotData")
    public String getSpotData(String spotId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberId = getMemberId(request, response);
            if (null == memberId)
                memberId = "0";
            Map data = itb_scenicSpotService.getSpotData(Integer.parseInt(spotId),memberId);
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
     * 旅游作品列表
     * @param page
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getSpotList")
    public String getSpotList(String page,String spotId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberId = getMemberId(request, response);
            if (null == memberId)
                memberId = "0";
            List data = itb_scenicSpotService.getTourismImgList(Integer.parseInt(page),Integer.parseInt(spotId),memberId);
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
     * 旅游景点列表
     * @param page
     * @param cityId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getTourismList")
    public String getTourismList(String page,String cityId,HttpServletRequest request,HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberId = getMemberId(request, response);
            if (null == memberId)
                memberId = "0";
            List data = itb_scenicSpotService.getTourismList(Integer.parseInt(page),Integer.parseInt(cityId),memberId);
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

}
