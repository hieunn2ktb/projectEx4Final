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
    private String uploadRootPath;

    public PropertyService() {
        this.propertyDao = new PropertyDao();
    }

    public void setUploadRootPath(String uploadRootPath) {
        this.uploadRootPath = uploadRootPath;
    }

    public String getUploadRootPath() {
        return uploadRootPath;
    }

    public List<PropertyDto> findPropertiesByPage(String minPrice, String maxPrice, String searchAddress, String searchPropertyType, int page, int pageSize) throws SQLException {
        return propertyDao.findPropertiesByPage(minPrice, maxPrice, searchAddress, searchPropertyType, page, pageSize);
    }

    public int countProperties(String minPrice, String maxPrice, String searchAddress, String searchPropertyType) throws SQLException {
        return propertyDao.countProperties(minPrice, maxPrice, searchAddress, searchPropertyType);
    }

    public int addProperty(PropertyResponse property, String uploadRootPath) {
        if (property == null || property.getTitle() == null || property.getPrice() <= 0) {
            throw new IllegalArgumentException("Thông tin bất động sản không hợp lệ!");
        }
        return propertyDao.addProperty(property, uploadRootPath);
    }


    public Property findPropertyById(int id) {
        return propertyDao.findPropertyById(id);
    }

    public boolean updateProperty(PropertyResponse property) {
        if (uploadRootPath == null || uploadRootPath.isEmpty()) {
            throw new IllegalStateException("Upload path chưa được thiết lập!");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            boolean isUpdated = propertyDao.updateProperty(conn, property, uploadRootPath);
            if (isUpdated) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

}
