package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@MultipartConfig(maxFileSize = 16177215)
public class UploadImageServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/real_estate_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int propertyId = Integer.parseInt(request.getParameter("property_id"));
        Part filePart = request.getPart("image");
        InputStream inputStream = null;

        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        if (inputStream != null) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO property_images (property_id, image_data) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, propertyId);
                stmt.setBlob(2, inputStream);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        response.sendRedirect("upload.jsp?success=true");
    }
}
