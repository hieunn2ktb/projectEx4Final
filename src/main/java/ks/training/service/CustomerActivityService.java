package ks.training.service;

import ks.training.dao.CustomerActivityDao;
import ks.training.dto.HistoryViewDto;
import ks.training.entity.CustomerActivity;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public List<HistoryViewDto> ListViewHistory(int page, int recordsPerPage) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return customerActivityDao.historyView(conn,page,recordsPerPage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<CustomerActivity> customerActivities(int userId, int propertyId ,int page, int recordsPerPage){
        return customerActivityDao.activityList(userId,propertyId,page,recordsPerPage);
    }
    public int getTotalPages(){
        try (Connection conn = DatabaseConnection.getConnection()) {
            return customerActivityDao.getTotalPages(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int countCustomer(int userId, int propertyId){
        return customerActivityDao.countCustomer(userId,propertyId);
    }


}
