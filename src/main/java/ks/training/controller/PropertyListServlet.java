package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.dao.PropertyDao;
import ks.training.dto.PropertyDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/property-list")
public class PropertyListServlet extends HttpServlet {

    private PropertyDao propertyDao;

    @Override
    public void init() {
        propertyDao = new PropertyDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int currentPage = 0;
        int recordsPerPage = 5; // số lượng mục mỗi trang

        // Lấy tham số page từ request
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }

        try {
            // Tính toán totalPages
            int totalRecords = propertyDao.countProperties(); // Phương thức này sẽ đếm tổng số bản ghi
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            // Lấy danh sách bất động sản theo phân trang
            List<PropertyDto> properties = propertyDao.findPropertiesByPage(currentPage, recordsPerPage);

            // Gửi thông tin cần thiết vào JSP
            request.setAttribute("properties", properties);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("property-list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn dữ liệu", e);
        }
    }
}
