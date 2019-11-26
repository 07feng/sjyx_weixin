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

public class AccreditFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
//        String sessionId=request.getParameter("sessionId");
//        System.out.println("sessionId="+sessionId);
        Object sessionObj = request.getSession().getAttribute(Constants.SESSIONKEY);
       if(null == sessionObj){
           writeErrorInfo(response,Constants.DATA_NOTEXIST,"您还未授权");
           return ;
       }
//       else{
//           String sessionStr=sessionObj.toString();
//           if(sessionStr.indexOf("memberId") < 0){
//            writeErrorInfo(response,Constants.SERVICE_EXCEPTION_CODE,"未你还授权");
//            return ;
//           }
//       }
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
