package com.budget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BudgetController {

    @GetMapping(value = {"/budget"})
    public String loginForm(Model model) {
        return "budget/budget";
    }

}
