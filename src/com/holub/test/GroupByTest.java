package com.holub.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class GroupByTest {

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

    @Test
    public void groupByTest() {
        Connection connection = null;
        Statement statement = null;

        // given
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

        // 정답 준비
        String answer =
        "country   avg(salary)\n"   +
        "--------- --------- \n"    +
        "Philippines3666      \n"   +
        "Ukraine   3700      \n"    +
        "Portugal  4200      \n"    +
        "Syria     3100      \n"    +
        "Russia    1100      \n"    + 
        "Greece    3600      \n"    + 
        "Venezuela 1100      \n"    + 
        "Sweden    3400      \n"    + 
        "Senegal   1400      \n"    + 
        "Ireland   1400      \n"    + 
        "China     3333      \n"    + 
        "Poland    1750      \n"    + 
        "Brazil    2900      \n"    + 
        "Indonesia 4300      \n"    ; 

        // when
        ResultSet results = null;
        String resultString = null;

        try {
            // 실제 쿼리 테스트 수행
            results = statement.executeQuery("select country, avg(salary) from sample2 group by country");
            resultString = resultSetasString(results);
        } catch (Exception e) {
            System.err.println("Query Failed on test");
            System.exit(1);
        }

        // then
        // 정답과 테스트결과 비교
        assertEquals(answer, resultString);
    }   
    
}
