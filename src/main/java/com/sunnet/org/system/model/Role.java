package com.sunnet.org.system.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;

@Entity
@Table(name = "t_role")
public class Role extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fdId;

	private String fdRoleCode;//角色编号

	private String fdRoleName;//角色名称

	private String fdRoleDesc;//角色描述

	private Integer fdStatus;//状态

	private long fdCreateTime;  // 创建时间
	
	public Role(){}

	@ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<User>();

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "t_role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<Permission> permissions = new HashSet<Permission>();

	public String getFdId()
	{
		return fdId;
	}

	public void setFdId(String fdId)
	{
		this.fdId = fdId;
	}

	public String getFdRoleCode()
	{
		return fdRoleCode;
	}

	public void setFdRoleCode(String fdRoleCode)
	{
		this.fdRoleCode = fdRoleCode;
	}

	public String getFdRoleName()
	{
		return fdRoleName;
	}

	public void setFdRoleName(String fdRoleName)
	{
		this.fdRoleName = fdRoleName;
	}

	public String getFdRoleDesc()
	{
		return fdRoleDesc;
	}

	public void setFdRoleDesc(String fdRoleDesc)
	{
		this.fdRoleDesc = fdRoleDesc;
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

	public Set<User> getUsers()
	{
		return users;
	}

	public void setUsers(Set<User> users)
	{
		this.users = users;
	}

	public Set<Permission> getPermissions()
	{
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions)
	{
		this.permissions = permissions;
	}

	@Override
	public String toString()
	{
		return "Role [fdId=" + fdId + ", fdRoleCode=" + fdRoleCode + ", fdRoleName=" + fdRoleName + ", fdRoleDesc=" + fdRoleDesc + ", fdStatus=" + fdStatus + ", fdCreateTime=" + fdCreateTime + ", users=" + users + ", permissions=" + permissions + "]";
	}

	public Role(String fdRoleCode, String fdRoleName, String fdRoleDesc, Integer fdStatus,
			long fdCreateTime, Set<Permission> permissions) {
		this.fdRoleCode = fdRoleCode;
		this.fdRoleName = fdRoleName;
		this.fdRoleDesc = fdRoleDesc;
		this.fdStatus = fdStatus;
		this.fdCreateTime = fdCreateTime;
		this.permissions = permissions;
	}

}
