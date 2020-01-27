package ru.otus.l17.servlet;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.otus.l17.service.DBManager;
import ru.otus.l17.service.DBUserService;
import ru.otus.l17.service.entity.AddressDataSet;
import ru.otus.l17.service.entity.PhoneDataSet;
import ru.otus.l17.service.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class UserEditorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DBUserService dbUserService;
    private Template userEditorTemplate;

    public UserEditorServlet(DBUserService dbUserService, Template userEditorTemplate) {
        this.dbUserService = dbUserService;
        this.userEditorTemplate = userEditorTemplate;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!DBManager.checkAuthIsActive())
            response.sendRedirect("/login");
        try {
            response.setContentType("text/html; charset=UTF-8");
            userEditorTemplate.process(null, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> errors = new ArrayList<>();

        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String street = request.getParameter("street");
        String[] phones = request.getParameterValues("phoneArray");

        User user = new User();
        AddressDataSet address = new AddressDataSet();
        user.setAddressDataSet(address);
        user.setPhoneDataSet(new HashSet<>());

        if (isBlank(name))
            errors.add("Имя");
        else
            user.setName(name);
        if (isBlank(age) || Integer.parseInt(age) < 1)
            errors.add("Возраст");
        else
            user.setAge(Integer.parseInt(age));
        if (isBlank(street))
            errors.add("Адрес");
        else
            user.getAddressDataSet().setStreet(street);
        if (phones[0].equals(""))
            errors.add("Телефон");
        else
            Arrays.stream(phones).forEach(phone -> user.getPhoneDataSet().add(new PhoneDataSet(phone)));

        if (errors.isEmpty()) {
            this.dbUserService.create(user);
            response.sendRedirect("/user-browser");
        }
    }
}
