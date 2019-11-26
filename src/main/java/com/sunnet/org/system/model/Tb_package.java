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

/**
 * 更新版本
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_package")
public class Tb_package extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String package_name; // 包名

	private String package_url; // 地址

	private String package_version; // 版本

	private Date edit_time; // 修改时间

	@ManyToOne
	@JoinColumn(name = "edit_user_id")
	private User edit_user_id; // 修改人

	@Column(name = "package_name")
	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;

	}

	@Column(name = "package_url")
	public String getPackage_url() {
		return package_url;
	}

	public void setPackage_url(String package_url) {
		this.package_url = package_url;

	}

	@Column(name = "package_version")
	public String getPackage_version() {
		return package_version;
	}

	public void setPackage_version(String package_version) {
		this.package_version = package_version;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("package_name", "包名");
		ModelUtil.total.put("package_url", "地址");
		ModelUtil.total.put("package_version", "版本");
		ModelUtil.total.put("edit_time", "修改时间");
		ModelUtil.total.put("edit_user_id", "修改人");
	}

}
