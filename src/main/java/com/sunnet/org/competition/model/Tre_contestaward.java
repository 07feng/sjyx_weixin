package com.sunnet.org.competition.model;

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
 * 赛事奖项
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tre_contestaward")
public class Tre_contestaward extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "Contestid")
	private Tb_contest contestid; // 赛事ID

	private String awardname; // 奖项名称

	private Integer orderto;

	public Tb_contest getContestid() {
		return contestid;
	}

	@Column(name = "orderto")
	public Integer getOrderto() {
		return orderto;
	}

	public void setOrderto(Integer orderto) {
		this.orderto = orderto;
	}

	public void setContestid(Tb_contest contestid) {
		this.contestid = contestid;

	}

	@Column(name = "AwardName")
	public String getAwardname() {
		return awardname;
	}

	public void setAwardname(String awardname) {
		this.awardname = awardname;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("contestid", "赛事ID");
		ModelUtil.total.put("awardname", "奖项名称");
	}

}
