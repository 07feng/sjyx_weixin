package com.sunnet.org.member.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/**
 * 连接
 * 
 * @author rq1
 *
 */
public class CreateBean {

	private Connection connection = null;
	public static String url;
	public static String username;
	public static String password;

	public CreateBean() {
	}

	public CreateBean(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public static void main(String[] args) {
		try {
			String url = "jdbc:sqlserver://rm-wz9sy836ybt016j4mo.sqlserver.rds.aliyuncs.com:3433;databaseName=ImageSystem";
			String username = "sjyx_sz880";
			String password = "SJYX@qq880";
			CreateBean createBean = new CreateBean(url, username, password);
			List list = createBean.getColumn("t_user");
			System.out.println(list.toString());
			list = createBean.getColumnDatas("select * from t_user");
			System.out.println(list.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取链接
	 * @param request
	 */
	public void getServlet(HttpServletRequest request){
		Properties prop = new Properties();
		try {
			// 读取属性文件a.properties
			String requesturl = request.getSession().getServletContext().getRealPath("");
			String path = requesturl + "WEB-INF/classes/jdbc.properties";
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			prop.load(in); /// 加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			Map<String,String> map =new HashMap<String,String>();
			while (it.hasNext()) {
				String key = it.next();
				map.put(key, prop.getProperty(key));
			}
			in.close();
			this.url = map.get("jdbc.jdbcUrl");
			this.username = map.get("jdbc.user");
			this.password = map.get("jdbc.password");
		} catch (Exception e) {
			System.out.println("没有找到某配置文件->jdbc.properties");
		}
	}

	/**
	 * 查询字段
	 *            表名
	 *            数据库
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getColumnDatas(String SQLColumns) throws SQLException {
		
		// 获取数据库连接
		Connection con = getConnection();
		// 创建对象 对sql语句进行查询
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		// 查询之后的结果
		ResultSet rs = ps.executeQuery();
		// N个表字段对象
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//数据列
		ResultSetMetaData rdata = rs.getMetaData();
		//简单jdbc链接
		while (rs.next()) {
			Map<String, Object> obj = new HashMap<String, Object>();
			for (int i = 1; i <= rdata.getColumnCount(); i++) {
				obj.put(rdata.getColumnName(i).toLowerCase(), rs.getObject(i));
			}
			list.add(obj);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}
	
	
	/**
	 * 列
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<String> getColumn(String tableName) throws SQLException {
		// 查询某数据库的某表
		String SQLColumns = "SELECT COLUMN_NAME,DATA_TYPE FROM INFORMATION_SCHEMA.columns WHERE TABLE_NAME='"
				+ tableName + "'";
		// 获取数据库连接
		Connection con = getConnection();
		// 创建对象 对sql语句进行查询
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		// 查询之后的结果
		ResultSet rs = ps.executeQuery();
		// N个表字段对象
		List<String> list = new ArrayList<String>();
		// 简单的jdbc连接
		while (rs.next()) {
			list.add(rs.getString(1).toLowerCase());
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}

}
