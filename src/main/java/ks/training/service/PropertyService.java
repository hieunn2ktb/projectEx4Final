package ks.training.service;

import ks.training.dao.PropertyDao;
import ks.training.dto.PropertyDto;
import ks.training.dto.PropertyResponse;
import ks.training.entity.Property;
import ks.training.utils.DatabaseConnection;

import java.io.InputStream;
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

    public boolean addProperty(PropertyResponse property) {
        boolean isSuccess = false;
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                propertyDao.addProperty(conn, property);
                conn.commit();
                isSuccess = true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }


    public Property findPropertyById(int id) {
        return propertyDao.findPropertyById(id);
    }

    public boolean updateProperty(PropertyResponse property) {
        boolean result = false;
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                boolean updated = propertyDao.updateProperty(conn, property);
                if (!updated) {
                    conn.rollback();
                    return false;
                }
                conn.commit();
                result = true;
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Lỗi khi cập nhật BĐS", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
    public boolean checkUser(int userId, int propertyId){
        return propertyDao.checkUser( userId, propertyId);
    };

    public List<byte[]> getImagesByPropertyId(int propertyId) {
        return propertyDao.getImagesByPropertyId(propertyId);
    }
}
