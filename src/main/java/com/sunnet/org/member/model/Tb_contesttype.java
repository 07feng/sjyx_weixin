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
 * 
 * @author  赛事类型
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_contesttype")
public class Tb_contesttype extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int fid; // 父ID

	private String name; // 赛事类型名称

	private String notes; // 备注

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人ID

	@Column(name = "FId")
	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;

	}

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	@Column(name = "Notes")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;

	}

	@Column(name = "EditTime")
	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;

	}

	public Integer getId() {
		return id;
	}

	public User getEdituserid() {
		return edituserid;
	}

	public void setEdituserid(User edituserid) {
		this.edituserid = edituserid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("fid", "父ID");
		ModelUtil.total.put("name", "赛事类型名称");
		ModelUtil.total.put("notes", "备注");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
	}

}
