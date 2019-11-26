package com.sunnet.org.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统日志类型表 [JAVA]
 * 
 * @author jing
 *
 */
@Entity
@Table(name = "t_syslogitem")
public class SysLogItem {
	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String fdUserName; // 操作员

	private String fdContent; // 操作内容

	private Date fdCreateDate; // 操作时间

	private String fdIp; // IP地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFdUserName() {
		return fdUserName;
	}

	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	public Date getFdCreateDate() {
		return fdCreateDate;
	}

	public void setFdCreateDate(Date fdCreateDate) {
		this.fdCreateDate = fdCreateDate;
	}

	public String getFdIp() {
		return fdIp;
	}

	public void setFdIp(String fdIp) {
		this.fdIp = fdIp;
	}

}
