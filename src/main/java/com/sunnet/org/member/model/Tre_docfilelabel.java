package com.sunnet.org.member.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.information.model.Tb_doc;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tre_docfilelabel")
public class Tre_docfilelabel extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	 
	@ManyToOne
	@JoinColumn(name="labelid")
	@NotFound(action= NotFoundAction.IGNORE)
	private Tb_filelabel labelid; // 标签ID 
	 
	@ManyToOne
	@JoinColumn(name="docid")
	@NotFound(action= NotFoundAction.IGNORE)
	private Tb_doc docid; // 文件ID //这个不是实体类？是

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("labelid", "标签ID");
		ModelUtil.total.put("docid", "文件ID");
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Tb_filelabel getLabelid() {
		return labelid;
	}


	public void setLabelid(Tb_filelabel labelid) {
		this.labelid = labelid;
	}


	public Tb_doc getDocid() {
		return docid;
	}


	public void setDocid(Tb_doc docid) {
		this.docid = docid;
	}

}
