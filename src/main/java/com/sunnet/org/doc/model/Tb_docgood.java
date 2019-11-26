package com.sunnet.org.doc.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;

import javax.persistence.*;
import java.util.Date;

/**
 * 点赞表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_docgood")
public class Tb_docgood extends BaseModel {
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

	private int goodcound; // 点赞数量

	private String deviceid;//手机唯一编码地址

	private Date todaytime;//点赞时间

	@Column(name = "Deviceid")
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	@Column(name = "todaytime")
	public Date getTodaytime() {
		return todaytime;
	}

	public void setTodaytime(Date todaytime) {
		this.todaytime = todaytime;
	}

	public Tb_doc getDocid() {
		return docid;
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

	@Column(name = "GoodCound")
	public int getGoodcound() {
		return goodcound;
	}

	public void setGoodcound(int goodcound) {
		this.goodcound = goodcound;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("docid", "文档ID");
		ModelUtil.total.put("memberid", "会员ID");
		ModelUtil.total.put("goodcound", "点赞数量");
		ModelUtil.total.put("deviceid", "设备id");
		ModelUtil.total.put("todaytime", "点赞时间");
	}

}
