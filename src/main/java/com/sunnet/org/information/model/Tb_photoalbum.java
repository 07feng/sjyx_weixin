package com.sunnet.org.information.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * @author 相册表
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_photoalbum")
public class Tb_photoalbum extends BaseModel {
	 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotFound(action= NotFoundAction.IGNORE)
	private Integer id;

	private String photoalbumname; // 相册名称

	/*
	 * @ManyToOne
	 *
	 * @JoinColumn(name = "memberid") private Tb_member memberid; // 会员ID
	 */
	private String memberid; // 会员ID
	
	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name = "frontcover_docid")
	private Tb_doc docid; // 文件ID
	
	private Date createtime; // 创建时间

	@Column(name = "PhotoAlbumName")
	public String getPhotoalbumname() {
		return photoalbumname;
	}

	public void setPhotoalbumname(String photoalbumname) {
		this.photoalbumname = photoalbumname;

	}

	@Column(name = "memberid")
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	@Column(name = "CreateTime")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	 

	public Tb_doc getDocid() {
		return docid;
	}

	public void setDocid(Tb_doc docid) {
		this.docid = docid;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("photoalbumname", "相册名称");
		ModelUtil.total.put("memberid", "会员ID");
		ModelUtil.total.put("createtime", "创建时间");
		ModelUtil.total.put("docid", "封面图");
	}

}
