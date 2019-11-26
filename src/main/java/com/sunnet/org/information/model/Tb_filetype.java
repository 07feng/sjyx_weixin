package com.sunnet.org.information.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.system.model.User;

/**
 * 前台菜单分类表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_filetype")
public class Tb_filetype extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "FId")
	private int fId; // 父ID

	@Column(name = "type_name")
	private String typeName; // 分类名称

	public Tb_filetype() {
	}

	/**
	 * @param id
	 * @param typeName
	 * author jinhao
	 */
	public Tb_filetype(Integer id, String typeName) {
		this.id = id;
		this.typeName = typeName;

	}

	@Column(name = "type_sort")
	private int typeSort; // 排序

	@Column(name = "type_notes")
	private String typeNotes; // 备注

	private Date edit_time; // 修改时间

	@ManyToOne
	@JoinColumn(name = "edit_user_id")
	private User editUserId; // 修改人id

	@Column(name = "type_type")
	private Integer type_type;// 是否显示

	public Integer getType_type() {
		return type_type;
	}

	public void setType_type(Integer type_type) {
		this.type_type = type_type;
	}

	public int getFId() {
		return fId;
	}

	public void setFId(int fId) {
		this.fId = fId;

	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;

	}

	public int getTypeSort() {
		return typeSort;
	}

	public void setTypeSort(int typeSort) {
		this.typeSort = typeSort;

	}

	public String getTypeNotes() {
		return typeNotes;
	}

	public void setTypeNotes(String typeNotes) {
		this.typeNotes = typeNotes;

	}

	public int getfId() {
		return fId;
	}

	public void setfId(int fId) {
		this.fId = fId;
	}

	public User getEditUserId() {
		return editUserId;
	}

	public void setEditUserId(User editUserId) {
		this.editUserId = editUserId;
	}

	@Column(name = "edit_time")
	public Date getEdit_time() {
		return edit_time;
	}

	public void setEdit_time(Date edit_time) {
		this.edit_time = edit_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
