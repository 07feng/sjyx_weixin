package com.sunnet.org.doc.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 朋友圈关注表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tre_friendscircle")
public class Tre_friendscircle extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "Circlememberid")
	@NotFound(action= NotFoundAction.IGNORE)
	private Tb_member circlememberid; // 被关注人的ID

	@ManyToOne
	@JoinColumn(name = "Memberid")
	@NotFound(action=NotFoundAction.IGNORE)
	private Tb_member memberid; // 会员ID

	private Date addtime; // 关注时间

	private int isfriends;//是否是互相关注（0不是，1是）
 
	@Column(name = "isfriends")
	public int getIsfriends() {
		return isfriends;
	}

	public void setIsfriends(int isfriends) {
		this.isfriends = isfriends;
	}

	public Tb_member getCirclememberid() {
		return circlememberid;
	}

	public void setCirclememberid(Tb_member circlememberid) {
		this.circlememberid = circlememberid;

	}

 
	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;

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
		ModelUtil.total.put("circlememberid", "被关注人的ID");
		ModelUtil.total.put("memberid", "会员ID");
		ModelUtil.total.put("addtime", "关注时间");
	}

}
