package com.sunnet.org.app;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.redis.web.RedisClientTemplate;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.framework.util.RandMathUtil;
import com.sunnet.framework.util.Util;
import com.sunnet.org.doc.model.Tb_dockeep;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.service.ITb_dockeepService;
import com.sunnet.org.doc.service.ITb_docpayService;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.filmfestival.service.IFilmfestivalService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvipService;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_payService;
import com.sunnet.org.filmfestival.vo.FilmfestivalUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.member.model.Tb_group;
import com.sunnet.org.member.model.Tb_level;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.service.*;
import com.sunnet.org.member.vo.Tb_memberUtil;
import com.sunnet.org.space.service.ITb_spaceService;
import com.sunnet.org.util.*;
import com.sunnet.org.view.model.Vie_Info;
import com.sunnet.org.view.service.IVie_InfoService;
import net.sf.json.JSONArray;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

/***
 * 会员管理
 *
 * @author caixiang
 */
@Controller
@RequestMapping("/app")
public class AppTb_memberController extends BaseController {
    @Autowired
    private ITb_spaceService tb_spaceService;
    @Autowired
    private ITb_memberService tb_memberService;
    @Autowired
    private ITb_dockeepService tb_dockeepService;
    @Autowired
    private ITre_docfilelabelService iTre_docfilelabelService;
    @Autowired
    private ITre_friendscircleService tre_friendscircleService;
    @Autowired
    private ITb_docpayService tb_docpayService;
    @Autowired
    private IFilmfestivalvipService iFilmfestivalvipService;
    @Autowired
    private IFilmfestivalService iFilmfestivalService;
    @Autowired
    private ITb_docService iTb_docService;
    @Autowired
    private IVie_InfoService iVie_infoService;
//    public static String checkCode ;
//    public static String checkphone ;

//    public static String getCheckphone() {
//        return checkphone;
//    }
//
//    public static void setCheckphone(String checkphone) {
//        AppTb_memberController.checkphone = checkphone;
//    }
//
//    public static String getCheckCode() {
//        return checkCode;
//    }
//
//    public static void setCheckCode(String checkCode) {
//        AppTb_memberController.checkCode = checkCode;
//    }

