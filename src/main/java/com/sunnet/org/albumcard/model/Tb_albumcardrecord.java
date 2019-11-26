package com.sunnet.org.albumcard.model;

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
 * 相册贺卡记录表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_albumcardrecord")
public class Tb_albumcardrecord extends BaseModel {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fd_id; // 编号

	@ManyToOne
	@JoinColumn(name = "fd_albumvard_id")
	private Tb_albumcard fd_albumvard_id; // 模板id

	@ManyToOne
	@JoinColumn(name = "fd_member_id")
	private Tb_member fdmemberid;// 用户id

	private String fd_url; // 文件路径

	private String fd_mname; // 文件名称
	
	private String fd_docpath; // 图片路径
	
	private String fd_docdec; //替换的文本内容 
	
	private String fd_filmfirstpic;// 影展第一张图片记录路径

	private int fd_ispay; // 是否支付（0否，1是）

	@Column(name = "fd_id")
	public String getFd_id() {
		return fd_id;
	}

	public void setFd_id(String fd_id) {
		this.fd_id = fd_id;

	}

	@Column(name = "fd_docpath")
	public String getFd_docpath() {
		return fd_docpath;
	}

	public void setFd_docpath(String fd_docpath) {
		this.fd_docpath = fd_docpath;
	}

	@Column(name = "fd_mname")
	public String getFd_mname() {
		return fd_mname;
	}

	public void setFd_mname(String fd_mname) {
		this.fd_mname = fd_mname;
	}

	@Column(name = "fd_filmfirstpic")
	public String getFd_filmfirstpic() {
		return fd_filmfirstpic;
	}

	public void setFd_filmfirstpic(String fd_filmfirstpic) {
		this.fd_filmfirstpic = fd_filmfirstpic;
	}

	public Tb_member getFdmemberid() {
		return fdmemberid;
	}

	public void setFdmemberid(Tb_member fdmemberid) {
		this.fdmemberid = fdmemberid;
	}

	public Tb_albumcard getFd_albumvard_id() {
		return fd_albumvard_id;
	}

	public void setFd_albumvard_id(Tb_albumcard fd_albumvard_id) {
		this.fd_albumvard_id = fd_albumvard_id;

	}

	@Column(name = "fd_url")
	public String getFd_url() {
		return fd_url;
	}

	public void setFd_url(String fd_url) {
		this.fd_url = fd_url;
	}

	@Column(name = "fd_ispay")
	public int getFd_ispay() {
		return fd_ispay;
	}

	public void setFd_ispay(int fd_ispay) {
		this.fd_ispay = fd_ispay;

	}

	public String getFd_docdec() {
		return fd_docdec;
	}

	public void setFd_docdec(String fd_docDec) {
		this.fd_docdec = fd_docDec;
	}

	public void getMap() {
		ModelUtil.total.put("fd_id", "编号");
		ModelUtil.total.put("fd_albumvard_id ", "模板id");
		ModelUtil.total.put("fd_ispay", "是否支付（0否，1是）");
		ModelUtil.total.put("fdmemberid", "用户编号");
	}

}
