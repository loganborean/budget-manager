package com.budget.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.budget.UiEntity.SpendingHistoryItem;
import com.budget.entity.Category;
import com.budget.entity.Expense;
import com.budget.entity.User;

public interface ExpenseDao {
    
    void insertExpense(Expense ex);

    List<Expense> getExpensesForUserAfterDate(User currentUser, Timestamp firstDayOfMonth);

    boolean expenseExists(Expense expenseToValidate);

    void deleteExpenseById(int id);


    List<Expense> getExpensesForCategoryAfterDateForUser(Category category, Timestamp dateFrom, User user);

    List<Expense> getAllExpensesForUser(User currentUser);

    List<SpendingHistoryItem> getExpenseMonthSummary(User currentUser);


    boolean userHasExpenseForDate(User currentUser, int month, int year);

    List<Expense> getExpensesFromMonthYear(User currentUser, int month, int year);


}
