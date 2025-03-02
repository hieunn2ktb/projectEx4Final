<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="WEB-INF/views/css/register.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>

<body>
<%
    String error = (request.getAttribute("error") + "");
    error = error.equals("null") ? "" : error;

    String email = request.getAttribute("email") + "";
    email = email.equals("null") ? "" : email;
    String fullName = request.getAttribute("fullName") + "";
    fullName = fullName.equals("null") ? "" : fullName;
    String phone = request.getAttribute("phone") + "";
    phone = phone.equals("null") ? "" : phone;
    String address = request.getAttribute("address") + "";
    address = address.equals("null") ? "" : address;

%>
<div class="container">
    <div>
        <h1>Register</h1>
        <p>Please fill in this form to create an account.</p>
        <hr>
        <div class="text-danger" id="error"><%=error%>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/user" method="post" >
        <input type="hidden" name="action" value="register">
        <div class="mb-3">
            <label for="fullName"><b>Full Name</b></label>
            <input type="text" class="form-control" placeholder="Enter Full Name" name="fullName" id="fullName"
                   required value="<%=fullName%>">
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label"><b>Phone</b></label>
            <input type="tel" class="form-control" placeholder="phone" name="phone" id="phone" required value="<%=phone%>">
        </div>
        <div class="mb-3">
            <label for="address"><b>Address</b></label>
            <input type="text" class="form-control" placeholder="address" name="address" id="address" required value="<%=address%>">
        </div>
        <div class="mb-3">
            <label for="email"><b>Email</b></label>
            <input type="email" class="form-control" placeholder="Enter Email" name="email" id="email" required value="<%=email%>">
        </div>
        <div class="mb-3">
            <label for="psw"><b>Password</b></label>
            <input type="password" class="form-control" placeholder="Enter Password" name="psw" id="psw" required
                   onkeyup="checkPasswordMatch()">
        </div>
        <div class="mb-3">
            <label for="rePassword"><b>Repeat Password</b></label> <span class="error"><span id="msg"
                                                                                             class="text-danger"></span></span>
            <input type="password" class="form-control" placeholder="Repeat Password" name="rePassword"
                   id="rePassword" required onkeyup="checkPasswordMatch()">
        </div>
        <input type="submit" class="btn btn-primary form-control" value="Register" name="register">
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>

<script>
    function checkPasswordMatch() {
        password = document.getElementById("psw").value;
        rePassword = document.getElementById("rePassword").value;
        if (password != rePassword) {
            rePassword = document.getElementById("msg").innerHTML = "Password does not match";
            return false;
        } else {
            rePassword = document.getElementById("msg").innerHTML = "";
            return true;
        }
    }
</script>

</html>