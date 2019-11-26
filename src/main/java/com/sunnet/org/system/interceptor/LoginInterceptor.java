package com.sunnet.org.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor
{

	/**
	 * 进入controller的方法之前进行拦截执行 用于身份验证或身份校验
	 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		
		return true;
	}

	/**
	 * 进入controller的方法中,方法返回modelAndView之前进行拦截执行 用于返回公用的数据比如菜单的导航数据
	 **/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{

	}

	/**
	 * 进入controller的方法返回之后进行拦截执行 用于统一异常处理及统一日志处理
	 **/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{

	}

}
