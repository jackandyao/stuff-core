package com.qbao.aisr.stuff.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBCUtil {
	
	
	static final String JDBC_DRIVER = SetSystemProperty.readValue(SetSystemProperty.profilepath, "JDBC_DRIVER");
	static final String DB_URL = SetSystemProperty.readValue(SetSystemProperty.profilepath, "DB_URL");
	static final String USER = SetSystemProperty.readValue(SetSystemProperty.profilepath, "USER");
	static final String PASS = SetSystemProperty.readValue(SetSystemProperty.profilepath, "PASS");
	
	static final String DB_URL_STUFF = SetSystemProperty.readValue(SetSystemProperty.profilepath, "DB_URL_STUFF");
	static final String USER_STUFF = SetSystemProperty.readValue(SetSystemProperty.profilepath, "USER_STUFF");
	static final String PASS_STUFF = SetSystemProperty.readValue(SetSystemProperty.profilepath, "PASS_STUFF");
	
	static final String DB_URL_REC = SetSystemProperty.readValue(SetSystemProperty.profilepath, "DB_URL_RECOMMEND");
	static final String USER_REC = SetSystemProperty.readValue(SetSystemProperty.profilepath, "USER");
	static final String PASS_REC = SetSystemProperty.readValue(SetSystemProperty.profilepath, "PASS");
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	public void createConn() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createConnStuff() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL_STUFF, USER_STUFF, PASS_STUFF);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createConnRec() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL_REC, USER_REC, PASS_REC);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	public ResultSet getResultSet(String sql) {
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public boolean execute(String sql) {
		boolean rest = false;
		try {
			rest = stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rest;
	}
	public void addBatch(String sql) {
		try {
			stmt.addBatch(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int[] executeBatch() {
		int[] res = new int[0];
		try {
			res = stmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	public void closeConn() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshConn(){
		closeConn();
		createConn();
	}
}
