package ru.otus.l17.servlet;

import ru.otus.l17.service.DBManager;
import ru.otus.l17.service.DBUserService;
import ru.otus.l17.service.entity.AddressDataSet;
import ru.otus.l17.service.entity.PhoneDataSet;
import ru.otus.l17.service.entity.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@WebServlet("/user-editor")
public class UserEditorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DBUserService dbUserService;

    public UserEditorServlet(DBUserService dbUserService) {
        this.dbUserService = dbUserService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!DBManager.checkAuthIsActive())
            response.sendRedirect("/login");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        String htmlResponse = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<script type=\"text/javascript\">\n" +
                "    let phoneCounter = 1;\n" +
                "    const phoneSet = [];\n" +
                "    function addPhone() {\n" +
                "        let phones = document.getElementById('phones');\n" +
                "        let input = document.createElement(\"input\");\n" +
                "        input.id = 'phone' + phoneCounter;\n" +
                "        input.type = 'text';\n" +
                "        input.name = 'number' + phoneCounter;\n" +
                "        input.className = 'form-control mt-2';\n" +
                "        input.placeholder = 'Введите номер телефона ' + phoneCounter;\n" +
                "        input.onchange = function() {\n" +
                "            phoneSet.push(this.value);\n" +
                "            document.getElementById(\"phoneArray\").value = phoneSet;\n" +
                "        };\n" +
                "        phones.appendChild(input);\n" +
                "        phoneCounter++;\n" +
                "    }\n" +
                "</script>\n" +
                "<head>\n" +
                "    <title>User Editor</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                "          integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h1 class=\"text-center\">Добавление пользователя</h1>\n" +
                "    <form name=\"userEditor\" id=\"userEditor\" action=\"user-editor\" method=\"POST\" accept-charset=\"ISO-8859-1\">\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"name\">Имя*</label>\n" +
                "            <input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"Введите имя\" name=\"name\"/>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"age\">Возраст*</label>\n" +
                "            <input type=\"number\" class=\"form-control\" id=\"age\" placeholder=\"Введите возраст\" name=\"age\">\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label for=\"address\">Адрес*</label>\n" +
                "            <input type=\"text\" class=\"form-control\" id=\"address\" placeholder=\"Введите адрес\" name=\"street\">\n" +
                "        </div>\n" +
                "        <div class=\"card\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col text-left ml-1\">\n" +
                "                    <h6>Телефоны*</h6>\n" +
                "                </div>\n" +
                "                <div class=\"col text-right\">\n" +
                "                    <button id=\"phoneButton\" type=\"button\" class=\"btn btn-link\" onclick=\"addPhone()\">Добавить телефон</button>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"form-group mr-1 ml-1\" id=\"phones\">\n" +
                "                <input type=\"hidden\" id=\"phoneArray\" name=\"phoneArray\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"row mt-3\">\n" +
                "            <div class=\"col text-left\">\n" +
                "                <a href=\"/user-browser\" class=\"btn btn-secondary\">Назад</a>\n" +
                "            </div>\n" +
                "            <div class=\"col text-right\">\n" +
                "                <button type=\"submit\" class=\"btn btn-primary\">Добавить</button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </form>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        writer.println(htmlResponse);
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
