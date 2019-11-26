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
 * 消息模板
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_messagetemplate")
public class Tb_messagetemplate extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String templatetype; // 模板类型 [SMS]短信模板 [APP]APP推送模板

	private String templatename; // 模板名称

	private String templateinfo; // 模板内容

	private String templateparam; // 模板code

	private String templatenote; // 模板备注

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人

	@Column(name = "TemplateType")
	public String getTemplatetype() {
		return templatetype;
	}

	public void setTemplatetype(String templatetype) {
		this.templatetype = templatetype;

	}

	@Column(name = "TemplateName")
	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;

	}

	@Column(name = "TemplateInfo")
	public String getTemplateinfo() {
		return templateinfo;
	}

	public void setTemplateinfo(String templateinfo) {
		this.templateinfo = templateinfo;

	}

	@Column(name = "TemplateNote")
	public String getTemplatenote() {
		return templatenote;
	}

	public void setTemplatenote(String templatenote) {
		this.templatenote = templatenote;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "templateparam")
	public String getTemplateparam() {
		return templateparam;
	}

	public void setTemplateparam(String templateparam) {
		this.templateparam = templateparam;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("templatetype", "模板类型 [SMS]短信模板  [APP]APP推送模板");
		ModelUtil.total.put("templatename", "模板名称");
		ModelUtil.total.put("templateinfo", "模板内容");
		ModelUtil.total.put("templatenote", "模板备注");
		ModelUtil.total.put("templateparam", "code");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人");

	}

}
