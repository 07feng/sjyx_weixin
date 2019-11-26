package com.sunnet.code.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.velocity.VelocityContext;

import com.sunnet.code.common.CodeManyUtil;
import com.sunnet.code.model.ClassifyModule;
import com.sunnet.code.util.CommonPageParser;
import com.sunnet.code.util.SQLSunnet;
import com.sunnet.code.util.StringBuModel;
import com.sunnet.code.util.TestUtil;
import com.sunnet.framework.util.RandomStringGenerator;

/**
 * 代码生成集合块
 * @author Administrator
 *
 */
public class CodeManyGenerateFactory{
	
	//项目路径
	private static String projectPath = TestUtil.getProjectPath();
	
	private static String framework="com/sunnet/framework/";
	
	/**
	 * 需要class类 -》 表名 
	 * title -》说明
	 * className -》 类民
	 * type -》 分类包名
	 */ 
	public static void codeGenerate(String className, String type,String title,String auto) throws SQLException {
		String com = "com/sunnet/org/";
		/* 链接数据库  */
		String bigName = className;
		String srcPath = projectPath + "src" + "/"; //src路径
		String pckPath = srcPath +"main/java/"+ com; //生成代码块路径
		
		
		String controllerPath = type  + "/controller/" + className + "Controller.java";
		String servicePath = type  + "/service/I" + className + "Service.java";
		String serviceImplPath = type  + "/service/impl/" + className + "ServiceImpl.java";
		String daoPath = type  + "/dao/I" + className + "Dao.java";
		String daoImplPath = type  + "/dao/impl/" + className + "DaoImpl.java";
		String voPath = type  + "/vo/" + className + "Util.java";
		String modelPath = type  + "/model/" + className + ".java";
		
		//小写类名
		String minName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length()); //小写开始
		
		VelocityContext context = new VelocityContext();
		context.put("className", className);//大写类名
		context.put("minName", minName);//小写类名
		context.put("bigName", bigName);//大写类名
		context.put("keyType", type);
		context.put("title", title);//内容
		context.put("type", type);//包名
		context.put("auto", auto);//作者
		context.put("com", TestUtil.getDelStr(com));//包
		context.put("framework", TestUtil.getDelStr(framework));//framework包
		
		/* 路径 */
		String webPath = srcPath +"main/webapp/view/"; //生成代码块路径
		String a_list = type  + "/" + minName + "_list.jsp";
		String a_add = type  + "/" + minName + "_add.jsp";
		String a_select = type  + "/" + minName + "_select.jsp";
		
		new ClassifyModule(className,type);//新增的类名选择
		
		//add页面
		context.put("jstitle", StringBuModel.jsAdd(minName)); //ADD页面
		context.put("voutilSelecyt", StringBuModel.getVoutilSelecyt(minName)); //外键
		context.put("isImg", StringBuModel.getImgUrl(minName)); //ADD页面
		context.put("imgJS", StringBuModel.getImg(minName)); //ADD页面
		context.put("ContentJS", StringBuModel.ContentJS()); //ADD页面
		context.put("ecommendJs", StringBuModel.ecommendJs(minName)); //ADD页面
		context.put("ecommendADD", StringBuModel.ecommendADD(minName)); //ADD页面
		
		//list页面
		context.put("jsname", StringBuModel.jsListTh());//list页面的th
		context.put("jsvlaue", StringBuModel.jsListTd());//list页面的td
		context.put("jssaititle", StringBuModel.jsScren());//list页面的赛选
		context.put("jslook", StringBuModel.jsListLook());//list页面的预览
		
		//select页面
		context.put("select", StringBuModel.jsSelect(minName));//select页面
		
		//vo 包
		context.put("toMap", StringBuModel.getVoUtil()); //vo 反馈接口
		
