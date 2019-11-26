package com.sunnet.org.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunnet.framework.controller.BaseController;
import com.sunnet.org.system.model.Menu;
import com.sunnet.org.system.model.Permission;
import com.sunnet.org.system.model.Role;
import com.sunnet.org.system.model.User;
import com.sunnet.org.system.service.IMenuService;
import com.sunnet.org.system.service.IPermissionService;
import com.sunnet.org.system.service.IRoleService;
import com.sunnet.org.system.service.IUserService;

@Controller
@RequestMapping(value = "/system")
public class SystemController extends BaseController {

	@Autowired
	private IMenuService menuService;

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserService userService;

	/**
	 * 系统初始化
	 * 
	 * @param captchaId
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/initialize")
	public String initialize(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (userService.findAll().size() > 0) {
			return "view/login";
		}
		/* 权限 */
		Permission userpermission = new Permission("sys_user", "用户权限", "用户权限描述", 1, new Date().getTime());
		Permission rolepermission = new Permission("sys_role", "角色权限", "角色权限描述", 1, new Date().getTime());
		Permission permission = new Permission("sys_permission", "权限管理", "权限管理描述", 1, new Date().getTime());
		Permission systempermission = new Permission("sys_systemItem", "系统管理", "系统管理描述", 1, new Date().getTime());
		Permission menupermission = new Permission("sys_menu", "菜单管理", "菜单管理描述", 1, new Date().getTime());
		permissionService.save(userpermission);
		permissionService.save(rolepermission);
		permissionService.save(permission);
		permissionService.save(systempermission);
		permissionService.save(menupermission);

		/* 角色 */
		Set<Permission> per = new HashSet<Permission>();
		per.addAll(permissionService.findAll());
		Role role = new Role("admin", "admin", "管理员角色", 1, new Date().getTime(), per);
		roleService.save(role);

		Set<Role> re = new HashSet<Role>();
		re.addAll(roleService.findAll());
		User user = new User();
		user.setFdName("admin");
		user.setFdCreateTime(new Date().getTime());
		user.setFdStatus(1);
		user.setFdUserName("admin");
		user.setFdAge(1);
		user.setFdPassword("e10adc3949ba59abbe56e057f20f883e");
		user.setRoles(re);
		userService.save(user);
		Menu menu = new Menu("&#xe61d;", "系统设置", null, null, 99, null);
		menuService.save(menu);
		List<Menu> item = menuService.findAll();
		menuService.save(new Menu(null, "用户管理", "admin/user/index", "sys_user", 1, item.get(0)));
		menuService.save(new Menu(null, "角色管理", "admin/role/index", "sys_role", 2, item.get(0)));
		menuService.save(new Menu(null, "权限管理", "admin/permission/index", "sys_permission", 3,item.get(0)));
		menuService.save(new Menu(null, "后台设置管理", "admin/systemItem/index", "sys_systemItem", 4,item.get(0)));
		menuService.save(new Menu(null, "菜单管理", "admin/menu/index", "sys_menu", 5,item.get(0)));
		return "view/login";
	}

	
	/**
	 * 前端url配合
	 * @param url
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/url")
	public String url(String url, HttpServletResponse response) throws IOException {
		return url;
	}

}
