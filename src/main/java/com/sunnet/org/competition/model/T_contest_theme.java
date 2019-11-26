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
 * 赛事主题主键表
 * 
 * @author 强强
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "t_contest_theme")
public class T_contest_theme extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "Contesttheme_id")
	private Tb_contesttheme contesttheme_id; // 主题id

	@ManyToOne
	@JoinColumn(name = "contest_id")
	private Tb_contest contest_id; // 赛事id

	 
	public Tb_contesttheme getContesttheme_id() {
		return contesttheme_id;
	}

	public void setContesttheme_id(Tb_contesttheme contesttheme_id) {
		this.contesttheme_id = contesttheme_id;

	}

 
	public Tb_contest getContest_id() {
		return contest_id;
	}

	public void setContest_id(Tb_contest contest_id) {
		this.contest_id = contest_id;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("contesttheme_id", "null");
		ModelUtil.total.put("contest_id", "null");
	}

}
