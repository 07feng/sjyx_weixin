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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.information.model.Tre_menberdoccomment;

@Entity
@Table(name = "t_user")
public class User extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String fdId;

	@Column(name = "fd_user_name")
	private String fdUserName;//登录账号

	@Column(name = "fd_password")
	private String fdPassword;//登录密码
	
	private String fdPhone; //手机号

	@Column(name = "fd_name")
	private String fdName;//显示的名称

	@Column(name = "fd_age")
	private Integer fdAge;

	private Integer fdStatus;

	private long fdCreateTime;
	
	@Column(name = "fd_isdelete")
	private Integer isdelete;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	 
	public String getFdId()
	{
		return fdId;
	}

	public void setFdId(String fdId)
	{
		this.fdId = fdId;
	}

	public String getFdName()
	{
		return fdName;
	}

	public void setFdName(String fdName)
	{
		this.fdName = fdName;
	}

	public Integer getFdAge()
	{
		return fdAge;
	}

	public void setFdAge(Integer fdAge)
	{
		this.fdAge = fdAge;
	}

	public String getFdUserName()
	{
		return fdUserName;
	}

	public void setFdUserName(String fdUserName)
	{
		this.fdUserName = fdUserName;
	}

	public String getFdPassword()
	{
		return fdPassword;
	}

	public void setFdPassword(String fdPassword)
	{
		this.fdPassword = fdPassword;
	}

	public Integer getFdStatus()
	{
		return fdStatus;
	}

	public void setFdStatus(Integer fdStatus)
	{
		this.fdStatus = fdStatus;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public long getFdCreateTime()
	{
		return fdCreateTime;
	}

	public void setFdCreateTime(long fdCreateTime)
	{
		this.fdCreateTime = fdCreateTime;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	@Override
	public String toString()
	{
		return "User [fdId=" + fdId + ", fdUserName=" + fdUserName + ", fdPassword=" + fdPassword + ", fdName=" + fdName + ", fdAge=" + fdAge + ", fdStatus=" + fdStatus + "]";
	}

	public String getFdPhone() {
		return fdPhone;
	}

	public void setFdPhone(String fdPhone) {
		this.fdPhone = fdPhone;
	}
	
}
