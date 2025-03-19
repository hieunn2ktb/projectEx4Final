package ks.training.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/uploads/*")
public class ImageServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "C:\\Users\\Admin\\Desktop\\Hieu\\projectEx4Final\\target\\RealEstateManagement\\uploads";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy đường dẫn ảnh từ URL
        String imagePath = request.getPathInfo();
        if (imagePath == null || imagePath.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Đường dẫn đầy đủ đến file ảnh
        File imageFile = new File(UPLOAD_DIR, imagePath);
        if (!imageFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Xác định kiểu MIME
        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) imageFile.length());

        // Gửi file về client
        try (FileInputStream in = new FileInputStream(imageFile);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
