package com.sunnet.org.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;

@Entity
@Table(name = "t_system")
public class SystemItem extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fdId;

	private String systemImg; //系统图片
	
	private String systemImgLog; //后台log设置
	
	private String systemTitle; //系统标题
	
	private String systemVersion; //版本号
	
	private String color; //颜色
	
	private String beian; //备案

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getSystemImg() {
		return systemImg;
	}

	public void setSystemImg(String systemImg) {
		this.systemImg = systemImg;
	}

	public String getSystemImgLog() {
		return systemImgLog;
	}

	public void setSystemImgLog(String systemImgLog) {
		this.systemImgLog = systemImgLog;
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBeian() {
		return beian;
	}

	public void setBeian(String beian) {
		this.beian = beian;
	}
	
	
	
}
