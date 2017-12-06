package com.xl.client.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xl.client.common.Global;

public class DBHelper {
	public static final String url = "jdbc:mysql://" + Global.DBhostIP + "/bdcloud"; // 172.16.102.20
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "123456"; // "qsdhjuy543"; //
													// "123456";//

	public Connection conn = null;
	public PreparedStatement pst = null;

	static {
		System.out.println("DBHelper，数据库IP：" + Global.DBhostIP);
	}

	public DBHelper(String sql) {
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection _conn = null;
		try {
			Class.forName(name);// 指定连接类型
			_conn = DriverManager.getConnection(url, user, password);// 获取连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _conn;
	}

}