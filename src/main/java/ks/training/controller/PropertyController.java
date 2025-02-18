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

@WebServlet("/properties")
public class PropertyController extends HttpServlet {
    private PropertyDao propertyDao;

    @Override
    public void init() {
        propertyDao = new PropertyDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<PropertyDto> properties = propertyDao.findAll();
            request.setAttribute("properties", properties);
            request.getRequestDispatcher("property-list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn dữ liệu", e);
        }
    }
}
