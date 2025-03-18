package ks.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dto.HistoryViewDto;
import ks.training.entity.User;
import ks.training.service.CustomerActivityService;


import java.io.IOException;
import java.util.List;

@WebServlet("/history-view")
public class HistoryViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CustomerActivityService customerActivityService = new CustomerActivityService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("User") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }


        List<HistoryViewDto> listView = customerActivityService.ListViewHistory(page, recordsPerPage);
        int totalPages = customerActivityService.getTotalPages();

        request.setAttribute("viewCount", listView);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/transaction/historyView.jsp").forward(request, response);
    }

}
