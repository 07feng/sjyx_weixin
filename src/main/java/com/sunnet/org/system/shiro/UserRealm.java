package com.sunnet.org.system.shiro;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.sunnet.org.system.model.Permission;
import com.sunnet.org.system.model.Role;
import com.sunnet.org.system.model.User;
import com.sunnet.org.system.service.IUserService;


public class UserRealm extends AuthorizingRealm
{
	@Autowired
	private IUserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
	{
		System.err.println("开始执行登录验证......");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

		/** 查出是否有此用户 **/
		try
		{
			User userInfo = userService.findUserByLoginName(usernamePasswordToken.getUsername());
			String password = new String((char[]) token.getCredentials());
			if (null == userInfo.getFdId())
			{
				throw new UnknownAccountException("用户帐号不存在！");
			}
			if (!password.equals(userInfo.getFdPassword()))
			{
				throw new IncorrectCredentialsException("用户密码错误");
			}
		}
		catch (Exception e)
		{
			throw new UnknownAccountException("用户验证失败！");
		}

		return new SimpleAuthenticationInfo(usernamePasswordToken.getUsername(), usernamePasswordToken.getPassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		System.err.println("开始执行权限验证......");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		/** 获取登录时输入的用户名 **/
		String username = (String) principals.fromRealm(getName()).iterator().next();

		/** 到数据库查是否有此对象 **/
		User user = userService.findUserByLoginName(username);
		if (null != user)
		{
			/** 超级管理员 始终拥有所有权限 **/
			if (user.getFdUserName().equals("admin"))
			{
				info.addStringPermission("*");
				info.addRole("*");
				info.addRole("R_Admin");
			}
			
			/** 查找当前用户对应的角色 **/
			HashSet<String> hs = new LinkedHashSet<String>();
			Set<Role> roleset = userService.loginRole(username);
			for (Role role : roleset)
			{
				hs.add(role.getFdRoleCode());
				/***当前用户对应角色所对应的权限集合 ***/
				Set<Permission> permissions=role.getPermissions();
				for (Permission permission : permissions)
				{
					info.addStringPermission(permission.getFdPermissionCode());
				}
			}
			
			info.setRoles(hs);
		}
		
		return info;
	}

	
	/**
	 * 清空所有缓存
	 */
	@Override
	public void clearCache(PrincipalCollection principals)
	{
		super.clearCache(principals);
	}
}
