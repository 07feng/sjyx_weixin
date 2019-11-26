package com.sunnet.org.visitors.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sunnet.code.util.ModelUtil;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;

/**
 * 用户行为表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "t_behavior")
public class T_behavior extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fdId; // 编号

	@ManyToOne
	@JoinColumn(name = "fd_member_id")
	private Tb_member fd_member_id; // 用户编号

	private String fd_ipaddress; // ip地址

	private String fd_system; // 系统

	private String fd_browser; // 浏览器

	private String fd_region; // 地区

	private String fd_networktype; // 网络类型（3g,4g,网线）

	private java.util.Date fd_operatingtime; // 操作时间

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	@Column(name = "fd_member_id")
	public Tb_member getFd_member_id() {
		return fd_member_id;
	}

	public void setFd_member_id(Tb_member fd_member_id) {
		this.fd_member_id = fd_member_id;

	}

	@Column(name = "fd_ipaddress")
	public String getFd_ipaddress() {
		return fd_ipaddress;
	}

	public void setFd_ipaddress(String fd_ipaddress) {
		this.fd_ipaddress = fd_ipaddress;

	}

	@Column(name = "fd_system")
	public String getFd_system() {
		return fd_system;
	}

	public void setFd_system(String fd_system) {
		this.fd_system = fd_system;

	}

	@Column(name = "fd_browser")
	public String getFd_browser() {
		return fd_browser;
	}

	public void setFd_browser(String fd_browser) {
		this.fd_browser = fd_browser;

	}

	@Column(name = "fd_region")
	public String getFd_region() {
		return fd_region;
	}

	public void setFd_region(String fd_region) {
		this.fd_region = fd_region;

	}

	@Column(name = "fd_networktype")
	public String getFd_networktype() {
		return fd_networktype;
	}

	public void setFd_networktype(String fd_networktype) {
		this.fd_networktype = fd_networktype;

	}

	@Column(name = "fd_operatingtime")
	public java.util.Date getFd_operatingtime() {
		return fd_operatingtime;
	}

	public void setFd_operatingtime(java.util.Date fd_operatingtime) {
		this.fd_operatingtime = fd_operatingtime;

	}

	public void getMap() {
		ModelUtil.total.put("fd_id", "编号");
		ModelUtil.total.put("fd_member_id", "用户编号");
		ModelUtil.total.put("fd_ipaddress", "ip地址");
		ModelUtil.total.put("fd_system", "系统");
		ModelUtil.total.put("fd_browser", "浏览器");
		ModelUtil.total.put("fd_region", "地区");
		ModelUtil.total.put("fd_networktype", "网络类型（3g,4g,网线）");
		ModelUtil.total.put("fd_operatingtime", "操作时间");
	}
}
