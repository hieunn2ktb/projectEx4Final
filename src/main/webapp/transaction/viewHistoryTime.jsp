<%@ page import="ks.training.entity.CustomerActivity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách hoạt động khách hàng</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%
    List<CustomerActivity> list = (List<CustomerActivity>) request.getAttribute("customerActivityList");
    if (list == null) {
        list = new ArrayList<>();
    }
%>

<div class="container mt-4">
    <h2 class="text-center">Lịch sử xem bất động sản</h2>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Mã khách hàng</th>
            <th>Mã bất động sản</th>
            <th>Thời gian xem</th>
        </tr>
        </thead>
        <tbody>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            for (CustomerActivity activity : list) {
                LocalDateTime viewedAt = activity.getViewedAt();
                Date viewedAtDate = null;

                if (viewedAt != null) {
                    viewedAtDate = Date.from(viewedAt.atZone(ZoneId.systemDefault()).toInstant());
                }
        %>
        <tr>
            <td><%= activity.getId() %></td>
            <td><%= activity.getCustomerId() %></td>
            <td><%= activity.getPropertyId() %></td>
            <td><%= (viewedAtDate != null) ? sdf.format(viewedAtDate) : "N/A" %></td>
        </tr>
        <%
            }
        %>
        </tbody>
        <a type="button" class="btn btn-secondary" href="transaction?action=viewHistory">Quay lại</a>
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
                        <a class="page-link" href="transaction?action=detailHistory&page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo; Trang trước</span>
                        </a>
                    </li>
                </c:if>

                <li class="page-item disabled">
                    <span class="page-link">Trang ${currentPage} / ${totalPages}</span>
                </li>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="transaction?action=detailHistory&page=${currentPage + 1}" aria-label="Next">
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
