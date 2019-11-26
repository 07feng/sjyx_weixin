package com.sunnet.org.member.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;

/**
 * 标签表的
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_filelabel")
public class Tb_filelabel extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name; // 名称

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("name", "名称");
	}

}
