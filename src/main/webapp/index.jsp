<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, ks.training.dto.PropertyDto" %>
<!DOCTYPE html>
<html lang="vi">

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
<div class="filter-bar">
    <div class="filter-price">
        <label for="min-price">Giá trong khoảng:</label>
        <input type="number" id="min-price" name="min-price" placeholder="Từ (USD)">
        <input type="number" id="max-price" name="max-price" placeholder="Đến (USD)">
    </div>
    <div class="filter-type">
        <label for="property-type">Loại hình bất động sản:</label>
        <select id="property-type" name="property-type">
            <option value="" disabled selected>Chọn loại hình bất động sản</option>
            <option value="can-ho">Căn Hộ</option>
            <option value="nha-dat">Nhà Đất</option>
            <option value="biet-thu">Biệt Thự</option>
        </select>
    </div>
    <div class="filter-Address">
        <label for="address">Địa chỉ:</label>
        <input type="text" id="address" name="address">
    </div>

    <button class="search-btn">Tìm kiếm</button>
</div>

<!-- Tiêu đề -->
<section class="title">
    <h1>1189 bất động sản để bán tại Việt Nam</h1>
</section>
<!-- Danh sách bất động sản -->
<section class="property-list">
    <div class="slider-container">
        <button id="prevBtn">❮</button>
        <div class="slider">
            <img src="property1.jpg" alt="Bất động sản 1">
            <img src="property2.jpg" alt="Bất động sản 2">
            <img src="property3.jpg" alt="Bất động sản 3">
            <img src="property4.jpg" alt="Bất động sản 4">
        </div>
        <button id="nextBtn">❯</button>
    </div>
    <div class="property-info">
        <p class="price">25.000.000.000 $</p>
        <h2>Metropolis OpusK Highend View Đẹp, Phải Xem 2PN, Q2</h2>
        <p>Thủ Thiêm, Quận 2, Hồ Chí Minh, Việt Nam</p>
        <div class="agent">
            <p>Nguyễn Thị Loan</p>
            <p>📞 +84 869319837</p>
            <a href="#">Liên Hệ Ngay</a>
        </div>
        <div style="background-color: red; position: relative; top: 150px; left: 0px;">
            <div><span>Loại bất động sản:</span></div>
            <div><span>Diện tích: </span></div>
        </div>

    </div>

</section>
<div class="pagination">
    <button id="prevPageBtn" class="pagination-btn">Trước</button>
    <span class="page-number">Trang 1</span>
    <button id="nextPageBtn" class="pagination-btn">Tiếp theo</button>
</div>

<script src="script.js"></script>
</body>

</html>