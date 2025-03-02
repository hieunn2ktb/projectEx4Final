package ks.training.service;

import ks.training.dao.CustomerActivityDao;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerActivityService {
    private CustomerActivityDao customerActivityDao;

    public CustomerActivityService() {
        this.customerActivityDao = new CustomerActivityDao();
    }

    public void logCustomerActivity(int customerId, int propertyId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                customerActivityDao.logCustomerActivity(conn,customerId,propertyId);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
