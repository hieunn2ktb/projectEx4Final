<%@ page import="java.util.List" %>
<%@ page import="ks.training.dto.TransactionResponseDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ks.training.dto.UserDto" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý Giao dịch</title>
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

    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");

    if (currentPage == null) currentPage = 1;
    if (totalPages == null) totalPages = 1;
%>
<body>
<div class="container mt-4">
    <h2 class="text-center mb-4">Danh sách User</h2>
    <a type="button" class="btn btn-danger" href="home">Quay lại</a>
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<UserDto> userList = (List<UserDto>) request.getAttribute("listUser");
            if (userList == null || userList.isEmpty()) userList = new ArrayList<>();
            if (!userList.isEmpty()) {
                for (UserDto user : userList) {
        %>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getUsername() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getPhone() %></td>
            <td><%= user.getAddress() %></td>
            <td>
                <form method="post" action="user">
                    <input type="hidden" name="userId" value="<%= user.getId() %>">
                    <input type="hidden" name="action" value="updateRole">
                    <div class="input-group">
                        <select name="role" class="form-select">
                            <option value="3" <%= user.getRole().equals("Customer") ? "selected" : "" %>>Customer</option>
                            <option value="2" <%= user.getRole().equals("Employee") ? "selected" : "" %>>Employee</option>
                        </select>
                        <button type="submit" class="btn btn-success">Cập nhật</button>
                    </div>
                </form>
            </td>
        </tr>
        <%
            }
        }
        %>
        </tbody>
    </table>

    <div class="container mt-3">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="user?action=showAllUser&page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo; Trang trước</span>
                        </a>
                    </li>
                </c:if>

                <li class="page-item disabled">
                    <span class="page-link">Trang ${currentPage} / ${totalPages}</span>
                </li>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="user?action=showAllUser&page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">Trang sau &raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>


</div>

<!-- Bootstrap 5 JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
