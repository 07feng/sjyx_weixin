package com.sunnet.code.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CreateBean {

	private static Logger log = Logger.getLogger(CreateBean.class);

	private Connection connection = null;
	static String url;
	static String username;
	static String password;
	static String rt = "\r\t";
	String SQLTables = "show tables";
	private String method;
	private String argv;
	static String selectStr;
	static String from;

	public CreateBean() {
		getSystem();
		this.url = TestUtil.URL;
		this.username = TestUtil.USERNAME;
		this.password = TestUtil.PASSWORD;
	}

	public CreateBean(String url, String username, String password) {
		getSystem();
		this.url = TestUtil.URL;
		this.username = TestUtil.USERNAME;
		this.password = TestUtil.PASSWORD;
	}

	static {
		try {
			/*Class.forName("com.mysql.jdbc.Driver");*/
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		selectStr = "select ";
		from = " from ";
	}

	public void setMysqlInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public void setConnection(Connection connection) {

		this.connection = connection;
	}

	public Connection getConnection() throws SQLException {

		return DriverManager.getConnection(url, username, password);
	}

	public List<String> getTables() throws SQLException {

		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(this.SQLTables);
		ResultSet rs = ps.executeQuery();
		List list = new ArrayList();
		while (rs.next()) {
			String tableName = rs.getString(1);
			list.add(tableName);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}

	/**
	 * 查询字段
	 * 
	 * @param tableName
	 *            表名
	 * @param database
	 *            数据库
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnData> getColumnDatas(String tableName) throws SQLException {
		log.info("开始查询数据库");

		// 查询某数据库的某表
		String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '"
				+ tableName + "' " + "and table_schema =  '" + TestUtil.BASEDATA + "'";
		// 获取数据库连接
		Connection con = getConnection();
		// 创建对象 对sql语句进行查询
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		// 查询之后的结果
		ResultSet rs = ps.executeQuery();

		// N个表字段对象
		List columnList = new ArrayList();

		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		// 简单的jdbc连接
		while (rs.next()) {
			String name = rs.getString(1).toLowerCase();
			String type = rs.getString(2);
			String comment = rs.getString(3);
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = (rs.getString(6) == null) ? "" : rs.getString(6);
			String nullable = TableConvert.getNullAble(rs.getString(7));
			type = getType(type, precision, scale);

			// 一个表字段的对象 包含 名称， 类型，是否为空，等待
			ColumnData cd = new ColumnData();
			cd.setColumnName(name);
			cd.setDataType(type);
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			formatFieldClassType(cd);
			columnList.add(cd);
		}
		this.argv = str.toString();
		this.method = getset.toString();
		rs.close();
		ps.close();
		con.close();
		log.info("查询数据库完成");
		return columnList;
	}

	/**
	 * 所以表
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public List<String> getColumnBase(String base) throws SQLException {

		String SQLColumns = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + base
				+ "' AND TABLE_NAME NOT LIKE 'sys%'";
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List columnList = new ArrayList();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String name = rs.getString(1).toLowerCase();
			columnList.add(name);
		}
		return columnList;
	}

	public String getBeanFeilds(String tableName) throws SQLException {
		List dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		for (Iterator localIterator = dataList.iterator(); localIterator.hasNext();) {
			ColumnData d = (ColumnData) localIterator.next();
			String name = d.getColumnName();
			if (name.equals("id") || name.equals("modify_date") || name.equals("create_date")) {
				continue;
			}
			String[] typ = d.getDataType().toString().split("java.lang.");
			String type = typ[typ.length - 1];

			String comment = d.getColumnComment();
			String maxChar = name.substring(0, 1).toUpperCase();
			str.append("\r\t").append("private ").append(type + " ").append(name).append(";//   ").append(comment);
			String method = maxChar + name.substring(1, name.length());

			getset.append("\r\r\t").append("@Column(name=\"" + name + "\")\r\t public ").append(type + " ")
					.append("get" + method + "() {\r\t");
			getset.append("    return this.").append(name).append(";\r\t}");
			getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
			getset.append("    this." + name + "=").append(name).append(";\r\t}");
		}
		this.argv = str.toString();
		this.method = getset.toString();
		return this.argv + this.method;
	}

	/**
	 * 重写一个set，get 生成器
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public String getBeanSetGet(String tableName) throws SQLException {
		// 从数据库 根据传过来的表名 去 查询字段 例如：sys_tem
		// 返回过来的是一个List<字段对象>
		List dataList = getColumnDatas(tableName);

		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();

		// for 循环
		for (Iterator localIterator = dataList.iterator(); localIterator.hasNext();) {
			// 循环出来的字段对象 强转
			ColumnData d = (ColumnData) localIterator.next();
			// 字段名称 ↓
			String name = d.getColumnName();
			if (name.equals("fd_id")) {
				continue;
			}
			String cloumnName = name;
			if (name.contains("_")) {
				cloumnName = "";
				String[] sp = name.split("_");
				for (int i = 0; i < sp.length; i++) {
					if (i == 0) {
						cloumnName += sp[i];
					} else {
						cloumnName += TestUtil.getToClassName(sp[i]);
					}
				}
			}
			// 大写
			String BigCloumnName = TestUtil.getToClassName(cloumnName);
			// 类型
			String[] typ = d.getDataType().toString().split("java.lang.");
			String type = typ[typ.length - 1];

			getset.append("\t private " + type + " " + cloumnName + ";    \r\r");
			getset.append("\t public " + type + " get" + BigCloumnName + "() \r");
			getset.append("\t {                       \r");
			getset.append("\t return " + cloumnName + ";        	   \r");
			getset.append("\t }                       \r\r");
			getset.append("\t public void set" + BigCloumnName + "(String " + cloumnName + ") \r");
			getset.append("\t {                       \r");
			getset.append("\t this." + cloumnName + " = " + cloumnName + ";       \r\r");
			getset.append("\t }				 \r\r");
			// getset.append("\r\r\t"+name);
		}
		this.argv = str.toString();
		this.method = getset.toString();
		return this.argv + this.method;
	}

	public void formatFieldClassType(ColumnData columnt) {

		String fieldType = columnt.getColumnType();
		String scale = columnt.getScale();

		if ("N".equals(columnt.getNullable()))
			columnt.setOptionType("required:true");

		if (("datetime".equals(fieldType)) || ("time".equals(fieldType))) {
			columnt.setClassType("easyui-datetimebox");
		} else if ("date".equals(fieldType)) {
			columnt.setClassType("easyui-datebox");
		} else if ("int".equals(fieldType)) {
			columnt.setClassType("easyui-numberbox");
		} else if ("number".equals(fieldType)) {
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
				columnt.setClassType("easyui-numberbox");
				if (StringUtils.isNotBlank(columnt.getOptionType()))
					columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
				else
					columnt.setOptionType("precision:2,groupSeparator:','");
			} else {
				columnt.setClassType("easyui-numberbox");
			}
		} else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {
			columnt.setClassType("easyui-numberbox");
			if (StringUtils.isNotBlank(columnt.getOptionType()))
				columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
			else
				columnt.setOptionType("precision:2,groupSeparator:','");
		} else {
			columnt.setClassType("easyui-validatebox");
		}
	}

	public String getType(String dataType, String precision, String scale) {

		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "java.lang.String";
		else if (dataType.contains("int"))
			dataType = "java.lang.Integer";
		else if (dataType.contains("float"))
			dataType = "java.lang.Float";
		else if (dataType.contains("double"))
			dataType = "java.lang.Double";
		else if (dataType.contains("number"))
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
				dataType = "java.math.BigDecimal";
			else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
				dataType = "java.lang.Long";
			else
				dataType = "java.lang.Integer";

		else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "java.util.Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else
			dataType = "java.lang.String";

		return dataType;
	}

	
	public String getType(String dataType) {

		dataType = dataType.toLowerCase();
		if (dataType.contains("varchar"))
			dataType = "String";
		else if (dataType.contains("int"))
			dataType = "int";
		else if (dataType.contains("datetime"))
			dataType = "String";
		else if (dataType.contains("double"))
			dataType = "double";
		else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "java.util.Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("uniqueidentifier"))
			dataType = "String";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else
			dataType = "String";

		return dataType;
	}
	
	public void getPackage(int type, String createPath, String content, String packageName, String className,
			String extendsClassName, String[] importName) throws Exception {

		if (packageName == null)
			packageName = "";

		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; ++i)
			sb.append("import ").append(importName[i]).append(";\r");

		sb.append("\r");
		sb.append("/**\r *  entity. @author wolf Date:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
				+ "\r */");
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (extendsClassName != null)
			sb.append(" extends ").append(extendsClassName);

		if (type == 1)
			sb.append(" ").append("implements java.io.Serializable {\r");
		else
			sb.append(" {\r");

		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp = temp + className.substring(1, className.length());
		if (type == 1)
			sb.append("private " + className + " " + temp + "; // entity ");

		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		createFile(createPath, "", sb.toString());
	}

	public String getTablesNameToClassName(String tableName) {

		String[] split = tableName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; ++i) {
				String tempTableName = split[i].substring(0, 1).toUpperCase()
						+ split[i].substring(1, split[i].length());
				sb.append(tempTableName);
			}

			return sb.toString();
		}
		String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
		return tempTables;
	}

	public void createFile(String path, String fileName, String str) throws IOException {

		FileWriter writer = new FileWriter(new File(path + fileName));
		writer.write(new String(str.getBytes("utf-8")));
		writer.flush();
		writer.close();
	}

	public Map<String, Object> getAutoCreateSql(String tableName) throws Exception {

		Map sqlMap = new HashMap();
		List columnDatas = getColumnDatas(tableName);
		String columns = getColumnSplit(columnDatas);
		String[] columnList = getColumnList(columns);
		String columnFields = getColumnFields(columns);
		String insert = "insert into " + tableName + "(" + columns.replaceAll("\\|", ",") + ")\n values(#{"
				+ columns.replaceAll("\\|", "},#{") + "})";
		String update = getUpdateSql(tableName, columnList);
		String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
		String selectById = getSelectByIdSql(tableName, columnList);
		String delete = getDeleteSql(tableName, columnList);
		sqlMap.put("columnList", columnList);
		sqlMap.put("columnFields", columnFields);
		sqlMap.put("insert", insert.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("update", update.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("delete", delete);
		sqlMap.put("updateSelective", updateSelective);
		sqlMap.put("selectById", selectById);
		return sqlMap;
	}

	public String getDeleteSql(String tableName, String[] columnsList) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("delete ");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}

	public String getSelectByIdSql(String tableName, String[] columnsList) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("select <include refid=\"Base_Column_List\" /> \n");
		sb.append("\t from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
		return sb.toString();
	}

	public String getColumnFields(String columns) throws SQLException {

		String fields = columns;
		if ((fields != null) && (!("".equals(fields))))
			fields = fields.replaceAll("[|]", ",");

		return fields;
	}

	public String[] getColumnList(String columns) throws SQLException {

		String[] columnList = columns.split("[|]");
		return columnList;
	}

	public String getUpdateSql(String tableName, String[] columnsList) throws SQLException {

		StringBuffer sb = new StringBuffer();

		for (int i = 1; i < columnsList.length; ++i) {
			String column = columnsList[i];
			if ("CREATETIME".equals(column.toUpperCase()))
				continue;

			if ("UPDATETIME".equals(column.toUpperCase()))
				sb.append(column + "=now()");
			else
				sb.append(column + "=#{" + column + "}");

			if (i + 1 < columnsList.length)
				sb.append(",");
		}

		String update = "update " + tableName + " set " + sb.toString() + " where " + columnsList[0] + "=#{"
				+ columnsList[0] + "}";
		return update;
	}

	public String getUpdateSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {

		StringBuffer sb = new StringBuffer();
		ColumnData cd = (ColumnData) columnList.get(0);
		sb.append("\t<trim  suffixOverrides=\",\" >\n");
		for (int i = 1; i < columnList.size(); ++i) {
			ColumnData data = (ColumnData) columnList.get(i);
			String columnName = data.getColumnName();
			sb.append("\t<if test=\"").append(columnName).append(" != null ");

			if ("String" == data.getDataType())
				sb.append(" and ").append(columnName).append(" != ''");

			sb.append(" \">\n\t\t");
			sb.append(columnName + "=#{" + columnName + "},\n");
			sb.append("\t</if>\n");
		}
		sb.append("\t</trim>");
		String update = "update " + tableName + " set \n" + sb.toString() + " where " + cd.getColumnName() + "=#{"
				+ cd.getColumnName() + "}";
		return update;
	}

	public String getColumnSplit(List<ColumnData> columnList) throws SQLException {

		StringBuffer commonColumns = new StringBuffer();
		for (Iterator localIterator = columnList.iterator(); localIterator.hasNext();) {
			ColumnData data = (ColumnData) localIterator.next();
			commonColumns.append(data.getColumnName() + "|");
		}
		return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
	}

	/**
	 * 数据库设置
	 */
	public void getSystem() {
		Properties prop = new Properties();
		try {
			// 读取属性文件a.properties
			String path = TestUtil.getProjectPath()+"src/main/resources/"+"jdbc.properties";
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			prop.load(in); /// 加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			Map<String,String> map =new HashMap<String,String>();
			while (it.hasNext()) {
				String key = it.next();
				map.put(key, prop.getProperty(key));
			}
			in.close();
			
			TestUtil.URL = map.get("jdbc.jdbcUrl");
			TestUtil.USERNAME = map.get("jdbc.user");
			TestUtil.PASSWORD = map.get("jdbc.password");
			TestUtil.BASEDATA = map.get("jdbc.base");
		} catch (Exception e) {
			System.out.println("没有找到某配置文件->jdbc.properties");
		}
	}
}