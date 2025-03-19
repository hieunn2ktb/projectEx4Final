
<%@ page contentType="text/html; charset=UTF-8" %>

<%
    String msg = (request.getAttribute("msg") + "");
    msg = msg.equals("null") ? "" : msg;
    %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Bất động sản</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2 class="text-center">Thêm Bất động sản</h2>
    <div class="text-danger" id="msg"><%=msg%>
    </div>
    <form action="${pageContext.request.contextPath}/propertyMng" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="addProperty">
        <div class="form-group">
            <label>Tiêu đề:</label>
            <input type="text" name="title" class="form-control" required>
        </div>
        <div class="form-group">
            <label>Mô tả:</label>
            <textarea name="description" class="form-control" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label>Giá:</label>
            <input type="number" name="price" class="form-control" required>
        </div>
        <div class="form-group">
            <label>Địa chỉ:</label>
            <input type="text" name="address" class="form-control" required>
        </div>
        <div class="form-group">
            <label>Loại hình:</label>
            <select name="property_type" class="form-control">
                <option value="Căn hộ">Căn hộ</option>
                <option value="Nhà riêng">Nhà riêng</option>
                <option value="Đất nền">Đất nền</option>
                <option value="Khác">Khác</option>
            </select>
        </div>
        <div class="form-group">
            <label>Diện tích:</label>
            <input type="text" name="acreage" class="form-control" required>
        </div>
        <div class="form-group">
            <label>Hình ảnh:</label>
            <input type="file" name="images" class="form-control" multiple>
        </div>

        <button type="submit" class="btn btn-primary">Thêm</button>
        <a href="home" class="btn btn-secondary">Hủy</a>
    </form>
</div>
</body>
</html>
