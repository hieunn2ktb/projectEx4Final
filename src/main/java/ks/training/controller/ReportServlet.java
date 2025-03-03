package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.dao.ReportDAO;

import java.io.IOException;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private ReportDAO reportDAO = new ReportDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        int transactionCount = reportDAO.getTransactionCountByMonth(month, year);

        request.setAttribute("transactionCount", transactionCount);
        request.getRequestDispatcher("report/reports.jsp").forward(request, response);
    }
}
