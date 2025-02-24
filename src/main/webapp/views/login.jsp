<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: #f7f7f7;
        }

        .container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        .container h2 {
            margin-bottom: 15px;
        }

        .logo {
            font-size: 30px;
            font-weight: bold;
            background: #000;
            color: #fff;
            width: 130px;
            height: 50px;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 10px auto;
            border-radius: 5px;
        }

        .input-group {
            margin: 15px 0;
            text-align: left;
        }

        .input-group label {
            display: block;
            font-size: 14px;
            color: #555;
        }

        .input-group input {
            width: 100%;
            padding: 8px;
            border: none;
            border-bottom: 2px solid #ccc;
            outline: none;
            font-size: 16px;
        }

        .input-group input:focus {
            border-bottom: 2px solid #6a11cb;
        }

        .password-wrapper {
            position: relative;
        }

        .password-wrapper i {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
        }

        .login-btn {
            width: 100%;
            padding: 10px;
            border: none;
            background: linear-gradient(to right, #00c6ff, #6a11cb);
            color: #fff;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }

        .login-btn:hover {
            opacity: 0.9;
        }

        .signup-link {
            margin-top: 10px;
            font-size: 14px;
            color: #555;
        }

        .signup-link a {
            color: #6a11cb;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Welcome</h2>
    <div class="logo">BĐS-VN</div>
    <form action="property-list" method="post">
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
    <p class="signup-link">Don't have an account? <a href="register">Sign Up</a></p>
</div>

</body>

</html>