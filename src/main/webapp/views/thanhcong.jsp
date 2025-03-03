<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	HttpSession sessionUser = request.getSession(false);
	Object obj = session.getAttribute("User");
	if (sessionUser == null || obj == null) {
		response.sendRedirect(request.getContextPath() + "/user/login.jsp");
		return;
	}
%>
<body>
	<div class="container">Thao tác thành công! Vui lòng quay lại trang đăng nhập.</div>

</body>
</html>