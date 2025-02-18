package ks.training.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	// Thông tin kết nối
	private static final String URL = "jdbc:mysql://localhost:3306/real_estate_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "123456";

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Lỗi kết nối đến MySQL.");
		} catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
	}


}
