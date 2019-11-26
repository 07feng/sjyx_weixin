package com.sunnet.org.view.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.org.member.model.Tb_member;

@Entity
@Table(name = "Vie_Info")
public class Vie_Info {
	private static final long serialVersionUID = 1L;

	/*@Id
	@Column(length = 36, name = "Id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")*/
	@Id
	private int rowid;
	/*@Column(length = 36, name = "memberid") 
	@GeneratedValue(generator = "system-guid")
	@GenericGenerator(name = "system-guid", strategy = "guid")*/
	/*@ManyToOne
	@JoinColumn(name = "memberid")*/
	private String memberid;//我 （做条件）
	
    
	private String id; //用户id

	private String usersname; // 用户名称
	private String docid; // 文件id
	private String docname; // 文件名称
	private String infotype;// 区分操作
	private Date adddate;//时间

	
	@Column(name = "rowId")
	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "usersname")
	public String getUsersname() {
		return usersname;
	}

	public void setUsersname(String usersname) {
		this.usersname = usersname;
	}

	@Column(name = "docid")
	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	@Column(name = "docname")
	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	@Column(name = "infotype")
	public String getInfotype() {
		return infotype;
	}

	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}

	@Column(name = "adddate")
	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("usersname", "用户名称");
		ModelUtil.total.put("docid", "文件id");
		ModelUtil.total.put("docname", "文件名称");
		ModelUtil.total.put("infotype", "区分操作");
		ModelUtil.total.put("adddate", "时间");
		ModelUtil.total.put("memberid", "会员id");
	}
}
