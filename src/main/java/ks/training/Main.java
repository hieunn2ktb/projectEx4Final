package ks.training;

import ks.training.utils.DatabaseConnection;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        //String filePath = "C:/Users/Admin/Pictures/New folder/z3729372475295_52ed3dfccca732f756808ae455065c8d.jpg";
        String filePath2 = "C:/Users/Admin/Pictures/New folder/z3729372475295_52ed3dfccca732f756808ae455065c8d.jpg";
        //String filePath3 = "C:/Users/Admin/Pictures/New folder/z3729372498572_82eee706f8c2f7303e945250b981d8b9.jpg";
        String filePath4 = "C:/Users/Admin/Pictures/New folder/z3729372502637_116d62df94a33e8054cf4314d0463496.jpg";

        // Đường dẫn ảnh cần upload C:\Users\Admin\Pictures\New folder
        int propertyId = 1; // ID của bất động sản cần liên kết ảnh

        try (Connection conn = DatabaseConnection.getConnection();
             FileInputStream fis = new FileInputStream(new File(filePath4))) {

            String sql = "INSERT INTO property_images (property_id, image_data) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, propertyId);
            stmt.setBinaryStream(2, fis, (int) new File(filePath4).length());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Ảnh đã được tải lên thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}