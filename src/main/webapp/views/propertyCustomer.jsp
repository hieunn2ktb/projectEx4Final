<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Bất Động Sản</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<header class="navbar">
    <div class="logo">Batdongsan</div>
    <nav>

    </nav>
</header>

<section class="hero">
    <div class="search-box">
        <input type="text" placeholder="Tìm kiếm nhà đất...">
    </div>
    <div class="filter-container">
        <div class="filter-item">
            <label for="property-type">Loại hình:</label>
            <select id="property-type">
                <option value="">Chọn loại hình</option>
                <option value="can-ho">Căn hộ</option>
                <option value="nha-rieng">Nhà riêng</option>
                <option value="dat-nen">Đất nền</option>
            </select>
        </div>
        <div class="filter-item">
            <label for="min-price">Giá:</label>
            <input type="number" id="min-price" placeholder="Từ (USD)">
            <input type="number" id="max-price" placeholder="Đến (USD)">
        </div>
        <button class="search-btn">Tìm kiếm</button>
    </div>
    <h1>Danh Sách Bất Động Sản</h1>
</section>

<section class="real-estate-list">
    <div class="property-list">
        <div class="property">
            <div class="property-image">
                <img src="house1.jpg" alt="Bất động sản 1">
            </div>
            <div class="property-info">
                <h3>Nhà riêng quận Hoàn Kiếm</h3>
                <p>Giá: 5 tỷ VND</p>
                <p>Diện tích: 100m²</p>
                <p>Địa chỉ: Hoàn Kiếm, Hà Nội</p>
                <p>Loại Hình: Nhà Đất</p>
            </div>
            <div class="property-info">
                <button class="search-btn">Đặt cọc</button>
            </div>
            <div class="property-info">
                <button class="search-btn">Mua</button>
            </div>
        </div>

    </div>

    <div class="pagination">
        <button>&laquo; Trước</button>
        <button>1</button>
        <button>2</button>
        <button>3</button>
        <button>Tiếp &raquo;</button>
    </div>
</section>

</body>

</html>