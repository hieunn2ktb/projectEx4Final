package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks.training.dto.TransactionResponseDto;
import ks.training.service.EmailService;
import ks.training.service.TransactionService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/transaction")
public class TransactionController extends HttpServlet {
    private TransactionService transactionService;

    public TransactionController() {
        this.transactionService = new TransactionService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }
        switch (action) {
            case "confirm":
                confirmTransaction(req, resp);
                break;
            case "allTransaction":
                getAllTransactions(req, resp);
                break;
            case "updateStatus":
                updateStatusTransaction(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        doGet(request, resp);
    }

    private void confirmTransaction(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));
        int sellerId = Integer.parseInt(request.getParameter("sellerId"));
        String transactionType = request.getParameter("transactionType");
        boolean check = transactionService.checkTransaction(propertyId);
        String msg = "";
        String url = "";
        if (!check) {
            transactionService.processTransaction(propertyId, buyerId, sellerId, transactionType);
            url = "transaction/transaction-success.jsp";
        }else {
            msg = "Bất động sản đã có giao dịch vui lòng chọn bất động sản khác ";
            url = "transaction/confirm-transaction.jsp";
        }
        request.setAttribute("msg", msg);
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, resp);
    }
    private void getAllTransactions(HttpServletRequest request, HttpServletResponse resp) throws ServletException {
        int recordsPerPage = 10;
        int currentPage = 1;
        String status = request.getParameter("status");
        String buyerName = request.getParameter("buyerName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) {
                currentPage = 1;
            }
        }

        try {
            int totalRecords = transactionService.countTransaction(status, buyerName, startDate, endDate);
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            if (totalPages == 0) {
                totalPages = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }
            List<TransactionResponseDto> properties = transactionService.findTransactionByPage(status, buyerName, startDate, endDate, currentPage, recordsPerPage);
            request.setAttribute("properties", properties);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("status", status);
            request.setAttribute("buyerName", buyerName);

            request.getRequestDispatcher("transaction/transactions.jsp").forward(request, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void updateStatusTransaction(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        int transactionId = Integer.parseInt(request.getParameter("transaction_id"));
        String newStatus = request.getParameter("status");
        try {
          boolean updated = transactionService.updateTransactionStatus(transactionId, newStatus);
            if (updated) {
                String buyerEmail = transactionService.getBuyerEmail(transactionId);
                if (buyerEmail != null) {
                    EmailService.sendEmail(buyerEmail, "Cập nhật giao dịch", "Trạng thái giao dịch của bạn đã thay đổi thành: " + newStatus);
                }
            }
            getAllTransactions(request,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }


}
