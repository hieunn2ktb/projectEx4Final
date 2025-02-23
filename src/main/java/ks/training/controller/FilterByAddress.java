//package ks.training.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import ks.training.dto.PropertyDto;
//import ks.training.service.FilterService;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//
//@WebServlet("/filter")
//public class FilterByAddress extends HttpServlet {
//    private FilterService filterService;
//
//    @Override
//    public void init() {
//        filterService = new FilterService();
//    }
//
////    @Override
////    protected void doGet(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////        // Hiển thị trang tìm kiếm
////        request.getRequestDispatcher("property-list.jsp").forward(request, response);
////    }
//
////    @Override
////    protected void doPost(HttpServletRequest request, HttpServletResponse response)
////            throws ServletException, IOException {
////        // Lấy dữ liệu từ form và các tham số phân trang
////        String minPrice = request.getParameter("min-price");
////        String maxPrice = request.getParameter("max-price");
////        String propertyType = request.getParameter("property-type");
////        String address = request.getParameter("address");
////
////        int currentPage = 1;
////        int recordsPerPage = 10; // Số lượng bản ghi mỗi trang
////
////        // Lấy tham số page từ request (nếu có)
////        String pageParam = request.getParameter("page");
////        if (pageParam != null && !pageParam.isEmpty()) {
////            currentPage = Integer.parseInt(pageParam);
////        }
////
////        List<PropertyDto> filteredProperties = null;
////        try {
////            filteredProperties = filterService.list(minPrice, maxPrice, propertyType, address, currentPage, recordsPerPage);
////            request.setAttribute("properties", filteredProperties);
////            request.setAttribute("currentPage", currentPage);
////            request.getRequestDispatcher("property-list.jsp").forward(request, response);
////        } catch (SQLException e) {
////            throw new RuntimeException("Lỗi khi lọc bất động sản", e);
////        }
////    }
//}
