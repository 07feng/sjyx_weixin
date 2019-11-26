package com.sunnet.org.doc.model;

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
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;

/**
 * 大赏表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_docpay")
public class Tb_docpay extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "Docid")
	private Tb_doc docid; // 文档ID

	@ManyToOne
	@JoinColumn(name = "Memberid")
	private Tb_member memberid; // 会员ID

	private double payamount;//打赏金额
	
	private Date addtime;

	@Column(name = "AddTime")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Tb_doc getDocid() {
		return docid;
	}

	@Column(name = "PayAmount")
	public double getPayamount() {
		return payamount;
	}

	public void setPayamount(double payamount) {
		this.payamount = payamount;
	}

	public void setDocid(Tb_doc docid) {
		this.docid = docid;

	}

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("docid", "null");
		ModelUtil.total.put("memberid", "null");
		ModelUtil.total.put("id", "null");
	}

}
