package com.budget.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.budget.entity.CreateUser;
import com.budget.entity.User;
import com.budget.service.UserService;

@Controller
public class PagesController {

    @Autowired @Qualifier("signupValid")
    private Validator signupValidator;
    
    @Autowired
    private UserService userService;

    @GetMapping(value = {"/login", "/"})
    public String loginForm(Model model) {
        return "login/login";
    }

    @GetMapping(value = "/signup")
    public String signup(Model model) {
        CreateUser createUser = new CreateUser();
        model.addAttribute("signupForm", createUser);
        return "login/signup";
    }

    @PostMapping(value = "/signup")
    public String signupSubmit(@ModelAttribute("signupForm") CreateUser user,
                              BindingResult result) {
        signupValidator.validate(user, result);
        if (result.hasErrors()) {
            return "login/signup";
        }
        userService.registerUser(user);

        return "redirect:/login?signup";
        
    }

    
    

}
