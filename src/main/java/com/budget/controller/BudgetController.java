package com.budget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.budget.entity.BudgetItem;
import com.budget.service.BudgetItemService;
import com.budget.service.CategoryService;
import com.budget.service.SpendingService;
import com.budget.validators.BudgetItemModificationValidator;

@Controller
public class BudgetController {
    
    @Autowired @Qualifier("budgetItemValidator")
    private Validator budgetItemValidator;

    @Autowired 
    private BudgetItemModificationValidator budgetItemModificationValidator;

    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private SpendingService spendingService;;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = {"/budget"})
    public String budget(Model model) {
        model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());
        model.addAttribute("budgetItems", budgetItemService.getAllBudgetItems());
        model.addAttribute("categoryExpenseSummary", spendingService.getAllSpendingSummaries());
        return "budget/budget";
    }

    @GetMapping(value = {"/budget/create"})
    public String createBudgetForm(Model model) {
        model.addAttribute("createBudgetForm", new BudgetItem());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());

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
            model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());
            return "budget/createBudget";
        }
        
        budgetItemService.createBudgetItem(budgetItem);
        return "redirect:/budget?budgetItemCreated";
    }

    @GetMapping(value = "/budget/edit/{budgetItemId}")
    public String editBudgetForm(@PathVariable int budgetItemId, Model model) {

        if (!budgetItemModificationValidator.validModificationRequest(budgetItemId)) {
            return "redirect:/unauthorized";
        }

        String errorMessage = (String) model.asMap().get("ERRORS");
        if (errorMessage != null) {
            model.addAttribute("flashErrorMessage", errorMessage);
        }

        model.addAttribute("editingBudgetItem", budgetItemService.getBudgetItemById(budgetItemId));
        model.addAttribute("editBudgetItemForm", new BudgetItem());
        model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());
        return "budget/editBudget";
    }

    @GetMapping(value = "/budget/delete/{budgetItemId}")
    public String deleteBudgetItem(@PathVariable int budgetItemId, Model model) {

        if (!budgetItemModificationValidator.validModificationRequest(budgetItemId)) {
            return "redirect:/unauthorized";
        }

        budgetItemService.deleteBudgetItemById(budgetItemId);

        return "redirect:/budget";
    }
    

    @PostMapping(value = "/budget/edit/{budgetItemId}")
    public String editBudgetItem(@PathVariable int budgetItemId,
                                 @ModelAttribute("editBudgetItemForm") BudgetItem budgetItem,
                                 BindingResult result, RedirectAttributes redir,
                                 Model model) {

        if (!budgetItemModificationValidator.validModificationRequest(budgetItemId)) {
            return "redirect:/unauthorized";
        }
        
        if (budgetItemModificationValidator.validAmount(budgetItem)) {
            redir.addFlashAttribute("ERRORS", "Invalid Amount");
            return "redirect:/budget/edit/" + budgetItemId;
        }

        
        budgetItem.setId(budgetItemId);
        budgetItemService.updateBudgetItem(budgetItem);

        return "redirect:/budget";
    }
    
    

}
