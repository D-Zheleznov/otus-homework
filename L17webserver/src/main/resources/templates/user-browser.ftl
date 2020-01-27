<html lang="en">
<head>
    <title>User Browser</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col text-left">
            <h1>Список пользователей</h1>
        </div>
        <div class="col mt-2">
            <form name="userBrowser" id="userBrowser" action="/user-browser" method="POST">
                <button type="submit" class="btn btn-secondary">Выход</button>
            </form>
        </div>
        <div class="col text-right mt-2">
            <a href="/user-editor" class="btn btn-primary">Добавить пользователя</a>
        </div>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">Имя</th>
            <th scope="col">Возраст</th>
            <th scope="col">Адрес</th>
            <th scope="col">Телефоны</th>
        </tr>
        </thead>
        <#list users as user>
            <tr>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>${user.addressDataSet}</td>
                <td>
                    <#list user.phoneDataSet as phone>
                        <p>${phone}</p>
                    </#list>
                </td>
            </tr>
        </#list>
    </table>
</div>
</body>
</html>