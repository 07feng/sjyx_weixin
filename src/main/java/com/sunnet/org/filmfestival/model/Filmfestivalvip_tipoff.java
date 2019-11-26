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
import com.sunnet.org.system.model.User;

/**
 * 影展举报
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "filmfestivalvip_tipoff")
public class Filmfestivalvip_tipoff extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Tb_member member_id; // 举报人

	@ManyToOne
	@JoinColumn(name = "vipid")
	private Filmfestivalvip vipid; // 影展编号

	private String cause; // 举报原因

	private Date causetime; // 举报时间

	private int status; // 状态

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user_id; // 管理员

	private Date usertime; // 修改时间

	@Column(name = "member_id")
	public Tb_member getMember_id() {
		return member_id;
	}

	public void setMember_id(Tb_member member_id) {
		this.member_id = member_id;

	}

	public Filmfestivalvip getVipid() {
		return vipid;
	}

	public void setVipid(Filmfestivalvip vipid) {
		this.vipid = vipid;

	}

	@Column(name = "cause")
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;

	}

	@Column(name = "causetime")
	public Date getCausetime() {
		return causetime;
	}

	public void setCausetime(Date causetime) {
		this.causetime = causetime;

	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;

	}

	@Column(name = "user_id")
	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;

	}

	@Column(name = "usertime")
	public Date getUsertime() {
		return usertime;
	}

	public void setUsertime(Date usertime) {
		this.usertime = usertime;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("member_id", "举报人");
		ModelUtil.total.put("vipid", "影展编号");
		ModelUtil.total.put("cause", "举报原因");
		ModelUtil.total.put("causetime", "举报时间");
		ModelUtil.total.put("status", "状态");
		ModelUtil.total.put("user_id", "用户");
		ModelUtil.total.put("usertime", "修改时间");
	}

}
