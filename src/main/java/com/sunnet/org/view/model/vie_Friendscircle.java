package com.sunnet.org.view.model;

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
import com.sunnet.org.member.model.Tb_member;

/**
 * 展出名单
 * 
 * @author
 *
 */
@Entity
@Table(name = "vie_Friendscircle")
public class vie_Friendscircle {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String titel;// 个人影展标题

	private String open_time; // 开通时间

	private int time_length; // 开通时长(天)

	private int sort; // 排序(后台可干预)
	
	private String titelnote;// 个人简介
	
	private int isfriends;// 是否是互相关注（0不是，1是）
	
	@Column(name = "titelnote")
	public String getTitelnote() {
		return titelnote;
	}

	public void setTitelnote(String titelnote) {
		this.titelnote = titelnote;
	}

	@ManyToOne
	@JoinColumn(name = "Member_id")
	private Tb_member member_id; // 用户

	@Column(name = "titel")
	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	@Column(name = "Open_time")
	public String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(String open_time) {
		this.open_time = open_time;

	}

	@Column(name = "Time_length")
	public int getTime_length() {
		return time_length;
	}

	public void setTime_length(int time_length) {
		this.time_length = time_length;

	}

	@Column(name = "Sort")
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;

	}

	public Tb_member getMember_id() {
		return member_id;
	}

	public void setMember_id(Tb_member member_id) {
		this.member_id = member_id;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getIsfriends() {
		return isfriends;
	}
 

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("open_time", "开通时间");
		ModelUtil.total.put("time_length", "开通时长(天)");
		ModelUtil.total.put("sort", "排序(后台可干预)");
		ModelUtil.total.put("member_id", "用户");
		ModelUtil.total.put("titelnote", "个人简介");
		ModelUtil.total.put("isfriends", "是否关注");
	}
}
