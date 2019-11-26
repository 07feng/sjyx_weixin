package com.sunnet.org.doc.model;

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
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.model.Tb_contesttheme;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;

/**
 * 专家推荐
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_groupdocgood")
public class Tb_groupdocgood extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "doc_id")
	private Tb_doc doc_id; // 文件
	
	@ManyToOne
	@JoinColumn(name = "contesttheme_id")
	private Tb_contesttheme contesttheme_id; //赛事主题
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Tb_member member_id; // 用户

	@ManyToOne
	@JoinColumn(name = "contest_id")
	private Tb_contest contest_id; // 赛事

	private int goodcound; // 点赞数

	private Date todaytime; // 点赞时间

	public Tb_doc getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(Tb_doc doc_id) {
		this.doc_id = doc_id;
	}

	public Tb_member getMember_id() {
		return member_id;
	}

	public void setMember_id(Tb_member member_id) {
		this.member_id = member_id;
	}

	public Tb_contest getContest_id() {
		return contest_id;
	}

	public void setContest_id(Tb_contest contest_id) {
		this.contest_id = contest_id;
	}

	@Column(name = "GoodCound")
	public int getGoodcound() {
		return goodcound;
	}

	public void setGoodcound(int goodcound) {
		this.goodcound = goodcound;

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

	public Tb_contesttheme getContesttheme_id() {
		return contesttheme_id;
	}

	public void setContesttheme_id(Tb_contesttheme contesttheme_id) {
		this.contesttheme_id = contesttheme_id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("doc_id", "文件");
		ModelUtil.total.put("member_id", "用户");
		ModelUtil.total.put("contest_id", "赛事");
		ModelUtil.total.put("goodcound", "点赞数");
		ModelUtil.total.put("todaytime", "点赞时间");
		ModelUtil.total.put("contesttheme_id", "主题");
	}

}
