package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dto.UserDto;
import ks.training.service.PropertyService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private PropertyService propertyService;

    @Override
    public void init() {
        propertyService = new PropertyService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDto user = null;
        try {
            user = propertyService.validateUser(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user != null && "Employee".equals(user.getRole()) && "Admin".equals(user.getRole())) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            response.sendRedirect("/WEB-INF/views/propertyEmployee.jsp");
        }else if (user != null && "Customer".equals(user.getRole())){
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            response.sendRedirect("/WEB-INF/views/propertyCustomer.jsp");
        }
        else {
            request.setAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
