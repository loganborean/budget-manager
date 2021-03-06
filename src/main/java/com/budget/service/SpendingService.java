package com.budget.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.UiEntity.MonthSpendingHistoryResponse;
import com.budget.UiEntity.SpendingHistoryItem;
import com.budget.UiEntity.SpendingSummary;
import com.budget.dao.ExpenseDao;
import com.budget.entity.BudgetItem;
import com.budget.entity.Category;
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
    
    public Map<Category, Double> getAllUnbudgetedForCategories() {
        User currentUser = currentUserFinder.getCurrentUser();
        List<Expense> expenses = expenseDao.getExpensesForUserAfterDate(currentUser, getFirstDayOfMonth());
        List<BudgetItem> budgetItems = budgetItemService.getAllBudgetItems();
        Set<Integer> budgetedForCategoryIds = new HashSet<Integer>(getListOfCategoryIds(budgetItems));
        
        Map<Category, Double> categoryAmountMap = new HashMap<>();
        
        for (Expense expense : expenses) {
            if (!budgetedForCategoryIds.contains(expense.getCategory().getId())) {
                if (categoryAmountMap.containsKey(expense.getCategory())) {
                    double currentAmount = categoryAmountMap.get(expense.getCategory());
                    categoryAmountMap.put(expense.getCategory(), currentAmount + expense.getAmount());
                } else {
                    categoryAmountMap.put(expense.getCategory(), expense.getAmount());
                }
            }
        }
        return categoryAmountMap;
    }
  
    public List<Integer> getListOfCategoryIds(List<BudgetItem> items) {
        List<Integer> list = new ArrayList<>();
        for (BudgetItem item : items) {
            list.add(item.getCategory().getId());
        }
        return list;
    }

    public boolean expenseBelongsToCurentUser(int id) {
        User currentUser = currentUserFinder.getCurrentUser();
        Expense expenseToValidate = new Expense();
        expenseToValidate.setUser_id(currentUser.getId());
        expenseToValidate.setId(id);
        return expenseDao.expenseExists(expenseToValidate);
    }

    public void deleteExpenseById(int id) {
        expenseDao.deleteExpenseById(id);
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
        if (percentage < 70) {
            return "progress-bar-success";
        } else if (percentage <= 100) {
            return "progress-bar-warning";
        } else {
            return "progress-bar-danger";
        }
    }

    public List<SpendingHistoryItem> getSpendingHistoryForCurrentUser() {
        User currentUser = currentUserFinder.getCurrentUser();
        List<SpendingHistoryItem> allUsersExpenses = expenseDao.getExpenseMonthSummary(currentUser);
        
        return allUsersExpenses;
    }

    public boolean userHasExpensesThisMonthAndYear(String monthYear) {
        User currentUser = currentUserFinder.getCurrentUser();
        Date date = getDateFromMonthYearStr(monthYear);
        return expenseDao.userHasExpenseForDate(currentUser,
                                                getMonth(date),
                                                getYear(date));
    }
    

    public MonthSpendingHistoryResponse makeMonthSpendingHistoryResponse(String monthYear) {
        User currentUser = currentUserFinder.getCurrentUser();
        Date date = getDateFromMonthYearStr(monthYear);
        List<Expense> specifiedMonthYearExpenses = 
                expenseDao.getExpensesFromMonthYear(currentUser,
                                                    getMonth(date),
                                                    getYear(date));
        MonthSpendingHistoryResponse response = new MonthSpendingHistoryResponse();
        return response.createResponse(specifiedMonthYearExpenses);
    }

    private Date getDateFromMonthYearStr(String monthYear) {
        SimpleDateFormat fmt = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        Date date = null;
        try {
             date = fmt.parse(monthYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        // calendar is 0 based (january is 0) for some reason.
        // mysql needs it to start from 1
        month += 1;
        return month;

        
    }
    private int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }
    
    

}
