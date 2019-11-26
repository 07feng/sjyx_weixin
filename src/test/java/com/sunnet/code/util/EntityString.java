package com.sunnet.code.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.velocity.VelocityContext;

import com.sunnet.code.common.CodeManyUtil;
import com.sunnet.code.model.ClassifyModule;

/**
 * 反向自动生成对应关系
 * 
 * @author 强强
 *
 */
public class EntityString {

	private static boolean falg = true;

	private static boolean falgMap = false;

	public static void getCole(String minName, String desc, String type)
			throws SQLException {
		String com = "com/sunnet/org/";
		/* 链接数据库 */
		String srcPath = TestUtil.getProjectPath() + "src" + "/"; // src路径
		String pckPath = srcPath + "main/java/" + com; // 生成代码块路径
		String bigName = TestUtil.getToClassName(minName);

		String modelPath = type + "/model/" + bigName + ".java";
		VelocityContext context = new VelocityContext();
		context.put("minName", minName);// 小写类名
		context.put("bigName", bigName);// 大写类名
		context.put("keyType", type);
		context.put("title", desc);// 内容 title表的说明
		context.put("com", TestUtil.getDelStr(com));// 包
		context.put("framework", TestUtil.getDelStr("com/sunnet/framework/"));// framework包
		context.put("entity", getTableStringColumn(minName));//
		context.put("entitySetGet", getStringColumnSetGet(minName));//
		context.put("value", getStringTxtSetGet(minName));//
		context.put("mapvalue", getStringSetGet(minName));//

		/*
		 * --------------------------------------Class--------------------------
		 * ---------
		 */
		if (falg) {
			CommonPageParser.WriterPage(context, "Entity.ftl", pckPath,
					modelPath);
		}
		if (falgMap) {
			CommonPageParser.WriterPage(context, "MapTxt.ftl", pckPath,
					"/explain/" + bigName + ".txt");
		}
	}

	/**
	 * 获取map的
	 * @throws SQLException 
	 */
	private static String getStringSetGet(String minName) throws SQLException {
		StringBuffer getset = new StringBuffer();
		List<ColumnData> list = getColumnDatas(minName);
		getset.append("\t public void getMap() { \r");
		for (ColumnData columnData : list) {
			/* key-》value */
						String columnComment = columnData.getColumnComment();
			String name = columnData.getColumnName();
			getset.append("\t 	ModelUtil.total.put(\""+name.toLowerCase()+"\", \""+columnComment+"\"); \r");
		}
		getset.append("\t } \r");
		return getset.toString();
		
	}

	public static void main(String[] args) throws SQLException {
		getCole("Tb_shuffling", "轮播图表", "information");
	}

	private static CreateBean jdbc = new CreateBean();

	// 1.先查询所有表
	// 2.对所有表进行处理
	// 》a.循环所有表
	// 》b.查询所有表的外键关系
	// 》c.对这个表进行拼接字符串
	// 3.生成实体类
	// 额外操作：删除原先数据库

