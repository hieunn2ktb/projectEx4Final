<%@ page import="ks.training.service.PropertyService" %>
<%@ page import="ks.training.entity.Property" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    String propertyIdStr = request.getParameter("id");
    int propertyId = 0;
    if (propertyIdStr != null) {
        try {
            propertyId = Integer.parseInt(propertyIdStr);
        } catch (NumberFormatException e) {
            propertyId = 0;
        }
    }
%>
<%
    PropertyService propertyService = new PropertyService();
    Property property = propertyService.findPropertyById(propertyId);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thông tin Bất động sản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .property-container {
            background: #fff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .carousel img {
            border-radius: 10px;
        }

        .price {
            font-size: 24px;
            font-weight: bold;
            color: #d9534f;
        }

        .btn-custom {
            background: linear-gradient(to right, #ff416c, #ff4b2b);
            border: none;
            color: white;
            padding: 10px 20px;
            font-size: 18px;
            border-radius: 5px;
            transition: 0.3s;
        }

        .btn-custom:hover {
            background: linear-gradient(to right, #ff4b2b, #ff416c);
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row property-container">
        <div class="col-md-8">
            <% if (property != null && property.getImages() != null && !property.getImages().isEmpty()) { %>
            <div id="propertyCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <% boolean first = true; %>
                    <% for (String imageUrl : property.getImages()) { %>
                    <div class="carousel-item <%= first ? "active" : "" %>">
                        <img src="<%= imageUrl %>" class="d-block w-100" alt="Hình ảnh bất động sản">
                    </div>
                    <% first = false; %>
                    <% } %>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#propertyCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#propertyCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            <% } %>

        </div>
        <% if (property != null) { %>
        <div class="col-md-4">
            <h3 class="mt-3"><%=property.getDescription()%></h3>
            <p><strong>Địa chỉ:</strong> <%=property.getAddress()%></p>
            <p class="price"><%=String.format("%.1f", property.getPrice() /1000000000)%> Tỷ (~<%=String.format("%.1f", property.getPrice() / (property.getAcreage() * 1000000))%>triệu/m²)</p>
            <p><strong>Diện tích:</strong> <%=property.getAcreage()%></p>
            <div class="alert alert-success" role="alert">
                <strong>⬆ 75%</strong> Giá tại khu vực này đã tăng trong vòng 1 năm qua.
            </div>
            <button class="btn btn-custom">Đặt Cọc</button>
            <button class="btn btn-custom">Mua</button>
            <button class="btn btn-custom">Quay lại</button>
        </div>
        <% }%>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
