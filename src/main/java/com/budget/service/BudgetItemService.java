package com.budget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.UiEntity.BudgetSummary;
import com.budget.dao.BudgetItemDao;
import com.budget.entity.BudgetItem;
import com.budget.entity.Category;
import com.budget.entity.Expense;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;


@Service
public class BudgetItemService {
    
    @Autowired @Qualifier("MySql")
    BudgetItemDao budgetDao;

    @Autowired
    CurrentUserUtils currentUserFinder;
    
    @Autowired
    SpendingService spendingService;

    public boolean hasBudgetedForCategory(Category category) {
        return budgetDao.budgetForCategoryExists(category);
    }
    
    public void createBudgetItem(BudgetItem item) {
        User user = currentUserFinder.getCurrentUser();
        item.setUser_id(user.getId());
        
        budgetDao.insertBudgetItem(item);
    }

    public List<BudgetItem> getAllBudgetItems() {
        User user = currentUserFinder.getCurrentUser();

        return budgetDao.getAllBudgetItemsForUser(user);
    }

    public boolean budgetItemBelongsToCurrentUser(Integer budgetItemId) {
        User user = currentUserFinder.getCurrentUser();
        BudgetItem item = new BudgetItem();
        item.setUser_id(user.getId());
        item.setId(budgetItemId);

        return budgetDao.budgetItemForUserExists(item);
    }

    public BudgetItem getBudgetItemById(int budgetItemId) {
        return budgetDao.getBudgetById(budgetItemId);
    }

    public void updateBudgetItem(BudgetItem item) {
        budgetDao.updateBudgetItemAmount(item);
        
    }

    public void deleteBudgetItemById(int budgetItemId) {
        budgetDao.deleteBudgetItemById(budgetItemId);
        
    }

    public BudgetSummary getBudgetSummary() {
        User user = currentUserFinder.getCurrentUser();
        BudgetSummary summary = new BudgetSummary();
        List<BudgetItem> allBudgetItems = budgetDao.getAllBudgetItemsForUser(user);
        int budgeted = (int)getTotalAmountFromBudgetItems(allBudgetItems);
        int totalSpent = (int) getTotalAmountSpent();
        int left, overBudget;

        if (budgeted - totalSpent <= 0) {
            left = 0;
            overBudget = totalSpent - budgeted;
        } else {
            left = budgeted - totalSpent;
            overBudget = 0;
        }
        summary.setBudgeted(budgeted);
        summary.setSpent(totalSpent);
        summary.setLeft(left);
        summary.setOverBudget(overBudget);
        return summary;
    }
    
    private double getTotalAmountSpent() {
        List<Expense> expenses = spendingService.getCurrentMonthsExpenses();
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }
    
    private double getTotalAmountFromBudgetItems(List<BudgetItem> items) {
        double total = 0;
        for (BudgetItem item : items) {
            total += item.getAmount();
        }
        return total;
    }
    

}
