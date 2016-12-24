package com.budget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.budget.entity.BudgetItem;

@Controller
public class BudgetController {

    @GetMapping(value = {"/budget"})
    public String budget(Model model) {
        return "budget/budget";
    }

    @GetMapping(value = {"/budget/create"})
    public String createBudgetForm(Model model) {
        model.addAttribute("createBudgetForm", new BudgetItem());

        return "budget/createBudget";
    }

}
