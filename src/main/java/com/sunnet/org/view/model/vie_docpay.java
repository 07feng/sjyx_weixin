package com.sunnet.org.view.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.org.member.model.Tb_level;

@Entity
@Table(name = "vie_docpay")
public class vie_docpay {
	@Id
	@Column(length = 36) 
	@GeneratedValue(generator = "system-guid")
	@GenericGenerator(name = "system-guid", strategy = "guid")
	private String id;//打赏人
	private String usersname; // 昵称
	private String headimg; // 头像
	private int levelid; // 会员等级ID
	private String levelname;//等级名称
	private String memberid;//我的用户编号
	private String cpaynum;//打赏次数
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "usersname")
	public String getUsersname() {
		return usersname;
	}
	public void setUsersname(String usersname) {
		this.usersname = usersname;
	}
	@Column(name = "headimg")
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	@Column(name = "levelid")
	public int getLevelid() {
		return levelid;
	}
	public void setLevelid(int levelid) {
		this.levelid = levelid;
	}
	@Column(name = "levelname")
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	@Column(name = "memberid")
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	@Column(name = "cpaynum")
	public String getCpaynum() {
		return cpaynum;
	}
	public void setCpaynum(String cpaynum) {
		this.cpaynum = cpaynum;
	}
	
}
