package com.budget.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.UiEntity.SpendingSummary;
import com.budget.dao.ExpenseDao;
import com.budget.entity.BudgetItem;
import com.budget.entity.Expense;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;

@Service
public class SpendingService {

    @Autowired @Qualifier("MySql")
    private ExpenseDao expenseDao;

    @Autowired
    private CurrentUserUtils currentUserFinder;
    
    @Autowired 
    private BudgetItemService budgetItemService;

    
    public void insertExpense(Expense expense) {
        User currentUser = currentUserFinder.getCurrentUser();
        expense.setUser_id(currentUser.getId());
        expenseDao.insertExpense(expense);
    }

    public List<Expense> getCurrentMonthsExpenses() {
        User currentUser = currentUserFinder.getCurrentUser();
        return expenseDao.getExpensesForUserAfterDate(currentUser, getFirstDayOfMonth());
    }
    
    public Timestamp getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new Timestamp(cal.getTimeInMillis());
    }

    public Map<String, SpendingSummary> getAllSpendingSummaries() {
        User currentUser = currentUserFinder.getCurrentUser();
        List<Expense> expenses = expenseDao.getExpensesForUserAfterDate(currentUser, getFirstDayOfMonth());
        List<BudgetItem> budgetItems = budgetItemService.getAllBudgetItems();
        
        Map<String, SpendingSummary> map = new HashMap<>();
        for (BudgetItem item : budgetItems) {
            map.put(item.getCategory().getName(), makeSpendingSummary(item, expenses));
            
        }
        return map;
    }
    
    private SpendingSummary makeSpendingSummary(BudgetItem item, List<Expense> expenses) {
        double expenseTotalForCategory = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().getId() == item.getCategory().getId()) {
                expenseTotalForCategory += expense.getAmount();
            }
        }
        
        double percentage = (expenseTotalForCategory / item.getAmount()) * 100; 
        String uiClass = getCssClassFromPercentage(percentage);
        SpendingSummary summary = new SpendingSummary(uiClass,
                                                      percentage,
                                                      expenseTotalForCategory);
        return summary;
    }
    
    private String getCssClassFromPercentage(double percentage) {
        if (percentage < 50) {
            return "progress-bar-success";
        } else if (percentage <= 100) {
            return "progress-bar-warning";
        } else {
            return "progress-bar-danger";
        }
    }
    
    
    
    
    
    
    
    
    
    

}
