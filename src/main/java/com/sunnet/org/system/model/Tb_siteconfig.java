package com.sunnet.org.system.model;

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
import com.sunnet.org.system.model.User;

/**
 * 站点设置
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_siteconfig")
public class Tb_siteconfig extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//编号
	private String sitename; // 站点名称

	private String siteswitch; // 站点开关

	private String logoimg; // LOGO地址

	private String isupload; // 是否允许上传

	private String uploadformat; // 允许上传格式

	private String watermarkswitch; // 水印开关

	private String sietdescribe; // 站点描述

	private String sitekeywords; // 站点关键词

	private String siterecord; // 站点备案号

	private String sitecopyright; // 站点版权信息

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人ID

	@Column(name = "SiteName")
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;

	}

	@Column(name = "SiteSwitch")
	public String getSiteswitch() {
		return siteswitch;
	}

	public void setSiteswitch(String siteswitch) {
		this.siteswitch = siteswitch;

	}

	
	 
	@Column(name = "IsUpload")
	public String getIsupload() {
		return isupload;
	}

	@Column(name = "logoimg")
	public String getLogoimg() {
		return logoimg;
	}

	public void setLogoimg(String logoimg) {
		this.logoimg = logoimg;
	}

	public void setIsupload(String isupload) {
		this.isupload = isupload;

	}

	@Column(name = "UploadFormat")
	public String getUploadformat() {
		return uploadformat;
	}

	public void setUploadformat(String uploadformat) {
		this.uploadformat = uploadformat;

	}

	@Column(name = "WatermarkSwitch")
	public String getWatermarkswitch() {
		return watermarkswitch;
	}

	public void setWatermarkswitch(String watermarkswitch) {
		this.watermarkswitch = watermarkswitch;

	}

	@Column(name = "SietDescribe")
	public String getSietdescribe() {
		return sietdescribe;
	}

	public void setSietdescribe(String sietdescribe) {
		this.sietdescribe = sietdescribe;

	}

	@Column(name = "Sitekeywords")
	public String getSitekeywords() {
		return sitekeywords;
	}

	public void setSitekeywords(String sitekeywords) {
		this.sitekeywords = sitekeywords;

	}

	@Column(name = "SiteRecord")
	public String getSiterecord() {
		return siterecord;
	}

	public void setSiterecord(String siterecord) {
		this.siterecord = siterecord;

	}

	@Column(name = "SiteCopyright")
	public String getSitecopyright() {
		return sitecopyright;
	}

	public void setSitecopyright(String sitecopyright) {
		this.sitecopyright = sitecopyright;

	}

	@Column(name = "EditTime")
	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;

	}

	@Column(name = "EditUserId")
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
		ModelUtil.total.put("sitename", "站点名称");
		ModelUtil.total.put("siteswitch", "站点开关");
		ModelUtil.total.put("logourl", "LOGO地址");
		ModelUtil.total.put("isupload", "是否允许上传");
		ModelUtil.total.put("uploadformat", "允许上传格式");
		ModelUtil.total.put("watermarkswitch", "水印开关");
		ModelUtil.total.put("sietdescribe", "站点描述");
		ModelUtil.total.put("sitekeywords", "站点关键词");
		ModelUtil.total.put("siterecord", "站点备案号");
		ModelUtil.total.put("sitecopyright", "站点版权信息");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
	}

}
