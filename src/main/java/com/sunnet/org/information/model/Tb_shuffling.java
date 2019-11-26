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
 * 轮播图表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "Tb_shuffling")
public class Tb_shuffling extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32, name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title; // 标题

	private int linktype; // 链接类型

	private String link; // 自定义链接

	private String imgurl; // 轮播图

	private int sort; // 排序

	private String isshow; // 是否显示

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "edituserid")
	private User edituserid; // 修改人ID

	@Column(name = "Title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	@Column(name = "LinkType")
	public int getLinktype() {
		return linktype;
	}

	public void setLinktype(int linktype) {
		this.linktype = linktype;

	}

	@Column(name = "Link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;

	}

	@Column(name = "ImgUrl")
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;

	}

	@Column(name = "Sort")
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;

	}

	@Column(name = "IsShow")
	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;

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
		ModelUtil.total.put("title", "标题");
		ModelUtil.total.put("linktype", "链接类型");
		ModelUtil.total.put("link", "自定义链接");
		ModelUtil.total.put("imgurl", "轮播图");
		ModelUtil.total.put("sort", "排序");
		ModelUtil.total.put("isshow", "是否显示");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
	}


}
