package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dao.ReportDAO;
import ks.training.entity.User;

import java.io.IOException;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private ReportDAO reportDAO = new ReportDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/report/reports.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessionUser = request.getSession(false);
        Object obj = sessionUser.getAttribute("User");
        User user = null;
        if (sessionUser == null || obj == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        int transactionCount = reportDAO.getTransactionCountByMonth(month, year);

        request.setAttribute("transactionCount", transactionCount);
        request.getRequestDispatcher("report/reports.jsp").forward(request, response);
    }
}
