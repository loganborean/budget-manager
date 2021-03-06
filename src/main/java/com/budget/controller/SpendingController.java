package com.budget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.budget.UiEntity.MonthSpendingHistoryResponse;
import com.budget.entity.Expense;
import com.budget.service.BudgetItemService;
import com.budget.service.CategoryService;
import com.budget.service.SpendingService;
import com.budget.validators.AjaxDataRequestValidator;
import com.budget.validators.ExpenseModificationValidator;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class SpendingController {

    @Autowired
    private BudgetItemService budgetItemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpendingService spendingService;

    @Autowired @Qualifier("expenseValidator")
    private Validator expenseValidator;

    @Autowired 
    private ExpenseModificationValidator expenseModificationValidator;

    @Autowired
    private AjaxDataRequestValidator ajaxRequestValidator;

    @GetMapping(value = {"/spending"})
    public String spendingOverview(Model model) {
        
        model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());
        model.addAttribute("expenses", spendingService.getCurrentMonthsExpenses());
        return "spending/overview";
    }

    @GetMapping(value = {"/spending/history"})
    public String spendingHistory(Model model) {
        
        model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());
        model.addAttribute("history", spendingService.getSpendingHistoryForCurrentUser());
        return "spending/history";
    }

    @GetMapping(value = {"/spending/create"})
    public String createExpenseForm(Model model) {
        
        List<ObjectError> errors = (List<ObjectError>) model.asMap().get("ERRORS");
        if (errors != null) {
            model.addAttribute("flashErrorMessage", errors);
        }

        model.addAttribute("createExpenseForm", new Expense());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("budgetSummary", budgetItemService.getBudgetSummary());
        return "spending/createExpense";
    }

    @PostMapping(value = {"/spending/create"})
    public String createExpense(@ModelAttribute("createExpenseForm") Expense expense,
                                BindingResult result,
                                RedirectAttributes redir,
                                Model model) {
        
        expenseValidator.validate(expense, result);
        if (result.hasErrors()) {
            redir.addFlashAttribute("ERRORS", result.getAllErrors());

            return "redirect:/spending/create";
        }
        
        spendingService.insertExpense(expense);
        return "redirect:/spending";
    }

    @GetMapping(value = "/expense/delete/{id}")
    public String deleteBudgetItem(@PathVariable int id, Model model) {

        if (!expenseModificationValidator.validModificationRequest(id)) {
            return "redirect:/unauthorized";
        }

        spendingService.deleteExpenseById(id);


        return "redirect:/spending";
    }

    @PostMapping(value = "/spending/history/data")
    public @ResponseBody ResponseEntity<MonthSpendingHistoryResponse>
                         ajaxExpenseHistory(@RequestParam String monthYear) {

        if (!ajaxRequestValidator.validSpendingHistoryRequest(monthYear)) {
            return null;
        }

        MonthSpendingHistoryResponse responseEntity 
                = spendingService.makeMonthSpendingHistoryResponse(monthYear);
        return ResponseEntity.ok(responseEntity);

    }
}
