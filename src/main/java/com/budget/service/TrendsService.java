package com.budget.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.UiEntity.GraphResponse;
import com.budget.dao.CategoryDao;
import com.budget.dao.ExpenseDao;
import com.budget.entity.Category;
import com.budget.entity.Expense;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;

@Service
public class TrendsService {
    
    @Autowired @Qualifier("MySql")
    ExpenseDao expenseDao;

    @Autowired @Qualifier("MySql")
    CategoryDao categoryDao;
    
    @Autowired
    CurrentUserUtils currentUserFinder;
    
    private static final int JAN = 1;
    private static final int FEB = 2;
    private static final int MAR = 3;
    private static final int APR = 4;
    private static final int MAY = 5;
    private static final int JUN = 6;
    private static final int JUL = 7;
    private static final int AUG = 8;
    private static final int SEP = 9;
    private static final int OCT = 10;
    private static final int NOV = 11;
    private static final int DEC = 12;
    

    public GraphResponse getGraphData(String categoryName, int dateFrom) {
        
        Timestamp date = getStartingTimestampOfXMonthsAgo(dateFrom);
        Category category = null;
        if (!categoryName.equals("-1")) {
            category = getFindableCategory(categoryName);
        }
        
        List<Expense> expenses =
                expenseDao.getExpensesForCategoryAfterDateForUser(category,
                                                                  date,
                                                                  currentUserFinder.getCurrentUser());
        return this.makeGraphResponse(expenses, dateFrom);

    }
    
    public GraphResponse makeGraphResponse(List<Expense> expenses, int numMonthsBack) {
        Map<String, Double> monthTotalExpense = initMonthTotalExpenseMap(numMonthsBack);
        
        for (Expense expense : expenses) {
            String monthName = new SimpleDateFormat("MMMM").format(expense.getDate().getTime());
            double currentTotal = monthTotalExpense.get(monthName);
            monthTotalExpense.put(monthName, currentTotal + expense.getAmount());
        }
        
        for (Map.Entry<String, Double> entry : monthTotalExpense.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            System.out.println(key + "   " + value);
        }
        
        return null;
    }
    
    private Map<String, Double> initMonthTotalExpenseMap(int numMonthsBack) {
        Map<String, Double> monthExpenseTotal = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        for (int i = numMonthsBack; i > 0; i--) {
            monthExpenseTotal.put(new SimpleDateFormat("MMMM").format(cal.getTime()), 0.0);
            cal.add(Calendar.MONTH, -1);
        }
        
        return monthExpenseTotal;
    }

    public static void main(String[] args) {
        TrendsService s = new TrendsService();
        
        s.getGraphData("-1", 3);
    }
    
    
    private Timestamp getStartingTimestampOfXMonthsAgo(int months) {
        months = months * -1;
    
        Calendar cal = Calendar.getInstance();
        // add negative months to current month month to current month
        cal.add(Calendar.MONTH, months);
        // set DATE to 1, so first date of X months ago
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }

    private Category getFindableCategory(String categoryName) {
        User user = currentUserFinder.getCurrentUser();
        Category findableCategory = new Category();
        findableCategory.setName(categoryName);
        findableCategory.setUser_id(user.getId());
        Category category = categoryDao.getCategoryByName(findableCategory);
        return category;
    }
    

}
