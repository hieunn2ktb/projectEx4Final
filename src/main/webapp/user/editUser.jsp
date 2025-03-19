<%@ page import="ks.training.entity.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
            integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
            integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz"
            crossorigin="anonymous"></script>
    <style>
        .red {
            color: red;
        }
    </style>
</head>



<body>
<%
    User user = null;
    HttpSession sessionUser = request.getSession(false);
    Object obj = session.getAttribute("User");
    if (sessionUser == null || obj == null) {
        response.sendRedirect(request.getContextPath() + "/user/login.jsp");
        return;
    } else {
        user = (User) obj;
    }
    if
    (user == null) { %>
<h1>Bạn chưa đăng nhập vào hệ thống. Vui lòng quay lại trang chủ!</h1>
<% } else { %>
<div class="container">
    <% String msgError = request.getAttribute("msgError") + "";
        msgError = (msgError.equals("null"))
                ? "" : msgError;
        String fullName = user.getFullName();
        String phone = user.getPhone();
        String address = user.getAddress(); %>
    <div class="container">
        <div class="text-center">
            <h1>THÔNG TIN TÀI KHOẢN</h1>
        </div>

        <div class="red" id="baoLoi">
            <%=msgError %>
        </div>
        <form class="form" action="user" method="post">
            <input type="hidden" name="action" value="editUser">
            <div class="row">
                <div class="col-sm-6">
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Họ và tên</label> <input
                            type="text" class="form-control" id="fullName" name="fullName"
                            value="<%=fullName%>">
                    </div>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Địa chỉ</label> <input type="text"
                                                                                   class="form-control" id="address"
                                                                                   name="address" value="<%=address%>">
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Điện thoại</label> <input type="tel"
                                                                                    class="form-control" id="phone"
                                                                                    name="phone" value="<%=phone%>">
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="<%= user.getEmail() %>"
                           readonly>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">password</label> <input type="password"
                                                                                     class="form-control" id="password"
                                                                                     name="password">
                </div>
                <div class="mb-3">
                    <label for="rePassword" class="form-label">Re-password</label> <input type="password"
                                                                                          class="form-control"
                                                                                          id="rePassword"
                                                                                          name="rePassword">
                </div>

                <hr/>
                <input class="btn btn-primary form-control" type="submit" value="Lưu thông tin"
                       name="submit" id="submit"/>
                <br>
                <a href="home" class="btn btn-success">Hủy</a>
            </div>
    </form>
</div>
</div>
<%} %>
</body>

</html>