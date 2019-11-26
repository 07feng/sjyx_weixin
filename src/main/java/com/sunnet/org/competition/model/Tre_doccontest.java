package com.sunnet.org.competition.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.system.model.User;

import javax.persistence.*;
import java.util.Date;

/**
 * 参赛作品表
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tre_doccontest")
public class Tre_doccontest extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int audit_status; // 审核状态 0未审核 1已审核 2未通过
	
	@ManyToOne
	@JoinColumn(name = "Contesttheme_id")
	private Tb_contesttheme contesttheme_id; //赛事主题
	 
	@ManyToOne
	@JoinColumn(name = "award_id")
	private Tre_contestaward award_id; // 获奖奖项ID
	
	@ManyToOne
	@JoinColumn(name = "contest_id") 
	private Tb_contest contest_id; // 赛事ID

	@ManyToOne
	@JoinColumn(name = "doc_id")
	private Tb_doc doc_id; // 参赛文档ID

	private Date edit_time; // 修改时间
	
	private Date add_date; // 参赛时间
 
	@ManyToOne
	@JoinColumn(name = "edit_user_id")
	private User edit_user_id; // 修改人Id

	private String is_get_award; // 是否获奖(0否，1是)

	private String is_shortlisted; // 是否入围(0否，1是)

	@Column(name = "audit_status")
	public int getAudit_status() {
		return audit_status;
	}
	
	@Column(name = "add_date")
	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Tb_contesttheme getContesttheme_id() {
		return contesttheme_id;
	}

	public void setContesttheme_id(Tb_contesttheme contesttheme_id) {
		this.contesttheme_id = contesttheme_id;
	}

	public void setAudit_status(int audit_status) {
		this.audit_status = audit_status;

	}


	public Tre_contestaward getAward_id() {
		return award_id;
	}

	public void setAward_id(Tre_contestaward award_id) {
		this.award_id = award_id;

	}

	
	public Tb_contest getContest_id() {
		return contest_id;
	}

	public void setContest_id(Tb_contest contest_id) {
		this.contest_id = contest_id;

	}

	public Tb_doc getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(Tb_doc doc_id) {
		this.doc_id = doc_id;

	}

	@Column(name = "edit_time")
	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;

	}

	 
	public User getEdit_user_id() {
		return edit_user_id;
	}

	public void setEdit_user_id(User edit_user_id) {
		this.edit_user_id = edit_user_id;

	}

	@Column(name = "is_get_award")
	public String getIs_get_award() {
		return is_get_award;
	}

	public void setIs_get_award(String is_get_award) {
		this.is_get_award = is_get_award;

	}

	@Column(name = "is_shortlisted")
	public String getIs_shortlisted() {
		return is_shortlisted;
	}

	public void setIs_shortlisted(String is_shortlisted) {
		this.is_shortlisted = is_shortlisted;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("audit_status", "审核状态 0未审核 1已审核 2未通过  ");
		ModelUtil.total.put("award_id", "获奖奖项ID  ");
		ModelUtil.total.put("contest_id", "赛事ID");
		ModelUtil.total.put("doc_id", "参赛文档ID ");
		ModelUtil.total.put("edit_time", "修改时间");
		ModelUtil.total.put("edit_user_id", "修改人Id");
		ModelUtil.total.put("is_get_award", "是否获奖");
		ModelUtil.total.put("is_shortlisted", "是否入围");
		ModelUtil.total.put("add_date", "参赛时间");}

}
