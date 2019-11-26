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

/**
 * 影展VIP开通时限表
 * 
 * @author  
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "filmfestivalvipopentime")
public class Filmfestivalvipopentime extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String time_length_name; // 套餐名称

	private int time_length; // 套餐开通时长

	private String pay_money; // 付费

	@Column(name = "Time_length_name")
	public String getTime_length_name() {
		return time_length_name;
	}

	public void setTime_length_name(String time_length_name) {
		this.time_length_name = time_length_name;

	}

	@Column(name = "Time_length")
	public int getTime_length() {
		return time_length;
	}

	public void setTime_length(int time_length) {
		this.time_length = time_length;

	}

	@Column(name = "pay_money")
	public String getPay_money() {
		return pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("time_length_name", "套餐名称");
		ModelUtil.total.put("time_length", "套餐开通时长");
		ModelUtil.total.put("pay_money", "付费");
	}

}
