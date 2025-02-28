<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<%
    HttpSession sessionUser = request.getSession(false);
    if (sessionUser == null || sessionUser.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int propertyId = Integer.parseInt(request.getParameter("id"));
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/realestate", "root", "password");
    String sql = "SELECT * FROM properties WHERE id=?";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setInt(1, propertyId);
    ResultSet rs = stmt.executeQuery();

    if (!rs.next()) {
        response.sendRedirect("propertyList.jsp?error=NotFound");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa Bất động sản</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Chỉnh sửa Bất động sản</h2>
    <form action="EditPropertyServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= rs.getInt("id") %>">

        <div class="form-group">
            <label>Tiêu đề:</label>
            <input type="text" name="title" class="form-control" value="<%= rs.getString("title") %>" required>
        </div>
        <div class="form-group">
            <label>Mô tả:</label>
            <textarea name="description" class="form-control" rows="3"><%= rs.getString("description") %></textarea>
        </div>
        <div class="form-group">
            <label>Giá:</label>
            <input type="number" name="price" class="form-control" value="<%= rs.getDouble("price") %>" required>
        </div>
        <div class="form-group">
            <label>Địa chỉ:</label>
            <input type="text" name="address" class="form-control" value="<%= rs.getString("address") %>" required>
        </div>
        <div class="form-group">
            <label>Loại hình:</label>
            <select name="property_type" class="form-control">
                <option value="Căn hộ" <%= rs.getString("property_type").equals("Căn hộ") ? "selected" : "" %>>Căn hộ</option>
                <option value="Nhà riêng" <%= rs.getString("property_type").equals("Nhà riêng") ? "selected" : "" %>>Nhà riêng</option>
                <option value="Đất nền" <%= rs.getString("property_type").equals("Đất nền") ? "selected" : "" %>>Đất nền</option>
                <option value="Khác" <%= rs.getString("property_type").equals("Khác") ? "selected" : "" %>>Khác</option>
            </select>
        </div>
        <div class="form-group">
            <label>Hình ảnh hiện tại:</label><br>
            <img src="<%= rs.getString("image_url") %>" width="150">
        </div>
        <div class="form-group">
            <label>Chọn ảnh mới (nếu cần thay đổi):</label>
            <input type="file" name="image" class="form-control">
        </div>
        <button type="submit" class="btn btn-success">Cập nhật</button>
        <a href="propertyList.jsp" class="btn btn-secondary">Hủy</a>
    </form>
</div>
</body>
</html>
<%
    conn.close();
%>
