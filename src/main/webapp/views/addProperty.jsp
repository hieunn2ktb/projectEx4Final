<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Bất Động Sản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div class="container mt-5">
    <h2 class="text-center">Thêm / Sửa Bất Động Sản</h2>

    <form action="property_handler.jsp" method="POST" enctype="multipart/form-data">
        <!-- Tiêu đề -->
        <div class="mb-3">
            <label for="title" class="form-label">Tiêu đề</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>

        <!-- Mô tả -->
        <div class="mb-3">
            <label for="description" class="form-label">Mô tả</label>
            <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
        </div>

        <!-- Giá -->
        <div class="mb-3">
            <label for="price" class="form-label">Giá (VNĐ)</label>
            <input type="number" class="form-control" id="price" name="price" required>
        </div>

        <!-- Địa chỉ -->
        <div class="mb-3">
            <label for="address" class="form-label">Địa chỉ</label>
            <input type="text" class="form-control" id="address" name="address" required>
        </div>

        <!-- Loại BĐS -->
        <div class="mb-3">
            <label for="property_type" class="form-label">Loại BĐS</label>
            <select class="form-select" id="property_type" name="property_type" required>
                <option value="">-- Chọn loại --</option>
                <option value="Căn hộ">Căn hộ</option>
                <option value="Nhà riêng">Nhà riêng</option>
                <option value="Đất nền">Đất nền</option>
                <option value="Khác">Khác</option>
            </select>
        </div>

        <!-- Diện tích -->
        <div class="mb-3">
            <label for="acreage" class="form-label">Diện tích (m²)</label>
            <input type="number" class="form-control" id="acreage" name="acreage" required>
        </div>

        <!-- Hình ảnh -->
        <div class="mb-3">
            <label for="image" class="form-label">Hình ảnh</label>
            <input type="file" class="form-control" id="image" name="image" accept="image/*">
        </div>

        <!-- Nút Gửi -->
        <button type="submit" class="btn btn-primary">Lưu</button>
        <a href="property_list.jsp" class="btn btn-secondary">Hủy</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>