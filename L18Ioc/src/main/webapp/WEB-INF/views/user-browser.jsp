<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
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
        <div class="col text-right mt-2">
            <form name="userEditor" id="userEditor" action="/L18-Ioc/user-editor" method="GET">
                <button type="submit" class="btn btn-primary">Добавить пользователя</button>
            </form>
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
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>${user.addressDataSet}</td>
                <td>${user.phoneDataSet}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>