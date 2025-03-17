package ks.training.dao;

import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {
    public int getTransactionCountByMonth(int month, int year,String type) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE MONTH(created_at) = ? AND YEAR(created_at) = ?";
        String baseSql = type.equals("Tất cả trạng thái") ? sql : sql + " AND status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(baseSql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            if (!type.equals("Tất cả trạng thái")) {
                stmt.setString(3, type);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
