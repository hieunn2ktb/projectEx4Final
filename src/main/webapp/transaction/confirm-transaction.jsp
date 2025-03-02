<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*, java.text.DecimalFormat" %>

<%
    // Lấy thông tin từ request (ID BĐS, ID người mua)
    int propertyId = Integer.parseInt(request.getParameter("propertyId"));
    int buyerId = Integer.parseInt(request.getParameter("buyerId"));
    String transactionType = request.getParameter("type"); // "Mua" hoặc "Đặt cọc"

    // Kết nối DB để lấy thông tin BĐS
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String propertyTitle = "", propertyAddress = "", sellerName = "";
    double propertyPrice = 0;
    int sellerId = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/real_estate_management", "root", "yourpassword");

        // Truy vấn thông tin BĐS
        String sql = "SELECT p.title, p.price, p.address, u.full_name AS seller_name, u.id AS seller_id " +
                "FROM properties p " +
                "JOIN users u ON p.created_by = u.id " +
                "WHERE p.id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, propertyId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            propertyTitle = rs.getString("title");
            propertyPrice = rs.getDouble("price");
            propertyAddress = rs.getString("address");
            sellerName = rs.getString("seller_name");
            sellerId = rs.getInt("seller_id");
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
    }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác nhận Giao dịch</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
        h2 { text-align: center; }
        .info { margin-bottom: 15px; }
        .info strong { display: inline-block; width: 150px; }
        .btn { display: block; width: 100%; padding: 10px; background: #28a745; color: white; text-align: center; border: none; border-radius: 5px; cursor: pointer; }
        .btn:hover { background: #218838; }
    </style>
</head>
<body>

<div class="container">
    <h2>Xác nhận Giao dịch</h2>

    <div class="info"><strong>Bất động sản:</strong> <%= propertyTitle %></div>
    <div class="info"><strong>Địa chỉ:</strong> <%= propertyAddress %></div>
    <div class="info"><strong>Giá:</strong> <%= new DecimalFormat("#,###").format(propertyPrice) %> VNĐ</div>
    <div class="info"><strong>Người bán:</strong> <%= sellerName %></div>
    <div class="info"><strong>Loại giao dịch:</strong> <%= transactionType %></div>

    <form action="process-transaction.jsp" method="post">
        <input type="hidden" name="propertyId" value="<%= propertyId %>">
        <input type="hidden" name="buyerId" value="<%= buyerId %>">
        <input type="hidden" name="sellerId" value="<%= sellerId %>">
        <input type="hidden" name="transactionType" value="<%= transactionType %>">

        <button type="submit" class="btn">Xác nhận Giao dịch</button>
    </form>
</div>

</body>
</html>
