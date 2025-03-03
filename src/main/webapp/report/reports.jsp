<%@ page import="ks.training.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Báo cáo giao dịch</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        form {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
        }
        input, select, button {
            padding: 5px;
            margin: 5px 0;
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
%>
<body>

<h2>Báo cáo giao dịch theo tháng</h2>

<form action="${pageContext.request.contextPath}/report" method="GET">
    <label for="month">Chọn tháng:</label>
    <select name="month" id="month" required>
        <% for(int i = 1; i <= 12; i++) { %>
        <option value="<%= i %>"><%= i %></option>
        <% } %>
    </select>
    <label for="year">Chọn năm:</label>
    <input type="number" name="year" id="year" min="2000" max="2100" required>
    <button type="submit">Xem báo cáo</button>
</form>

<% Integer transactionCount = (Integer) request.getAttribute("transactionCount"); %>
<% if (transactionCount != null) { %>
<p><strong>Tổng số giao dịch:</strong> <%= transactionCount %></p>
<% } %>

</body>
</html>
