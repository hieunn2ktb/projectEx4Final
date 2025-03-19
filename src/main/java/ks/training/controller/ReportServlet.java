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
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        if (action.equals("reportCount")) {
            request.getRequestDispatcher("/report/reports.jsp").forward(request, response);
        }
        if (action.equals("reportsRevenue")) {
            request.getRequestDispatcher("/report/reportsRevenue.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        if (action.equals("reportCount")) {
            reportCountTransaction(request, response);
        }
        if (action.equals("reportsRevenue")) {
            reportRevenue(request, response);
        }
    }

    private void reportRevenue(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession sessionUser = request.getSession(false);
        Object obj = sessionUser.getAttribute("User");
        User user = null;
        if (sessionUser == null || obj == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        String monthParam = request.getParameter("month");
        Integer month = (monthParam != null && !monthParam.isEmpty()) ? Integer.parseInt(monthParam) : null;

        int year = Integer.parseInt(request.getParameter("year"));
        long total = reportDAO.getTotalRevenueByMonth(month,year);
        request.setAttribute("total", total);
        request.getRequestDispatcher("report/reportsRevenue.jsp").forward(request, response);
    }

    private void reportCountTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession sessionUser = request.getSession(false);
        Object obj = sessionUser.getAttribute("User");
        User user = null;
        if (sessionUser == null || obj == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        String monthParam = request.getParameter("month");
        Integer month = (monthParam != null && !monthParam.isEmpty()) ? Integer.parseInt(monthParam) : null;

        int year = Integer.parseInt(request.getParameter("year"));
        String type = request.getParameter("status");
        int transactionCount = reportDAO.getTransactionCountByMonth(month, year, type);

        request.setAttribute("transactionCount", transactionCount);
        request.getRequestDispatcher("report/reports.jsp").forward(request, response);
    }
}
