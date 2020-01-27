package ru.otus.l17.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login-error")
public class LoginErrorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
                "<h4 class=\"text-danger text-center mt-4\">Неверный логин или пароль!</h4>\n" +
                "<a href=\"/login\" class=\"btn btn-secondary text-center\">Назад</a>" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        writer.println(htmlResponse);
    }
}
