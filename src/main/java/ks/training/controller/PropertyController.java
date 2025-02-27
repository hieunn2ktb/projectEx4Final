package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dto.PropertyDto;
import ks.training.service.PropertyService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/property")
public class PropertyController extends HttpServlet {
    private PropertyService propertyService;

    public PropertyController() {
        this.propertyService = new PropertyService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("delete")) {
            deleteProperty(req, resp);
        } else if (action.equals("list")) {

        }else if (action.equals("register")) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void deleteProperty(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        int propertyId = Integer.parseInt(request.getParameter("id"));
        String msgDelete = "";
        HttpSession session = request.getSession();
        if (propertyService.checkTransactionSQL(propertyId)) {
            msgDelete += "bất động sản đã có giao dịch không thể xoá";
            session.setAttribute("deleteMessage", msgDelete);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request,resp);
            return;
        }
        int rowsAffected = propertyService.deleteProperty(propertyId);
        if (rowsAffected > 0) {
            msgDelete += "đã xoá bất động sản";
            session.setAttribute("deleteMessage", msgDelete);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request,resp);
    }


}