		//service 包
		context.put("toService", StringBuModel.getServceImpl()); //service 后台赛选条件
		context.put("entityVlue", StringBuModel.getStringCode()); //service 后台赛选条件
		context.put("delVlue", StringBuModel.getStringDel(minName)); //service 后台赛选条件

		//controller 包
		context.put("ConllerImg", StringBuModel.ConllerImg(minName)); //controller
		context.put("ConllerImgD", StringBuModel.ConllerImgD(minName)); //controller
	
		//权限
		if(CodeManyUtil.permission){
			String code="sys_"+TestUtil.getStr(className);
			String select=code;
			String update=code;
			String add=code;
			String del=code;
			if(CodeManyUtil.is_permission){ //true 一个
				SQLSunnet.getPermission(code, title+"管理");
			}else{
				select=code+"_select";
				update=code+"_update";
				add=code+"_add";
				del=code+"_del";
				SQLSunnet.getPermissionAll(code, title+"管理");
			}
			context.put("permissions_select", StringBuModel.permissionAdd(select)); //
			context.put("permissions_update", StringBuModel.permissionAdd(update)); //
			context.put("permissions_add", StringBuModel.permissionAdd(add)); //
			context.put("permissions_del", StringBuModel.permissionAdd(del)); //
			
			context.put("permissions_select_list",select); //
			context.put("permissions_update_list", update); //
			context.put("permissions_add_list",add); //
			context.put("permissions_del_list", del); //
		}
		
		
		/* --------------------------------------Class----------------------------------- */
		
		if(CodeManyUtil.controller){
			if(CodeManyUtil.permission){
				CommonPageParser.WriterPage(context, "ControllerPermission.ftl", pckPath, controllerPath);
			}else{
				CommonPageParser.WriterPage(context, "Controller.ftl", pckPath, controllerPath);
			}
		}
		if(CodeManyUtil.service){
			CommonPageParser.WriterPage(context, "Service.ftl", pckPath, servicePath);
		}
		if(CodeManyUtil.serviceImpl){
			CommonPageParser.WriterPage(context, "ServiceImpl.ftl", pckPath, serviceImplPath);
		}
		if(CodeManyUtil.dao){
			CommonPageParser.WriterPage(context, "Dao.ftl", pckPath, daoPath);
		}
		if(CodeManyUtil.daoImpl){
			CommonPageParser.WriterPage(context, "DaoImpl.ftl", pckPath, daoImplPath);
		}
		if(CodeManyUtil.txtContent){
			CommonPageParser.WriterTxt(context, "TxtDao.ftl", title);
		}
		if(CodeManyUtil.vo){
			CommonPageParser.WriterPage(context, "MapUtil.ftl", pckPath, voPath);
		}
		if(CodeManyUtil.entity){
			CommonPageParser.WriterPage(context, "Entity.ftl", pckPath, modelPath);
		}
		
		/* --------------------------------------Js----------------------------------- */
		
