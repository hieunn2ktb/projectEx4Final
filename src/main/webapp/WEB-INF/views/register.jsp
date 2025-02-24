<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="css/register.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <form action="">
        <div>
            <h1>Register</h1>
            <p>Please fill in this form to create an account.</p>
            <hr>
        </div>
        <div class="mb-3">
            <label for="fullName"><b>Full Name</b></label>
            <input type="text" class="form-control" placeholder="Enter Full Name" name="fullName" id="idFullName"
                   required>
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label"><b>Phone</b></label>
            <input type="tel" class="form-control" placeholder="phone" name="phone" id="phone" required>
        </div>
        <div class="mb-3">
            <label for="address"><b>Address</b></label>
            <input type="text" class="form-control" placeholder="address" name="address" id="address" required>
        </div>
        <div class="mb-3">
            <label for="email"><b>Email</b></label>
            <input type="email" class="form-control" placeholder="Enter Email" name="email" id="email" required>
        </div>
        <div class="mb-3">
            <label for="psw"><b>Password</b></label>
            <input type="password" class="form-control" placeholder="Enter Password" name="psw" id="psw" required>
        </div>
        <div class="mb-3">
            <label for="psw-repeat"><b>Repeat Password</b></label>
            <input type="password" class="form-control" placeholder="Repeat Password" name="psw-repeat"
                   id="psw-repeat" required>
        </div>
        <input type="submit" class="btn btn-primary form-control">
        <div class="container signin">
            <p>Already have an account? <a href="#">Sign in</a>.</p>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>

</html>