package ru.otus.l17.servlet;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.otus.l17.service.DBManager;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private Template loginTemplate;
    private Template loginErrorTemplate;

    public LoginServlet(Template loginTemplate, Template loginErrorTemplate) {
        this.loginTemplate = loginTemplate;
        this.loginErrorTemplate = loginErrorTemplate;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/html; charset=UTF-8");
            loginTemplate.process(null, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/html; charset=UTF-8");
            if (!DBManager.checkAuth(request.getParameter(LOGIN), request.getParameter(PASSWORD))) {
                loginErrorTemplate.process(null, response.getWriter());
            } else {
                DBManager.activateAuth();
                response.sendRedirect("/user-browser");
            }
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}