		if(CodeManyUtil._list){
			if(CodeManyUtil.is_permission){
				CommonPageParser.WriterPage(context, "JS_list.ftl", webPath, a_list);
			}else{
				CommonPageParser.WriterPage(context, "JS_list2.ftl", webPath, a_list);
			}
		}
		if(CodeManyUtil._add){
			CommonPageParser.WriterPage(context, "JS_add.ftl", webPath, a_add);
		}
		if(CodeManyUtil._select){
			CommonPageParser.WriterPage(context, "JS_select.ftl", webPath, a_select);
		}
		
	}
	
	/**
	 * 初始化
	 * @throws SQLException 
	 */
	public static void initAdmin() throws SQLException{
		
		//3.初始化权限
		String fdId = (new Date()).getTime() + RandomStringGenerator.getRandomStringByLength(6);// 24
		String SQLColumns = " insert into t_permission(fd_id,fd_create_time,fd_permission_code,fd_permission_desc,fd_permission_name,fd_status)"
		+ " values('" + fdId + "'," + new Date().getTime() + ",'sys_user','初始化权限','初始化权限',1)";
		SQLSunnet.getAdmin(SQLColumns);
		
		fdId = (new Date()).getTime() + RandomStringGenerator.getRandomStringByLength(6);// 24
		SQLColumns = " insert into t_permission(fd_id,fd_create_time,fd_permission_code,fd_permission_desc,fd_permission_name,fd_status)"
		+ " values('" + fdId + "'," + new Date().getTime() + ",'sys_role','初始化权限','初始化权限',1)";
		SQLSunnet.getAdmin(SQLColumns);
		
		fdId = (new Date()).getTime() + RandomStringGenerator.getRandomStringByLength(6);// 24
		SQLColumns = " insert into t_permission(fd_id,fd_create_time,fd_permission_code,fd_permission_desc,fd_permission_name,fd_status)"
		+ " values('" + fdId + "'," + new Date().getTime() + ",'sys_sys_systemItem','初始化权限','初始化权限',1)";
		SQLSunnet.getAdmin(SQLColumns);
		
		fdId = (new Date()).getTime() + RandomStringGenerator.getRandomStringByLength(6);// 24
		SQLColumns = " insert into t_permission(fd_id,fd_create_time,fd_permission_code,fd_permission_desc,fd_permission_name,fd_status)"
		+ " values('" + fdId + "'," + new Date().getTime() + ",'sys_permission','初始化权限','初始化权限',1)";
		SQLSunnet.getAdmin(SQLColumns);
		
		//权限已经有了
		fdId = (new Date()).getTime() + RandomStringGenerator.getRandomStringByLength(6);// 24
		//1.新增一个角色
		SQLColumns = " insert into t_role(fd_id,fd_create_time,fd_role_code,fd_role_desc,fd_role_name,fd_status)"
				+ " values('" + fdId + "'," + new Date().getTime() + ",'001','admin','admin',1)";
		SQLSunnet.getAdmin(SQLColumns);
		
		String role_fdid="";//角色fdId
		SQLColumns = "select fd_id from t_role where fd_role_name='admin'";
		Connection con = SQLSunnet.createBean.getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			role_fdid = rs.getString(1);
			System.err.println("角色主键ID:"+role_fdid);
			break;
		}		
		
		//2.给角色附有权限
		SQLColumns = "select fd_id from t_permission";
		ps = con.prepareStatement(SQLColumns);
		rs = ps.executeQuery();
		while (rs.next()) {
			String name = rs.getString(1);
			SQLColumns = " insert into t_role_permission(role_id,permission_id)"
			+ " values('"+role_fdid+"','"+name+"')";
			SQLSunnet.getAdmin(SQLColumns);
		}		
		
		//3.新增一个用户
		fdId = (new Date()).getTime() + RandomStringGenerator.getRandomStringByLength(6);// 24
		SQLColumns = " insert into t_user(fd_id,fd_user_name,fd_password,fd_name,fd_age,fd_status,fd_create_time)"
		+ " values('" + fdId + "','admin','e10adc3949ba59abbe56e057f20f883e','强强',18,1," + new Date().getTime() + ")";
		SQLSunnet.getAdmin(SQLColumns);
		
		//4.给用户附有角色
		String user_fdId="";
		SQLColumns = "select fd_id from t_user where fd_user_name='admin'";
		ps = con.prepareStatement(SQLColumns);
		rs = ps.executeQuery();
		while (rs.next()) {
			user_fdId = rs.getString(1);
			break;
		}	
		
		SQLColumns = "select fd_id from t_role";
		ps = con.prepareStatement(SQLColumns);
		rs = ps.executeQuery();
		while (rs.next()) {
			String name = rs.getString(1);
			SQLColumns = " insert into t_user_role(user_id,role_id)"
			+ " values('"+user_fdId+"','"+name+"')";
			SQLSunnet.getAdmin(SQLColumns);
		}
		
		System.err.println("初始化结束");
	}
	
}