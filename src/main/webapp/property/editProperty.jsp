<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ks.training.entity.Property" %>
<%@ page import="java.util.List" %>
<%
    String msg = (request.getAttribute("msg") + "");
    msg = msg.equals("null") ? "" : msg;
    Property property = (Property) request.getAttribute("property");
    int propertyId = property.getId();
    List<byte[]> images = (List<byte[]>) request.getAttribute("images");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa Bất động sản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Chỉnh sửa Bất động sản</h2>
    <div class="text-danger" id="msg"><%=msg%>
    </div>
    <form action="${pageContext.request.contextPath}/propertyMng" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="editSuccess">
        <input type="hidden" name="propertyId" value="<%= propertyId %>">
        <div class="form-group">
            <label>Tiêu đề:</label>
            <input type="text" name="title" class="form-control" value="<%= property.getTitle() %>" required>
        </div>
        <div class="form-group">
            <label>Mô tả:</label>
            <textarea name="description" class="form-control" rows="3"><%= property.getDescription() %></textarea>
        </div>
        <div class="form-group">
            <label>Giá:</label>
            <input type="number" name="price" class="form-control" value="<%= property.getPrice() %>" required>
        </div>
        <div class="form-group">
            <label>Địa chỉ:</label>
            <input type="text" name="address" class="form-control" value="<%= property.getAddress() %>" required>
        </div>
        <div class="form-group">
            <label>Loại hình:</label>
            <select name="property_type" class="form-control">
                <option value="Căn hộ" <%= property.getPropertyType().equals("Căn hộ") ? "selected" : "" %>>Căn hộ
                </option>
                <option value="Nhà riêng" <%= property.getPropertyType().equals("Nhà riêng") ? "selected" : "" %>>Nhà
                    riêng
                </option>
                <option value="Đất nền" <%= property.getPropertyType().equals("Đất nền") ? "selected" : "" %>>Đất nền
                </option>
                <option value="Khác" <%= property.getPropertyType().equals("Khác") ? "selected" : "" %>>Khác</option>
            </select>
        </div>
        <div class="form-group">
            <label>Diện tích:</label>
            <input type="text" name="acreage" class="form-control" value="<%= property.getAcreage() %>" required>
        </div>
        <div class="form-group">
            <label>Hình ảnh hiện tại:</label><br>
            <% if (images != null && !images.isEmpty()) { %>
            <div id="propertyCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <% for (int i = 0; i < images.size(); i++) { %>
                    <div class="carousel-item <%= i == 0 ? "active" : "" %>">
                        <img src="<%= request.getContextPath() %>/ImageServlet?propertyId=<%= propertyId %>&imageIndex=<%= i %>"
                             class="d-block w-100" alt="<%=property.getTitle()%>">
                    </div>
                    <% } %>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#propertyCarousel"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#propertyCarousel"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            <% } %>
        </div>
        <div class="form-group">
            <label>Chọn ảnh mới (nếu cần thay đổi):</label>
            <input type="file" name="images" class="form-control" multiple>
        </div>
        <button type="submit" class="btn btn-success">Cập nhật</button>
        <a href="home" class="btn btn-secondary">Hủy</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
