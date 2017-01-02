package com.budget.dao;

import java.sql.Timestamp;
import java.util.List;

import com.budget.entity.Expense;
import com.budget.entity.User;

public interface ExpenseDao {
    
    void insertExpense(Expense ex);

    List<Expense> getExpensesForUserAfterDate(User currentUser, Timestamp firstDayOfMonth);


}
