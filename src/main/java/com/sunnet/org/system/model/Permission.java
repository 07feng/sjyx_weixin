package com.sunnet.org.system.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;

@Entity
@Table(name = "t_permission")
public class Permission extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fdId;

	private String fdPermissionCode; //权限标识

	private String fdPermissionName; // 权限名称

	private String fdPermissionDesc; // 权限描述

	private Integer fdStatus; // 状态

	private long fdCreateTime; // 创建时间

	@ManyToMany(mappedBy = "permissions", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	public String getFdId()
	{
		return fdId;
	}

	public void setFdId(String fdId)
	{
		this.fdId = fdId;
	}

	public String getFdPermissionCode()
	{
		return fdPermissionCode;
	}

	public void setFdPermissionCode(String fdPermissionCode)
	{
		this.fdPermissionCode = fdPermissionCode;
	}

	public String getFdPermissionName()
	{
		return fdPermissionName;
	}

	public void setFdPermissionName(String fdPermissionName)
	{
		this.fdPermissionName = fdPermissionName;
	}

	public String getFdPermissionDesc()
	{
		return fdPermissionDesc;
	}

	public void setFdPermissionDesc(String fdPermissionDesc)
	{
		this.fdPermissionDesc = fdPermissionDesc;
	}

	public Integer getFdStatus()
	{
		return fdStatus;
	}

	public void setFdStatus(Integer fdStatus)
	{
		this.fdStatus = fdStatus;
	}

	public long getFdCreateTime()
	{
		return fdCreateTime;
	}

	public void setFdCreateTime(long fdCreateTime)
	{
		this.fdCreateTime = fdCreateTime;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public Permission(){}
	public Permission(String fdPermissionCode, String fdPermissionName, String fdPermissionDesc,
			Integer fdStatus, long fdCreateTime) {
		this.fdPermissionCode = fdPermissionCode;
		this.fdPermissionName = fdPermissionName;
		this.fdPermissionDesc = fdPermissionDesc;
		this.fdStatus = fdStatus;
		this.fdCreateTime = fdCreateTime;
	}

}
