package com.sunnet.org.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunnet.framework.controller.BaseController;

/**
 * 前段页面需要
 *<p>Title: UrlController</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author jing
 * @date 2017年6月7日 上午11:02:08
 */
@Controller
public class UrlController extends BaseController {

	/**
	 * 可进各种页面
	 * @param url
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/url")
	public String welcome(String url, HttpServletRequest request,
			HttpServletResponse response) { 
		return url;
	}

}
