package com.budget.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class BudgetController {

    @GetMapping(value = {"/budget", "/"})
    public String loginForm(Model model) {
        return null;
    }

}
