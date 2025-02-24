package ks.training.service;

import ks.training.dao.PropertyDao;
import ks.training.dto.PropertyDto;
import ks.training.dto.UserDto;
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
    public UserDto validateUser(String username, String password) throws SQLException {
        return propertyDao.validateUser(username, password);
    }
    public void addProperty(Property property) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                propertyDao.addProperty(property);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProperty(Property property) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                propertyDao.updateProperty(property);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProperty(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                propertyDao.deleteProperty(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
