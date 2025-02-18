package ks.training.dao;

import ks.training.dto.PropertyDto;
import ks.training.utils.DatabaseConnection;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyDao {
    public List<PropertyDto> findAll() throws SQLException {
        String sql = "SELECT pi.image_url, \n" +
                "       p.title, \n" +
                "       p.price, \n" +
                "       p.description, \n" +
                "       p.address, \n" +
                "       p.property_type, \n" +
                "       p.age, \n" +
                "       u.full_name, \n" +
                "       u.phone\n" +
                "FROM properties p\n" +
                "LEFT JOIN property_images pi ON pi.property_id = p.id \n" +
                "JOIN users u ON p.created_by = u.id;";
        List<PropertyDto> propertyDtos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                PropertyDto propertyDto = new PropertyDto();
                propertyDto.setImageUrl(rs.getString("image_url"));
                propertyDto.setTitle(rs.getString("title"));
                propertyDto.setPrice(rs.getDouble("price"));
                propertyDto.setDescription(rs.getString("description"));
                propertyDto.setAddress(rs.getString("address"));
                propertyDto.setPropertyType(rs.getString("property_type"));
                propertyDto.setAcreage(rs.getInt("age"));
                propertyDto.setFullName(rs.getString("full_name"));
                propertyDto.setPhone(rs.getString("phone"));
                propertyDtos.add(propertyDto);
            }
        }
        return propertyDtos;
    }

    public List<PropertyDto> findPropertyByPrice(double startPrice, double endPrice) throws SQLException {
        String sql = "SELECT pi.image_url, \n" +
                "       p.title, \n" +
                "       p.price, \n" +
                "       p.description, \n" +
                "       p.address, \n" +
                "       p.property_type, \n" +
                "       p.age, \n" +
                "       u.full_name, \n" +
                "       u.phone\n" +
                "FROM properties p\n" +
                "LEFT JOIN property_images pi ON pi.property_id = p.id \n" +
                "JOIN users u ON p.created_by = u.id" +
                "WHERE p.price BETWEEN ? AND ?;\n";
        List<PropertyDto> propertyDtos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            pstmt.setDouble(1, startPrice);
            pstmt.setDouble(2, endPrice);
            while (rs.next()) {
                PropertyDto propertyDto = new PropertyDto();
                propertyDto.setImageUrl(rs.getString("image_url"));
                propertyDto.setTitle(rs.getString("title"));
                propertyDto.setPrice(rs.getDouble("price"));
                propertyDto.setDescription(rs.getString("description"));
                propertyDto.setAddress(rs.getString("address"));
                propertyDto.setPropertyType(rs.getString("property_type"));
                propertyDto.setAcreage(rs.getInt("age"));
                propertyDto.setFullName(rs.getString("full_name"));
                propertyDto.setPhone(rs.getString("phone"));
                propertyDtos.add(propertyDto);
            }
        }
        return propertyDtos;

    }

    public List<PropertyDto> findPropertyByType(String propertyType) throws SQLException {
        String sql = "SELECT pi.image_url, \n" +
                "       p.title, \n" +
                "       p.price, \n" +
                "       p.description, \n" +
                "       p.address, \n" +
                "       p.property_type, \n" +
                "       p.age, \n" +
                "       u.full_name, \n" +
                "       u.phone\n" +
                "FROM properties p\n" +
                "LEFT JOIN property_images pi ON pi.property_id = p.id \n" +
                "JOIN users u ON p.created_by = u.id" +
                "WHERE property_type = ?;\n";
        List<PropertyDto> propertyDtos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            pstmt.setString(1, propertyType);
            while (rs.next()) {
                PropertyDto propertyDto = new PropertyDto();
                propertyDto.setImageUrl(rs.getString("image_url"));
                propertyDto.setTitle(rs.getString("title"));
                propertyDto.setPrice(rs.getDouble("price"));
                propertyDto.setDescription(rs.getString("description"));
                propertyDto.setAddress(rs.getString("address"));
                propertyDto.setPropertyType(rs.getString("property_type"));
                propertyDto.setAcreage(rs.getInt("age"));
                propertyDto.setFullName(rs.getString("full_name"));
                propertyDto.setPhone(rs.getString("phone"));
                propertyDtos.add(propertyDto);
            }
        }
        return propertyDtos;

    }
    public List<PropertyDto> findPropertyByAddress(String address) throws SQLException {
        String sql = "SELECT pi.image_url, \n" +
                "       p.title, \n" +
                "       p.price, \n" +
                "       p.description, \n" +
                "       p.address, \n" +
                "       p.property_type, \n" +
                "       p.age, \n" +
                "       u.full_name, \n" +
                "       u.phone\n" +
                "FROM properties p\n" +
                "LEFT JOIN property_images pi ON pi.property_id = p.id \n" +
                "JOIN users u ON p.created_by = u.id" +
                "WHERE address = ?;\n";
        List<PropertyDto> propertyDtos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            pstmt.setString(1, address);
            while (rs.next()) {
                PropertyDto propertyDto = new PropertyDto();
                propertyDto.setImageUrl(rs.getString("image_url"));
                propertyDto.setTitle(rs.getString("title"));
                propertyDto.setPrice(rs.getDouble("price"));
                propertyDto.setDescription(rs.getString("description"));
                propertyDto.setAddress(rs.getString("address"));
                propertyDto.setPropertyType(rs.getString("property_type"));
                propertyDto.setAcreage(rs.getInt("age"));
                propertyDto.setFullName(rs.getString("full_name"));
                propertyDto.setPhone(rs.getString("phone"));
                propertyDtos.add(propertyDto);
            }
        }
        return propertyDtos;
    }

    public List<PropertyDto> findPropertyByAll(int startPrice, int endPrice,String propertyType,String address) throws SQLException {
        String sql = "SELECT p.title, \n" +
                "       MAX(pi.image_url) AS image_url, \n" +
                "       p.price, \n" +
                "       p.description, \n" +
                "       p.address, \n" +
                "       p.property_type, \n" +
                "       p.age, \n" +
                "       u.full_name, \n" +
                "       u.phone\n" +
                "FROM properties p\n" +
                "LEFT JOIN property_images pi ON pi.property_id = p.id\n" +
                "JOIN users u ON p.created_by = u.id\n" +
                "WHERE p.price BETWEEN ? AND ?\n" +
                "      AND p.property_type = ?\n" +
                "      AND p.address = ?\n" +
                "GROUP BY p.title, p.price, p.description, p.address, p.property_type, p.age, u.full_name, u.phone\n" +
                "LIMIT 10000;\n";
        List<PropertyDto> propertyDtos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            pstmt.setInt(1, startPrice);
            pstmt.setInt(2, endPrice);
            pstmt.setString(3, propertyType);
            pstmt.setString(4, address);
            while (rs.next()) {
                PropertyDto propertyDto = new PropertyDto();
                propertyDto.setImageUrl(rs.getString("image_url"));
                propertyDto.setTitle(rs.getString("title"));
                propertyDto.setPrice(rs.getDouble("price"));
                propertyDto.setDescription(rs.getString("description"));
                propertyDto.setAddress(rs.getString("address"));
                propertyDto.setPropertyType(rs.getString("property_type"));
                propertyDto.setAcreage(rs.getInt("age"));
                propertyDto.setFullName(rs.getString("full_name"));
                propertyDto.setPhone(rs.getString("phone"));
                propertyDtos.add(propertyDto);
            }
        }
        return propertyDtos;
    }
}
