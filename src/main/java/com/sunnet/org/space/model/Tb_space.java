package com.sunnet.org.space.model;

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
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_space")
public class Tb_space extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String spacesize; // 空间大小

	private String charges; // 费用

	private String note; // 备注

	private String sppacestatus; // 状态 0未上架 1已上架

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人ID

	@Column(name = "SpaceSize")
	public String getSpacesize() {
		return spacesize;
	}

	public void setSpacesize(String spacesize) {
		this.spacesize = spacesize;

	}

	@Column(name = "Charges")
	public String getCharges() {
		return charges;
	}

	public void setCharges(String charges) {
		this.charges = charges;

	}

	@Column(name = "Note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;

	}

	@Column(name = "SppaceStatus")
	public String getSppacestatus() {
		return sppacestatus;
	}

	public void setSppacestatus(String sppacestatus) {
		this.sppacestatus = sppacestatus;

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

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("spacesize", "空间大小");
		ModelUtil.total.put("charges", "费用");
		ModelUtil.total.put("note", "备注");
		ModelUtil.total.put("sppacestatus", "状态 0未上架 1已上架");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
	}

}
