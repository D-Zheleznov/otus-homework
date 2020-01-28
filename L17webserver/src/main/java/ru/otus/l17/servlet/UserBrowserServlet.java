package ru.otus.l17.servlet;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.otus.l17.service.DBManager;
import ru.otus.l17.service.DBUserService;
import ru.otus.l17.service.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserBrowserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DBUserService dbUserService;
    private Template userBrowserTemplate;

    public UserBrowserServlet(DBUserService dbUserService, Template userBrowserTemplate) {
        this.dbUserService = dbUserService;
        this.userBrowserTemplate = userBrowserTemplate;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!DBManager.checkAuthIsActive())
            response.sendRedirect("/login");
        try {
            response.setContentType("text/html; charset=UTF-8");
            List<User> users = this.dbUserService.loadAll();
            Map<String, Object> input = new HashMap<>();
            input.put("users", users);
            userBrowserTemplate.process(input, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DBManager.dropAuth();
        response.sendRedirect("/login");
    }
}
