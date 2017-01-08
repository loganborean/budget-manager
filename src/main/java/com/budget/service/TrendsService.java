package com.budget.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
            String monthName = new SimpleDateFormat("MMM YYYY").format(expense.getDate().getTime());
            double currentTotal = monthTotalExpense.get(monthName);
            monthTotalExpense.put(monthName, currentTotal + expense.getAmount());
        }
        
        GraphResponse response = new GraphResponse();
        response.setMonthTotals(monthTotalExpense);
        response.setGrandTotal(this.getTotal(expenses));
        String mostMonth = this.getMonthWithHighestExpense(monthTotalExpense);
        String leastMonth = this.getMonthWithLowestExpense(monthTotalExpense);
        response.setMostSpent(monthTotalExpense.get(mostMonth).intValue(), mostMonth);
        response.setLeastSpent(monthTotalExpense.get(leastMonth).intValue(), leastMonth);
        response.setAverage(this.getAverage(monthTotalExpense));
        
        
//        for (Map.Entry<String, Double> entry : monthTotalExpense.entrySet()) {
//            String key = entry.getKey();
//            Double value = entry.getValue();
//            System.out.println(key + "   " + value);
//        }
        
        return response;
    }
    private String getMonthWithLowestExpense(Map<String, Double> monthTotalExpense) {
        Entry<String, Double> randomEntry = monthTotalExpense.entrySet().iterator().next();

        double lowestExpense = randomEntry.getValue();
        String lowestExpenseMonth = randomEntry.getKey();

        for (Map.Entry<String, Double> entry : monthTotalExpense.entrySet()) {
            Double value = entry.getValue();
            if (value < lowestExpense) {
                lowestExpense = value;
                lowestExpenseMonth = entry.getKey();
            }
        }
        return lowestExpenseMonth;
    }

    private String getMonthWithHighestExpense(Map<String, Double> monthTotalExpense) {
        double highestExpense = 0;
        String highestExpenseMonth = null;
        for (Map.Entry<String, Double> entry : monthTotalExpense.entrySet()) {
            Double value = entry.getValue();
            if (value > highestExpense) {
                highestExpense = value;
                highestExpenseMonth = entry.getKey();
            }
        }
        return highestExpenseMonth;
    }

    private int getAverage(Map<String, Double> monthTotalExpense) {
        double total = 0;
        for (Map.Entry<String, Double> entry : monthTotalExpense.entrySet()) {
            total +=  entry.getValue();        
        }
        return (int) (total / monthTotalExpense.size());
    }

    private double getTotal(List<Expense> expenses) {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }
    
    private Map<String, Double> initMonthTotalExpenseMap(int numMonthsBack) {
        Map<String, Double> monthExpenseTotal = new LinkedHashMap<>();

        Calendar cal = Calendar.getInstance();
        for (int i = numMonthsBack; i > 0; i--) {
            monthExpenseTotal.put(new SimpleDateFormat("MMM YYYY").format(cal.getTime()), 0.0);
            cal.add(Calendar.MONTH, -1);
        }
        
        return monthExpenseTotal;
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
