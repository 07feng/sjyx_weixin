package com.sunnet.org.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;

/**
 * 菜单
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_menu")
public class Menu extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fdId;

	private String fdGm;// 图片

	private String fdName;// 名称

	private String fdUrl; // 链接

	private String fdPermission; // 权限

	private Integer sort; // 排序

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	public String getFdPermission() {
		return fdPermission;
	}

	public void setFdPermission(String fdPermission) {
		this.fdPermission = fdPermission;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getFdGm() {
		return fdGm;
	}

	public void setFdGm(String fdGm) {
		this.fdGm = fdGm;
	}

	public Menu(){}
	public Menu( String fdGm, String fdName, String fdUrl, String fdPermission, Integer sort) {
		this.fdGm = fdGm;
		this.fdName = fdName;
		this.fdUrl = fdUrl;
		this.fdPermission = fdPermission;
		this.sort = sort;
	}

	public Menu(String fdGm, String fdName, String fdUrl, String fdPermission, Integer sort, Menu menu) {
		this.fdGm = fdGm;
		this.fdName = fdName;
		this.fdUrl = fdUrl;
		this.fdPermission = fdPermission;
		this.sort = sort;
		this.menu = menu;
	}

}
