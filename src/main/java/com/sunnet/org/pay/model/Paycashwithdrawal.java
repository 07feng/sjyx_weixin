package com.sunnet.org.pay.model;

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

/**
 * 提现方式表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "paycashwithdrawal")
public class Paycashwithdrawal extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String member_id; // 用户编号

	private String name; // 真实姓名

	private String cashtime; // 申请时间

	private int cashtype; // 申请类型（0支付宝1微信2银行卡）

	private String cashaccountnumber; // 收款账号

	private String user_id; // 用户编号

	private String usertime; // 处理时间

	@Column(name = "member_id")
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;

	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	@Column(name = "cashtime")
	public String getCashtime() {
		return cashtime;
	}

	public void setCashtime(String cashtime) {
		this.cashtime = cashtime;

	}

	@Column(name = "cashtype")
	public int getCashtype() {
		return cashtype;
	}

	public void setCashtype(int cashtype) {
		this.cashtype = cashtype;

	}

	@Column(name = "cashAccountNumber")
	public String getCashaccountnumber() {
		return cashaccountnumber;
	}

	public void setCashaccountnumber(String cashaccountnumber) {
		this.cashaccountnumber = cashaccountnumber;

	}

	@Column(name = "user_id")
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;

	}

	@Column(name = "usertime")
	public String getUsertime() {
		return usertime;
	}

	public void setUsertime(String usertime) {
		this.usertime = usertime;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "null");
		ModelUtil.total.put("member_id", "用户编号");
		ModelUtil.total.put("name", "真实姓名");
		ModelUtil.total.put("cashtime", "申请时间");
		ModelUtil.total.put("cashtype", "申请类型（0支付宝1微信2银行卡）");
		ModelUtil.total.put("cashaccountnumber", "收款账号");
		ModelUtil.total.put("user_id", "用户编号");
		ModelUtil.total.put("usertime", "处理时间");
	}

}
