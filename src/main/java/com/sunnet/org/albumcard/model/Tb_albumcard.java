package com.sunnet.org.albumcard.model;

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
import com.sunnet.org.system.model.User;

/**
 * 相册贺卡模板表
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_albumcard")
public class Tb_albumcard extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fd_id; // 编号

	private String fd_name; // 模板名称

	private String fd_type; // 模板类型（0贺卡 1相册  2影展）

	private String fd_urlimg; // 模板路径
	
	private Double pay_money;//金额

	private int fd_number; // 图片数量
	
	private int fd_isedit;//能否替换文字0不能替换问题1可以替换文字
	@ManyToOne
	@JoinColumn(name = "fd_user")
	private User fd_userid; // 用户编号
	
	private Date fd_usertime;
 
	@Column(name = "pay_money")
	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	@Column(name = "fd_isEdit")
	public int getFd_isedit() {
		return fd_isedit;
	}

	public void setFd_isedit(int fd_isedit) {
		this.fd_isedit = fd_isedit;
	}
	
	@Column(name = "fd_userTime")
	public Date getFd_usertime() {
		return fd_usertime;
	}

	public void setFd_usertime(Date fd_usertime) {
		this.fd_usertime = fd_usertime;
	}

	public User getFd_userid() {
		return fd_userid;
	}

	public void setFd_userid(User fd_userid) {
		this.fd_userid = fd_userid;
	}

	
	 

	public String getFd_id() {
		return fd_id;
	}

	public void setFd_id(String fd_id) {
		this.fd_id = fd_id;

	}

	@Column(name = "fd_name")
	public String getFd_name() {
		return fd_name;
	}

	public void setFd_name(String fd_name) {
		this.fd_name = fd_name;

	}

	@Column(name = "fd_type")
	public String getFd_type() {
		return fd_type;
	}

	public void setFd_type(String fd_type) {
		this.fd_type = fd_type;

	}

	@Column(name = "fd_urlimg")
	public String getFd_urlimg() {
		return fd_urlimg;
	}

	public void setFd_urlimg(String fd_urlimg) {
		this.fd_urlimg = fd_urlimg;

	}

	@Column(name = "fd_number")
	public int getFd_number() {
		return fd_number;
	}

	public void setFd_number(int fd_number) {
		this.fd_number = fd_number;

	}

	 
	 

	public void getMap() {
		ModelUtil.total.put("fd_id", "编号");
		ModelUtil.total.put("fd_name", "模板名称");
		ModelUtil.total.put("fd_type", "模板类型（0贺卡 1相册）");
		ModelUtil.total.put("fd_urlimg", "模板路径");
		ModelUtil.total.put("fd_number", "图片数量");
		ModelUtil.total.put("fd_userid", "用户编号");
		ModelUtil.total.put("fd_userTime", "用户上传时间");
	}

}
