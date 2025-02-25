package ks.training.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String propertyId = request.getParameter("id");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT image_data FROM property_images WHERE property_id = ?")) {

            pstmt.setInt(1, Integer.parseInt(propertyId));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] imgData = rs.getBytes("image_data");

                response.setContentType("image/jpeg");
                response.getOutputStream().write(imgData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

