package com.sunnet.org.information.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.system.model.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * @author 评论管理
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tre_menberdoccomment")
public class Tre_menberdoccomment extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int fid; // 评论分级 为0则一级评论,其他为二级评论

	@ManyToOne
	@JoinColumn(name = "Docid")
	@NotFound(action=NotFoundAction.IGNORE)
	private Tb_doc docid; // 文件ID

	private String comments; // 评论

	private Date edit_time; // 修改时间

	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "member_id")
	private Tb_member member_id; // 关联会员表id

	private Date comment_time; // 评论时间

	@ManyToOne
	@JoinColumn(name = "edit_userid")
	private User edit_userid; // 修改人id

	private String is_public; // 是否显示

	@Column(name = "FId")
	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;

	}

	// @Column(name = "DocId")

	public Tb_doc getDocid() {
		return docid;
	}

	public void setDocid(Tb_doc docid) {
		this.docid = docid;
	}

	@Column(name = "Comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;

	}

	@Column(name = "edit_time")
	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}

	public Tb_member getMember_id() {
		return member_id;
	}

	public void setMember_id(Tb_member member_id) {
		this.member_id = member_id;
	}

	@Column(name = "comment_time")
	public Date getComment_time() {
		return comment_time;
	}

	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}

	// @Column(name = "edit_userid")

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("fid", "评论分级 为0则一级评论,其他为二级评论");
		ModelUtil.total.put("docid", "文件ID");
		ModelUtil.total.put("comments", "评论");
		ModelUtil.total.put("edit_time", "修改时间");
		ModelUtil.total.put("member_id", "会员");
		ModelUtil.total.put("comment_time", "评论时间");
		ModelUtil.total.put("edit_userid", "用户名");
		ModelUtil.total.put("is_public", "是否发布");
	}

	public Tre_menberdoccomment() {
	}

	public Tre_menberdoccomment(Integer id, String comments, Tb_member member_id, Date comment_time) {
		this.id = id;
		this.comments = comments;
		this.member_id = member_id;
		this.comment_time = comment_time;
	}
}
