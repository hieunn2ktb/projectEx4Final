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
    List<PropertyDto> properties;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int recordsPerPage = 5;
        int currentPage = 1;
        System.out.println("Servlet PropertyListServlet da duoc goi!");

        // Lấy tham số tìm kiếm từ request
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String searchAddress = request.getParameter("searchAddress");
        String searchPropertyType = request.getParameter("searchPropertyType");

        System.out.println("Gia tri minPrice: " + minPrice);
        System.out.println("Gia tri maxPrice: " + maxPrice);
        System.out.println("Gia tri searchAddress: " + searchAddress);
        System.out.println("Gia tri searchPropertyType: " + searchPropertyType);
        System.out.println();

        // Xử lý tham số trang
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) {
                currentPage = 1;
            }
        }

        System.out.println("Gia tri currentPage: " + currentPage);

        try {
            // Đếm số bản ghi sau khi lọc
            int totalRecords = propertyDao.countProperties(minPrice, maxPrice, searchAddress, searchPropertyType);
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            System.out.println("Gia tri totalRecords: " + totalRecords);
            System.out.println("Gia tri totalPages: " + totalPages);

            // Điều chỉnh currentPage nếu vượt quá tổng số trang
            if (totalPages == 0) {
                totalPages = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }

            System.out.println("Gia tri currentPage: " + currentPage);
            // Lấy danh sách bất động sản theo tìm kiếm và phân trang
             properties = propertyDao.findPropertiesByPage(minPrice, maxPrice, searchAddress, searchPropertyType, currentPage, recordsPerPage);


            // Gửi dữ liệu đến JSP
            request.setAttribute("properties", properties);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchAddress", searchAddress);
            request.setAttribute("searchPropertyType", searchPropertyType);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);

            request.getRequestDispatcher("property-list.jsp").forward(request, response);
            System.out.println("Đã forward đến property-list.jsp");
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn dữ liệu");
        }
    }


}
