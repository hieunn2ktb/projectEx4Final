<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/loginStyle.css">
</head>

<body>
<div class="container">
    <h2>Welcome</h2>
    <div class="logo">BĐS-VN</div>
    <form action="login" method="post">
        <div class="input-group">
            <label>Email</label>
            <input type="email" name="email" required>
        </div>
        <div class="input-group password-wrapper">
            <label>Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="login-btn">LOGIN</button>
    </form>
    <p class="signup-link">Don't have an account? <a href="#">Sign Up</a></p>
</div>

</body>

</html>