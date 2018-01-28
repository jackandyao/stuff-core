package com.qbao.aisr.stuff.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
 
/**
 * @author zhangjun
 * @since 21.07.2017
 */
public class DBTools {
 
    private static final Logger logger = Logger.getLogger(DBTools.class);
    
    private Connection conn = null;
    private StringBuilder builder = new StringBuilder();
 
//    private JDBCUtil jdbcutil = new JDBCUtil();
    private InputStream getTestDataInputStream(){
    	
    	byte[] bytes = builder.toString().getBytes();
    	InputStream is = new ByteArrayInputStream(bytes);
    	return is;
    }
    
    public void addOneCSVRecord(String[] record){
    	
    	builder.append(String.join("\t", record)).append("\n");
    }
//    {
//        StringBuilder builder = new StringBuilder();
//        for (int i = 1; i <= 10; i++) {
//            for (int j = 0; j <= 10000; j++) {
// 
//                builder.append(4);
//                builder.append("\t");
//                builder.append(4 + 1);
//                builder.append("\t");
//                builder.append(4 + 2);
//                builder.append("\t");
//                builder.append(4 + 3);
//                builder.append("\t");
//                builder.append(4 + 4);
//                builder.append("\t");
//                builder.append(4 + 5);
//                builder.append("\n");
//            }
//        }
//        byte[] bytes = builder.toString().getBytes();
//        InputStream is = new ByteArrayInputStream(bytes);
//        return is;
//    }
 
    /**
     * 
     * load bulk data from InputStream to MySQL
     */
    private int bulkLoadFromInputStream(JDBCUtil jdbcutil, String loadDataSql,
            InputStream dataStream) throws SQLException {
        if(dataStream==null){
            logger.info("InputStream is null ,No data is imported");
            return 0;
        }
//        jdbcutil.createConnStuff();
        conn = jdbcutil.getConnection();
//        conn = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement(loadDataSql);
 
        int result = 0;
 
        if (statement.isWrapperFor(com.mysql.jdbc.Statement.class)) {
 
            com.mysql.jdbc.PreparedStatement mysqlStatement = statement
                    .unwrap(com.mysql.jdbc.PreparedStatement.class);
 
            mysqlStatement.setLocalInfileInputStream(dataStream);
            result = mysqlStatement.executeUpdate();
        }
        return result;
    }
 
    public void loadCSV2Mysql(JDBCUtil jdbcutil, String table, String[] columns) {
        String testSql = "LOAD DATA LOCAL INFILE 'sql.csv' IGNORE INTO TABLE "+table+" ("+String.join(",", columns)+")";
        InputStream dataStream = this.getTestDataInputStream();
//        DBTools dao = new DBTools();
        try {
            long beginTime=System.currentTimeMillis();
            int rows=this.bulkLoadFromInputStream(jdbcutil, testSql, dataStream);
            long endTime=System.currentTimeMillis();
            logger.info("importing "+rows+" rows data into mysql and cost "+(endTime-beginTime)+" ms!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.exit(1);
    }
 
    
    public static void deleteTables(JDBCUtil jdbcutil, String...tables){
		for (int i = 0; i < tables.length; i++) {
			String sql = "DROP TABLE IF EXISTS "+tables[i];
			jdbcutil.execute(sql);
		}
	}
    public static void renameTable(JDBCUtil jdbcutil, String fromTable , String toTable) {
		
		String sql = " rename table " + fromTable + " to " + toTable;
		jdbcutil.execute(sql);
	}
    public static void createTables(JDBCUtil jdbcutil, String... tables) {
    	
		for (int i = 0; i < tables.length; i++) {
			String sql = "CREATE TABLE "+tables[i]+" (user_id bigint(20) NOT NULL,stuff_ids text NOT NULL,create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (user_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
			jdbcutil.execute(sql);
		}
		
	}
    
    public static void createUserTable(JDBCUtil jdbcutil, String table) {
    	
		String sql = "CREATE TABLE "+table+" (user_id bigint(20) NOT NULL,stuff_ids text NOT NULL,view_ids text NOT NULL,buy_ids text NOT NULL,create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (user_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
		jdbcutil.execute(sql);
		
	}
    
    public static void createDetailTable(JDBCUtil jdbcutil, String table) {
    	
		String sql = "CREATE TABLE "+table+" (stuff_id bigint(20) NOT NULL,stuff_ids text NOT NULL,create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (stuff_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
		jdbcutil.execute(sql);
		
	}

}
