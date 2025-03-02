package ks.training.dao;

import ks.training.dto.TransactionDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO {
    public TransactionDto getPropertyById(Connection conn, int propertyId) throws SQLException {
        String sql = "SELECT p.title, p.price, p.address, u.full_name AS seller_name, u.id AS seller_id " +
                "FROM properties p " +
                "JOIN users u ON p.created_by = u.id " +
                "WHERE p.id = ?";
        TransactionDto transaction = null;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    transaction = new TransactionDto();
                    transaction.setSellerId(rs.getInt("seller_id"));
                    transaction.setAddress(rs.getString("address"));
                    transaction.setPrice(rs.getDouble("price"));
                    transaction.setTitle(rs.getString("title"));
                    transaction.setSellerName(rs.getString("seller_name"));
                }
            }
        }
        return transaction;
    }
}
