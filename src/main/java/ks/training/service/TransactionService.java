package ks.training.service;

import ks.training.dao.TransactionDAO;
import ks.training.dto.PropertyDto;
import ks.training.dto.TransactionDto;
import ks.training.dto.TransactionResponseDto;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO;

    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }

    public TransactionDto getPropertyById(int propertyId) {
        TransactionDto transactionDto = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                transactionDto = transactionDAO.getPropertyById(conn, propertyId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionDto;
    }

    public boolean processTransaction(int propertyId, int buyerId, int sellerId, String transactionType) {
        boolean result = false;
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                transactionDAO.processTransaction(conn, propertyId, buyerId, sellerId, transactionType);
                conn.commit();
                result = true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public boolean checkTransaction(int propertyId){
        return transactionDAO.checkTransaction(propertyId);
    }


    public boolean updateTransactionStatus(int transactionId, String newStatus) throws SQLException {
        return transactionDAO.updateTransactionStatus(transactionId,newStatus);
    }

    public List<TransactionResponseDto> findTransactionByPage(String status, String buyerName, String startDate, String endDate, int page, int pageSize) throws SQLException {
        return transactionDAO.getAllTransactions(status,buyerName,startDate,endDate,page,pageSize);
    }

    public int countTransaction(String status, String buyerName, String startDate, String endDate) {
        return transactionDAO.countTransaction(status, buyerName, startDate, endDate);
    }

    public String getBuyerEmail(int transactionId) {
        return  transactionDAO.getBuyerEmail(transactionId);
    }
}
