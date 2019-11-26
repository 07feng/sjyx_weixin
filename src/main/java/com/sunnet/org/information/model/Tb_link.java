package com.sunnet.org.information.model;

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
 * @author 友情链接
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_link")
public class Tb_link extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title; // 标题

	private String iconimg; // 图标

	private int sort; // 排序

	private Date edit_time; // null

	private Integer istype;//链接类型
	@ManyToOne
	@JoinColumn(name = "edit_user_id")
	private User edit_user_id; // null

	private String link_url; // null

	@Column(name = "istype")
	public Integer getIstype() {
		return istype;
	}

	public void setIstype(Integer istype) {
		this.istype = istype;
	}

	public User getEdit_user_id() {
		return edit_user_id;
	}

	public void setEdit_user_id(User edit_user_id) {
		this.edit_user_id = edit_user_id;
	}

	@Column(name = "Title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	@Column(name = "iconimg")
	public String getIconimg() {
		return iconimg;
	}

	public void setIconimg(String iconimg) {
		this.iconimg = iconimg;
	}

	@Column(name = "Sort")
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;

	}

	@Column(name = "link_url")
	public String getLink_url() {
		return link_url;
	}

	@Column(name = "edit_time")
	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("title", "标题");
		ModelUtil.total.put("iconimg", "图标");
		ModelUtil.total.put("sort", "排序");
		ModelUtil.total.put("edit_time", "null");
		ModelUtil.total.put("edit_user_id", "null");
		ModelUtil.total.put("link_url", "null");
	}

}
