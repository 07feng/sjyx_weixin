package com.sunnet.org.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunnet.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/center")
public class CenterController extends BaseController {

	/**
	 * 进入欢迎页面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("进来了");
		return "view/common/welcome";
	}

}
