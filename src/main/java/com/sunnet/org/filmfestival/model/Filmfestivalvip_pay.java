package com.sunnet.org.filmfestival.model;

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
 * 影展打赏
 * 
 * @author 
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "filmfestivalvip_pay")
public class Filmfestivalvip_pay extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "Memberid")
	private Tb_member memberid; // 打赏人

	@ManyToOne
	@JoinColumn(name = "vipid")
	private Filmfestivalvip vipid; // 影展

	private double payamount; // 打赏金额

	private Date addtime; // 打赏时间

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;

	}

	public Filmfestivalvip getVipid() {
		return vipid;
	}

	public void setVipid(Filmfestivalvip vipid) {
		this.vipid = vipid;

	}

	@Column(name = "PayAmount")
	public double getPayamount() {
		return payamount;
	}

	public void setPayamount(double payamount) {
		this.payamount = payamount;

	}

	@Column(name = "AddTime")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("memberid", "打赏人");
		ModelUtil.total.put("vipid", "影展");
		ModelUtil.total.put("payamount", "打赏金额");
		ModelUtil.total.put("addtime", "打赏时间");
	}

}