    /**
     * 验证登录信息是否过期
     * aurthod : caixiang
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/validate",method = RequestMethod.GET)
    @ResponseBody
    public String validate(HttpServletResponse response, HttpServletRequest request) {
//        System.out.println("memberId="+memberId);
//        String session =(String) request.getSession().getAttribute(memberId);
        String session =(String) request.getSession().getAttribute(Constants.SESSIONKEY);
        JsonResult jsonResult = new JsonResult();
        if(null == session || session.equals("")){
//            System.out.println("memberId="+session);
            //没有session  code==1
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("登录信息已过期");
            return ajaxJson(JSONObject.toJSONString(jsonResult),
                    response);
        }else{
            System.out.println("memberId="+session.toString());
            String sessionMap = session.toString();
            //有session  code==0
            jsonResult.setCode(Constants.SUCCESS_CODE);
            Map map = new HashMap();
            //是否有memberId
            //有 isLogin = true  , 没有 isLogin = false
            if(sessionMap.indexOf("memberId") >= 0){
                String[] sessionArray=sessionMap.split(",");
                String memberId="";
                for(int i=0;i<sessionArray.length;i++){
                    if(sessionArray[i].indexOf("memberId") >= 0){
                        memberId=sessionArray[i].substring(sessionArray[i].indexOf("=")+1,sessionArray[i].length()-1);
                    }
                }
                System.out.println(memberId);
                map.put("memberId",memberId);
                jsonResult.setMsg("用户已注册");
            }
            else {
                map.put("isLogin",false);
                jsonResult.setMsg("用户未注册");
            }
            jsonResult.setData(map);
            return ajaxJson(JSONObject.toJSONString(jsonResult),
                    response);
        }
    }


    /**
     * 用户注册
     * @param phone  手机号
     * @param verifyCode  验证码
     * @param nickName  昵称
     * @param avatarUrl 微信头像路径
     * @param sex  性别
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public String loginMe(String phone, String verifyCode, String nickName,String avatarUrl, String sex,
                          HttpServletResponse response, HttpServletRequest request) {
//        System.out.println("result="+phone+","+verifyCode+","+nickName+","+avatarUrl+","+sex);
        String checkphone=request.getSession().getAttribute("phone").toString();
        String checkCode=request.getSession().getAttribute("code").toString();
        JsonResult jsonResult = new JsonResult();
        String msg = StringUtils.isObject(
                new Object[]{phone,
                        verifyCode}, new String[]{
                        "手机号不能为空",  "驗證码不能为空"});
        if (!msg.equals("")) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg(msg);
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        System.out.println("checkphone="+checkphone+","+checkCode);
        Map map=new HashMap();
        if (phone == checkphone || phone.equals(checkphone)) {
            if (checkCode == verifyCode || checkCode.equals(verifyCode)) {
                try {
                    List<Tb_member> fdUserItem = tb_memberService.findByHQL(
                            "select obj from Tb_member obj where obj.mobilenumber =?",
                            phone);
                    Tb_member tb_member=new Tb_member();
                    String sessionValue=(String)request.getSession().getAttribute(Constants.SESSIONKEY);
                    String[] sessionArray=sessionValue.split(",");
                    String openId="";
                    for(int i=0;i<sessionArray.length;i++){
                        if(sessionArray[i].indexOf("openId") >= 0){
                            openId=sessionArray[i].substring(sessionArray[i].indexOf("=")+1,sessionArray[i].length());
                        }
                    }
//                    System.out.println(openId);
//                    System.out.println("fdUserItem.size()="+fdUserItem.size());
                    if (fdUserItem.size() > 0) {
                        //存在用戶，則插入openid
                        tb_member.setId(fdUserItem.get(0).getId());
                        tb_member.setOpenid(openId);
                        map.put("memberId",tb_member.getId());
                        tb_memberService.updateOpenId(tb_member.getOpenid(),tb_member.getId());
                        String sessionStr=sessionValue.substring(0,sessionValue.length()-1)+",memberId="+tb_member.getId()+"}";
                        request.getSession().setAttribute(Constants.SESSIONKEY,sessionStr);
                        map.put("sessionId",request.getSession().getId());
                    }else{
                        //不存在則註冊
                        Tb_level tb_level=new Tb_level();
                        tb_level.setId(1);
                        tb_member.setLevelid(tb_level);
                        Tb_group tb_group=new Tb_group();
                        tb_group.setId(1);
                        tb_member.setGroupid(tb_group);
                        tb_member.setCapacity("5.0");
                        tb_member.setRemainingcapacity("5.0");
                        tb_member.setLevelintegral(0);
                        tb_member.setMobilenumber(phone);
                        tb_member.setStatus("0");
                        tb_member.setWallet(0.0);
                        if(sex.equals("1")){
                            tb_member.setGender(1);
                        }else{
                            tb_member.setGender(0);
                        }
                        tb_member.setHeadimg(avatarUrl);
                        tb_member.setUsersname(nickName);
                        tb_member.setRegtime(new Date());
                        tb_member.setIslogin(1);
                        tb_member.setBackgroundimgoneimg(Constants.BACKGROUNDIMG);
//                            tb_member.setOpenid(openId);
                        tb_memberService.save(tb_member);
                        tb_memberService.updateOpenId(openId,tb_member.getId());
                        String sessionStr=sessionValue.substring(0,sessionValue.length()-1)+",memberId="+tb_member.getId()+"}";
                        request.getSession().setAttribute(Constants.SESSIONKEY,sessionStr);
                        map.put("memberId",tb_member.getId());
                        map.put("sessionId",request.getSession().getId());
                    }
                    jsonResult.setCode(Constants.SUCCESS_CODE);
                    jsonResult.setMsg(Constants.SUCCESS_DATA);
                    jsonResult.setData(map);
                } catch (Exception e) {
                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                    jsonResult.setMsg("执行错误！");
                    log.debug("异常:" + e);
                }
            } else {
                jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                jsonResult.setMsg("验证码输入错误！");
            }
        } else {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("请重新获取验证码！");
        }
        checkphone=null;
        checkCode=null;
        return ajaxJson(JSONObject.toJSONString(jsonResult),
                response);
    }


    /**
     * 更换绑定的手机号
     * @param phone
     * @param verifyCode
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/changeUser")
    public String changeUser(String phone,String verifyCode,
                             HttpServletRequest request,HttpServletResponse response){
        String checkphone=request.getSession().getAttribute("phone").toString();
        String checkCode=request.getSession().getAttribute("code").toString();
        JsonResult jsonResult = new JsonResult();
        String msg = StringUtils.isObject(
                new Object[]{phone,
                        verifyCode}, new String[]{
                        "手机号不能为空",  "驗證码不能为空"});
        if (!msg.equals("")) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg(msg);
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        String memberId=getMemberId(request,response);
        String openId=getOpenId(request,response);
//        String openId="321";
//        String memberId="2BB14EAC-882F-4386-B532-AFC38DA250D5";
        String sessionValue = (String)request.getSession().getAttribute(Constants.SESSIONKEY);
        List<Tb_member> list = tb_memberService.getByPhone(phone);
        Map map=new HashMap();
        if(list.size() >0 && memberId.equals(list.get(0).getId())){
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("不能绑定原手机号");
            return ajaxJson(JSONObject.toJSONString(jsonResult),
                    response);
        }
        if (list.size() == 0) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("此手机尚未注册");
            return ajaxJson(JSONObject.toJSONString(jsonResult),
                    response);
        }
        if (phone == checkphone || phone.equals(checkphone)) {
            if (checkCode == verifyCode || checkCode.equals(verifyCode)) {
//                if (list.size() == 0) {
                    //不存在
//                    Tb_member tb_member=new Tb_member();
//                    Tb_level tb_level=new Tb_level();
//                    tb_level.setId(1);
//                    tb_member.setLevelid(tb_level);
//                    Tb_group tb_group=new Tb_group();
//                    tb_group.setId(1);
//                    tb_member.setGroupid(tb_group);
//                    tb_member.setCapacity("5.0");
//                    tb_member.setRemainingcapacity("5.0");
//                    tb_member.setLevelintegral(0);
//                    tb_member.setMobilenumber(phone);
//                    tb_member.setStatus("0");
//                    tb_member.setWallet(0.0);
//                    Tb_member tb_memberBefor=tb_memberService.getByKey(memberId);
//                    tb_member.setGender(tb_memberBefor.getGender());
//                    tb_member.setHeadimg(tb_memberBefor.getHeadimg());
//                    tb_member.setUsersname(tb_memberBefor.getUsersname());
//                    tb_member.setRegtime(new Date());
//                    tb_member.setIslogin(1);
//                    tb_member.setBackgroundimgoneimg(Constants.BACKGROUNDIMG);
//                    tb_member.setOpenid(openId);
//                    tb_member.setViewnum(0);
//                    tb_memberService.updateUserPhone(memberId,tb_member);
//                    String sessionStr=sessionValue.substring(0,sessionValue.lastIndexOf('=')+1)+tb_member.getId()+"}";
////                    System.out.println("sessionStr="+sessionStr);
//                    request.getSession().setAttribute(Constants.SESSIONKEY,sessionStr);
//                    map.put("memberId",tb_member.getId());
//                    map.put("sessionId",request.getSession().getId());
//                    jsonResult.setData(map);
//                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
//                    jsonResult.setMsg("此手机尚未注册！");
//                } else {
                    //存在
                    tb_memberService.updateUserOpenId(list.get(0),memberId,openId);
                    String sessionStr=sessionValue.substring(0,sessionValue.lastIndexOf('=')+1)+list.get(0).getId()+"}";
//                    System.out.println("sessionStr="+sessionStr);
                    request.getSession().setAttribute(Constants.SESSIONKEY,sessionStr);
                    map.put("memberId",memberId);
                    map.put("sessionId",request.getSession().getId());
                    jsonResult.setData(map);
//                }
                jsonResult.setCode(Constants.SUCCESS_CODE);
                jsonResult.setMsg(Constants.SUCCESS_DATA);
            }else {
                jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                jsonResult.setMsg("验证码输入错误！");
            }
        }else {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("请重新获取验证码！");
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult),
                response);
    }

	/**
	 * author jinhao
	 * 修改密码
	 * @param phone
	 * @param password
	 * @param repassword
	 * @param verifyCode
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/changePassword")
	public String changePassword(String phone,String password, String repassword,String verifyCode, HttpServletRequest request,HttpServletResponse response){
        String checkphone=request.getSession().getAttribute("phone").toString();
        String checkCode=request.getSession().getAttribute("code").toString();
	    JsonResult jsonResult = new JsonResult();
		try {
		    String memberId = getMemberId(request,response);
		    if (memberId == null) {
                if ((phone == checkphone || phone.equals(checkphone)) && (checkCode == verifyCode || checkCode.equals(verifyCode))) {
                    if (password.equals(repassword)) {
                        jsonResult.setCode("501");
                        jsonResult.setMsg("两次密码输入不一致！");
                    } else {
                        List<Tb_member> result = tb_memberService.getByPhone(phone);
                        if (result.size() > 0 && result.get(0).getMobilenumber() != null) {
                            Tb_member mem = result.get(0);
                            mem.setPassword(PasswordMd5.getMd5Password(password));
                            tb_memberService.update(mem);
                            jsonResult.setCode(Constants.SUCCESS_CODE);
                            jsonResult.setMsg(Constants.SUCCESS_DATA);
                        } else {
                            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                            jsonResult.setMsg("用户不存在！");
                        }
                    }

                } else {
                    jsonResult.setCode("-1");
                    jsonResult.setMsg("用户未登录");
                }
            }else {

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
	 * 修改个人资料
	 * @param user_birthday
	 * @param email
	 * @param address
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/session/updateUserInfo")
	public String updateUserInfo(String user_sex,String user_birthday,String realName, String email, String address,String weibo,String qq,String weixin,String alipayname,
                                 String user_profile,String user_name,String user_idCard,HttpServletResponse response, HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();

        String access_token = WeixinSign.getAccess_token();
        Boolean nameSafe = SenInfoCheckUtil.cotentFilter(access_token, user_name);
        if (nameSafe == false){
            jsonResult.setCode("3");
            jsonResult.setMsg("用户名含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        Boolean profileSafe = SenInfoCheckUtil.cotentFilter(access_token, user_profile);
        if (profileSafe == false){
            jsonResult.setCode("3");
            jsonResult.setMsg("用户签名含有违法违规内容");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }
        try {
		    String memberId = getMemberId(request,response);
		    if(null != memberId) {
                Tb_member mem = tb_memberService.getByKey(memberId);
                if(null !=user_birthday)
                    mem.setBirthday(user_birthday);
                if(null !=email)
                    mem.setEmail(email);
                if (null != user_idCard)
                    mem.setIdcard(user_idCard);
                if(null != user_name)
                    mem.setUsersname(user_name);
                if(null !=user_profile)
                    mem.setIntroduction(user_profile);
                if(null !=alipayname)
                    mem.setAlipayname(alipayname);
                if(null !=qq)
                    mem.setQqnumber(qq);
                if(null !=weixin)
                    mem.setWeixinnumber(weixin);
                if(null !=weibo)
                    mem.setWeibonumber(weibo);
                if(null !=realName)
                    mem.setRealname(realName);
                //地址字符串处理
                if(null !=address) {
                    if (address.contains("[")) {
                        JSONArray json = JSONArray.fromObject(address); // 首先把字符串转成 JSONArray  对象
                        StringBuffer addre = new StringBuffer();
                        if (json.size() > 0) {
                            for (int i = 0; i < json.size(); i++) {
                                String job = json.getString(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                                addre.append(job);  // 得到 每个对象中的属性值
                            }
                        }
                        mem.setAddress(addre.toString());
                    }else
                        mem.setAddress(address);
                }
                if("男".equals(user_sex))
                    mem.setGender(0);
                if ("女".equals(user_sex))
                    mem.setGender(1);

                tb_memberService.update(mem);
                jsonResult.setCode("0");
                jsonResult.setMsg("success");
            }else {
                jsonResult.setCode("-1");
                jsonResult.setMsg("用户未登录");
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
     * 获取用户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/getMemberInfo")
    public String getMemberInfo(HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try
        {
            String memberid = getMemberId(request, response);
            if(memberid != null){
                List<String> list = new ArrayList<>();
                list.add(memberid);
                Tb_member result = tb_memberService.findByPrimaryKey(memberid);
                jsonResult.setData(Tb_memberUtil.getControllerMapJ(result));
                jsonResult.setCode("0");
                jsonResult.setMsg("success");
            }else{
                jsonResult.setCode("-1");
                jsonResult.setMsg("请先登录!");
            }
        }
        catch (Exception e)
        {
            jsonResult.setCode("500");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * author jinhao
     * 获取关注列表，收藏列表
     * @param code
     * @param page
     * @param response
     * @return
     */
    @RequestMapping(value = "/session/getFocusList")
//    @RequestMapping(value = "/getFocusList")
    public String getFocusList(String code,String page, HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request,response);
//            String memberId ="9C696C98-C282-4606-8803-6EAD540A9453";
            List<Map> result = new ArrayList<>();
            if(memberId != null) {
                boolean status = true;
                int p = Integer.parseInt(page);
                int startRow = 16*(p-1)+1;
                int endRow = 16*p;
                List<Object[]> data = null;
                if ("2".equals(code))
                    data = tb_dockeepService.keepDocList(memberId,startRow,endRow);
                if("1".equals(code)) {
                    List<Object[]> res = iTb_docService.getFocusUserDoc(memberId,startRow,endRow);
                    if (null == res || 0 == res.size()){
                        status = false;
//                        int[] is = RandMathUtil.getRandMath(48*p);
                        data = iTb_docService.getDocList1();
                    }else {     //添加影展信息
                        String startTime = null;
                        String endTime = null;
                        if (-1 != res.get(res.size()-1)[11].toString().lastIndexOf("."))
                            startTime = res.get(res.size()-1)[11].toString().substring(0, res.get(res.size()-1)[11].toString().lastIndexOf("."));
                        else
                            startTime = res.get(res.size()-1)[11].toString();
                        if (p ==1){
                            endTime = DateUtil.DateToString(DateUtil.getDate(),"yyyy-MM-dd HH:mm:ss");
                        }else {
                            if (-1 != res.get(0)[11].toString().lastIndexOf("."))
                                endTime = res.get(0)[11].toString().substring(0, res.get(0)[11].toString().lastIndexOf("."));
                            else
                                endTime = res.get(0)[11].toString();
                        }
                        List<Object[]> friendFilm = iFilmfestivalvipService.friendFilm(memberId,startTime,endTime);
                        System.out.println("riendFilm.size()="+friendFilm.size());
                        List<Object[]> friendFilmDoc = new ArrayList<>();
                        List is = new ArrayList();
                        if (friendFilm.size()>0 && 0 != friendFilm.get(0).length) {
                            for (int i =0; i<friendFilm.size(); i++) {
                                List<Object[]> list = iFilmfestivalService.festivalCover(friendFilm.get(i)[0].toString());
                                System.out.println("obj[11].toString()=="+friendFilm.get(i)[11].toString());
                                if (list.size() ==0){
                                    is.add(i);
                                    System.out.println("riendFilm.size()="+friendFilm.size());
//                                    System.out.println("删除没图片的影集");
                                }else {
//                                    System.out.println("添加影集首图");
                                    friendFilmDoc.add(list.get(0));
                                }
                            }
                            if (is.size()>0){
                                for (int i =0;i<is.size(); i++){
                                    friendFilm.remove(Integer.parseInt(is.get(i).toString()));
                                }
                            }
                            data = FilmfestivalUtil.getFilmList(friendFilm,friendFilmDoc,res);
                        }else
                            data = res;
                    }
                }
                if(null != data) {
                    for (int i=0; i<data.size();i++) {
                        Map<String, Object> map = new HashMap<>();
                        Object[] d = (Object[])data.get(i);
                        map.put("doc_id", d[0].toString());
                        if (null != d[1]) {
                            if ("2".equals(d[16].toString()))
                                map.put("doc_title", URLDecoder.decode(d[1].toString(),"utf-8"));
                            else
                                map.put("doc_title", d[1].toString());
                        }else
                            map.put("doc_title","");

                        if(null != d[2])
                            map.put("describe", URLDecoder.decode(d[2].toString(),"utf-8"));
                        else
                            map.put("describe", "");
                        map.put("fileType", d[3].toString());
                        if(1 ==  Integer.parseInt(d[3].toString()))
                            map.put("doc_path",d[4].toString()+ Constants.DOC_PATH_END3);
                        if(0 == Integer.parseInt(d[3].toString()))
                            map.put("doc_path", d[5].toString() + Constants.DOC_PATH_END3);
                        if (null != d[6])
                            map.put("view_num", d[6].toString());
                        else
                            map.put("view_num",0);
                        map.put("iheight", d[7].toString());
                        map.put("iwidth", d[8].toString());
                        if (null != d[9])
                            map.put("good_num", d[9].toString());
                        else
                            map.put("good_num",0);
                        if (null != d[10])
                            map.put("comment_num", d[10].toString());
                        else
                            map.put("comment_num",0);
                                    //上传时间
                        if(null != d[11]) {
                            String uploadtime = d[11].toString();
                            Date upload = null;
                            if (-1 != uploadtime.lastIndexOf("."))
                                upload = DateUtil.StringToDate(uploadtime.substring(0, uploadtime.lastIndexOf(".")), "yyyy-MM-dd HH:mm:ss");
                            else
                                upload = DateUtil.StringToDate(uploadtime, "yyyy-MM-dd HH:mm:ss");
                            String diffDate = DateUtil.getDatePoor(DateUtil.getDate(), upload);
                            map.put("upload_time", diffDate);
                        }else
                            map.put("upload_time", "");
                        if(null != d[12])
                            map.put("author_id", d[12].toString());
                        else
                            map.put("author_id", "");
                        if(null != d[12])
                            map.put("author_name", d[13].toString());
                        else
                            map.put("author_name", "");
                        if(null != d[14])
                            map.put("author_portrait", d[14].toString());
                        else
                            map.put("author_portrait", "");
                        if(null !=d[15])
                            map.put("rank", d[15].toString());
                        else
                            map.put("rank", 1);
                        //是否关注作者
                        if("1".equals(code))
                            if (status)
                                map.put("isfollow", 1);
                            else
                                map.put("isfollow", 0);
                        else {
                            map.put("iscollection", 1);
                        }
                        map.put("isDouble",d[16].toString());
                        result.add(map);
                    }
                }
            }else {
                jsonResult.setCode("1");
                jsonResult.setMsg("请先登录!");
            }
            jsonResult.setData(result);
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
     * author jinhao
     * 获取个人数据信息
     * @return
     */
    @RequestMapping(value = "/session/getMemberData")
//    @RequestMapping(value = "/getMemberData")
    public String getMemberData(HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request, response);
//            String memberId = "9C696C98-C282-4606-8803-6EAD540A9453";
            if (memberId != null) {
                List<Object[]> mems = tb_memberService.getByMenid(memberId);
                if(mems.size()>0) {
                    Map result = new HashMap();
                    Object[] obj = mems.get(0);
                    result.put("user_id", obj[0].toString());
                    result.put("user_name", obj[1].toString());
                    if (null != obj[2])
                        result.put("portrait", obj[2].toString() + Constants.DOC_PATH_END1);
                    else
                        result.put("portrait", "");

                    if (null != obj[3])
                        result.put("user_profile", obj[3].toString());
                    else
                        result.put("user_profile", "");
                    result.put("user_rank", obj[9]);
                    if (null != obj[14])
                        result.put("background", obj[14].toString() + Constants.DOC_PATH_END2);
                    else
                        result.put("background", "");
                    //作品被浏览次数
                    int view_num = 0;
                    List docs = iTb_docService.findBySql("select sum(fileviewcount) from Tb_doc where memberid = ?", memberId);
                    if (docs.size()>0 && null != docs.get(0)) {
                        view_num = view_num + Integer.parseInt(((Object)docs.get(0)).toString());
                    }
                    result.put("view_num", view_num);
                    //打赏次数
                    Integer reward_num = tb_docpayService.findCount(memberId);
                    result.put("reward_num", reward_num);
                    //被关注
                    Integer fans_num = tre_friendscircleService.findCount2(memberId);
                    result.put("fans_num", fans_num);
                    //关注
                    Integer follow_num = tre_friendscircleService.findCount(memberId);
                    result.put("follow_num", follow_num);

                    result.put("yunku", "容量：" + obj[12].toString() + "G/剩余：" + obj[13].toString().substring(0, obj[13].toString().indexOf(".") + 2) + "G");
                    //收藏
                    int count = tb_dockeepService.findCount(memberId);
                    result.put("total_collect_num", count);  //收藏总数
                    //图文数
                    int docCount = iFilmfestivalService.festivalCount(memberId);
                    result.put("docCount",docCount);
                    PageBean pageBean = new PageBean();
                    pageBean.setPageSize(4);
                    List<Object[]> collect = null;
                    collect = iTb_docService.findBySql("SELECT * FROM (SELECT d.id,d.FileType,d.Phonethumbnailpathimg,d.Filepath,ROW_NUMBER() over(order by d.uploadtime DESC)T FROM TB_DocKeep AS k LEFT JOIN TB_Doc AS d ON k.DocId = d.id WHERE k.MemberId =? AND d.IsPublic = 1 AND d.DocStatus =1 AND d.IsDelete = 0)TT WHERE TT.T BETWEEN 1 AND 4", memberId);
                    List<Map> colList = new ArrayList<>();
                    if (collect.size()>0 && collect.get(0).length>0) {
                        for (Object[] t : collect) {
                            Map temp = new HashMap();
                            temp.put("doc_id", t[0].toString());
                            temp.put("fileType", t[1].toString());
                            if ("1".equals(t[1].toString()))
                                temp.put("image", t[2].toString()+ Constants.DOC_PATH_END);
                            else
                                temp.put("image", t[3].toString() + Constants.DOC_PATH_END);    //收藏文件路径
                            colList.add(temp);
                        }
                    }
                    result.put("collect", colList);
                    //是否有新的消息通知
                    if (null == obj[20]){
                        result.put("isNewMessage",1);
                    }else {
                        List<Object[]> info = iVie_infoService.findBySql("SELECT * FROM(SELECT adddate,ROW_NUMBER() over(order by adddate DESC)T FROM Vie_Info  WHERE MemberId=? and infotype<>'6' and id<>?)TT WHERE TT.T between 1 and 1", memberId, memberId);
                        List<Object[]> sysInfo = iVie_infoService.findBySql("SELECT * FROM(SELECT edittime,ROW_NUMBER() over(order by edittime DESC)T FROM Tb_sendmessage where memberid=?)TT WHERE TT.T between 1 and 1", memberId);
                        if (info.size() == 0 || sysInfo.size() ==0){
                            result.put("isNewMessage", 0);
                        }else {
                            Date infoDate = (Date) info.get(0)[0];
                            Date sysInfoDate = (Date) sysInfo.get(0)[0];
                            int isNewMessage1 = DateUtil.compareDate((Date) obj[20], infoDate);
                            int isNewMessage2 = DateUtil.compareDate((Date) obj[20], sysInfoDate);
                            if (isNewMessage1 == 1 && isNewMessage2 == 1)
                                result.put("isNewMessage", 0);
                            else
                                result.put("isNewMessage", 1);
                        }
                    }
                    jsonResult.setData(result);
                    jsonResult.setCode("0");
                    jsonResult.setMsg("success");
                }else {
                    jsonResult.setCode("1");
                    jsonResult.setMsg("用户不存在");
                }
            }else{
                jsonResult.setCode("1");
                jsonResult.setMsg("请先登录!");
            }
        }
        catch (Exception e)
        {
            jsonResult.setCode("1");
            jsonResult.setMsg("fail");
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }


    /**
     * author jinhao
     * 获取他人主页数据
     * @param userID
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUserData")
    public String getUserData(String userID, HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        String memberId = getMemberId(request,response);
        try {
            List<Object[]> mems = tb_memberService.getByMenid(userID);
            if(mems.size()>0) {
                Map result = new HashMap();
                Object[] obj = mems.get(0);
                result.put("user_id", obj[0].toString());
                result.put("user_name", obj[1].toString());
                result.put("portrait", obj[2].toString() + Constants.DOC_PATH_END1);
                if (null != obj[3])
                    result.put("user_profile", obj[3].toString());
                else
                    result.put("user_profile", "");
                result.put("user_rank", obj[9].toString());
                if (null != obj[14])
                    result.put("background", obj[14].toString() + Constants.DOC_PATH_END2);
                else
                    result.put("background", "");
                //作品被浏览次数
                int view_num = 0;
                List docs = iTb_docService.findByHQL("select sum(fileviewcount) from Tb_doc where memberid.id = ?", userID);
                if (docs.size()>0 && null != docs.get(0)) {
                    view_num = view_num + Integer.parseInt(((Object) docs.get(0)).toString());
                }
                result.put("view_num", view_num);
                //打赏人数
                Integer reward_num = tb_docpayService.findCount(userID);
                result.put("reward_num", reward_num);
                //被关注
                Integer fans_num = tre_friendscircleService.findCount2(userID);
                result.put("fans_num", fans_num);
                //关注
                Integer follow_num = tre_friendscircleService.findCount(userID);
                result.put("follow_num", follow_num);

                //result.put("yunku", "容量：" + tmem.getCapacity() + "G/剩余：" + tmem.getRemainingcapacity().substring(0, tmem.getRemainingcapacity().indexOf(".") + 2) + "G");
                if (null != memberId) {
                    boolean isFollow = tre_friendscircleService.idFucos(memberId, userID);
                    if (isFollow)
                        result.put("isfollow", 1);
                    else
                        result.put("isfollow", 0);
                } else
                    result.put("isfollow", 0);

                //修改访问量
                int viewnum = tb_memberService.getViewnum(userID) + 1;
                tb_memberService.updateViewnum(viewnum, userID);

                jsonResult.setData(result);
                jsonResult.setCode("0");
                jsonResult.setMsg("success");
            }else {
                jsonResult.setCode("1");
                jsonResult.setMsg("用户不存在");
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
     * 获取个人作品列表
     * @param page
     * @param code
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUserImgList")
    public String getUserImgList(String userId,String page, String code,HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try {
            List<Map> result = new ArrayList<>();
            List<Object[]> docs = null;
            int p = Integer.parseInt(page);
            int StartRow = 20*(p-1)+1;
            int endRow = 20*p;
            StringBuffer sql = new StringBuffer("select * from(select d.Id,d.doctitle,d.filetype,d.iwidht,d.iheight,d.filepath,d.phonethumbnailpathimg,d.filegoodcount,d.filecommentscount,ROW_NUMBER() ");
            //最新作品
            if ("1".equals(code)) {
                sql.append("over(order by d.uploadtime DESC)T FROM Tb_doc AS d where d.docstatus =1 and d.ispublic =1 and d.isdelete =0 and d.memberid =?)TT WHERE TT.T between ? and ?");
            }
            //点赞作品
            if ("2".equals(code)) {
                sql.append("over(order by dg.todaytime DESC)T FROM Tb_doc AS d left join Tb_docgood as dg on d.Id=dg.docid where d.docstatus =1 and d.ispublic =1 and d.isdelete =0 and dg.memberid =?)TT WHERE TT.T between ? and ?");
            }
            //获奖作品
            if ("3".equals(code)) {
                sql.append("over(order by d.uploadtime DESC)T FROM Tb_doc AS d left join Tre_doccontest as c on c.doc_id = d.id where d.docstatus =1 and d.ispublic =1 and d.isdelete =0 and c.audit_status = 1 and c.is_get_award = 1 and d.memberid =?)TT WHERE TT.T between ? and ?");
            }
            docs = iTb_docService.findBySql(sql.toString(),userId,StartRow,endRow);
            if (null != docs) {
                for (int i =0; i<docs.size(); i++) {
                    Map map = new HashMap();
                    Object[] ob = docs.get(i);
                    map.put("imgId", ob[0].toString());
                    if (null != ob[1])
                        map.put("doc_title", ob[1].toString());
                    else
                        map.put("doc_title", "无题");
                    map.put("fileType",ob[2].toString());
                    map.put("iwidht",ob[3].toString());
                    map.put("iheight",ob[4].toString());
                    map.put("height",0);
                    if (0 == Integer.parseInt(ob[2].toString()))
                        map.put("pic", ob[5].toString() + Constants.DOC_PATH_END);
                    else
                        map.put("pic", ob[6].toString() + Constants.DOC_PATH_END);
                    map.put("good_num", ob[7].toString());
                    map.put("comment", ob[8].toString());
                    result.add(map);
                }
            }
            jsonResult.setData(result);
            jsonResult.setCode("0");
            jsonResult.setMsg("执行成功！");
        } catch (Exception e) {
            jsonResult.setCode("1");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * author jinhao
     * 获取粉丝用户列表 获取赏客用户列表 获取关注用户列表
     * @param userId
     * @param code
     * @param page
     * @return
     */
    @RequestMapping(value = "/session/myUserList")
    public  String myUserList(String userId, String code,String page,String docId ,HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request, response);
            if (memberId != null) {
                List<Map> result = new ArrayList<>();
                List<Object[]> ms = null;
                int p = Integer.parseInt(page);
                int startRow = 16*(p-1)+1;
                int endRow = 16*p;
                if(null == docId || "".equals(docId)) {
                    if (null == userId || "".equals(userId))
                        userId = memberId;
                    //粉丝
                    if ("1".equals(code)) {
                        ms = tb_memberService.findBySql("SELECT * FROM (select m.id,m.UsersName,m.Levelid,m.HeadImg,f.isfriends,ROW_NUMBER() over(order by f.addtime desc)T from Tre_friendscircle AS f LEFT JOIN Tb_member AS m ON m.id = f.memberId where f.circlememberid = ? )TT WHERE TT.T between ? and ?", userId,startRow,endRow);
                    }
                    //赏客
                    if ("2".equals(code)) {
                        ms = tb_memberService.findBySql("select * from (select ms.id,ms.UsersName,ms.Levelid,ms.HeadImg,ROW_NUMBER() over(order by dp.addtime desc)T from Tb_docpay AS dp LEFT JOIN Tb_member AS ms ON dp.memberId = ms.id LEFT JOIN Tb_doc d ON dp.docid = d.id LEFT JOIN Tb_member AS m ON d.memberId = m.id WHERE m.id =?)TT WHERE TT.T between ? and ?", userId,startRow,endRow);
                        if (null != ms) {
                            for (int i = 0; i < ms.size(); i++) {
                                for (int j = ms.size() - 1; j > i; j--) {
                                    if (ms.get(i)[0] == ms.get(j)[0]) {
                                        ms.remove(j);
                                    }
                                }
                            }
                        }
                    }
                    //关注
                    if ("3".equals(code)) {
                        ms = tb_memberService.findBySql("SELECT * FROM (select m.id,m.UsersName,m.Levelid,m.HeadImg,f.isfriends,ROW_NUMBER() over(order by f.addtime desc)T from Tre_friendscircle AS f LEFT JOIN Tb_member AS m ON m.id = f.circlememberid where f.memberId = ? )TT WHERE TT.T between ? and ?", userId,startRow,endRow);
                    }
                }else {     //点赞用户
                    if ("4".equals(code)){
                        ms = tb_memberService.findBySql("select * from(select m.id,m.UsersName,m.Levelid,m.HeadImg,ROW_NUMBER() over(order by dg.todaytime DESC)TT FROM TB_DocGood AS dg LEFT JOIN Tb_member AS m ON dg.MemberId=m.Id WHERE dg.MemberId is not null AND dg.DocId =?)TTT WHERE TTT.TT between ? and ?",docId,startRow,endRow);
                    }
                    if ("5".equals(code)){
                        ms = iFilmfestivalService.findBySql("select * from(select m.id,m.UsersName,l.levelName,m.HeadImg,ROW_NUMBER() over(order by dg.todaytime DESC)TT FROM Filmfestivalvip_Good AS dg LEFT JOIN Tb_member AS m ON dg.MemberId=m.Id LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE dg.MemberId is not null AND dg.vipid =?)TTT WHERE TTT.TT between ? and ?",Integer.parseInt(docId),startRow,endRow);
                    }

                }
                if(null != ms){
                    for (int i = 0; i <ms.size();i++){
                        Map map = new HashMap();
                        Object[] os = ms.get(i);
                        map.put("user_id", os[0].toString());
                        map.put("user_name", os[1].toString());
                        map.put("user_rank", os[2].toString());
                        map.put("portrait", os[3].toString() + Constants.DOC_PATH_END1);
                        if ("3".equals(code))
                            map.put("isfollow", true);
                        else {
                            if("1".equals(code)){
                                if ("1".equals(os[4].toString())){
                                    map.put("isfollow", true);
                                }
                                if ("0".equals(os[4].toString())){
                                    map.put("isfollow", false);
                                }
                            }else {
                                if ("2".equals(code))
                                    map.put("isfollow", tre_friendscircleService.idFucos(userId, os[0].toString()));
                                else
                                    map.put("isfollow", tre_friendscircleService.idFucos(memberId, os[0].toString()));
                            }
                        }
                        result.add(map);
                    }
                }
                jsonResult.setData(result);
                jsonResult.setCode("0");
                jsonResult.setMsg("执行成功！");
            }else{
                jsonResult.setCode("-1");
                jsonResult.setMsg("请先登录!");
            }
        } catch (Exception e) {
            jsonResult.setCode("1");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }


    /**
     * 修改关注状态
     * @param userId
     * @param code
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/session/changeFollowStatus")
    public  String changeFollowStatus(String userId, String code, HttpServletResponse response, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request,response);
            if(null != memberId) {
                if (userId.equals(memberId)){
                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                    jsonResult.setMsg("不能关注自己");
                }else {
                    Map data = tre_friendscircleService.changeFollowStatus(memberId,userId,code);
                    jsonResult.setData(data);
                    jsonResult.setCode(Constants.SUCCESS_CODE);
                    jsonResult.setMsg(Constants.SUCCESS_DATA);
                }
            }else{
                jsonResult.setCode("-1");
                jsonResult.setMsg("请先授权登录");
            }
        } catch (Exception e) {
            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
            jsonResult.setMsg("fail!");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 修改用户头像
     * @param file
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/session/changeHead")
    public  String changeHead(MultipartFile file, HttpServletResponse response, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();

        String access_token = WeixinSign.getAccess_token();
        Boolean isSafe = SenInfoCheckUtil.imgFilter(access_token, file);
        if (isSafe == false){
            jsonResult.setCode("2");
            jsonResult.setMsg("图片不合法");
            return ajaxJson(JSONObject.toJSONString(jsonResult), response);
        }

        try {
            String memberId = getMemberId(request,response);
            if(null != memberId) {
                String fileName = file.getOriginalFilename();
                OSSClientUtil oss = new OSSClientUtil();    //上传到阿里云
                String time = DateUtil.DateToString(DateUtil.getDate(),"YYYY-MM-dd")+"/";   //存储时间文件夹
                oss.setFOLDER(time);
                //生成随机文件名
                String fn = oss.fileName(fileName);
                String key = oss.uploadFile2OSS(file.getInputStream(),fn);
                if ("".equals(key)){
                    jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
                    jsonResult.setMsg("上传服务器失败");
                    return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                }

                //获取远程url
                String url = oss.getImgUrl(fn);
                //图片鉴黄
                String str=oss.getReplace()+time+fn;
                String newStr=str.substring(str.length()-3);
                String tags=null;
                if(newStr!="GIF" && !"GIF".equals(newStr)){
                    JSONObject json=JSONObject.parseObject(AppAliyunImg.CheckImage(str));
                    //String detect= json.getJSONObject("result").getJSONObject("detect").get("body").toString();
                    tags= json.getJSONObject("result").getJSONObject("porn").get("body").toString();
                }
                if (!Util.isnull(tags)) {
                    if (tags.indexOf("色情") != -1){
                        jsonResult.setCode("1");
                        jsonResult.setMsg("图片内容不通过！");
                        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
                    }
                }
                oss.destory();  //关闭资源
                tb_memberService.updateHeading(str,memberId);    //更改头像路径
                jsonResult.setCode("0");
                jsonResult.setMsg("执行成功！");
            }else{
                jsonResult.setCode("-1");
                jsonResult.setMsg("请先授权登录");
            }
        } catch (Exception e) {
            jsonResult.setCode("1");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

   /* *//**
     * author jinhao
     * 我的钱包
     * @param response
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/session/myWallet")
    public  String myWallet(HttpServletResponse response, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request,response);
            if(null != memberId) {
                Tb_member mem = tb_memberService.getByKey(memberId);
                Map data = new HashMap();
                data.put("balance", mem);
                data.put("day_income", mem);
                data.put("day_expenditure", mem);
                data.put("total_income", mem);
                data.put("total_expenditure", mem);
                jsonResult.setData(data);
                jsonResult.setCode("0");
                jsonResult.setMessage("执行成功！");
            }else{
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

    *//**
     * author jinhao
     * 获取交易记录列表
     * @param response
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/session/gettradeList")
    public  String gettradeList(HttpServletResponse response, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();
        try {
            String memberId = getMemberId(request,response);
            if(null != memberId) {
                Tb_member mem = tb_memberService.getByKey(memberId);
                Map data = new HashMap();
                data.put("balance", mem);
                jsonResult.setData(data);
                jsonResult.setCode("0");
                jsonResult.setMessage("执行成功！");
            }else{
                jsonResult.setCode("-1");
                jsonResult.setMessage("请先授权登录");
            }
        } catch (Exception e) {
            jsonResult.setCode("1");
            jsonResult.setMsg("执行错误！");
            log.debug("异常:" + e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }*/
    //454毫秒
    @RequestMapping(value = "/session/selectByKey")
    public  String gettradeList(String docId,HttpServletResponse response, HttpServletRequest request){
        JsonResult jsonResult = new JsonResult();
        try {
            //String memberId = getMemberId(request,response);
            if(null != docId) {
                //Tb_member mem = tb_memberService.getByKey(memberId);
                //Tb_doc doc = iTb_docService.getByKey(docId);
                //作品标签
                List doclabels = iTre_docfilelabelService.findByDocId(docId);
                List<String> labelList = new ArrayList<>();
                for(int i = 0; i<doclabels.size(); i++){
                    labelList.add(doclabels.get(i).toString());
                }
                jsonResult.setCode("0");
                jsonResult.setMessage("执行成功！");
            }else{
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
}
