package com.zj.study.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyDb {
	private static String driver = "org.apache.derby.jdbc.ClientDriver";
	private static String url = "jdbc:derby://localhost:1527/COREJAVA";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName(driver);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return DriverManager.getConnection(url);
	}

	public static void query() throws SQLException {
		try (Connection conn = getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM greetings");

			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		}
	}
}
