<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .card {
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .btn-success:hover {
            background-color: #218838;
        }

        .container {
            height: 100vh;
        }
    </style>
</head>

<body>
<div class="container d-flex justify-content-center align-items-center">
    <div class="card p-4" style="width: 100%; max-width: 500px;">
        <h2 class="text-center text-primary mb-4">Edit User</h2>
        <!-- Form chỉnh sửa thông tin người dùng -->
        <form action="${pageContext.request.contextPath}/user/edit/${user.userId}" method="post">
            <!-- Thêm id của user vào trong form -->
            <input type="hidden" name="id" value="${user.userId}" />

            <div class="form-group">
                <label for="username">User name:</label> <input type="text" class="form-control" id="username"
                                                                name="username" value="${user.userName}" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label> <input type="email" class="form-control" id="email" name="email"
                                                         value="${user.email}" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label> <input type="password" class="form-control" id="password"
                                                               name="password" value="${user.password}" required>
            </div>

            <div class="form-group">
                <label for="phone">Phone:</label> <input type="text" class="form-control" id="phone" name="phone"
                                                         value="${user.phone}" required>
            </div>

            <div class="form-group">
                <label for="address">Address:</label> <input type="text" class="form-control" id="address"
                                                             name="address" value="${user.address}" required>
            </div>

            <div class="form-group">
                <label for="role">Role:</label> <select id="role" name="role" class="form-control">
                <!-- Lặp qua danh sách Role -->
                <c:forEach var="role" items="${roles}">
                    <option value="${role}" <c:if test="${user.role == role}">selected</c:if>>
                            ${role}</option>
                </c:forEach>
            </select>
            </div>

            <button type="submit" class="btn btn-success">Save Change</button>
            <a href="${pageContext.request.contextPath}/user" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</div>

<!-- Thêm Bootstrap JS và Popper.js từ CDN -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>