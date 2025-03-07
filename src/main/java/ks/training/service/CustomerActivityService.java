package ks.training.service;

import ks.training.dao.CustomerActivityDao;
import ks.training.dto.HistoryViewDto;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerActivityService {
    private CustomerActivityDao customerActivityDao;

    public CustomerActivityService() {
        this.customerActivityDao = new CustomerActivityDao();
    }

    public void logCustomerActivity(int customerId, int propertyId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                customerActivityDao.logCustomerActivity(conn, customerId, propertyId);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<HistoryViewDto> countViewHistory() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return customerActivityDao.historyView(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
