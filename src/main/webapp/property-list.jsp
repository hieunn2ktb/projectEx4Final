<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, ks.training.dto.PropertyDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--<jsp:useBean id="currentPage" scope="request" type="java.lang.Integer" />--%>
<%--<jsp:useBean id="totalPages" scope="request" type="java.lang.Integer" />--%>

<%
    List<PropertyDto> properties = (List<PropertyDto>) request.getAttribute("properties");
    if (properties == null) properties = new ArrayList<>();
%>
<%
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    System.out.println("DEBUG JSP - currentPage: " + currentPage);
    System.out.println("DEBUG JSP - totalPages: " + totalPages);


%>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Bất Động Sản</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="apple-touch-startup-image" href="images/">
</head>

<body>
<!-- Header -->
<header>
    <div class="logo">
        <img src="images/Untitled-25-copy.jpg" alt="Savills">
    </div>
    <div class="account">
        <a href="">Đăng nhập</a>
    </div>
</header>

<!-- Thanh bộ lọc -->
<form action="property-list" method="get" class="filter-bar">
    <div class="filter-price">
        <label for="min-price">Giá trong khoảng:</label>
        <input type="number" id="min-price" name="minPrice" placeholder="Từ (USD)">
        <input type="number" id="max-price" name="maxPrice" placeholder="Đến (USD)">
    </div>
    <div class="filter-type">
        <label for="property-type">Loại hình bất động sản:</label>
        <select id="property-type" name="searchPropertyType">
            <option value="" disabled selected>Chọn loại hình bất động sản</option>
            <option value="Căn hộ">Căn Hộ</option>
            <option value="Nhà riêng">Nhà riêng</option>
            <option value="Đất nền">Đất nền</option>
        </select>
    </div>
    <div class="filter-Address">
        <label for="address">Địa chỉ:</label>
        <input type="text" id="address" name="searchAddress">
    </div>
    <button class="search-btn">Tìm kiếm</button>
</form>

<!-- Tiêu đề -->
<section class="title">
    <h1> Bất Động Sản Việt Nam</h1>
</section>


<% for (PropertyDto property : properties) { %>
<section class="property-list">
    <div class="slider-container">
        <button id="prevBtn">❮</button>
        <div class="slider">
            <img src="<%= property.getImageUrl() %>" alt="<%= property.getTitle() %>">
        </div>
        <button id="nextBtn">❯</button>
    </div>

    <div class="property-info">
        <p class="price"><%= property.getPrice() %> $</p>
        <h2><%= property.getTitle() %></h2>
        <p><%= property.getAddress() %></p>
        <div class="agent">
            <p>Nhân viên tư vấn: </p>
            <p><%= property.getFullName() %></p>
            <p>📞 <%= property.getPhone() %></p>
        </div>
        <div style="background-color: red; position: relative; top: 150px; left: 0px;">
            <div><span>Loại bất động sản: <%= property.getPropertyType() %></span></div>
            <div><span>Diện tích: <%= property.getAcreage() %> m²</span></div>
        </div>
    </div>
</section>
<% } %>

<!-- Phân trang -->

<div class="pagination">
    <a href="?page=<%= (currentPage > 1) ? currentPage - 1 : 1 %>"
       class="pagination-btn <%= (currentPage == 1) ? "disabled" : "" %>">
        Trước
    </a>
    <span>Trang <%= currentPage %> / <%= totalPages %></span>
    <a href="?page=<%= (currentPage < totalPages) ? currentPage + 1 : totalPages %>"
       class="pagination-btn <%= (currentPage == totalPages) ? "disabled" : "" %>">
        Tiếp theo
    </a>
</div>


<script src="script.js"></script>
</body>

</html>
