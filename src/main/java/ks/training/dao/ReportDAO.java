package ks.training.dao;

import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {
    public int getTransactionCountByMonth(Integer month, int year, String type) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE YEAR(created_at) = ?";

        if (month != null) {
            sql += " AND MONTH(created_at) = ?";
        }

        if (!type.equals("Tất cả trạng thái")) {
            sql += " AND status = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            stmt.setInt(paramIndex++, year);

            if (month != null) {
                stmt.setInt(paramIndex++, month);
            }

            if (!type.equals("Tất cả trạng thái")) {
                stmt.setString(paramIndex++, type);
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
