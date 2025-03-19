package ks.training.dao;

import ks.training.dto.PropertyDto;
import ks.training.dto.PropertyResponse;
import ks.training.entity.Property;
import ks.training.utils.DatabaseConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDao {
    private static final String UPLOAD_DIR = "uploads";
    public List<PropertyDto> findPropertiesByPage(String minPrice, String maxPrice, String searchAddress, String searchPropertyType, int page, int pageSize) throws SQLException {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT p.id, p.image_url, p.title, p.price, p.description, \n" +
                "       p.address, p.property_type, p.acreage, u.full_name, u.phone, p.created_by\n" +
                "FROM properties p\n" +
                "JOIN users u ON p.created_by = u.id\n" +
                "LEFT JOIN transactions t ON p.id = t.property_id\n" +
                "WHERE t.property_id IS NULL ";
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
                    propertyDto.setCreateBy(rs.getInt("created_by"));
                    propertyDtos.add(propertyDto);
                }
            }
        }
        return propertyDtos;
    }


    public int countProperties(String minPrice, String maxPrice, String searchAddress, String searchPropertyType) throws SQLException {
        String sql = "SELECT COUNT(*) FROM properties p " +
                "LEFT JOIN transactions t ON p.id = t.property_id " +
                "WHERE t.property_id IS NULL";
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


    public int addProperty(PropertyResponse property, String uploadRootPath) {
        int propertyId = -1;
        String sql = "INSERT INTO properties (title, description, price, address, property_type, acreage, created_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String uploadImageSQL = "INSERT INTO property_images (property_id, image_url) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, property.getTitle());
            pstmt.setString(2, property.getDescription());
            pstmt.setDouble(3, property.getPrice());
            pstmt.setString(4, property.getAddress());
            pstmt.setString(5, property.getPropertyType());
            pstmt.setDouble(6, property.getAcreage());
            pstmt.setInt(7, property.getCreatedBy());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    propertyId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        if (propertyId != -1 && property.getImageStreams() != null) {
            File uploadDir = new File(uploadRootPath);

            System.out.println("Upload directory path: " + uploadDir.getAbsolutePath());

            if (!uploadDir.exists()) uploadDir.mkdirs();

            for (InputStream imageStream : property.getImageStreams()) {
                String fileName = "property_" + propertyId + "_" + System.currentTimeMillis() + ".jpg";
                String filePath = uploadDir + File.separator + fileName;

                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = imageStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(uploadImageSQL)) {
                    stmt.setInt(1, propertyId);
                    stmt.setString(2, UPLOAD_DIR + "/" + fileName);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return propertyId;
    }

    public boolean updateProperty(Connection conn, PropertyResponse property, String uploadRootPath) {
        String updatePropertySQL = "UPDATE properties SET title = ?, description = ?, price = ?, address = ?, property_type = ?, acreage = ? WHERE id = ?";
        String deleteImagesSQL = "DELETE FROM property_images WHERE property_id = ?";
        String insertImageSQL = "INSERT INTO property_images (property_id, image_url) VALUES (?, ?)";

        try {
            try (PreparedStatement pstmt = conn.prepareStatement(updatePropertySQL)) {
                pstmt.setString(1, property.getTitle());
                pstmt.setString(2, property.getDescription());
                pstmt.setDouble(3, property.getPrice());
                pstmt.setString(4, property.getAddress());
                pstmt.setString(5, property.getPropertyType());
                pstmt.setDouble(6, property.getAcreage());
                pstmt.setInt(7, property.getId());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    return false;
                }
            }

            List<InputStream> imageStreams = property.getImageStreams();
            if (imageStreams != null && !imageStreams.isEmpty()) {

                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteImagesSQL)) {
                    deleteStmt.setInt(1, property.getId());
                    deleteStmt.executeUpdate();
                }

                File uploadDir = new File(uploadRootPath + File.separator);
                if (uploadDir.exists() && uploadDir.isDirectory()) {
                    File[] files = uploadDir.listFiles((dir, name) -> name.startsWith("property_" + property.getId() + "_"));
                    if (files != null) {
                        for (File file : files) {
                            file.delete();
                        }
                    }
                }

                for (InputStream imageStream : imageStreams) {
                    String fileName = "property_" + property.getId() + "_" + System.currentTimeMillis() + ".jpg";
                    String filePath = uploadDir + File.separator + fileName;

                    try (FileOutputStream fos = new FileOutputStream(filePath)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = imageStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertImageSQL)) {
                        insertStmt.setInt(1, property.getId());
                        insertStmt.setString(2, "uploads/" + fileName);
                        insertStmt.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean checkTransactionSQL(int id) {
        String checkTransactionSQL = "SELECT COUNT(*) FROM transactions WHERE property_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkTransactionSQL)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteProperty(Connection conn, int id) {
        String sql = "DELETE FROM properties WHERE id=?";
        int rowsDeleted = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rowsDeleted = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }

    public Property findPropertyById(int id) {
        String sql = "SELECT * FROM properties WHERE id = ?";
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return property;
    }

    public List<String> getImagesByPropertyId(int propertyId) {
        List<String> images = new ArrayList<>();
        String sql = "SELECT image_url FROM property_images WHERE property_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, propertyId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                images.add(rs.getString("image_url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public boolean checkUser(int userId, int propertyId) {
        String sql = "SELECT COUNT(*) FROM properties WHERE id = ? AND created_by = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, propertyId);
            pstmt.setInt(2, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Nếu COUNT(*) > 0, tức là user là chủ sở hữu
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}



