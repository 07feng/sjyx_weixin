package com.sunnet.org.view.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;

/**
 * 查询影展与文件表的打赏被打赏人
 * 
 * @author rq1
 *
 */
@Entity
@Table(name = "vip_doc_pay")
public class vip_doc_pay {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String docid; // 文档ID，影展id
	private String doctitle; // 文档ID，影展id
	private String filetype; // 文档ID，影展id
	private String paymemberid; // 打赏人 会员ID
	private String payusersname; // 会员名称
	private String docmemberid; // 被打赏人 会员ID
	private String docmembername; // 会员名称
	private double payamount;// 打赏金额
	private Date addtime;// 打赏时间
	private Integer infotype;// 类型 0影展，1文件

	@Column(name = "addtime")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	@Column(name = "payamount")
	public double getPayamount() {
		return payamount;
	}

	public void setPayamount(double payamount) {
		this.payamount = payamount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "docid")
	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	@Column(name = "paymemberid")
	public String getPaymemberid() {
		return paymemberid;
	}

	public void setPaymemberid(String paymemberid) {
		this.paymemberid = paymemberid;
	}

	@Column(name = "payusersname")
	public String getPayusersname() {
		return payusersname;
	}

	public void setPayusersname(String payusersname) {
		this.payusersname = payusersname;
	}

	@Column(name = "docmemberid")
	public String getDocmemberid() {
		return docmemberid;
	}

	public void setDocmemberid(String docmemberid) {
		this.docmemberid = docmemberid;
	}

	@Column(name = "docmembername")
	public String getDocmembername() {
		return docmembername;
	}

	public void setDocmembername(String docmembername) {
		this.docmembername = docmembername;
	}

	@Column(name = "infotype")
	public Integer getInfotype() {
		return infotype;
	}

	public void setInfotype(Integer infotype) {
		this.infotype = infotype;
	}


	@Column(name = "doctitle")
	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	@Column(name = "filetype")
	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("docid", "文件影展编号");
		ModelUtil.total.put("paymemberid", "打赏人编号");
		ModelUtil.total.put("payusersname", "打赏人名字");
		ModelUtil.total.put("docmemberid", "被打赏人");
		ModelUtil.total.put("docmembername", "被打赏人名字");
		ModelUtil.total.put("payamount", "打赏金额");
		ModelUtil.total.put("addtime", "时间");
		ModelUtil.total.put("infotype", "文件影展类型");
		ModelUtil.total.put("doctitle", "文件名称");
		ModelUtil.total.put("filetype", "图片视频类型");

	}

	@Override
	public String toString() {
		return "vip_doc_pay [id=" + id + ", docid=" + docid + ", doctitle="
				+ doctitle + ", filetype=" + filetype + ", paymemberid="
				+ paymemberid + ", payusersname=" + payusersname
				+ ", docmemberid=" + docmemberid + ", docmembername="
				+ docmembername + ", payamount=" + payamount + ", addtime="
				+ addtime + ", infotype=" + infotype + "]";
	}

}