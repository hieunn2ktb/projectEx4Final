package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.dao.PropertyDao;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
    private final PropertyDao propertyDao = new PropertyDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(" Servlet duoc ket noi!");

        try {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            int imageIndex = Integer.parseInt(request.getParameter("imageIndex"));
            System.out.println(" propertyId: " + propertyId + ", imageIndex: " + imageIndex);

            List<byte[]> images = propertyDao.getImagesByPropertyId(propertyId);
            System.out.println(" So anh tim thay: " + images.size());

            if (images.isEmpty()) {
                System.out.println(" khong co anh nao trong database!");
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (imageIndex < 0 || imageIndex >= images.size()) {
                System.out.println(" Index anh khong hop le!");
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            byte[] imageData = images.get(imageIndex);
            System.out.println(" gui anh ve client, kich thuoc: " + imageData.length + " bytes");

            response.setContentType("image/jpeg");
            response.setContentLength(imageData.length);

            try (OutputStream os = response.getOutputStream()) {
                os.write(imageData);
                os.flush();
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ loi parse so: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
