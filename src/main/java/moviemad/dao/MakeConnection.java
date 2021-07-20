package moviemad.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MakeConnection {
	/**
	 * Database connection class
	 */
	private static Connection con;
	private static String dbName = "sef";

	public MakeConnection() {
		if (con == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + MakeConnection.dbName, "root", "wat3r");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public Statement createStatement() {
		try {
			Statement stmt = con.createStatement();
			return stmt;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public void close() {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String getDBName() {
		return dbName;
	}

}
