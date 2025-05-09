<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ks.training.dto.HistoryViewDto" %>
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
<body>
<div class="container mt-4">
    <h2 class="text-center mb-4">Lịch sử khách hàng xem Bất động sản</h2>
    <a type="button" class="btn btn-secondary" href="home">Quay lại</a>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>Khách hàng</th>
            <th>Bất động sản</th>
            <th>Số điện thoại</th>
            <th>Giá</th>
            <th>Số lần xem</th>
            <th>Chi tiết thời gian khách hàng xem</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<HistoryViewDto> viewCount = (List<HistoryViewDto>) request.getAttribute("viewCount");
            if (viewCount != null && !viewCount.isEmpty()) {
                for (HistoryViewDto hv : viewCount) {
        %>
        <tr>
            <td><%= hv.getCustomerName() %></td>
            <td><%= hv.getTitleProperty() %></td>
            <td><%= hv.getPhone() %></td>
            <td><%= hv.getPrice() %></td>
            <td><%= hv.getCountView() %></td>
            <td>
                <form action="transaction" method="post">
                    <input type="hidden" name="action" value="detailHistory">
                    <input type="hidden" name="customerId" value="<%=hv.getUserId()%>">
                    <input type="hidden" name="propertyId" value="<%=hv.getPropertyId()%>">
                    <button type="submit" class="btn btn-primary">Xem</button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5" class="text-center text-muted">Không có giao dịch nào</td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
        Integer currentPage = (Integer) request.getAttribute("currentPage");
        Integer totalPages = (Integer) request.getAttribute("totalPages");

        if (currentPage == null) currentPage = 1;
        if (totalPages == null) totalPages = 1;
    %>
    <div class="container mt-3">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="transaction?action=viewHistory&page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo; Trang trước</span>
                        </a>
                    </li>
                </c:if>

                <li class="page-item disabled">
                    <span class="page-link">Trang ${currentPage} / ${totalPages}</span>
                </li>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="transaction?action=viewHistory&page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">Trang sau &raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
