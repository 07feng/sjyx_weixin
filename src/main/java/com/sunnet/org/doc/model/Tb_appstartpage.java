package com.sunnet.org.doc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sunnet.code.util.ModelUtil;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.system.model.User;

/**
 * APP启动页表（广告。。）
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_appstartpage")
public class Tb_appstartpage extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String note; // 备注

	private String imgurl; // 图片

	private String linktype; // 链接类型

	private String linkurl; // 链接地址

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "edituserid")
	private User edituserid; // 修改人ID
	
	private Integer ispublic;//是否启用

	@Column(name = "ispublic")
	public Integer getIspublic() {
		return ispublic;
	}

	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}

	@Column(name = "Note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;

	}

	@Column(name = "ImgUrl")
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;

	}

	@Column(name = "LinkType")
	public String getLinktype() {
		return linktype;
	}

	public void setLinktype(String linktype) {
		this.linktype = linktype;

	}

	@Column(name = "LinkUrl")
	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;

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
		ModelUtil.total.put("note", "备注");
		ModelUtil.total.put("imgurl", "图片");
		ModelUtil.total.put("linktype", "链接类型");
		ModelUtil.total.put("linkurl", "链接地址");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
		ModelUtil.total.put("ispublic", "是否启用");
	}

}
