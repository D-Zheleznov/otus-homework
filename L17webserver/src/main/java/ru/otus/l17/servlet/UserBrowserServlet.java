package ru.otus.l17.servlet;

import ru.otus.l17.service.DBManager;
import ru.otus.l17.service.DBUserService;
import ru.otus.l17.service.entity.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user-browser")
public class UserBrowserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DBUserService dbUserService;

    public UserBrowserServlet(DBUserService dbUserService) {
        this.dbUserService = dbUserService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!DBManager.checkAuthIsActive())
            response.sendRedirect("/login");

        response.setContentType("text/html; charset=UTF-8");
        List<User> users = this.dbUserService.loadAll();

        PrintWriter writer = response.getWriter();
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>User Browser</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container-fluid\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col text-left\">\n" +
                "            <h1>Список пользователей</h1>\n" +
                "        </div>\n" +
                "        <div class=\"col mt-2\">\n" +
                "        <form name=\"userBrowser\" id=\"userBrowser\" action=\"user-browser\" method=\"POST\">\n" +
                "             <button type=\"submit\" class=\"btn btn-secondary\">Выход</button>\n" +
                "         </form>" +
                "        </div>\n" +
                "        <div class=\"col text-right mt-2\">\n" +
                "            <a href=\"/user-editor\" class=\"btn btn-primary\">Добавить пользователя</a>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <table class=\"table\">\n" +
                "        <thead>\n" +
                "        <tr>\n" +
                "            <th scope=\"col\">Имя</th>\n" +
                "            <th scope=\"col\">Возраст</th>\n" +
                "            <th scope=\"col\">Адрес</th>\n" +
                "            <th scope=\"col\">Телефоны</th>\n" +
                "        </tr>\n" +
                "        </thead>\n" +
                "\n" +
                "\n");

        users.forEach(user -> {
            htmlResponse.append("<tr>").append("<td>").append(user.getName()).append("</td>")
                    .append("<td>").append(user.getAge()).append("</td>")
                    .append("<td>").append(user.getAddressDataSet()).append("</td>")
                    .append("<td>").append(user.getPhoneDataSet()).append("</td>")
                    .append("</tr>");
        });
        htmlResponse.append(
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");

        writer.println(htmlResponse.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DBManager.dropAuth();
        response.sendRedirect("/login");
    }
}
