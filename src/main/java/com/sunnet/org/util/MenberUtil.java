package com.sunnet.org.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sunnet.org.member.model.Tb_member;


public class MenberUtil {
	public final static String SESSION_KEY = "Menber"; //后台用户

	public final static String CODE_KEY = "CODE"; // 验证码

	public static Tb_member getSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Tb_member user = (Tb_member) session.getAttribute(MenberUtil.SESSION_KEY);
		return user;
	}

	public static Tb_member getSession(HttpSession session) {
		Tb_member user = (Tb_member) session.getAttribute(MenberUtil.SESSION_KEY);
		return user;
	}

	public static void setSession(HttpServletRequest request, Tb_member user) {
		HttpSession session = request.getSession();
		session.setAttribute(MenberUtil.SESSION_KEY, user);
	}

	public static void setSession(HttpSession session, Tb_member user) {
		session.setAttribute(MenberUtil.SESSION_KEY, user);
	}
	
	
	/*-----------------------------分割线--------------------------------*/
	public static String getSessionCode(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute(MenberUtil.CODE_KEY);
		return code;
	}

	public static String getSessionCode(HttpSession session) {
		String code = (String) session.getAttribute(MenberUtil.CODE_KEY);
		return code;
	}

	public static void setSessionCode(HttpServletRequest request, String code) {
		HttpSession session = request.getSession();
		session.setAttribute(MenberUtil.CODE_KEY, code);
	}

	public static void setSessionCode(HttpSession session, String code) {
		session.setAttribute(MenberUtil.CODE_KEY, code);
	}
	
	public static void removeSessionCode(HttpSession session) {
		session.removeAttribute(MenberUtil.CODE_KEY);
	}

}
