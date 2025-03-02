<%@ page import="ks.training.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, ks.training.dto.PropertyDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    List<PropertyDto> properties = (List<PropertyDto>) request.getAttribute("properties");
    if (properties == null) properties = new ArrayList<>();
%>
<%
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");

%>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Bất Động Sản</title>
    <link rel="stylesheet" href="views/css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

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


        body {
            background-color: #f8f8f8;
        }

        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: white;
            padding: 15px 50px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .navbar .logo {
            font-size: 20px;
            font-weight: bold;
            color: red;
        }

        .navbar ul {
            list-style: none;
            display: flex;
        }

        .navbar ul li {
            margin: 0 15px;
        }

        .navbar ul li a {
            text-decoration: none;
            color: black;
        }

        .navbar .btn {
            background-color: red;
            color: white;
            padding: 8px 15px;
            border-radius: 5px;
        }

        .hero {
            background: red;
            color: white;
            text-align: center;
            padding: 50px 20px;
        }

        .search-box input {
            width: 300px;
            padding: 10px;
            border: none;
            border-radius: 5px;
        }

        .filter-container {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }

        .filter-item {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .search-btn {
            background-color: white;
            color: red;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
        }

        .real-estate-list {
            padding: 20px;
            background: white;
            margin: 20px;
            border-radius: 10px;
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        .property {
            display: flex;
            align-items: center;
            background: #fff;
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            width: 50%;

        }

        .property-list {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 15px;
        }

        .property-image img {
            width: 150px;
            height: 100px;
            object-fit: cover;
            border-radius: 10px;
        }

        .property-info {
            margin-left: 20px;
        }

        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .pagination button {
            margin: 5px;
            padding: 8px 12px;
            border: none;
            background-color: #f1f1f1;
            cursor: pointer;
        }

        .pagination button:hover {
            background-color: #ddd;
        }
    </style>
</head>

<body>
<%
    Object obj = session.getAttribute("User");
    User user = null;
    if (obj != null) {
        user = (User) obj;

    }
%>

<%
    String deleteMessage = (String) session.getAttribute("deleteMessage");
    if (deleteMessage != null) {
%>
<div style="color: green; text-align: center; padding: 10px; background: #d4edda; border: 1px solid #c3e6cb; margin-bottom: 10px;">
    <%= deleteMessage %>
</div>
<%
        session.removeAttribute("deleteMessage");
    }
%>
<header class="navbar">
    <div class="logo">Batdongsan</div>
    <nav>
        <ul>
            <% if (user == null) { %>
            <li><a href="user/login.jsp" class="btn">Đăng Nhập</a></li>
            <% } else { %>
            <li><a style="while-space: nowrap;">Xin chào <%=user.getFullName()%>
            </a></li>
            <li><a href="?action=logout" class="btn">Đăng xuất</a></li>
            <li><a href="user/editUser.jsp" class="btn">Thay đổi thông tin</a></li>
            <% if ("Employee".equals(user.getRole())) { %>
            <li><a href="property/addProperty.jsp" class="btn">Thêm Bất Động Sản</a></li>
            <%
                    }
                }
            %>

        </ul>
    </nav>
</header>

<section class="hero">
    <form action="" method="get">
        <div class="search-box">
            <label for="address">Địa chỉ:</label>
            <input type="text" id="address" name="searchAddress">
        </div>
        <div class="filter-container">
            <div class="filter-item">
                <label for="property-type">Loại hình:</label>
                <select id="property-type" name="searchPropertyType">
                    <option value="" disabled selected>Chọn loại hình bất động sản</option>
                    <option value="Căn hộ">Căn Hộ</option>
                    <option value="Nhà riêng">Nhà riêng</option>
                    <option value="Đất nền">Đất nền</option>
                </select>
            </div>
            <div class="filter-item">
                <label for="min-price">Giá:</label>
                <input type="number" id="min-price" name="minPrice" placeholder="Từ (USD)">
                <input type="number" id="max-price" name="maxPrice" placeholder="Đến (USD)">
            </div>
            <button class="search-btn">Tìm kiếm</button>
        </div>
        </div>
    </form>

    <h1>Danh Sách Bất Động Sản</h1>
</section>
<% for (PropertyDto property : properties) { %>
<section class="real-estate-list">
    <div class="property-list">
        <div class="property">
            <div class="property-image">
                <img src="<%= request.getContextPath() %>/ImageServlet?propertyId=<%= property.getId() %>&imageIndex=<%= 0 %>"
                     class="d-block w-100" alt="<%=property.getTitle()%>">
            </div>
            <div class="property-info">
                <h3><%= property.getTitle() %>
                </h3>
                <p>Giá: <%=property.getPrice()%>
                </p>
                <p>Diện tích: <%=property.getAcreage()%>
                </p>
                <p>Địa chỉ: <%= property.getAddress() %>
                </p>
                <p>Loại Hình: <%=property.getPropertyType()%>
                </p>
            </div>
            <% if (user != null) { %>
            <a href="property/PropertyInfo.jsp?id=<%= property.getId() %>" class="search-btn">
                Xem chi Tiết
            </a>
            <% if ("Employee".equals(user.getRole())) { %>
            <a href="propertyMng?action=delete&id=<%= property.getId() %>&createBy=<%=property.getCreateBy()%>" class="search-btn">
                Xoá Bất Động Sản
            </a>
            <a href="property/editProperty.jsp?propertyId=<%= property.getId() %>" class="search-btn">
                Update Bất Động Sản
            </a>
            <% }
            } %>
        </div>

    </div>
</section>
<% } %>
<div>
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="${pageContext.request.contextPath}?page=${currentPage - 1}&minPrice=${minPrice}&maxPrice=${maxPrice}&searchAddress=${searchAddress}&searchPropertyType=${searchPropertyType}">
                &laquo; Trước
            </a>
        </c:if>

        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="${pageContext.request.contextPath}?page=${i}&minPrice=${minPrice}&maxPrice=${maxPrice}&searchAddress=${searchAddress}&searchPropertyType=${searchPropertyType}"
               class="${i == currentPage ? 'active' : ''}">
                    ${i}
            </a>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <a href="${pageContext.request.contextPath}?page=${currentPage + 1}&minPrice=${minPrice}&maxPrice=${maxPrice}&searchAddress=${searchAddress}&searchPropertyType=${searchPropertyType}">
                Tiếp &raquo;
            </a>
        </c:if>
    </div>

</div>

</body>

</html>