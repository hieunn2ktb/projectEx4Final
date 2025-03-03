<%@ page import="java.text.DecimalFormat" %>
<%@ page import="ks.training.dto.TransactionDto" %>
<%@ page import="ks.training.entity.User" %>
<%@ page import="ks.training.service.TransactionService" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    HttpSession sessionUser = request.getSession(false);
    Object obj = session.getAttribute("User");
    User user = null;
    if (sessionUser == null || obj == null) {
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        return;
    } else  {
        user = (User) obj;
    }
    int propertyId = Integer.parseInt(request.getParameter("propertyId"));
    int buyerId = Integer.parseInt(request.getParameter("buyerId"));
    String transactionType = request.getParameter("type");


    TransactionService transactionService = new TransactionService();
    TransactionDto transactionDto = transactionService.getPropertyById(propertyId);
    if (transactionDto == null) transactionDto = new TransactionDto();
    String msg  = request.getAttribute("msg") + "";
    msg = msg.equals("null") ? "" : msg;
    System.out.println(msg);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác nhận Giao dịch</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
        }

        h2 {
            text-align: center;
        }

        .info {
            margin-bottom: 15px;
        }

        .info strong {
            display: inline-block;
            width: 150px;
        }
        .text-danger{
            color : red;
        }
        .btn {
            display: block;
            width: 100%;
            padding: 10px;
            background: #28a745;
            color: white;
            text-align: center;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn:hover {
            background: #218838;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Xác nhận Giao dịch</h2>
    <div class="text-danger" id="error"><%=msg%>
    </div>
    <div class="info"><strong>Bất động sản:</strong> <%=transactionDto.getTitle() %>
    </div>
    <div class="info"><strong>Địa chỉ:</strong> <%= transactionDto.getAddress() %>
    </div>
    <div class="info"><strong>Giá:</strong> <%= new DecimalFormat("#,###").format(transactionDto.getPrice()) %> VNĐ</div>
    <div class="info"><strong>Người bán:</strong> <%= transactionDto.getSellerName() %>
    </div>
    <div class="info"><strong>Loại giao dịch:</strong> <%= transactionType %>
    </div>

    <form action="${pageContext.request.contextPath}/transaction" method="post">
        <input type="hidden" name="action" value="confirm">
        <input type="hidden" name="propertyId" value="<%= propertyId %>">
        <input type="hidden" name="buyerId" value="<%= buyerId %>">
        <input type="hidden" name="sellerId" value="<%= transactionDto.getSellerId() %>">
        <input type="hidden" name="transactionType" value="<%= transactionType %>">
        <button type="submit" class="btn">Xác nhận Giao dịch</button>
    </form>
</div>

</body>
</html>
