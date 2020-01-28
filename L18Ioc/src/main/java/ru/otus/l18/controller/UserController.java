package ru.otus.l18.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.l18.model.User;
import ru.otus.l18.service.UserService;

import java.util.List;

@RestController(value = "/L18-Ioc")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user-browser")
    public ModelAndView getUserBrowser() {
        ModelAndView model = new ModelAndView("user-browser");
        model.addObject("users", userService.getAllUsers());
        return model;
    }

    @GetMapping(value = "/user-editor")
    public ModelAndView getUserEditor() {
        return new ModelAndView("user-editor", "user", new User());
    }

    @PostMapping(value = "/user-editor")
    public ModelAndView saveUser(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
        List<String> errors = userService.validateUser(user);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return new ModelAndView("user-editor", model);
        }
        userService.saveUser(user);
        return getUserBrowser();
    }
}
