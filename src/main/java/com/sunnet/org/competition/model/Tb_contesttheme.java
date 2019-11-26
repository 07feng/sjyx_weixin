package com.sunnet.org.competition.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sunnet.code.util.ModelUtil;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.system.model.User;

/**
 * 赛事主题
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_contesttheme")
public class Tb_contesttheme extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String themename; // 名称
	
	@ManyToOne
	@JoinColumn(name = "themenametype")
	private Tb_filetype themenametype; // 主题分类

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人ID
	
	@ManyToMany(mappedBy = "contesttheme", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Tb_contest> tth = new HashSet<Tb_contest>();

	@Column(name = "ThemeName")
	public String getThemename() {
		return themename;
	}

	public void setThemename(String themename) {
		this.themename = themename;

	}

	@Column(name = "EditTime")
	public Date getEdittime() {
		return edittime;
	}
	 
	public Tb_filetype getThemenametype() {
		return themenametype;
	}

	public void setThemenametype(Tb_filetype themenametype) {
		this.themenametype = themenametype;
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

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("themename", "名称");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
	}

}
