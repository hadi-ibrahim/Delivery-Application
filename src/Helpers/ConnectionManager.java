package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String ConnectionString = "jdbc:mysql://localhost/deliveryapp?user=oop&password=2";
	private static int NumberOfOpenedConnection = 0;
	private static Connection con = null;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver initiated successfully");
			if (con == null || con.isClosed()) {
				con = DriverManager.getConnection(ConnectionString);
			}
		} catch (Exception ex) {
			System.out.println("Connection could not be established " + ex);
		}
		NumberOfOpenedConnection++;
		return con;
	}

	public static boolean IsConnectionOpened() {
		try {

			return !con.isClosed();

		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return false;
	}

	public static void Close() {
		try {
			con.close();
			NumberOfOpenedConnection--;
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}
}
