package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.dao.UserDao;

import java.io.IOException;

@WebServlet("/do-Register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserDao userDao = new UserDao();
        if (userDao.checkEmailUser(email)) {
            msgError += "email đã tồn tại trong hệ thống, vui lòng nhập email khác.<br/>";
        }

        if (!password.equals(rePassword)) {
            msgError += "Mật khẩu và nhập lại mật khẩu không giống nhau.<br/>";
        }

        if (msgError.length() > 0) {
            url = "/views/register.jsp";
        } else {
            userDao.register(email, password, fullName, phone, address);
             url = "/views/thanhcong.jsp";
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
        rd.forward(request,response);
    }
}
