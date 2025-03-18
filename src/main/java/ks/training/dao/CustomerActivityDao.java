package ks.training.dao;

import ks.training.dto.HistoryViewDto;
import ks.training.entity.CustomerActivity;
import ks.training.utils.DatabaseConnection;

import java.sql.*;
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
    public int getTotalPages(Connection conn) {
        String countSql = "SELECT COUNT(DISTINCT u.id, p.id) FROM users u " +
                "JOIN user_roles ur ON u.id = ur.user_id " +
                "JOIN customer_activity ca ON ca.customer_id = u.id " +
                "JOIN properties p ON ca.property_id = p.id " +
                "WHERE ur.role_id = 3";
        int totalRecords = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(countSql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                 totalRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }
    public List<HistoryViewDto> historyView(Connection conn, int page, int recordsPerPage) {
        String sql = "SELECT \n" +
                "    u.full_name, \n" +
                "    u.phone, \n" +
                "    p.title, \n" +
                "    p.price, u.id as userId, p.id as propertyId,\n" +
                "    COUNT(ca.property_id) AS view_count\n" +
                "FROM users u\n" +
                "JOIN user_roles ur \n" +
                "    ON u.id = ur.user_id\n" +
                "JOIN customer_activity ca\n" +
                "    ON ca.customer_id = u.id\n" +
                "JOIN properties p \n" +
                "    ON ca.property_id = p.id\n" +
                "WHERE ur.role_id = 3\n" +
                "GROUP BY u.full_name, u.phone, p.title, p.price, u.id, p.id \n" +
                "ORDER BY view_count DESC \n" +
                "LIMIT ? OFFSET ?";

        List<HistoryViewDto> historyViewDtos = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int offset = (page - 1) * recordsPerPage;
            pstmt.setInt(1, recordsPerPage);
            pstmt.setInt(2, offset);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HistoryViewDto historyViewDto = new HistoryViewDto();
                historyViewDto.setCustomerName(rs.getString("full_name"));
                historyViewDto.setPhone(rs.getString("phone"));
                historyViewDto.setTitleProperty(rs.getString("title"));
                historyViewDto.setPrice(rs.getDouble("price"));
                historyViewDto.setCountView(rs.getInt("view_count"));
                historyViewDto.setUserId(rs.getInt("userId"));
                historyViewDto.setPropertyId(rs.getInt("propertyId"));
                historyViewDtos.add(historyViewDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyViewDtos;
    }


    public List<CustomerActivity> activityList(int userId, int propertyId, int page, int recordsPerPage) {
        String sql = "SELECT * FROM real_estate_management.customer_activity " +
                "WHERE customer_id = ? AND property_id = ? " +
                "LIMIT ? OFFSET ?";

        List<CustomerActivity> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * recordsPerPage;
            pstmt.setInt(1, userId);
            pstmt.setInt(2, propertyId);
            pstmt.setInt(3, recordsPerPage);
            pstmt.setInt(4, offset);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CustomerActivity customerActivity = new CustomerActivity();
                customerActivity.setId(rs.getInt("id"));
                customerActivity.setCustomerId(rs.getInt("customer_id"));
                customerActivity.setPropertyId(rs.getInt("property_id"));

                Timestamp timestamp = rs.getTimestamp("viewed_at");
                if (timestamp != null) {
                    customerActivity.setViewedAt(timestamp.toLocalDateTime());
                }

                list.add(customerActivity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int countCustomer(int userId, int propertyId){
        String sql = "SELECT Count(*) FROM real_estate_management.customer_activity Where customer_id = ? And property_id = ?";
        int count = 0;
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, userId);
            pstmt.setInt(2, propertyId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

}
