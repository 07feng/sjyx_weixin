package com.sunnet.org.doc.model;

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
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;

/**
 * 用户评分历史表
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tre_menberdocscore")
public class Tre_menberdocscore extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "Docid")
	private Tb_doc docid; // 文件ID

	@ManyToOne
	@JoinColumn(name = "Memberid")
	private Tb_member memberid; // 会员id

	private String score; // 评分

	private String scoretime; // 评分时间

	 
	public Tb_doc getDocid() {
		return docid;
	}

	public void setDocid(Tb_doc docid) {
		this.docid = docid;

	}

	 
	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;

	}

	@Column(name = "Score")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;

	}

	@Column(name = "ScoreTime")
	public String getScoretime() {
		return scoretime;
	}

	public void setScoretime(String scoretime) {
		this.scoretime = scoretime;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("docid", "文件ID");
		ModelUtil.total.put("memberid", "会员");
		ModelUtil.total.put("score", "评分");
		ModelUtil.total.put("scoretime", "评分时间");
	}

}
