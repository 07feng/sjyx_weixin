package com.sunnet.org.filmfestival.model;

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
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;

/**
 * 个人影展作品表
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "filmfestival")
public class Filmfestival extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    
	@ManyToOne
	@JoinColumn(name = "Doc_id")
	private Tb_doc doc_id; // 作品ID

	private int sort; // 排序
	 
	@ManyToOne
	@JoinColumn(name = "vip_id")
	private Filmfestivalvip vip_id;//filmfestivalvip表的id
	
	public Filmfestivalvip getVip_id() {
		return vip_id;
	}

	public void setVip_id(Filmfestivalvip vip_id) {
		this.vip_id = vip_id;
	}

	@ManyToOne
	@JoinColumn(name = "Member_id")
	private Tb_member member_id; // 用户编号


	public Tb_doc getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(Tb_doc doc_id) {
		this.doc_id = doc_id;

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

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("doc_id", "作品ID");
		ModelUtil.total.put("sort", "排序");
		ModelUtil.total.put("member_id", "用户编号");
	}

}
