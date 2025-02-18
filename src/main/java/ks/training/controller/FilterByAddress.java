package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.dto.PropertyDto;
import ks.training.service.FilterService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/filter")
public class FilterByAddress extends HttpServlet {
    private FilterService filterService;

    @Override
    public void init() {
        filterService = new FilterService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang tìm kiếm
        request.getRequestDispatcher("property-list.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String minPrice = request.getParameter("min-price");
        String maxPrice = request.getParameter("max-price");
        String propertyType = request.getParameter("property-type");
        String address = request.getParameter("address");

        List<PropertyDto> filteredProperties = null;
        try {
            filteredProperties = filterService.list(minPrice, maxPrice, propertyType, address);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("properties", filteredProperties);
        request.getRequestDispatcher("property-list.jsp").forward(request, response);
    }
}
