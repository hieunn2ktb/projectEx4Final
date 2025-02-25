package ks.training.dao;

import ks.training.dto.PropertyDto;
import ks.training.entity.Property;
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
        String sql = "SELECT p.id, p.image_url, p.title, p.price, p.description, \n" +
                "                p.address, p.property_type, p.acreage, u.full_name, u.phone\n" +
                "                FROM properties p \n" +
                "                JOIN users u ON p.created_by = u.id WHERE 1=1";
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
                    propertyDto.setId(rs.getInt("id"));
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


    public void addProperty(Connection conn, Property property) {
        String sql = "INSERT INTO properties (title, description, price, address, property_type, acreage, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, property.getTitle());
            pstmt.setString(2, property.getDescription());
            pstmt.setDouble(3, property.getPrice());
            pstmt.setString(4, property.getAddress());
            pstmt.setString(5, property.getPropertyType());
            pstmt.setDouble(6, property.getAcreage());
            pstmt.setInt(7, property.getCreatedBy());

            pstmt.executeUpdate();
            System.out.println("Thêm bất động sản thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateProperty(Connection conn, Property property) {
        String sql = "UPDATE properties SET title=?, description=?, price=?, address=?, property_type=?, acreage=? WHERE id=?";

        try (
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, property.getTitle());
            pstmt.setString(2, property.getDescription());
            pstmt.setDouble(3, property.getPrice());
            pstmt.setString(4, property.getAddress());
            pstmt.setString(5, property.getPropertyType());
            pstmt.setDouble(6, property.getAcreage());
            pstmt.setInt(7, property.getId());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cập nhật bất động sản thành công!");
            } else {
                System.out.println("Không tìm thấy bất động sản để cập nhật!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteProperty(Connection conn, int id) {
        String sql = "DELETE FROM properties WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Xóa bất động sản thành công!");
            } else {
                System.out.println("Không tìm thấy bất động sản để xóa!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Property findPropertyById(int id) {
        String sql = "SELECT * FROM properties WHERE id = ?";
        String imageSql = "SELECT image_url FROM property_images WHERE property_id = ?";
        Property property = null;

        try (Connection conn = DatabaseConnection.getConnection()) {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        property = new Property();
                        property.setId(rs.getInt("id"));
                        property.setTitle(rs.getString("title"));
                        property.setDescription(rs.getString("description"));
                        property.setPrice(rs.getDouble("price"));
                        property.setAddress(rs.getString("address"));
                        property.setPropertyType(rs.getString("property_type"));
                        property.setAcreage(rs.getInt("acreage"));
                    }
                }
            }
            if (property != null) {
                List<String> images = new ArrayList<>();
                try (PreparedStatement imgStmt = conn.prepareStatement(imageSql)) {
                    imgStmt.setInt(1, id);
                    try (ResultSet imgRs = imgStmt.executeQuery()) {
                        while (imgRs.next()) {
                            images.add(imgRs.getString("image_url"));
                        }
                    }
                }
                property.setImages(images);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return property;
    }
}



