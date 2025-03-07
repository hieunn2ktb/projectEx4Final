package ks.training.dao;

import ks.training.dto.HistoryViewDto;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerActivityDao {
    public void logCustomerActivity(Connection conn, int customerId, int propertyId) {
        String sql = "INSERT INTO customer_activity (customer_id, property_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, propertyId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<HistoryViewDto> historyView(Connection conn){
        String sql = "SELECT \n" +
                "    u.full_name, \n" +
                "    u.phone, \n" +
                "    p.title, \n" +
                "    p.price,\n" +
                "    COUNT(ca.property_id) AS view_count\n" +
                "FROM users u\n" +
                "JOIN user_roles ur \n" +
                "    ON u.id = ur.user_id\n" +
                "JOIN customer_activity ca\n" +
                "    ON ca.customer_id = u.id\n" +
                "JOIN properties p \n" +
                "    ON ca.property_id = p.id\n" +
                "WHERE ur.role_id = 3\n" +
                "GROUP BY u.full_name, u.phone, p.title,p.price\n" +
                "ORDER BY view_count DESC;";
        List<HistoryViewDto> historyViewDtos = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                HistoryViewDto historyViewDto = new HistoryViewDto();
                historyViewDto.setCustomerName(rs.getString("full_name"));
                historyViewDto.setPhone(rs.getString("phone"));
                historyViewDto.setTitleProperty(rs.getString("title"));
                historyViewDto.setPrice(rs.getDouble("price"));
                historyViewDto.setCountView(rs.getInt("view_count"));
                historyViewDtos.add(historyViewDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyViewDtos;
    }

}
