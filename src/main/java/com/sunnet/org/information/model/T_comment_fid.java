package com.sunnet.org.information.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 评论子表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "t_comment_fid")
public class T_comment_fid extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotFound(action= NotFoundAction.IGNORE)
	private Integer id;
	private int fd_doccomment_id; // 评论编号

	@ManyToOne
	@JoinColumn(name = "fd_member_id")
	private Tb_member fd_member_id; // 评论人

	@ManyToOne
	@JoinColumn(name = "fd_c_member_id")
	private Tb_member fd_c_member_id; // 被评论人

	private Date fd_comment_time; // 评论时间

	private String fd_comment_note; // 评论内容

	@Column(name = "fd_doccomment_id")
	public int getFd_doccomment_id() {
		return fd_doccomment_id;
	}

	public void setFd_doccomment_id(int fd_doccomment_id) {
		this.fd_doccomment_id = fd_doccomment_id;

	}

	 
	public Tb_member getFd_member_id() {
		return fd_member_id;
	}

	public void setFd_member_id(Tb_member fd_member_id) {
		this.fd_member_id = fd_member_id;

	}
  
	 
	public Tb_member getFd_c_member_id() {
		return fd_c_member_id;
	}

	public void setFd_c_member_id(Tb_member fd_c_member_id) {
		this.fd_c_member_id = fd_c_member_id;

	}

	@Column(name = "fd_comment_time")
	public Date getFd_comment_time() {
		return fd_comment_time;
	}

	public void setFd_comment_time(Date fd_comment_time) {
		this.fd_comment_time = fd_comment_time;

	}

	@Column(name = "fd_comment_note")
	public String getFd_comment_note() {
		return fd_comment_note;
	}

	public void setFd_comment_note(String fd_comment_note) {
		this.fd_comment_note = fd_comment_note;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "主键");
		ModelUtil.total.put("fd_doccomment_id", "评论编号");
		ModelUtil.total.put("fd_member_id", "评论人");
		ModelUtil.total.put("fd_c_member_id", "被评论人");
		ModelUtil.total.put("fd_comment_time", "评论时间");
		ModelUtil.total.put("fd_comment_note", "评论内容");
	}

}
