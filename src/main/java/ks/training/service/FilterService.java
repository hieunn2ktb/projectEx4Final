package ks.training.service;

import ks.training.dao.PropertyDao;
import ks.training.dto.PropertyDto;

import java.sql.SQLException;
import java.util.List;

public class FilterService {
    private PropertyDao propertyDao;

    public FilterService() {
        this.propertyDao = new PropertyDao();
    }

    public List<PropertyDto> list(String minPrice, String maxPrice, String propertyType, String address,
                                  int page, int recordsPerPage) throws SQLException {
        if (address.isEmpty() && propertyType == null) {
            double minPriceValue = minPrice != null && !minPrice.isEmpty() ? Double.parseDouble(minPrice) : 0.0;
            double maxPriceValue = maxPrice != null && !maxPrice.isEmpty() ? Double.parseDouble(maxPrice) : Double.MAX_VALUE;
            return propertyDao.findPropertyByPriceWithPagination(minPriceValue, maxPriceValue, page, recordsPerPage);
        } else if (minPrice.isEmpty() && maxPrice.isEmpty() && address.isEmpty()) {
            return propertyDao.findPropertyByTypeWithPagination(propertyType, page, recordsPerPage);
        } else if (minPrice.isEmpty() && maxPrice.isEmpty() && propertyType == null) {
            return propertyDao.findPropertyByAddressWithPagination(address, page, recordsPerPage);
        } else {
            return propertyDao.findAllWithPagination(page, recordsPerPage);
        }
    }
}
