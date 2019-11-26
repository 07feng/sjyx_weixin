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
 * 影展点赞
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "filmfestivalvip_good")
public class Filmfestivalvip_good extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int vipid; // 影展

	@ManyToOne
	@JoinColumn(name = "Memberid")
	private Tb_member memberid; // 用户

	private int goodcound; // 点赞数

	private String deviceid; // 唯一设备号

	private Date todaytime; // 点赞时间

	@Column(name = "vipid")
	public int getVipid() {
		return vipid;
	}

	public void setVipid(int vipid) {
		this.vipid = vipid;

	}

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;

	}

	@Column(name = "Goodcound")
	public int getGoodcound() {
		return goodcound;
	}

	public void setGoodcound(int goodcound) {
		this.goodcound = goodcound;

	}

	@Column(name = "deviceid")
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;

	}

	@Column(name = "todaytime")
	public Date getTodaytime() {
		return todaytime;
	}

	public void setTodaytime(Date todaytime) {
		this.todaytime = todaytime;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("vipid", "影展");
		ModelUtil.total.put("memberid", "用户");
		ModelUtil.total.put("goodcound", "点赞数");
		ModelUtil.total.put("deviceid", "唯一设备号");
		ModelUtil.total.put("todaytime", "点赞时间");
	}

}
