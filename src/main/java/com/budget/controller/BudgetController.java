package com.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.budget.entity.BudgetItem;
import com.budget.service.BudgetItemService;
import com.budget.service.CategoryService;

@Controller
public class BudgetController {
    
    @Autowired @Qualifier("budgetItemValidator")
    Validator budgetItemValidator;

    @Autowired
    BudgetItemService budgetItemService;

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/budget"})
    public String budget(Model model) {
        model.addAttribute("budgetItems", budgetItemService.getAllBudgetItems());
        return "budget/budget";
    }

    @GetMapping(value = {"/budget/create"})
    public String createBudgetForm(Model model) {
        model.addAttribute("createBudgetForm", new BudgetItem());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "budget/createBudget";
    }
    

    @PostMapping(value = {"/budget/create"})
    public String createBudgetItem(@ModelAttribute("createBudgetForm")
                                   BudgetItem budgetItem,
                                   BindingResult result,
                                   Model model) {

        budgetItemValidator.validate(budgetItem, result);
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "budget/createBudget";
        }
        
        budgetItemService.createBudgetItem(budgetItem);
        return "redirect:/budget?budgetItemCreated";
    }
    
    

}
