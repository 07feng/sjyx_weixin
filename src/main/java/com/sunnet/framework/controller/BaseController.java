package com.sunnet.framework.controller;

import com.sunnet.org.system.model.SysLogItem;
import com.sunnet.org.system.service.ISysLogItemService;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.RandomUtil;
import com.sunnet.org.util.UserUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class BaseController {

	@Autowired
	private ISysLogItemService sysLogItemService;//记录系统日志操作

	protected static final Log log = LogFactory.getLog(BaseController.class);

	protected String ajaxHtml(String html, HttpServletResponse response) {
		return ajax(html, "text/html", response);
	}

	protected String ajaxJson(String json, HttpServletResponse response) {
		return ajax(json, "application/json", response);
	}

	private String ajax(String content, String type,
			HttpServletResponse response) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			log.error("ajax", e);
		}
		return null;
	}
	
	/* 这个是继承的父类 */
	/**
	 * 系统日志记录
	 * @param request
	 * @param fdContent
	 */
	public void Log(HttpServletRequest request,String fdContent){
		SysLogItem sysLogItem =new SysLogItem();
		sysLogItem.setFdIp(RandomUtil.getRemortIP(request)); //获取IP
		sysLogItem.setFdCreateDate(new Date());
		sysLogItem.setFdUserName(UserUtil.getSession(request).getFdUserName());
		sysLogItem.setFdContent(fdContent);
		try {
			sysLogItemService.save(sysLogItem);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	/**
	 * 获取openId
	 * @param request
	 * @param response
	 * @return
	 */
	public String getOpenId(HttpServletRequest request,HttpServletResponse response){
		Object session=(Object)request.getSession().getAttribute(Constants.SESSIONKEY);
		if(null == session || session.equals("")){
			return null;
		}
		String sessionStr=session.toString();
		String[] sessionArray=sessionStr.split(",");
		String openId="";
		for(int i=0;i<sessionArray.length;i++){
			if(sessionArray[i].indexOf("openId") >= 0){
				if(sessionArray[i].indexOf("}") >= 0) {
					openId = sessionArray[i].substring(sessionArray[i].indexOf("=") + 1, sessionArray[i].length()-1);
				}else{
					openId = sessionArray[i].substring(sessionArray[i].indexOf("=") + 1, sessionArray[i].length());
				}
			}
		}
		return openId;
	}

	/**
	 * 获取memberId
	 * @param request
	 * @param response
	 * @return
	 */
	public String getMemberId(HttpServletRequest request,HttpServletResponse response){
		Object session=(Object)request.getSession().getAttribute(Constants.SESSIONKEY);
		if(null == session || session.equals("")){
			return null;
		}
		String sessionStr=session.toString();
		String[] sessionArray=sessionStr.split(",");
		String memberId="";
		for(int i=0;i<sessionArray.length;i++){
			if(sessionArray[i].indexOf("memberId") >= 0){
				memberId=sessionArray[i].substring(sessionArray[i].indexOf("=")+1,sessionArray[i].length()-1);
			}
		}
		if(memberId.equals("")){
			return null;
		}
		return memberId;
	}

	/**
	 * 获得HttpServletRequest对象
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	/**
	 * 获得客户端真实IP地址信息
	 *
	 * @return 返回获取到的ip
	 */
	public String getClientIpAddr() {

		HttpServletRequest request = getRequest();

		String ip = request.getHeader("X-Real-IP");
		if (!StringUtil.isBlank(ip)
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtil.isBlank(ip)
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("Cdn-Src-Ip");
		if (!StringUtil.isBlank(ip)
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("Proxy-Client-IP");
		if (!StringUtil.isBlank(ip)
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("WL-Proxy-Client-IP");
		if (!StringUtil.isBlank(ip)
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("HTTP_CLIENT_IP");
		if (!StringUtil.isBlank(ip)
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (!StringUtil.isBlank(ip)
				&& !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}

		return request.getRemoteAddr();
	}
}
