<html lang="en">
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
        input.onchange = function () {
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
    <form name="userEditor" id="userEditor" action="/user-editor" method="POST" accept-charset="ISO-8859-1">
        <div class="form-group">
            <label for="name">Имя*</label>
            <input type="text" class="form-control" id="name" placeholder="Введите имя" name="name"/>
        </div>
        <div class="form-group">
            <label for="age">Возраст*</label>
            <input type="number" class="form-control" id="age" placeholder="Введите возраст" name="age">
        </div>
        <div class="form-group">
            <label for="address">Адрес*</label>
            <input type="text" class="form-control" id="address" placeholder="Введите адрес" name="street">
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
                <input type="hidden" id="phoneArray" name="phoneArray">
            </div>
        </div>
        <div class="row mt-3">
            <div class="col text-left">
                <a href="/user-browser" class="btn btn-secondary">Назад</a>
            </div>
            <div class="col text-right">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>