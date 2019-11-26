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
 * 影展评论
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "filmfestivalvip_comment")
public class Filmfestivalvip_comment extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int fid; // 评论分级 为0则一级评论,其他为二级评论

	@ManyToOne
	@JoinColumn(name = "vipid")
	private Filmfestivalvip vipid; // 影展

	private String comments; // 评论

	private Date comment_time; // 评论时间

	private Date edit_time; // 修改时间

	@ManyToOne
	@JoinColumn(name = "edit_userid")
	private User edit_userid; // 修改人

	private String is_public; // 是否公开（0否1是）

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Tb_member member_id; // 用户

	@Column(name = "FId")
	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;

	}

	public Filmfestivalvip getVipid() {
		return vipid;
	}

	public void setVipid(Filmfestivalvip vipid) {
		this.vipid = vipid;

	}

	@Column(name = "Comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;

	}

	@Column(name = "comment_time")
	public Date getComment_time() {
		return comment_time;
	}

	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;

	}

	@Column(name = "edit_time")
	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;

	}

	@Column(name = "edit_userid")
	public User getEdit_userid() {
		return edit_userid;
	}

	public void setEdit_userid(User edit_userid) {
		this.edit_userid = edit_userid;

	}

	@Column(name = "is_public")
	public String getIs_public() {
		return is_public;
	}

	public void setIs_public(String is_public) {
		this.is_public = is_public;

	}

	@Column(name = "member_id")
	public Tb_member getMember_id() {
		return member_id;
	}

	public void setMember_id(Tb_member member_id) {
		this.member_id = member_id;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("fid", "评论分级 为0则一级评论,其他为二级评论");
		ModelUtil.total.put("vipid", "影展");
		ModelUtil.total.put("comments", "评论");
		ModelUtil.total.put("comment_time", "评论时间");
		ModelUtil.total.put("edit_time", "修改时间");
		ModelUtil.total.put("edit_userid", "修改人");
		ModelUtil.total.put("is_public", "是否公开（0否1是）");
		ModelUtil.total.put("member_id", "用户");
	}

}
