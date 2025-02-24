package ks.training.service;

import ks.training.dao.PropertyDao;
import ks.training.dto.PropertyDto;
import ks.training.dto.UserDto;

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
}
