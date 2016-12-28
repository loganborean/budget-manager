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

import com.budget.entity.Category;
import com.budget.service.CategoryService;

@Controller
public class CategoryController {
    
    @Autowired @Qualifier("categoryValidator")
    Validator categoryValidator; 

    @Autowired
    CategoryService categoryService; 

    @GetMapping(value = {"/category/create"})
    public String createCategoryForm(Model model) {
        model.addAttribute("createCategoryForm", new Category());

        return "budget/createCategory";
    }

    @PostMapping(value = {"/category/create"})
    public String createCategory(@ModelAttribute("createCategoryForm")
                                 Category category,
                                 BindingResult result) {
        categoryValidator.validate(category, result);
        if (result.hasErrors()) {
            return "budget/createCategory";
        }
        categoryService.createCategory(category);

        return "redirect:/budget/create?categoryInsert";
    }

}
