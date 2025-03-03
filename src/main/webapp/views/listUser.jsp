<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member Manager</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }

        .container {
            margin-top: 30px;
        }

        /* Tạo style cho khối tìm kiếm và Add User */
        .search-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .search-container form {
            display: flex;
            align-items: center;
            width: 70%;
            /* Điều chỉnh chiều rộng cho form */
        }

        .search-container select,
        .search-container input,
        .search-container button {
            margin-right: 10px;
        }

        .search-container select,
        .search-container input {
            width: auto;
            padding: 8px 12px;
            font-size: 14px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        .search-container button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 12px;
            font-size: 14px;
            border-radius: 4px;
            cursor: pointer;
        }

        .search-container button:hover {
            background-color: #0056b3;
        }

        /* Style cho nút Add User */
        .btn-add {
            padding: 8px 16px;
            margin-left: 5px;
            font-size: 16px;
        }
    </style>
</head>
<%
    HttpSession sessionUser = request.getSession(false);
    Object obj = session.getAttribute("User");
    if (sessionUser == null || obj == null) {
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        return;
    }
%>
<body>

<% if (session.getAttribute("errorMessage") !=null) { %>
<div class="alert alert-danger">
    <%=session.getAttribute("errorMessage")%>
</div>
<%session.removeAttribute("errorMessage");}%>

<div class="container">
    <h1 class="text-center mb-4">Member List</h1>

    <!-- Nút Add User và Form tìm kiếm -->
    <div class="search-container">

        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-add">Home</a>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-warning btn-add">Logout</a>
    </div>

    <!-- Bảng hiển thị danh sách người dùng -->
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">User Name</th>
            <th scope="col">Email</th>
            <th scope="col">Phone</th>
            <th scope="col">Address</th>
            <th scope="col">Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${listMember}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.userName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.address}</td>
                <td>${user.role}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Phân trang -->
<div class="mt-3">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}"><a class="page-link"
                                                                             href="${pageContext.request.contextPath}/user?page=${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>

<!-- Thêm Bootstrap JS và Popper.js -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>