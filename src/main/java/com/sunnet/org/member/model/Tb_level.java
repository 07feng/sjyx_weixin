package com.sunnet.org.member.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.system.model.User;

/**
 * 等级表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "Tb_level")
public class Tb_level extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String levelname; // 级别名称

	private String minexp; // 级别最小经验值

	private String maxexp; // 级别最大经验值

	private String iconimg; // 图标图片地址

	private String notes; // 备注

	private Date edittime; // 修改时间
	 
	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人ID

	@Column(name = "LevelName")
	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;

	}

	@Column(name = "MinExp")
	public String getMinexp() {
		return minexp;
	}

	public void setMinexp(String minexp) {
		this.minexp = minexp;

	}

	@Column(name = "MaxExp")
	public String getMaxexp() {
		return maxexp;
	}

	public void setMaxexp(String maxexp) {
		this.maxexp = maxexp;

	}

	@Column(name = "IconImg")
	public String getIconimg() {
		return iconimg;
	}

	public void setIconimg(String iconimg) {
		this.iconimg = iconimg;

	}

	@Column(name = "Notes")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;

	}


	public User getEdituserid() {
		return edituserid;
	}

	@Column(name = "EditTime")
	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
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

	@Override
	public String toString() {
		return "Tb_level [id=" + id + ", levelname=" + levelname + ", minexp="
				+ minexp + ", maxexp=" + maxexp + ", iconimg=" + iconimg
				+ ", notes=" + notes + ", edittime=" + edittime
				+ ", edituserid=" + edituserid + "]";
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("levelname", "级别名称");
		ModelUtil.total.put("minexp", "级别最小经验值");
		ModelUtil.total.put("maxexp", "级别最大经验值");
		ModelUtil.total.put("iconimg", "图标图片地址");
		ModelUtil.total.put("notes", "备注");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
	}

}
