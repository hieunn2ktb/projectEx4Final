<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
<form action="TransactionServlet" method="post">
    <input type="hidden" name="propertyId" value="${property.id}">
    <input type="hidden" name="customerId" value="${sessionScope.customerId}">

    <label>Loại Giao Dịch:</label>
    <select name="transactionType">
        <option value="MUA">Mua</option>
        <option value="DAT_COC">Đặt Cọc</option>
    </select>

    <label>Số Tiền (VNĐ):</label>
    <input type="number" name="amount" required>

    <button type="submit">Tạo Giao Dịch</button>
</form>
</body>

</html>