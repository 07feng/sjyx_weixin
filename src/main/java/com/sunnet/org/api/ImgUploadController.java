package com.sunnet.org.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunnet.framework.controller.BaseController;

@Controller
public class ImgUploadController extends BaseController
{
	
	@RequestMapping(value = "/imageUp.jsp")
	public void image(String captchaId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("进来了");
	}
}
