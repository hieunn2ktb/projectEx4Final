<%@ page import="java.util.List" %>
<%@ page import="ks.training.dto.TransactionResponseDto" %>
<%@ page import="java.util.ArrayList" %>
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

  String status = request.getParameter("status") != null ? request.getParameter("status") : "";
  String buyerName = request.getParameter("buyerName") != null ? request.getParameter("buyerName") : "";
  String startDate = request.getParameter("startDate") != null ? request.getParameter("startDate") : "";
  String endDate = request.getParameter("endDate") != null ? request.getParameter("endDate") : "";

  Integer currentPage = (Integer) request.getAttribute("currentPage");
  Integer totalPages = (Integer) request.getAttribute("totalPages");

  if (currentPage == null) currentPage = 1;
  if (totalPages == null) totalPages = 1;
%>
<body>
<div class="container mt-4">
  <h2 class="text-center mb-4">Danh sách giao dịch</h2>
  <form method="get" action="transaction" class="row g-3">
    <input type="hidden" name="action" value="allTransaction">
    <div class="col-md-3">
      <label for="status" class="form-label">Trạng thái</label>
      <select id="status" name="status" class="form-select">
        <option value="">Tất cả</option>
        <option value="Đang xử lý" ${status == "Đang xử lý" ? "selected" : ""}>Đang xử lý</option>
        <option value="Đã hoàn thành" ${status == "Đã hoàn thành" ? "selected" : ""}>Đã hoàn thành</option>
      </select>
    </div>
    <div class="col-md-3">
      <label for="email" class="form-label">Email KH</label>
      <input type="email" id="email" name="email" class="form-control" value="${buyerEmail}">
    </div>
    <div class="col-md-3">
      <label for="startDate" class="form-label">Từ ngày</label>
      <input type="date" id="startDate" name="startDate" class="form-control" value="${startDate}">
    </div>
    <div class="col-md-3">
      <label for="endDate" class="form-label">Đến ngày</label>
      <input type="date" id="endDate" name="endDate" class="form-control" value="${endDate}">
    </div>
    <div class="col-12">
      <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    </div>
    <div col-12>
      <a class="btn btn-danger mt-3" href="home">
        <i class="bi bi-arrow-left-circle"></i> Quay về trang chủ
      </a>
    </div>
  </form>

  <table class="table table-bordered table-striped">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Khách hàng</th>
      <th>Bất động sản</th>
      <th>Loại giao dịch</th>
      <th>Trạng thái</th>
      <th>Thời gian tạo</th>
      <th>Cập nhật</th>
      <th>Chi Tiết</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<TransactionResponseDto> transactions = (List<TransactionResponseDto>) request.getAttribute("transactions");
      if (transactions == null || transactions.isEmpty()) transactions = new ArrayList<>();
      if (!transactions.isEmpty()) {
        for (TransactionResponseDto transaction : transactions) {
    %>
    <tr>
      <td><%= transaction.getId() %></td>
      <td><%= transaction.getBuyerName() %></td>
      <td><%= transaction.getPropertyName() %></td>
      <td><%= transaction.getTransactionType() %></td>
      <td>
        <span class="badge <%= transaction.getStatus().equals("Đã hoàn thành") ? "bg-success" : "bg-warning" %>">
          <%= transaction.getStatus() %>
        </span>
      </td>
      <td><%= transaction.getCreatedAt() %></td>
      <td>
        <form method="post" action="transaction">
          <input type="hidden" name="transaction_id" value="<%= transaction.getId() %>">
          <input type="hidden" name="action" value="updateStatus">
          <div class="input-group">
            <select name="status" class="form-select">
              <option value="Đang xử lý" <%= transaction.getStatus().equals("Đang xử lý") ? "selected" : "" %>>Đang xử lý</option>
              <option value="Đã hoàn thành" <%= transaction.getStatus().equals("Đã hoàn thành") ? "disabled" : "" %>>Đã hoàn thành</option>
            </select>
            <button type="submit" class="btn btn-primary" <%= transaction.getStatus().equals("Đã hoàn thành") ? "disabled" : "" %>>Cập nhật</button>
          </div>
        </form>
      </td>
      <td>
        <form method="post" action="transaction">
          <input type="hidden" name="propertyId" value="<%= transaction.getPropertyId() %>">
          <input type="hidden" name="transactionId" value="<%= transaction.getId() %>">
          <input type="hidden" name="action" value="propertyDetail">
          <button type="submit" class="btn btn-primary">Chi Tiết Bất Động Sản</button>
        </form>
        <br>
        <form method="post" action="transaction">
          <input type="hidden" name="userId" value="<%= transaction.getUserId() %>">
          <input type="hidden" name="action" value="userDetail">
          <button type="submit" class="btn btn-primary">Chi Tiết Khách Hàng</button>
        </form>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="7" class="text-center text-muted">Không có giao dịch nào</td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <div class="pagination">
    <% if ((Integer) request.getAttribute("currentPage") > 1) { %>
    <a href="?page=<%= (Integer) request.getAttribute("currentPage") - 1 %>&status=<%= status %>&buyerName=<%= buyerName %>&startDate=<%= startDate %>&endDate=<%= endDate %>">&laquo; Trước</a>
    <% } %>

    <% for (int i = 1; i <= (Integer) request.getAttribute("totalPages"); i++) { %>
    <a href="?page=<%= i %>&status=<%= status %>&buyerName=<%= buyerName %>&startDate=<%= startDate %>&endDate=<%= endDate %>"
       class="<%= i == (Integer) request.getAttribute("currentPage") ? "active" : "" %>">
      <%= i %>
    </a>
    <% } %>

    <% if ((Integer) request.getAttribute("currentPage") < (Integer) request.getAttribute("totalPages")) { %>
    <a href="?page=<%= (Integer) request.getAttribute("currentPage") + 1 %>&status=<%= status %>&buyerName=<%= buyerName %>&startDate=<%= startDate %>&endDate=<%= endDate %>">Tiếp &raquo;</a>
    <% } %>
  </div>

</div>

<!-- Bootstrap 5 JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
