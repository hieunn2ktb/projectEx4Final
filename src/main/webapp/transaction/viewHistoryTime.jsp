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

    <!-- Bootstrap CSS -->
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
        <button type="button" class="btn btn-secondary" onclick="history.back();">Quay lại</button>
    </table>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
