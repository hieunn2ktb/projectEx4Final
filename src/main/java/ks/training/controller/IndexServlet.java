package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dto.PropertyDto;
import ks.training.service.PropertyService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    private PropertyService propertyService;

    @Override
    public void init() {
        propertyService = new PropertyService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int recordsPerPage = 5;
        int currentPage = 1;
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String searchAddress = request.getParameter("searchAddress");
        String searchPropertyType = request.getParameter("searchPropertyType");

        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) {
                currentPage = 1;
            }
        }

        try {
            int totalRecords = propertyService.countProperties(minPrice, maxPrice, searchAddress, searchPropertyType);
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            if (totalPages == 0) {
                totalPages = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }
            List<PropertyDto> properties = propertyService.findPropertiesByPage(minPrice, maxPrice, searchAddress, searchPropertyType, currentPage, recordsPerPage);
            request.setAttribute("properties", properties);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchAddress", searchAddress);
            request.setAttribute("searchPropertyType", searchPropertyType);
            request.setAttribute("minPrice", minPrice);
            request.setAttribute("maxPrice", maxPrice);

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn dữ liệu");
        }
    }
}
