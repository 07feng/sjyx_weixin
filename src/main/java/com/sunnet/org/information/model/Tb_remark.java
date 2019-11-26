package com.sunnet.org.information.model;

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
 * 说明表
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_remark")
public class Tb_remark extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title; // 名称

	private Date edit_time; // 修改时间

	@ManyToOne
	@JoinColumn(name = "edit_user_id")
	private User edit_user_id; // 修改人

	private String link_url; // 链接地址

	private String remark; // 备注

	private int istype; // 类型（0办展说明，1帮助，2意见反馈，3定制说明,4关于）

	@Column(name = "Title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

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

	@Column(name = "link_url")
	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;

	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;

	}

	@Column(name = "istype")
	public int getIstype() {
		return istype;
	}

	public void setIstype(int istype) {
		this.istype = istype;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("title", "名称");
		ModelUtil.total.put("edit_time", "修改时间");
		ModelUtil.total.put("edit_user_id", "修改人");
		ModelUtil.total.put("link_url", "链接地址");
		ModelUtil.total.put("remark", "备注");
		ModelUtil.total.put("istype", "类型（0办展说明，1帮助，2意见反馈，3定制说明，4关于）");
	}

}
