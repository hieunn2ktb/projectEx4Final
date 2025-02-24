package ks.training.dao;

import ks.training.dto.PropertyDto;
import ks.training.dto.UserDto;
import ks.training.entity.User;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyDao {

    public List<PropertyDto> findPropertiesByPage(String minPrice, String maxPrice, String searchAddress, String searchPropertyType, int page, int pageSize) throws SQLException {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT pi.image_url, p.title, p.price, p.description, " +
                "p.address, p.property_type, p.acreage, u.full_name, u.phone " +
                "FROM properties p " +
                "LEFT JOIN property_images pi ON pi.property_id = p.id " +
                "JOIN users u ON p.created_by = u.id WHERE 1=1";

        List<Object> params = new ArrayList<>();

        if (minPrice != null && maxPrice != null && !minPrice.isEmpty() && !maxPrice.isEmpty()) {
            sql += " AND p.price BETWEEN ? AND ?";
            params.add(minPrice);
            params.add(maxPrice);
        }

        if (searchAddress != null && !searchAddress.isEmpty()) {
            sql += " AND p.address LIKE ?";
            params.add("%" + searchAddress + "%");
        }

        if (searchPropertyType != null && !searchPropertyType.isEmpty()) {
            sql += " AND p.property_type LIKE ?";
            params.add("%" + searchPropertyType + "%");
        }

        sql += " LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(offset);

        List<PropertyDto> propertyDtos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
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


    public int countProperties(String minPrice, String maxPrice, String searchAddress, String searchPropertyType) throws SQLException {
        String sql = "SELECT COUNT(*) FROM properties WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (minPrice != null && maxPrice != null && !minPrice.isEmpty() && !maxPrice.isEmpty()) {
            int min = Integer.parseInt(minPrice);
            int max = Integer.parseInt(maxPrice);
            sql += " AND price BETWEEN ? AND ?";
            params.add(min);
            params.add(max);
        }

        if (searchAddress != null && !searchAddress.isEmpty()) {
            sql += " AND address LIKE ?";
            params.add("%" + searchAddress + "%");
        }

        if (searchPropertyType != null && !searchPropertyType.isEmpty()) {
            sql += " AND property_type LIKE ?";
            params.add("%" + searchPropertyType + "%");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public UserDto validateUser(String username, String password) {
        String sql = "SELECT u.id , u.email,u.password, r.name FROM users u Join user_roles ur ON u.id = ur.user_id join roles r ON ur.role_id = r.id WHERE u.email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserDto(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
