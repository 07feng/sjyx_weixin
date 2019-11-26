package com.sunnet.code.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sunnet.code.common.CodeManyUtil;
import com.sunnet.framework.util.RandomStringGenerator;

/**
 * 底层 sql 新增
 * 
 * @author 强强
 *
 *         时间: 2017年2月27日
 */
public class SQLSunnet {

	private static Logger log = Logger.getLogger(SQLSunnet.class);

	public static CreateBean createBean = new CreateBean(TestUtil.URL, TestUtil.USERNAME, TestUtil.PASSWORD);

	/**
	 * 增加权限 【一个】
	 * 
	 * @param code
	 *            权限
	 * @param desc
	 *            描述
	 * @return
	 * @throws SQLException
	 */
	public static void getPermission(String code, String desc) throws SQLException {
			String	SQLColumns = "select fd_id from t_permission where fd_permission_code ='"+code+"'";
			Connection con = SQLSunnet.createBean.getConnection();
			PreparedStatement ps = con.prepareStatement(SQLColumns);
			ResultSet rs = ps.executeQuery();
			boolean flg=true;
			while (rs.next()) {
				flg=false;
				break;
			}	
			if(flg){
					String fdId = (new Date()).getTime() + RandomStringGenerator.getRandomStringByLength(6);// 24
					SQLColumns = " insert into t_permission(fd_id,fd_create_time,fd_permission_code,fd_permission_desc,fd_permission_name,fd_status)"
							+ " values('" + fdId + "'," + new Date().getTime() + ",'" + code + "','" + desc + "描述','" + desc
							+ "',1);";
					con = createBean.getConnection();
					ps = con.prepareStatement(SQLColumns);
					int i = ps.executeUpdate();
					if (i > 0) {
						log.info("新增成功");
					}
			}
	}
	
	/**
	 * 增加权限 【三个】
	 * 
	 * @param code
	 *            权限
	 * @param desc
	 *            描述
	 * @return
	 * @throws SQLException
	 */
	public static void getPermissionAll(String code, String desc) throws SQLException {
		getPermission(code+"_select",desc+"查询权限");
		getPermission(code+"_update",desc+"更新权限");
		getPermission(code+"_add",desc+"新增权限");
		getPermission(code+"_del",desc+"删除权限");
	}

	public static void main(String[] args) throws SQLException {
		SQLSunnet.getPermissionAll("sys_out", "最新");
	}
	
	
	/**
	 * 新增【修改】【删除】
	 * 
	 * @param code
	 *            权限
	 * @param desc
	 *            描述
	 * @return
	 * @throws SQLException
	 */
	public static void getAdmin(String SQLColumns) throws SQLException {
		if(CodeManyUtil.is_){
			Connection con = createBean.getConnection();
			PreparedStatement ps = con.prepareStatement(SQLColumns);
			int i = ps.executeUpdate();
			if (i > 0) {
				log.info("新增成功");
			}
		}
	}

}
