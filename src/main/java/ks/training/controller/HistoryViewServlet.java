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

        List<HistoryViewDto> viewCount = customerActivityService.countViewHistory();
        request.setAttribute("viewCount", viewCount);
        request.getRequestDispatcher("/transaction/history-view.jsp").forward(request, response);
    }
}
