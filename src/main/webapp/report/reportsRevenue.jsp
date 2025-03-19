<%@ page import="ks.training.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Báo cáo doanh thu</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6"></div>
        <div class="col-md-6">
            <div class="card shadow-lg">
                <div class="card-header bg-primary text-white text-center">
                    <h4>Báo cáo giao dịch theo tháng</h4>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/report" method="post">
                        <input type="hidden" name="action" value="reportsRevenue">
                        <div class="mb-3">
                            <label for="month" class="form-label">Chọn tháng:</label>
                            <select class="form-select" name="month" id="month">
                                <option value="">Tất cả các tháng</option>
                                <% for (int i = 1; i <= 12; i++) { %>
                                <option value="<%= i %>"><%= i %>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="year" class="form-label">Chọn năm:</label>
                            <input type="number" class="form-control" name="year" id="year" min="2000" max="2100"
                                   required>
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-success w-100">Xem báo cáo</button>
                        </div>
                    </form>
                    <div class="d-flex justify-content-center mt-3">
                        <a href="home" class="btn btn-danger btn-lg px-4 shadow">
                             Quay lại
                        </a>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <% Long total = (Long) request.getAttribute("total"); %>
    <% if (total != null) { %>
    <div class="row justify-content-center mt-4">
        <div class="col-md-6">
            <div class="alert alert-info text-center">
                <strong>Tổng số Tiền giao dịch:</strong> <%= total / 100000000%> <strong>Tỷ</strong>
            </div>
        </div>
    </div>
    <% } %>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
