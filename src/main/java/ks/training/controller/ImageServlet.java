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
        try {
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            int imageIndex = Integer.parseInt(request.getParameter("imageIndex"));


            List<byte[]> images = propertyDao.getImagesByPropertyId(propertyId);

            if (images.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (imageIndex < 0 || imageIndex >= images.size()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            byte[] imageData = images.get(imageIndex);

            response.setContentType("image/jpeg");
            response.setContentLength(imageData.length);

            try (OutputStream os = response.getOutputStream()) {
                os.write(imageData);
                os.flush();
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
