package app;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {
	private static final String driverName = "com.holub.database.jdbc.JDBCDriver";
	private Connection connection = null;
	private Statement statement = null;

	public DBConnector(String dbName) {
		// db open
		String databaseName = dbName;

		try {
			Class.forName(driverName).newInstance();

			while (true) {
				File database = new File(databaseName);
				if (database.exists() && database.isDirectory())
					break;
				else
					System.out.println("error opening database~");
			}
			try {
				connection = DriverManager.getConnection("file:/" + databaseName, "harpo", "swordfish");
				statement = connection.createStatement();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> querySelect(String sqlStr) {
		ResultSet results;

		try {
			results = statement.executeQuery(sqlStr);
			ArrayList<String> tmp = resultSetasStringArray(results);
			return tmp;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		ArrayList<String> a = new ArrayList<String>();
		return a;
	}

	public void query(String sqlStr) {
		try {
			int status = statement.executeUpdate(sqlStr);
			System.out.println("DB updated: " + String.valueOf(status));
			// test
			ArrayList<String> resultTable = querySelect("selece * from product");
			System.out.println(resultTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// ----------------------------------------------------------------------
	private ArrayList<String> resultSetasStringArray(ResultSet results) throws SQLException {
		ResultSetMetaData metadata = results.getMetaData();
		ArrayList<String> resultArray = new ArrayList<String>();
		int columns = metadata.getColumnCount();

		for (int i = 0; results.next(); i++) {
			StringBuffer tmp = new StringBuffer();
			for (int j = 1; j <= columns; ++j) {
				tmp.append(results.getString(metadata.getColumnName(j)));
				// this to make the String easy to parse
				tmp.append("/");
			}
			resultArray.add(tmp.toString());

		}
		System.out.println(resultArray);

		return resultArray;
	}

}
