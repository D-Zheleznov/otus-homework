package ru.otus.l17.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.l17.service.DBManager;
import ru.otus.l17.service.DBUserServiceImpl;
import ru.otus.l17.servlet.LoginErrorServlet;
import ru.otus.l17.servlet.LoginServlet;
import ru.otus.l17.servlet.UserBrowserServlet;
import ru.otus.l17.servlet.UserEditorServlet;

public class MainClass {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        DBUserServiceImpl userService = DBUserServiceImpl.init(DBManager.getConnection());

        context.addServlet(new ServletHolder(new LoginServlet()),"/*");
        context.addServlet(new ServletHolder(new LoginErrorServlet()),"/login-error");
        context.addServlet(new ServletHolder(new UserBrowserServlet(userService)),"/user-browser");
        context.addServlet(new ServletHolder(new UserEditorServlet(userService)),"/user-editor");

        server.start();
        server.join();
    }
}
