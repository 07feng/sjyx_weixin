package com.sunnet.org.feedback.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.system.model.User;

import javax.persistence.*;
import java.util.Date;

/**
 * 意见反馈
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_feedback")
public class Tb_feedback extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String feedbackinfo; // 反馈信息

	private Date addtime; // 反馈时间

	private String feedbackstatus; // 反馈状态 0未处理 1已处理

	private Date edittime; // 修改时间
	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人ID

	@ManyToOne
	@JoinColumn(name = "memberid")
	private Tb_member memberid; // 会员ID

	@Column(name = "FeedBackInfo")
	public String getFeedbackinfo() {
		return feedbackinfo;
	}

	public void setFeedbackinfo(String feedbackinfo) {
		this.feedbackinfo = feedbackinfo;

	}

	@Column(name = "AddTime")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date date) {
		this.addtime = date;

	}

	@Column(name = "FeedBackStatus")
	public String getFeedbackstatus() {
		return feedbackstatus;
	}

	public void setFeedbackstatus(String feedbackstatus) {
		this.feedbackstatus = feedbackstatus;

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

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("feedbackinfo", "反馈信息");
		ModelUtil.total.put("addtime", "反馈时间");
		ModelUtil.total.put("feedbackstatus", "反馈状态 0未处理 1已处理");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
		ModelUtil.total.put("memberid", "会员ID");
	}

}
