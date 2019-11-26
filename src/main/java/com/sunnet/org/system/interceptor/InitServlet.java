package com.sunnet.org.system.interceptor;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sunnet.org.system.model.SystemItem;
import com.sunnet.org.system.service.ISystemItemService;

/**
 * 初始化servlet 线程获取、Token
 * 
 * @author  
 * @date 2016-09-08
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(InitServlet.class);

	private ApplicationContext applicationContext;

	private ISystemItemService systemItemService;
	
	public void init() throws ServletException {
		log.info("系统初始化开始");
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		systemItemService = (ISystemItemService) applicationContext.getBean("systemItemService");
		List<SystemItem> list = systemItemService.findAll();
		//大于 0
		if(list.size() > 0){
			SystemItem sys = list.get(0);
			SystemXT.systemImg = sys.getSystemImg();
			SystemXT.systemImgLog = sys.getSystemImgLog();
			SystemXT.systemTitle =sys.getSystemTitle();
			SystemXT.systemVersion = sys.getSystemVersion();
		}
		log.info("系统初始化结束");
	}


}
