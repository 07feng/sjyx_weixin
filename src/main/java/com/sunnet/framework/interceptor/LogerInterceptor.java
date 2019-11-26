package com.sunnet.framework.interceptor;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogerInterceptor
  implements HandlerInterceptor
{
  private static final Logger log = Logger.getLogger(LogerInterceptor.class);
  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    long starTime = System.currentTimeMillis();
    String startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(starTime));
    request.setAttribute("starTime", Long.valueOf(starTime));
    HandlerMethod handlerMethod = (HandlerMethod)handler;
    String host = request.getRemoteHost();
    log.info(startDate + "客户机:" + host + " 开始执行: " + handlerMethod.getMethod().getName());
    return true;
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {
    long starTime = ((Long)request.getAttribute("starTime")).longValue();
    long endTime = System.currentTimeMillis();
    long lossTime = endTime - starTime;
    HandlerMethod handlerMethod = (HandlerMethod)handler;
    String host = request.getRemoteHost();
    String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date(endTime));
    log.info(endDate + " 客户机:" + host + " 结束执行: " + handlerMethod.getMethod().getName());
    log.info("客户端:" + host + " 调用方法: " + handlerMethod.getMethod().getName() + " 耗时" + lossTime + "毫秒");
  }
  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception
  {}
}
