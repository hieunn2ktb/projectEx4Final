package ks.training.dao;

import ks.training.dto.TransactionDto;
import ks.training.dto.TransactionResponseDto;
import ks.training.entity.Transaction;
import ks.training.utils.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public boolean processTransaction(Connection conn, int propertyId, int buyerId, int sellerId, String transactionType) {
        String statusId = "Đang xử lý";
        boolean result = false;
        String sql = "INSERT INTO transactions (property_id, buyer_id, seller_id, transaction_type, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, propertyId);
            pstmt.setInt(2, buyerId);
            pstmt.setInt(3, sellerId);
            pstmt.setString(4, transactionType);
            pstmt.setString(5, statusId);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkTransaction(int propertyId) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE property_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, propertyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<TransactionResponseDto> getAllTransactions(String status, String buyerName, String startDate, String endDate, int page, int pageSize) throws SQLException {
        List<TransactionResponseDto> transactions = new ArrayList<>();
        String sql = "SELECT t.id, u.full_name AS buyer_name, p.title AS property_name, t.transaction_type, t.status, t.created_at ,p.id as propertyId ,u.id as userId\n" +
                "                              FROM transactions t \n" +
                "                              JOIN users u ON t.buyer_id = u.id\n" +
                "                              JOIN properties p ON t.property_id = p.id where 1=1";
        int offset = (page - 1) * pageSize;
        List<Object> params = new ArrayList<>();
        if (status != null && !status.isEmpty()) {
            sql += " AND t.status = ?";
            params.add(status);
        }

        if (buyerName != null && !buyerName.isEmpty()) {
            sql += " AND u.email = ?";
            params.add(buyerName);
        }

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            sql += " AND t.created_at BETWEEN ? AND ?";
            params.add(startDate);
            params.add(endDate);
        }

        sql += " LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(offset);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new TransactionResponseDto(
                            rs.getInt("id"),
                            rs.getString("buyer_name"),
                            rs.getString("property_name"),
                            rs.getString("transaction_type"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("propertyId"),
                            rs.getInt("userId")
                    ));
                }
            }
        }
        return transactions;
    }

    public int countTransaction(String status, String buyerName, String startDate, String endDate) {
        String sql = "SELECT COUNT(*) AS total_transactions\n" +
                "FROM transactions t \n" +
                "JOIN users u ON t.buyer_id = u.id\n" +
                "JOIN properties p ON t.property_id = p.id WHERE 1=1";

        List<Object> params = new ArrayList<>();
        if (endDate != null && startDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
            sql += " AND price BETWEEN ? AND ?";
            params.add(startDate);
            params.add(endDate);
        }

        if (status != null && !status.isEmpty()) {
            sql += " AND t.status = ?";
            params.add(status);
        }

        if (buyerName != null && !buyerName.isEmpty()) {
            sql += " AND u.email = ?";
            params.add(buyerName);
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean updateTransactionStatus(int transactionId, String newStatus) throws SQLException {
        boolean result = false;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE transactions SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, transactionId);
            result = stmt.executeUpdate() > 0;
        }
        return result;
    }

    public String getBuyerEmail(int transactionId) {
        String sql = "SELECT u.email FROM users u JOIN transactions t ON u.id = t.buyer_id WHERE t.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TransactionResponseDto getTransactionById(int id) {
        String sql = "SELECT t.id, u.full_name AS buyer_name, p.title AS property_name, t.transaction_type, t.status, t.created_at ,p.id as propertyId ,u.id as userId\n" +
                "        FROM transactions t\n" +
                "JOIN users u ON t.buyer_id = u.id\n" +
                "        JOIN properties p ON t.property_id = p.id where t.id = ?";
        TransactionResponseDto transaction = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    transaction = new TransactionResponseDto(
                            rs.getInt("id"),
                            rs.getString("buyer_name"),
                            rs.getString("property_name"),
                            rs.getString("transaction_type"),
                            rs.getString("status"),
                            rs.getTimestamp("created_at"),
                            rs.getInt("propertyId"),
                            rs.getInt("userId")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }
}
