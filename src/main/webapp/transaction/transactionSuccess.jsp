<%@ page import="ks.training.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Giao dịch thành công</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
        .success { color: green; font-size: 20px; }
        a { display: block; margin-top: 20px; text-decoration: none; color: blue; }
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
%>
<body>
<p class="success">🎉 Giao dịch đã được tạo thành công!</p>
<a href="home">Quay lại Trang chủ</a>
</body>
</html>
