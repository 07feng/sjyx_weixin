package com.sunnet.org.pay.model;

import java.math.BigDecimal;
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
 * 充值记录
 * 
 * @author jng
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_rechargerecord")
public class Tb_rechargerecord extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rrid; // 编号

	private String orno; // 订单编号

	private String alino; // 支付宝交易账号

	private BigDecimal rramount; // 支付金额

	private String rrstatus; // 支付状态（WAIT_CONFIRM等待确认 TRADE_SUCCESS交易成功）

	private int way; // 支付方式（0支付宝1微信2内购）

	@ManyToOne
	@JoinColumn(name = "memberid")
	private Tb_member memberid; // 用户

	private Date rechargetime; // 充值时间

	private String ipaddress; // ip地址

	private BigDecimal reamount; // 成功充值金额

	private Date successtime; // 成功时间

	private Date finishtime; // 完成时间
	
	private String seller;//支付收款人
	
	@Column(name = "seller")
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Integer getRrid() {
		return rrid;
	}

	public void setRrid(Integer rrid) {
		this.rrid = rrid;

	}

	@Column(name = "orNo")
	public String getOrno() {
		return orno;
	}

	public void setOrno(String orno) {
		this.orno = orno;

	}

	@Column(name = "aliNo")
	public String getAlino() {
		return alino;
	}

	public void setAlino(String alino) {
		this.alino = alino;

	}

	@Column(name = "rrAmount")
	public BigDecimal getRramount() {
		return rramount;
	}

	public void setRramount(BigDecimal rramount) {
		this.rramount = rramount;

	}

	@Column(name = "rrStatus")
	public String getRrstatus() {
		return rrstatus;
	}

	public void setRrstatus(String rrstatus) {
		this.rrstatus = rrstatus;

	}

	@Column(name = "Way")
	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;

	}

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;

	}

	@Column(name = "RechargeTime")
	public Date getRechargetime() {
		return rechargetime;
	}

	public void setRechargetime(Date rechargetime) {
		this.rechargetime = rechargetime;

	}

	@Column(name = "IPAddress")
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;

	}

	@Column(name = "reamount")
	public BigDecimal getReamount() {
		return reamount;
	}

	public void setReamount(BigDecimal reamount) {
		this.reamount = reamount;

	}

	@Column(name = "successTime")
	public Date getSuccesstime() {
		return successtime;
	}

	public void setSuccesstime(Date successtime) {
		this.successtime = successtime;

	}

	@Column(name = "finishTime")
	public Date getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;

	}

	 

	public void getMap() {
		ModelUtil.total.put("rrid", "编号");
		ModelUtil.total.put("orno", "订单编号");
		ModelUtil.total.put("alino", "支付宝交易账号");
		ModelUtil.total.put("rramount", "支付金额");
		ModelUtil.total.put("rrstatus", "支付状态");
		ModelUtil.total.put("way", "支付方式（0微信1支付宝）");
		ModelUtil.total.put("memberid", "用户");
		ModelUtil.total.put("rechargetime", "充值时间");
		ModelUtil.total.put("ipaddress", "ip地址");
		ModelUtil.total.put("reamount", "成功充值金额");
		ModelUtil.total.put("successtime", "成功时间");
		ModelUtil.total.put("finishtime", "完成时间");
	}

}
