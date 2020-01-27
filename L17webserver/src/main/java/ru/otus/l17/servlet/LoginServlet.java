package ru.otus.l17.servlet;

import ru.otus.l17.service.DBManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        String htmlResponse = "<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                "<head>\n" +
                "<meta charset=\"ISO-8859-1\">" +
                "    <title>DBWebService</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" +
                "    <link href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\" rel=\"stylesheet\">\n" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">\n" +
                "    <form name=\"login\" id=\"loginForm\" action=\"login\" method=\"POST\">\n" +
                "        <fieldset>\n" +
                "            <h1 class=\"text-center\">Аутентификация DBWebService</h1>\n" +
                "            <div class=\"input-group mb-2 mt-3\">\n" +
                "                <div class=\"input-group-prepend\">\n" +
                "                    <span class=\"input-group-text\"><i class=\"fa fa-user\"></i></span>\n" +
                "                </div>\n" +
                "                <input type=\"text\" name=\"login\" id=\"username\" class=\"form-control\" placeholder=\"Введите логин\">\n" +
                "            </div>\n" +
                "            <div class=\"input-group mb-3\">\n" +
                "                <div class=\"input-group-prepend\">\n" +
                "                    <span class=\"input-group-text\"><i class=\"fa fa-lock\"></i></span>\n" +
                "                </div>\n" +
                "                <input type=\"password\" name=\"password\" id=\"password\" class=\"form-control\" placeholder=\"Введите пароль\">\n" +
                "            </div>\n" +
                "            <div>\n" +
                "                <input type=\"submit\" class=\"btn btn-primary\" name=\"submit\" value=\"Войти\">\n" +
                "            </div>\n" +
                "        </fieldset>\n" +
                "    </form>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        writer.println(htmlResponse);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!DBManager.checkAuth(request.getParameter(LOGIN), request.getParameter(PASSWORD))) {
            response.sendRedirect("/login-error");
        } else {
            DBManager.activateAuth();
            response.sendRedirect("/user-browser");
        }
    }
}