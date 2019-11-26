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

/**
 * 会员资金明细
 * 
 * @author jing
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_memberconsumption")
public class Tb_memberconsumption extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double amount; // 操作金额

	private int operationtype; // 操作类型 0充值 1支出 2收入 3提现

	@ManyToOne
	@JoinColumn(name = "memberid")
	private Tb_member memberid; // 会员ID

	private Double overage; // 余额

	private Date operationtime; // 操作时间
	
	private int withdrawalstate;//提现状态

	private int walletorincome;	//账户类别


	@Column(name = "walletorincome")
	public int getWalletorincome() {
		return walletorincome;
	}

	public void setWalletorincome(int walletorincome) {
		this.walletorincome = walletorincome;
	}

	@Column(name = "withdrawalstate")
	public int getWithdrawalstate() {
		return withdrawalstate;
	}

	public void setWithdrawalstate(int withdrawalstate) {
		this.withdrawalstate = withdrawalstate;
	}

	@Column(name = "Amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;

	}

	@Column(name = "OperationType")
	public int getOperationtype() {
		return operationtype;
	}

	public void setOperationtype(int operationtype) {
		this.operationtype = operationtype;

	}

	@Column(name = "Overage")
	public Double getOverage() {
		return overage;
	}

	public void setOverage(Double overage) {
		this.overage = overage;

	}

	@Column(name = "OperationTime")
	public Date getOperationtime() {
		return operationtime;
	}

	public void setOperationtime(Date operationtime) {
		this.operationtime = operationtime;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	 

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("amount", "操作金额");
		ModelUtil.total.put("operationtype", "操作类型 0充值 1支出 2收入 3提现");
		ModelUtil.total.put("memberid", "会员ID");
		ModelUtil.total.put("overage", "余额");
		ModelUtil.total.put("operationtime", "操作时间");
	}

}
