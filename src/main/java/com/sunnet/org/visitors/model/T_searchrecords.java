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
 * 搜索记录表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "t_searchrecords")
public class T_searchrecords extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fdId; // 编号

	@ManyToOne
	@JoinColumn(name = "fd_member_id")
	private Tb_member fdMemberId; // 用户编号

	private String fd_searchterm; // 搜索词

	private String fd_ipaddress; // ip地址

	private Date fd_searchtime; // 搜索时间

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public Tb_member getFdMemberId() {
		return fdMemberId;
	}

	public void setFdMemberId(Tb_member fdMemberId) {
		this.fdMemberId = fdMemberId;
	}

	@Column(name = "fd_searchterm")
	public String getFdsearchterm() {
		return fd_searchterm;
	}

	public void setFdsearchterm(String fd_searchterm) {
		this.fd_searchterm = fd_searchterm;
	}

	@Column(name = "fd_ipaddress")
	public String getFdipaddress() {
		return fd_ipaddress;
	}

	public void setFdipaddress(String fd_ipaddress) {
		this.fd_ipaddress = fd_ipaddress;
	}

	@Column(name = "fd_searchtime")
	public Date getFdsearchtime() {
		return fd_searchtime;
	}

	public void setFdsearchtime(Date fd_searchtime) {
		this.fd_searchtime = fd_searchtime;
	}

	public void getMap() {
		ModelUtil.total.put("fdId", "编号");
		ModelUtil.total.put("fdMemberId", "用户编号");
		ModelUtil.total.put("fdSearchTerm", "搜索词");
		ModelUtil.total.put("fdIpAddress", "ip地址");
		ModelUtil.total.put("fdSearchtime", "搜索时间");
	}

}
