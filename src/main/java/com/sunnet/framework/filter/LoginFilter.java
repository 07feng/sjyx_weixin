package com.sunnet.framework.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.StringUtils;

public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
        Object sessionObj = request.getSession().getAttribute(Constants.SESSIONKEY);
        if(null == sessionObj){
            writeErrorInfo(response,Constants.DATA_NOTEXIST,"您还未授权");
            return ;
        }
        String sessionStr=sessionObj.toString();
        System.out.println("sessionStr="+sessionStr);
        if(sessionStr.indexOf("memberId") < 0){
            writeErrorInfo(response,Constants.DATA_NOTEXIST,"您还未登陆");
            return ;
        }
        chain.doFilter(arg0, arg1);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private void writeErrorInfo(HttpServletResponse response, String code, String text) throws IOException{
        response.setContentType("text/html;charset=UTF-8");
        JsonResult jsonResult = new JsonResult();
        PrintWriter writer = null;
        try {
            jsonResult.setCode(code);
            jsonResult.setMsg(text);
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(jsonResult));
            writer.flush();
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }

}
