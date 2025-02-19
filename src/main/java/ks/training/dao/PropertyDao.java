package ks.training.dao;

import ks.training.dto.PropertyDto;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyDao {

    private static final String BASE_QUERY = "SELECT pi.image_url, p.title, p.price, p.description, " +
            "p.address, p.property_type, p.acreage, u.full_name, u.phone " +
            "FROM properties p " +
            "LEFT JOIN property_images pi ON pi.property_id = p.id " +
            "JOIN users u ON p.created_by = u.id ";

    public List<PropertyDto> findAll() throws SQLException {
        String sql = BASE_QUERY;
        return executeQuery(sql, null);
    }

    public List<PropertyDto> findPropertyByPrice(double startPrice, double endPrice) throws SQLException {
        String sql = BASE_QUERY + "WHERE p.price BETWEEN ? AND ?";
        return executeQuery(sql, new Object[]{startPrice, endPrice});
    }

    public List<PropertyDto> findPropertyByType(String propertyType) throws SQLException {
        String sql = BASE_QUERY + "WHERE p.property_type = ?";
        return executeQuery(sql, new Object[]{propertyType});
    }

    public List<PropertyDto> findPropertyByAddress(String address) throws SQLException {
        String sql = BASE_QUERY + "WHERE p.address = ?";
        return executeQuery(sql, new Object[]{address});
    }

    public List<PropertyDto> findPropertyByAll(int startPrice, int endPrice, String propertyType, String address) throws SQLException {
        String sql = "SELECT p.title, MAX(pi.image_url) AS image_url, p.price, p.description, " +
                "p.address, p.property_type, p.acreage, u.full_name, u.phone " +
                "FROM properties p " +
                "LEFT JOIN property_images pi ON pi.property_id = p.id " +
                "JOIN users u ON p.created_by = u.id " +
                "WHERE p.price BETWEEN ? AND ? " +
                "AND p.property_type = ? " +
                "AND p.address = ? " +
                "GROUP BY p.title, p.price, p.description, p.address, p.property_type, p.acreage, u.full_name, u.phone " +
                "LIMIT 10000";
        return executeQuery(sql, new Object[]{startPrice, endPrice, propertyType, address});
    }

    private List<PropertyDto> executeQuery(String sql, Object[] params) throws SQLException {
        List<PropertyDto> propertyDtos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof Integer) {
                        pstmt.setInt(i + 1, (Integer) params[i]);
                    } else if (params[i] instanceof Double) {
                        pstmt.setDouble(i + 1, (Double) params[i]);
                    } else if (params[i] instanceof String) {
                        pstmt.setString(i + 1, (String) params[i]);
                    }
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PropertyDto propertyDto = new PropertyDto();
                    propertyDto.setImageUrl(rs.getString("image_url"));
                    propertyDto.setTitle(rs.getString("title"));
                    propertyDto.setPrice(rs.getDouble("price"));
                    propertyDto.setDescription(rs.getString("description"));
                    propertyDto.setAddress(rs.getString("address"));
                    propertyDto.setPropertyType(rs.getString("property_type"));
                    propertyDto.setAcreage(rs.getInt("acreage"));
                    propertyDto.setFullName(rs.getString("full_name"));
                    propertyDto.setPhone(rs.getString("phone"));
                    propertyDtos.add(propertyDto);
                }
            }
        }
        return propertyDtos;
    }
    public int countProperties() throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM properties";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(countQuery);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }

    public List<PropertyDto> findPropertiesByPage(int page, int recordsPerPage) throws SQLException {
        // Tính toán offset dựa trên trang hiện tại và số bản ghi mỗi trang
        int offset = (page - 1) * recordsPerPage;

        String sql = BASE_QUERY + " LIMIT ?, ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set các tham số LIMIT
            statement.setInt(1, offset);
            statement.setInt(2, recordsPerPage);

            ResultSet resultSet = statement.executeQuery();

            List<PropertyDto> properties = new ArrayList<>();
            while (resultSet.next()) {
                PropertyDto property = new PropertyDto();
                property.setImageUrl(resultSet.getString("image_url"));
                property.setTitle(resultSet.getString("title"));
                property.setPrice(resultSet.getDouble("price"));
                property.setDescription(resultSet.getString("description"));
                property.setAddress(resultSet.getString("address"));
                property.setPropertyType(resultSet.getString("property_type"));
                property.setAcreage(resultSet.getInt("acreage"));
                property.setFullName(resultSet.getString("full_name"));
                property.setPhone(resultSet.getString("phone"));
                properties.add(property);
            }
            return properties;
        }
    }
    public List<PropertyDto> findPropertyByPriceWithPagination(double minPrice, double maxPrice, int page, int recordsPerPage) throws SQLException {
        String query = "SELECT * FROM properties WHERE price >= ? AND price <= ? LIMIT ?, ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);
            statement.setInt(3, (page - 1) * recordsPerPage); // OFFSET
            statement.setInt(4, recordsPerPage); // LIMIT

            ResultSet resultSet = statement.executeQuery();
            List<PropertyDto> properties = new ArrayList<>();
            while (resultSet.next()) {
                PropertyDto property = new PropertyDto();
                property.setTitle(resultSet.getString("title"));
                property.setPrice(resultSet.getDouble("price"));
                property.setDescription(resultSet.getString("description"));
                property.setAddress(resultSet.getString("address"));
                property.setPropertyType(resultSet.getString("property_type"));
                properties.add(property);
            }
            return properties;
        }
    }

    // Phương thức tìm bất động sản theo loại với phân trang
    public List<PropertyDto> findPropertyByTypeWithPagination(String propertyType, int page, int recordsPerPage) throws SQLException {
        String query = "SELECT * FROM properties WHERE property_type = ? LIMIT ?, ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, propertyType);
            statement.setInt(2, (page - 1) * recordsPerPage); // OFFSET
            statement.setInt(3, recordsPerPage); // LIMIT

            ResultSet resultSet = statement.executeQuery();
            List<PropertyDto> properties = new ArrayList<>();
            while (resultSet.next()) {
                PropertyDto property = new PropertyDto();
                property.setTitle(resultSet.getString("title"));
                property.setPrice(resultSet.getDouble("price"));
                property.setDescription(resultSet.getString("description"));
                property.setAddress(resultSet.getString("address"));
                property.setPropertyType(resultSet.getString("property_type"));
                properties.add(property);
            }
            return properties;
        }
    }

    // Phương thức tìm bất động sản theo địa chỉ với phân trang
    public List<PropertyDto> findPropertyByAddressWithPagination(String address, int page, int recordsPerPage) throws SQLException {
        String query = "SELECT * FROM properties WHERE address LIKE ? LIMIT ?, ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + address + "%");
            statement.setInt(2, (page - 1) * recordsPerPage); // OFFSET
            statement.setInt(3, recordsPerPage); // LIMIT

            ResultSet resultSet = statement.executeQuery();
            List<PropertyDto> properties = new ArrayList<>();
            while (resultSet.next()) {
                PropertyDto property = new PropertyDto();
                property.setTitle(resultSet.getString("title"));
                property.setPrice(resultSet.getDouble("price"));
                property.setDescription(resultSet.getString("description"));
                property.setAddress(resultSet.getString("address"));
                property.setPropertyType(resultSet.getString("property_type"));
                properties.add(property);
            }
            return properties;
        }
    }

    // Phương thức lấy tất cả bất động sản với phân trang
    public List<PropertyDto> findAllWithPagination(int page, int recordsPerPage) throws SQLException {
        String query = "SELECT * FROM properties LIMIT ?, ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, (page - 1) * recordsPerPage); // OFFSET
            statement.setInt(2, recordsPerPage); // LIMIT

            ResultSet resultSet = statement.executeQuery();
            List<PropertyDto> properties = new ArrayList<>();
            while (resultSet.next()) {
                PropertyDto property = new PropertyDto();
                property.setTitle(resultSet.getString("title"));
                property.setPrice(resultSet.getDouble("price"));
                property.setDescription(resultSet.getString("description"));
                property.setAddress(resultSet.getString("address"));
                property.setPropertyType(resultSet.getString("property_type"));
                properties.add(property);
            }
            return properties;
        }
    }


}
