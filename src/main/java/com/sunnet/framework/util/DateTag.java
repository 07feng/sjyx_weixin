package com.sunnet.framework.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sunnet.org.util.DataTime;

/**
 * 自定义标签库
 *
 * @Title: JSTLDateUtils.java
 * @Description: TODO(用一句话描述该文件做什么)
 * @author eleven.song(宋涛)
 * @date 2014 -3- 4 下午06:28:51
 */
public class DateTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String longValue; // 值

	private String longType = "yyyy-MM-dd HH:mm"; // 转换类型

	private String compare = "1"; //1默认 2是比较时间搓

	@Override
	public int doStartTag() throws JspException {
		String vv = "" + longValue;
		long time = Long.valueOf(vv);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		SimpleDateFormat dateformat = new SimpleDateFormat(longType);
		String s = dateformat.format(c.getTime());
		try {
			if (compare == null) {
				pageContext.getOut().write(s);
			} else if (compare.equals("1")) {
				pageContext.getOut().write(s);
			} else if (compare.equals("2")) {
				Long a = DataTime.getDistanceDays(s, dateformat.format(new Date().getTime()));
				pageContext.getOut().write(a.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public void setLongValue(String longValue) {
		this.longValue = longValue;
	}

	public void setLongType(String longType) {
		this.longType = longType;
	}

	public void setCompare(String compare) {
		this.compare = compare;
	}

}
