package ru.otus.l17.servlet;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ru.otus.l17.service.DBServiceImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        MongoDatabase database = DBServiceImpl.getConnection();
        MongoCollection<Document> credentials = database.getCollection("credentials");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Document document = credentials.find(Filters.and(Filters.eq("login", login), Filters.eq("password", password))).first();
        if (document == null) {
            req.setAttribute("error_message", "Неверный логин или пароль");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } else
            req.getRequestDispatcher("/views/user-browser.jsp").forward(req, resp);
    }
}