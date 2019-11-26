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
 * 发送消息列表
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_sendmessage")
public class Tb_sendmessage extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String messagetype; // 消息类型 [SMS]短信 [APP]APP推送

	private String messageinfo; // 消息内容

	private Date edittime; // 操作时间

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 操作人ID

	@ManyToOne
	@JoinColumn(name = "Memberid")
	private Tb_member memberid; // 会员

	@Column(name = "MessageType")
	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;

	}

	@Column(name = "MessageInfo")
	public String getMessageinfo() {
		return messageinfo;
	}

	public void setMessageinfo(String messageinfo) {
		this.messageinfo = messageinfo;

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
		ModelUtil.total.put("messagetype", "消息类型 [SMS]短信  [APP]APP推送");
		ModelUtil.total.put("messageinfo", "消息内容");
		ModelUtil.total.put("edittime", "操作时间");
		ModelUtil.total.put("edituserid", "操作人ID");
		ModelUtil.total.put("memberid", "null");
	}

}
