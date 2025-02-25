package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dao.UserDao;
import ks.training.entity.User;
import ks.training.service.UserService;

import java.io.IOException;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("login")) {
            login(request, response);
        } else if (action.equals("logout")) {
            logout(request, response);
        }else if (action.equals("register")) {
            register(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User result = userService.selectByEmailAndPassWord(user);
        String url = "";
        if (result != null) {
            HttpSession session = request.getSession();
            session.setAttribute("User", result);
            url = "/index.jsp";
        }else {
            request.setAttribute("error","Email hoặc mật khẩu không chính xác");
            url = "/login.jsp";
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request,response);
    }
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
    }
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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

        if (msgError.length() > 0) {
            url = "/register.jsp";
        } else {
            userService.register(email, password, fullName, phone, address);
            url = "/views/thanhcong.jsp";
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request,response);
    }
}
