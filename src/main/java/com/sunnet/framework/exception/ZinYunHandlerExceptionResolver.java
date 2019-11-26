package com.sunnet.framework.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sunnet.framework.util.JsonResult;

public class ZinYunHandlerExceptionResolver
  implements HandlerExceptionResolver
{
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
  {
    BusinessException businessException = null;
    if ((ex instanceof BusinessException))
    {
      businessException = (BusinessException)ex;
    }
    else
    {
      if ((ex instanceof UnauthorizedException))
      {
        businessException = new BusinessException("500", "对不起用户无权访问，请联系管理员！");
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(businessException.getCode());
        jsonResult.setMessage(businessException.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(jsonResult);
        modelAndView.setViewName("unauthorized");
        return modelAndView;
      }
      businessException = new BusinessException("500", "系统发生未知错误，请联系管理员!");
    }
    JsonResult jsonResult = new JsonResult();
    jsonResult.setCode(businessException.getCode());
    jsonResult.setMessage(businessException.getMessage());
    
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject(jsonResult);
    modelAndView.setViewName("error");
    
    return modelAndView;
  }
}
