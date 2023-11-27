package com.holub.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

// SelectStarOnJoinTest.java 코드 채용하였음 (정한울)
class DistinctOrderByTest {
    // ResultSet을 String으로 파싱하기 위해서 코드 그대로 가져옴(Console.java:195)
    private String resultSetasString( ResultSet results ) throws SQLException
	{	ResultSetMetaData metadata = results.getMetaData();

		StringBuffer b = new StringBuffer();
		int			 columns = metadata.getColumnCount();
		for( int i = 1; i <= columns; ++i )
			b.append( formatColumn( metadata.getColumnName(i), 10) );
		b.append("\n");

		for( int i = 1; i <= columns; ++i )
			b.append("--------- ");
		b.append("\n");

		while( results.next() )
		{	for( int i = 1; i <= columns; ++i )
				b.append( formatColumn(results.getString(metadata.getColumnName(i)), 10) );
			b.append("\n");
		}
		return b.toString();
	}

    // ResultSet을 String으로 파싱하기 위해서 코드 그대로 가져옴(Console.java:216)
	private String formatColumn( String msg, int width )
	{	StringBuffer b = new StringBuffer( msg );
		for( width -= msg.length(); --width >= 0 ; )
			b.append(" ");
		return b.toString();
	}
	
	private class DBConnector {
		public Connection connection;
		public Statement statement;
		
		public DBConnector (Connection conn, Statement stat) {
			this.connection = conn;
			this.statement = stat;
		}
	}	
	private DBConnector init () {		
		Connection connection = null;
        Statement statement = null;
        try {
            // jdbc 드라이버 설정
			Class.forName("com.holub.database.jdbc.JDBCDriver").newInstance();
        } catch (Exception e) {
            System.err.println("Could not find the jdbc driver on test");
            System.exit(1);
        }

        try {
            // 데이터베이스 열기
            connection = DriverManager.getConnection("file:/C:/dp2023", "harpo", "swordfish");
            statement = connection.createStatement();
        } catch( SQLException e ) {
            System.err.println("Could not open test database");
            System.exit(1);
        }
        return new DBConnector(connection, statement);
	}

	@Test
	void testDistinct() {
        DBConnector dbcon = init();
        String ans_gender = 
        		"gender    \n"
        		+ "--------- \n"
        		+ "Male      \n"
        		+ "Female    \n"
        		+ "Bigender  \n";
        
        String ans_country = 
        		"country   \n"
        		+ "--------- \n"
        		+ "Philippines\n"
        		+ "Poland    \n"
        		+ "Ireland   \n"
        		+ "Portugal  \n"
        		+ "Greece    \n"
        		+ "Russia    \n"
        		+ "China     \n"
        		+ "Venezuela \n"
        		+ "Ukraine   \n"
        		+ "Syria     \n"
        		+ "Brazil    \n"
        		+ "Senegal   \n"
        		+ "Indonesia \n"
        		+ "Sweden    \n";
        
        // when
        ResultSet results = null;
        String res_gender = null;
        String res_country = null;

        try {
            results = dbcon.statement.executeQuery("select distinct gender from sample2");
            res_gender = resultSetasString(results);
            
            results = dbcon.statement.executeQuery("select distinct country from sample2");
            res_country = resultSetasString(results);
        } catch (Exception e) {
            System.err.println("Query Failed on test");
            System.exit(1);
        }
        assertEquals(ans_gender, res_gender);
        assertEquals(ans_country, res_country);
	}
	
	@Test	
	void testOrderBy() {
        DBConnector dbcon = init();
        String ans_asc =
        		"salary    \n"
        		+ "--------- \n"
        		+ "1100      \n"
        		+ "1100      \n"
        		+ "1200      \n"
        		+ "1400      \n"
        		+ "1400      \n"
        		+ "2300      \n"
        		+ "2500      \n"
        		+ "2500      \n"
        		+ "2900      \n"
        		+ "3100      \n"
        		+ "3100      \n"
        		+ "3400      \n"
        		+ "3600      \n"
        		+ "3700      \n"
        		+ "3700      \n"
        		+ "3800      \n"
        		+ "4100      \n"
        		+ "4300      \n"
        		+ "4300      \n"
        		+ "5400      \n";

        String ans_desc =
        		"salary    \n"
        		+ "--------- \n"
        		+ "5400      \n"
        		+ "4300      \n"
        		+ "4300      \n"
        		+ "4100      \n"
        		+ "3800      \n"
        		+ "3700      \n"
        		+ "3700      \n"
        		+ "3600      \n"
        		+ "3400      \n"
        		+ "3100      \n"
        		+ "3100      \n"
        		+ "2900      \n"
        		+ "2500      \n"
        		+ "2500      \n"
        		+ "2300      \n"
        		+ "1400      \n"
        		+ "1400      \n"
        		+ "1200      \n"
        		+ "1100      \n"
        		+ "1100      \n";
        
        ResultSet results = null;
        String res_asc = null;
        String res_desc = null;
        
        try {
            results = dbcon.statement.executeQuery("select salary from sample2 order by salary asc");
            res_asc = resultSetasString(results);
            
            results = dbcon.statement.executeQuery("select salary from sample2 order by salary desc");
            res_desc = resultSetasString(results);
        } catch (Exception e) {
            System.err.println("Query Failed on test");
            System.exit(1);
        }
        assertEquals(ans_asc, res_asc);
        assertEquals(ans_desc, res_desc);
	}
}
