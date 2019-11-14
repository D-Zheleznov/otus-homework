package ru.otus.l17.servlet;

import ru.otus.l17.service.DBService;
import ru.otus.l17.service.DBServiceImpl;
import ru.otus.l17.service.entity.AddressDataSet;
import ru.otus.l17.service.entity.PhoneDataSet;
import ru.otus.l17.service.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@WebServlet("/userEditor")
public class UserEditor extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        this.handleRequest(req, resp);
    }

    @SuppressWarnings("unchecked")
    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<String> errors = new ArrayList();

        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String street = req.getParameter("street");
        String[] phones = req.getParameterValues("phoneArray");

        DBService dbService = DBServiceImpl.init("users");
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

        if (!errors.isEmpty()) {
            req.setAttribute("error_message", "Должно быть указано: " + String.join(", ", errors));
            req.getRequestDispatcher("/views/user-editor.jsp").forward(req, resp);
        } else {
            dbService.create(user);
            req.getRequestDispatcher("/views/user-browser.jsp").forward(req, resp);
        }
    }
}
