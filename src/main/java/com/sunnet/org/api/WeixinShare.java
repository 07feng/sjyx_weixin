package com.sunnet.org.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunnet.org.util.JsapiTicketScheduled;
import com.sunnet.org.util.SignUtil;
import com.sunnet.org.util.WeixinSign;

@Controller
@RequestMapping("/app")
public class WeixinShare {

    @RequestMapping(value = "/share", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> share(String url,HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> ret = new HashMap<String, String>();
        String jsapi_ticket = JsapiTicketScheduled.jsapiTicketScheduled();
    	ret =  WeixinSign.sign(jsapi_ticket, url);
 
        return ret;
    }
}
