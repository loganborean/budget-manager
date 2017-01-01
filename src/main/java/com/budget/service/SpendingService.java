package com.budget.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.dao.ExpenseDao;
import com.budget.entity.Expense;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;

@Service
public class SpendingService {

    @Autowired @Qualifier("MySql")
    private ExpenseDao expenseDao;

    @Autowired
    private CurrentUserUtils currentUserFinder;

    
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

}
