package com.sunnet.org.api;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.util.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.UUID;

@RestController
public class QRCodeController extends BaseController {

    private static String appid = "wxa2c607187b4e51b4";
    private static String secret = "be56a63229716465104ab42869e4a608";

    /**
     * 获取二维码上传到服务器，获取二维码url给前端
     * @param scene
     * @param page
     * @param response
     * @return
     */
    @GetMapping(value = "/qrcode")
    public String getQrCode(String scene, String page, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        //获取token
        String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appid+"&secret="+secret;
        String result = HttpRequestUtil.httpGet(token_url);
        String access_token = JSONObject.parseObject(result).getString("access_token");

        //生成二维码
        String base64Data = QRCodeUtil.getQrCode(scene, page, access_token);
        ByteArrayInputStream bai = null;
        if(!StringUtils.isEmpty(base64Data)) {
            //解码成字节数组
            byte[] bytes = Base64.decodeBase64(base64Data);
            bai = new ByteArrayInputStream(bytes);
            OSSClientUtil oss = new OSSClientUtil();    //上传到阿里云
            String folder = "QRCode/"+DateUtil.DateToString(DateUtil.getDate(), "YYYY-MM-dd") + "/";   //存储时间文件夹
            oss.setFOLDER(folder);
            String fn = UUID.randomUUID().toString().replaceAll("-","") + ".jpg";
            String key = oss.uploadFile2OSS(bai, fn);
            String url = oss.getReplace() + folder + fn;
            System.out.println(url);
            if (StringUtils.isEmpty(key)){
                jsonResult.setCode("101");
                jsonResult.setMessage("上传服务器失败");
                return ajaxJson(JSONObject.toJSONString(jsonResult),response);
            }
            jsonResult.setCode("200");
            jsonResult.setMessage("上传服务器成功，得到二维码url");
            jsonResult.setData(url);
            return ajaxJson(JSONObject.toJSONString(jsonResult),response);
        }else {
            jsonResult.setCode("102");
            jsonResult.setMessage("获取二维码失败");
            return ajaxJson(JSONObject.toJSONString(jsonResult),response);
        }
    }


    /**
     * 获取二维码时需要的access_token
     * @param response
     * @return
     */
    @GetMapping(value = "/getToken")
    public String  getToken(HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appid+"&secret="+secret;
        String result = HttpRequestUtil.httpGet(token_url);
        String access_token = JSONObject.parseObject(result).getString("access_token");
        if (StringUtils.isEmpty(access_token)){
            jsonResult.setCode("101");
            jsonResult.setMessage("获取token失败");
            return ajaxJson(JSONObject.toJSONString(jsonResult),response);
        }
        jsonResult.setCode("200");
        jsonResult.setMessage("获取token成功");
        jsonResult.setData(access_token);
        return ajaxJson(JSONObject.toJSONString(jsonResult),response);
    }
}
