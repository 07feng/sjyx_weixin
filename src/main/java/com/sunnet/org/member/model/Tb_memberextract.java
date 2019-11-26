package com.sunnet.org.member.model;

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

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.system.model.User;

/**
 * 会员提现表
 * 
 * @author jing
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_memberextract")
public class Tb_memberextract extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name; // 名称

	private String account; // 提现帐户

	private String accountname; // 账户名称

	@ManyToOne
	@JoinColumn(name = "memberid")
	private Tb_member memberid; // 会员ID

	private int extractway; // 提现方式 0微信 1支付宝 2银行卡

	private String extractamount; // 提现金额

	private Date applytime; // 申请时间

	private Date returntime; // 返现时间

	private String extractstatus; // 提现申请状态 0未处理 1已处理

	private String ipaddress; // 提现人IP地址

	private Date edittime; // 支付时间

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 支付人ID

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	@Column(name = "Account")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;

	}

	@Column(name = "AccountName")
	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;

	}

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;
	}

	@Column(name = "ExtractWay")
	public int getExtractway() {
		return extractway;
	}

	public void setExtractway(int extractway) {
		this.extractway = extractway;

	}

	@Column(name = "ExtractAmount")
	public String getExtractamount() {
		return extractamount;
	}

	public void setExtractamount(String extractamount) {
		this.extractamount = extractamount;

	}

	@Column(name = "ApplyTime")
	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;

	}

	@Column(name = "ReturnTime")
	public Date getReturntime() {
		return returntime;
	}

	public void setReturntime(Date returntime) {
		this.returntime = returntime;

	}

	@Column(name = "ExtractStatus")
	public String getExtractstatus() {
		return extractstatus;
	}

	public void setExtractstatus(String extractstatus) {
		this.extractstatus = extractstatus;

	}

	@Column(name = "IpAddress")
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;

	}

	@Column(name = "EditTime")
	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;

	}

	
	public User getEdituserid() {
		return edituserid;
	}

	public void setEdituserid(User edituserid) {
		this.edituserid = edituserid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("name", "名称");
		ModelUtil.total.put("account", "提现帐户");
		ModelUtil.total.put("accountname", "账户名称");
		ModelUtil.total.put("memberid", "会员ID");
		ModelUtil.total.put("extractway", "提现方式 0微信 1支付宝");
		ModelUtil.total.put("extractamount", "提现金额");
		ModelUtil.total.put("applytime", "申请时间");
		ModelUtil.total.put("returntime", "返现时间");
		ModelUtil.total.put("extractstatus", "提现申请状态 0未处理 1已处理");
		ModelUtil.total.put("ipaddress", "提现人IP地址");
		ModelUtil.total.put("edittime", "支付时间");
		ModelUtil.total.put("edituserid", "支付人ID");
	}

}
