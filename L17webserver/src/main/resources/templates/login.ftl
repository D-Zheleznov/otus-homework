<html lang="en">
<head>
    <title>DBWebService</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form name="login" id="loginForm" action="/login" method="POST">
        <fieldset>
            <h1 class="text-center">Аутентификация DBWebService</h1>
            <div class="input-group mb-2 mt-3">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fa fa-user"></i></span>
                </div>
                <input type="text" name="login" id="username" class="form-control" placeholder="Введите логин">
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fa fa-lock"></i></span>
                </div>
                <input type="password" name="password" id="password" class="form-control" placeholder="Введите пароль">
            </div>
            <div>
                <input type="submit" class="btn btn-primary" name="submit" value="Войти">
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>