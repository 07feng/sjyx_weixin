package com.sunnet.framework.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sunnet.org.system.model.User;

public class UserUtil {
	public final static String SESSION_KEY = "USER"; //后台用户

	public final static String CODE_KEY = "CODE"; // 验证码

	public static User getSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(UserUtil.SESSION_KEY);
		return user;
	}

	public static User getSession(HttpSession session) {
		User user = (User) session.getAttribute(UserUtil.SESSION_KEY);
		return user;
	}

	public static void setSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute(UserUtil.SESSION_KEY, user);
	}

	public static void setSession(HttpSession session, User user) {
		session.setAttribute(UserUtil.SESSION_KEY, user);
	}
	
	
	/*-----------------------------分割线--------------------------------*/
	public static String getSessionCode(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute(UserUtil.CODE_KEY);
		return code;
	}

	public static String getSessionCode(HttpSession session) {
		String code = (String) session.getAttribute(UserUtil.CODE_KEY);
		return code;
	}

	public static void setSessionCode(HttpServletRequest request, String code) {
		HttpSession session = request.getSession();
		session.setAttribute(UserUtil.CODE_KEY, code);
	}

	public static void setSessionCode(HttpSession session, String code) {
		session.setAttribute(UserUtil.CODE_KEY, code);
	}
	
	public static void removeSessionCode(HttpSession session) {
		session.removeAttribute(UserUtil.CODE_KEY);
	}

}
