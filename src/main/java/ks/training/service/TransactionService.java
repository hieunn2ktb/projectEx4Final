package ks.training.service;

import ks.training.dao.TransactionDAO;
import ks.training.dto.TransactionDto;

import java.sql.Connection;

public class TransactionService {
    private TransactionDAO transactionDAO;

    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }

    public public TransactionDto getPropertyById(Connection conn, int propertyId){

    }
}
