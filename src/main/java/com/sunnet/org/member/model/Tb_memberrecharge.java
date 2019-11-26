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
 * 会员充值记录表(不用)
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "Tb_memberrecharge")
public class Tb_memberrecharge extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String amount; // 充值金额

	private int way; // 充值方式 0支付宝 1微信 ....

	@ManyToOne
	@JoinColumn(name = "memberid")
	private Tb_member memberid; // 会员ID

	private Date rechargetime; // 充值时间

	private String ipaddress; // 充值IP地址

	@Column(name = "Amount")
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("Id", "编号");
		ModelUtil.total.put("Amount", "充值金额");
		ModelUtil.total.put("Way", "充值方式 0支付宝 1微信 ....");
		ModelUtil.total.put("MemberId", "会员ID");
		ModelUtil.total.put("RechargeTime", "充值时间");
		ModelUtil.total.put("IPAddress", "充值IP地址");
	}

}