	public static List<Map<String, Object>> getTable() {
		List<Map<String, Object>> columnList = new ArrayList<Map<String, Object>>();
		try {
			String SQLColumns = "SELECT name FROM SysObjects Where XType='U' ORDER BY Name ";
			Connection con;
			con = jdbc.getConnection();
			PreparedStatement ps = con.prepareStatement(SQLColumns);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("TABLE_NAME", rs.getString(1).toLowerCase());
				columnList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnList;
	}

	public static List<ColumnData> getColumnDatas(String tableName)
			throws SQLException {
		// 查询某数据库的某表
		StringBuffer sql = new StringBuffer();

		sql.append("	SELECT ");
		sql.append("	 a.name,");
		sql.append("	  b.name,");
		sql.append("	 cast(g.[value] as varchar(500))  ");
		sql.append("	FROM ");
		sql.append("	  syscolumns a  ");
		sql.append("	  LEFT JOIN systypes b  ");
		sql.append("    ON a.xusertype = b.xusertype ");
		sql.append("	  INNER JOIN sysobjects d  ");
		sql.append("    ON a.id = d.id  ");
		sql.append("	    AND d.xtype = 'u'  ");
		sql.append("	    AND d.name <> 'dtproperties' ");
		sql.append("	  LEFT JOIN syscomments e ");
		sql.append("	    ON a.cdefault = e.id ");
		sql.append("	  LEFT JOIN sys.extended_properties g ");
		sql.append("	    ON a.id = g.major_id ");
		sql.append("	    AND a.colid = g.minor_id ");
		sql.append("	  LEFT JOIN sys.extended_properties f ");
		sql.append("	    ON d.id = f.major_id ");
		sql.append("	    AND f.minor_id = 0 ");
		sql.append("	WHERE d.name = '"+tableName+"' ");

		String SQLColumns = "SELECT COLUMN_NAME,DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS where table_name = '"
				+ tableName + "'";
		SQLColumns = sql.toString();
		// 获取数据库连接
		Connection con = jdbc.getConnection();
		// 创建对象 对sql语句进行查询
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		// 查询之后的结果
		ResultSet rs = ps.executeQuery();

		// N个表字段对象
		List<ColumnData> columnList = new ArrayList<ColumnData>();

		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		// 简单的jdbc连接
		while (rs.next()) {
			String name = rs.getString(1);
			String type = rs.getString(2);
			type = jdbc.getType(type);

			// 一个表字段的对象 包含 名称， 类型，是否为空，等待
			ColumnData cd = new ColumnData();
			cd.setColumnComment(rs.getString(3));
			cd.setColumnName(name);
			cd.setColumnType(type);
			columnList.add(cd);
		}
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}

	// 对应的String
	public static String getTableStringColumn(String minName)
			throws SQLException {
		StringBuffer getset = new StringBuffer();
		List<ColumnData> list = getColumnDatas(minName);
		for (ColumnData columnData : list) {
			String columnComment = columnData.getColumnComment();
			String name = columnData.getColumnName();
			if (name.equals("id") || name.equals("Id")) {
				continue;
			}
			String type = columnData.getColumnType();
			getset.append("\t private " + type + " " + name.toLowerCase()
					+ "; //" + columnComment + "   \r\r");
		}
		return getset.toString();
	}

	// 对应的String
	public static String getStringColumnSetGet(String minName)
			throws SQLException {
		StringBuffer getset = new StringBuffer();
		List<ColumnData> list = getColumnDatas(minName);
		for (ColumnData columnData : list) {
			String name = columnData.getColumnName();
			String BigCloumnName = TestUtil.getToClassName(name);
			String minCloumnName = TestUtil.getStr(name);
			String type = columnData.getColumnType();
			if (name.equals("id") || name.equals("Id")) {
				continue;
			}

			getset.append("\t @Column(name = \"" + name + "\")  \r");
			getset.append("\t public " + type + " get"
					+ TestUtil.getToClassName(name.toLowerCase()) + "() \r");
			getset.append("\t {                       \r");
			getset.append("\t return " + name.toLowerCase() + ";        	   \r");
			getset.append("\t }                       \r\r");
			getset.append("\t public void set"
					+ TestUtil.getToClassName(name.toLowerCase()) + "(" + type
					+ " " + name.toLowerCase() + ") \r");
			getset.append("\t {                       \r");
			getset.append("\t this." + name.toLowerCase() + " = "
					+ name.toLowerCase() + ";       \r\r");
			getset.append("\t }				 \r\r");
		}
		return getset.toString();
	}

	// 对应的String
	public static String getStringTxtSetGet(String minName) throws SQLException {
		StringBuffer getset = new StringBuffer();
		List<ColumnData> list = getColumnDatas(minName);
		for (ColumnData columnData : list) {
			getset.append("ModelUtil.total.put(\"" + columnData.getColumnName().toLowerCase()
					+ "\", \"" + columnData.getColumnComment() + "\"); \r");
		}
		return getset.toString();
	}

}
