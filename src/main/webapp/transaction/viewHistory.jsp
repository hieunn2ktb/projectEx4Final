<%@ page import="java.util.List" %>
<%@ page import="ks.training.dto.TransactionResponseDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ks.training.entity.User" %>
<%@ page import="ks.training.service.CustomerActivityService" %>
<%@ page import="ks.training.dto.HistoryViewDto" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Giao dịch</title>
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .pagination a {
            padding: 8px 16px;
            margin: 0 4px;
            border: 1px solid #ddd;
            text-decoration: none;
            color: black;
            background-color: white;
        }

        .pagination a.active {
            font-weight: bold;
            background-color: lightgray;
        }

        .pagination a:hover {
            background-color: #ddd;
        }

    </style>
</head>
<%
    HttpSession sessionUser = request.getSession(false);
    Object obj = session.getAttribute("User");
    User user = null;
    if (sessionUser == null || obj == null) {
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        return;
    }
    CustomerActivityService customerActivityService = new CustomerActivityService();
    List<HistoryViewDto> viewCount = customerActivityService.countViewHistory();
    if (viewCount == null) {
        viewCount = new ArrayList<>();
    }
%>
<body>
<div class="container mt-4">
    <h2 class="text-center mb-4">Lịch sử khách hàng xem Bất động sản</h2>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>Khách hàng</th>
            <th>Bất động sản</th>
            <th>Số điện thoại</th>
            <th>Giá</th>
            <th>Số lần xem</th>
        </tr>
        </thead>
        <tbody>
        <%for(HistoryViewDto hv : viewCount){ %>
        <tr>
            <td><%= hv.getCustomerName() %></td>
            <td><%= hv.getTitleProperty() %></td>
            <td><%= hv.getPhone() %></td>
            <td><%= hv.getPrice() %></td>
            <td><%= hv.getCountView() %></td>
        </tr>
        <%
            }
            if (viewCount.isEmpty()){
        %>
        <tr>
            <td colspan="7" class="text-center text-muted">Không có giao dịch nào</td>
        </tr>
        <% } %>
        </tbody>
    </table>


</div>

<!-- Bootstrap 5 JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
