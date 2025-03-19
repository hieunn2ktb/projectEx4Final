package ks.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks.training.dao.PropertyDao;
import ks.training.dto.HistoryViewDto;
import ks.training.dto.TransactionDto;
import ks.training.dto.TransactionResponseDto;
import ks.training.entity.CustomerActivity;
import ks.training.entity.Property;
import ks.training.entity.User;
import ks.training.service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

@WebServlet("/transaction")
public class TransactionController extends HttpServlet {
    private TransactionService transactionService;
    private CustomerActivityService customerActivityService;
    private UserService userService;
    private PropertyService propertyService;

    public TransactionController() {
        this.transactionService = new TransactionService();
        this.customerActivityService = new CustomerActivityService();
        this.userService = new UserService();
        this.propertyService = new PropertyService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }
        switch (action) {
            case "allTransaction":
                getAllTransactions(req, resp);
                break;
            case "updateStatus":
                updateStatusTransaction(req, resp);
                break;
            case "viewHistory":
                historyView(req, resp);
                break;
            case "detailHistory":
                detailHistory(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số action");
            return;
        }
        switch (action) {
            case "redirect":
                showConfirmPage(req, resp);
                break;
            case "confirm":
                confirmTransaction(req, resp);
                break;
            case "allTransaction":
                getAllTransactions(req, resp);
                break;
            case "updateStatus":
                updateStatusTransaction(req, resp);
                break;
            case "viewHistory":
                historyView(req, resp);
                break;
            case "userDetail":
                userDetail(req, resp);
                break;
            case "propertyDetail":
                propertyDetail(req, resp);
            case "detailHistory":
                detailHistory(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ");
                break;
        }
    }

    private void detailHistory(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int recordsPerPage = 5;
        int currentPage = 1;

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                int userId = Integer.parseInt(request.getParameter("customerId"));
                int propertyId = Integer.parseInt(request.getParameter("propertyId"));

                session.setAttribute("userId", userId);
                session.setAttribute("propertyId", propertyId);
            } catch (NumberFormatException e) {
                resp.sendRedirect("error.jsp?message=Invalid+ID");
                return;
            }
        }

        Integer userId = (Integer) session.getAttribute("userId");
        Integer propertyId = (Integer) session.getAttribute("propertyId");

        if (userId == null || propertyId == null) {
            resp.sendRedirect("error.jsp?message=Session+Expired");
            return;
        }

        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
        }
        if (currentPage < 1) currentPage = 1;

        int totalRecords = customerActivityService.countCustomer(userId, propertyId);
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        totalPages = Math.max(totalPages, 1);
        if (currentPage > totalPages) currentPage = totalPages;

        List<CustomerActivity> customerActivityList = customerActivityService.customerActivities(userId, propertyId, currentPage, recordsPerPage);

        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("customerActivityList", customerActivityList);

        request.getRequestDispatcher("transaction/viewHistoryTime.jsp").forward(request, resp);
    }


    private void showConfirmPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int propertyId = Integer.parseInt(req.getParameter("propertyId"));
        int buyerId = Integer.parseInt(req.getParameter("buyerId"));

        String transactionType = req.getParameter("transactionType");
        req.setAttribute("msg", "");
        TransactionDto transactionDto = transactionService.getPropertyById(propertyId);
        req.setAttribute("transactionDto", transactionDto);
        req.setAttribute("transactionType", transactionType);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(transactionDto.getPrice());
        req.setAttribute("formattedPrice", formattedPrice);

        req.getRequestDispatcher("/transaction/confirmTransaction.jsp").forward(req, resp);
    }

    private void confirmTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("User") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        User user = (User) session.getAttribute("User");

        int propertyId = Integer.parseInt(request.getParameter("propertyId"));
        int buyerId = Integer.parseInt(request.getParameter("buyerId"));

        String transactionType = request.getParameter("transactionType");


        TransactionDto transactionDto = transactionService.getPropertyById(propertyId);
        request.setAttribute("transactionDto", transactionDto);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(transactionDto.getPrice());
        request.setAttribute("formattedPrice", formattedPrice);
        int sellerId = transactionDto.getSellerId();
        boolean check = transactionService.checkTransaction(propertyId);
        String msg = "";
        String url = "";
        if (!check) {
            transactionService.processTransaction(propertyId, buyerId, sellerId, transactionType);
            url = "transaction/transactionSuccess.jsp";
        } else {
            msg = "Bất động sản đã có giao dịch vui lòng chọn bất động sản khác ";
            url = "transaction/confirmTransaction.jsp";
        }
        request.setAttribute("msg", msg);
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }

    private void getAllTransactions(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object obj = session.getAttribute("User");
        User user = null;
        if (session == null || obj == null) {
            resp.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        int recordsPerPage = 10;
        int currentPage = 1;
        String status = request.getParameter("status");
        String buyerEmail = request.getParameter("email");
        System.out.println(buyerEmail);
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String buyerName = request.getParameter("buyerName");

        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) {
                currentPage = 1;
            }
        }

        try {
            int totalRecords = transactionService.countTransaction(status, buyerEmail, startDate, endDate);
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            if (totalPages == 0) {
                totalPages = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }
            List<TransactionResponseDto> transactions = transactionService.findTransactionByPage(status, buyerEmail, startDate, endDate, currentPage, recordsPerPage);
            System.out.println("danh sach transaction: ");
            transactions.forEach(System.out::println);
            System.out.println("het ");
            request.setAttribute("transactions", transactions);
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
            getAllTransactions(request, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }

    private void historyView(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("User") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        int recordsPerPage = 5;
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
            if (currentPage < 1) currentPage = 1;
        }
        int totalRecords = customerActivityService.getTotalPages();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        if (totalPages == 0) totalPages = 1;
        if (currentPage > totalPages) currentPage = totalPages;


        List<HistoryViewDto> listView = customerActivityService.ListViewHistory(currentPage, recordsPerPage);

        request.setAttribute("viewCount", listView);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/transaction/historyView.jsp").forward(request, response);
    }

    private void userDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userService.userDetail(userId);
        request.setAttribute("userDetail", user);
        request.getRequestDispatcher("user/detailUser.jsp").forward(request, response);
    }

    private void propertyDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("User") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }
        int id = Integer.parseInt(request.getParameter("transactionId"));
        TransactionResponseDto transaction = transactionService.getTransactionById(id);

        String propertyIdStr = request.getParameter("propertyId");
        int propertyId = 0;
        try {
            if (propertyIdStr != null) {
                propertyId = Integer.parseInt(propertyIdStr);
            }
        } catch (NumberFormatException e) {
            return;
        }

        Property property = propertyService.findPropertyById(propertyId);
        PropertyDao propertyDao = new PropertyDao();
        List<String> images = propertyDao.getImagesByPropertyId(propertyId);

        customerActivityService.logCustomerActivity(user.getId(), propertyId);

        request.setAttribute("property", property);
        request.setAttribute("images", images);
        request.setAttribute("user", user);
        request.setAttribute("transaction", transaction);

        request.getRequestDispatcher("/property/PropertyDetail.jsp").forward(request, response);
    }
}
