package com.sunnet.org.visitors.model;

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
 * 热门搜索词
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "t_topsearchterm")
public class T_topsearchterm extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 255)
	private String fdSearchterm; // 搜索词

	private Integer fd_searchnumber; // 搜索次数

	private Date fd_lastsearchtime; // 最后搜索时间

	public String getFdSearchterm() {
		return fdSearchterm;
	}

	public void setFdSearchterm(String fdSearchterm) {
		this.fdSearchterm = fdSearchterm;
	}

	@Column(name = "fd_lastsearchtime")
	public Date getFd_lastsearchtime() {
		return fd_lastsearchtime;
	}

	@Column(name = "fd_searchNumber")
	public Integer getFd_searchnumber() {
		return fd_searchnumber;
	}

	public void setFd_searchnumber(Integer fd_searchnumber) {
		this.fd_searchnumber = fd_searchnumber;
	}

	public void setFd_lastsearchtime(Date fd_lastsearchtime) {
		this.fd_lastsearchtime = fd_lastsearchtime;
	}

	public void getMap() {
		ModelUtil.total.put("fdSearchterm", "搜索词");
		ModelUtil.total.put("fdsearchnumber", "搜索次数");
		ModelUtil.total.put("fdLastsearchTime", "最后搜索时间");
	}

}
