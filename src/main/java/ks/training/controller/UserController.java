package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dao.CustomerActivityDao;
import ks.training.dto.PropertyDto;
import ks.training.dto.UserDto;
import ks.training.entity.Pagination;
import ks.training.entity.User;
import ks.training.service.CustomerActivityService;
import ks.training.service.PropertyService;
import ks.training.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private UserService userService;
    private PropertyService propertyService;

    public UserController() {
        this.userService = new UserService();
        this.propertyService = new PropertyService();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        switch (action) {
            case "login":
                login(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "register":
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                break;
            case "showAllUser":
                showListUser(request,response);
                break;
            case "editUser":
                request.getRequestDispatcher("/user/editUser.jsp").forward(request, response);
                break;
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        switch (action) {
            case "editUser":
                editUser(request, response);
                break;
            case "register":
                register(request, response);
                break;
            case "login":
                login(request, response);
                break;
            case "updateRole":
                updateRole(request, response);
                break;
        }
    }

    private void updateRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int newRole = Integer.parseInt(request.getParameter("role"));

        UserService userService = new UserService();
        boolean success = userService.updateUserRole(userId, newRole);

        if (success) {
            request.setAttribute("message", "Cập nhật role thành công!");
        } else {
            request.setAttribute("error", "Cập nhật role thất bại!");
        }
        response.sendRedirect("user?action=showAllUser");
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getAuthenticatedUser(request, response);
        if (user == null) return;
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("rePassword");

        request.setAttribute("fullName", fullName);
        request.setAttribute("address", address);
        request.setAttribute("phone", phone);

        String url = "";
        String msgError = "";

        request.setAttribute("msgError", msgError);
        boolean isUpdated = false;

        if (user != null) {
            user.setFullName(fullName);
            user.setAddress(address);
            user.setPhone(phone);
            if (password.equals(rePassword)) {
                isUpdated  = userService.updateUser(user.getId(), fullName, password, phone, address);
                if (isUpdated) {
                    request.getSession().setAttribute("User", user);
                    msgError = "Cập nhật thành công!";
                    request.setAttribute("msgError", msgError);
                    url = "/user/editUser.jsp";
                } else {
                    msgError = "Cập nhật không thành công!";
                    request.setAttribute("msgError", msgError);
                    url = "/user/editUser.jsp";
                }
            }
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);

    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.validateUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("User", user);

            switch (user.getRole()) {
                case "Admin":
                    response.sendRedirect("admin/dashboard.jsp");
                    break;
                case "Employee":
                case "Customer":
                    getAllList(request);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
        } else {
            request.setAttribute("error", "Email hoặc mật khẩu không chính xác");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/user/login.jsp");
            rd.forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("user/login.jsp");
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("psw");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String rePassword = request.getParameter("rePassword");

        request.setAttribute("email", email);
        request.setAttribute("fullName", fullName);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);

        String msgError = "";
        String url = "";
        if (userService.checkEmailUser(email)) {
            msgError += "email đã tồn tại trong hệ thống, vui lòng nhập email khác.<br/>";
        }

        if (!password.equals(rePassword)) {
            msgError += "Mật khẩu và nhập lại mật khẩu không giống nhau.<br/>";
        }
        request.setAttribute("error", msgError);

        if (!msgError.isEmpty()) {
            url = "/user/register.jsp";
        } else {
            userService.register(email, password, fullName, phone, address);
            url = "/views/thanhcong.jsp";
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void getAllList(HttpServletRequest request) throws ServletException {
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

        } catch (SQLException e) {
            throw new ServletException("Lỗi truy vấn dữ liệu");
        }
    }
    private User getAuthenticatedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("User") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return null;
        }
        return user;
    }
    private void showListUser(HttpServletRequest request, HttpServletResponse response){
        int currentPage = 1;

        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) currentPage = 1;
        }
        int totalRecords = userService.countUsers();
        Pagination pagination = new Pagination(currentPage, totalRecords);

        List<UserDto> list = userService.listUser(currentPage,pagination.getRecordsPerPage());
        request.setAttribute("listUser",list);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", pagination.getTotalPages());
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/user/listUserView.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
