<%@ page contentType="text/html;charset=UTF-8" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript">
    let phoneCounter = 1;
    const phoneSet = [];
    function addPhone() {
        let phones = document.getElementById('phones');
        let input = document.createElement("input");
        input.id = 'phone' + phoneCounter;
        input.type = 'text';
        input.name = 'number' + phoneCounter;
        input.className = 'form-control mt-2';
        input.placeholder = 'Введите номер телефона ' + phoneCounter;
        input.onchange = function() {
            phoneSet.push(this.value);
            document.getElementById("phoneArray").value = phoneSet;
        };
        phones.appendChild(input);
        phoneCounter++;
    }
</script>
<head>
    <title>User Editor</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1 class="text-center">Добавление пользователя</h1>
    <form name="userEditor" id="userEditor" action="/L18-Ioc/user-editor" method="POST" accept-charset="ISO-8859-1" modelAttribute="user">
        <div class="form-group">
            <label for="name">Имя*</label>
            <input type="text" class="form-control" id="name" placeholder="Введите имя" name="name"/>
        </div>
        <div class="form-group">
            <label for="age">Возраст*</label>
            <input type="number" class="form-control" id="age" placeholder="Введите возраст" name="age">
        </div>
        <div class="form-group">
            <label for="addressDataSet">Адрес*</label>
            <input type="text" class="form-control" id="addressDataSet" placeholder="Введите адрес" name="addressDataSet">
        </div>
        <div class="card">
            <div class="row">
                <div class="col text-left ml-1">
                    <h6>Телефоны*</h6>
                </div>
                <div class="col text-right">
                    <button id="phoneButton" type="button" class="btn btn-link" onclick="addPhone()">Добавить телефон</button>
                </div>
            </div>
            <div class="form-group mr-1 ml-1" id="phones">
                <input type="hidden" id="phoneArray" name="phoneDataSet">
            </div>
        </div>
        <div class="row mt-3">
            <div class="col text-left">
                <form name="userBrowser" id="userBrowser" action="/L18-Ioc/user-browser" method="GET">
                    <div class="col">
                        <button type="submit" class="btn btn-secondary">Назад</button>
                    </div>
                </form>
            </div>
            <div class="col text-right">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </div>
    </form>
    <h4 class="text-danger text-center">${errors}</h4>
</div>
</body>
</html>