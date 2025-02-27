package ks.training.service;

import ks.training.dao.PropertyDao;
import ks.training.dto.PropertyDto;
import ks.training.entity.Property;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PropertyService {
    private final PropertyDao propertyDao;

    public PropertyService() {
        this.propertyDao = new PropertyDao();
    }

    public List<PropertyDto> findPropertiesByPage(String minPrice, String maxPrice, String searchAddress, String searchPropertyType, int page, int pageSize) throws SQLException {
        return propertyDao.findPropertiesByPage(minPrice, maxPrice, searchAddress, searchPropertyType, page, pageSize);
    }

    public int countProperties(String minPrice, String maxPrice, String searchAddress, String searchPropertyType) throws SQLException {
        return propertyDao.countProperties(minPrice, maxPrice, searchAddress, searchPropertyType);
    }

    public void addProperty(Property property) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                propertyDao.addProperty(conn, property);
                propertyDao.addProperty(conn, property);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Property findPropertyById(int id) {
        return propertyDao.findPropertyById(id);
    }

    public void updateProperty(Property property) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                propertyDao.updateProperty(conn, property);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkTransactionSQL(int id) {
        return propertyDao.checkTransactionSQL(id);
    }

    public int deleteProperty(int id) {
        int rowsAffected = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                rowsAffected = propertyDao.deleteProperty(conn, id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

}
