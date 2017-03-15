package com.budget.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.entity.Category;
import com.budget.service.CategoryService;
import com.budget.service.SpendingService;

@Component
public class AjaxDataRequestValidator {
    
    private static final int MONTH_NAME_INDEX = 0;
    private static final int MONTH_YEAR_INDEX = 1;
    private static final int NUM_MONTHYEAR_ITEMS = 2;
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpendingService spendingService;

    public boolean validDataRequest(String categoryName, int dateFrom) {
        if (dateFrom != 3 && dateFrom != 6 && dateFrom != 9 && dateFrom != 12) {
            return false;
        }
        
        if (!categoryName.equals("-1")) {
            Category category = new Category();
            category.setName(categoryName);
            return categoryService.categoryExists(category);
        }
        
        return true;
    }
    
    public boolean validSpendingHistoryRequest(String monthYear) {
        String[] monthAndYear = monthYear.split(" ");
        if (monthAndYear.length != NUM_MONTHYEAR_ITEMS) {
            return false;
        }
        
        if (!isValidMonthAndYear(monthYear)) {
            return false;
        }
        
        if (!getSpendingService().userHasExpensesThisMonthAndYear(monthYear)) {
            return false;
        }
        
        
        return true;
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

    private boolean isValidMonthAndYear(String monthYear) {
        SimpleDateFormat fmt = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        try {
            fmt.parse(monthYear);
        } catch(ParseException e) {
            return false;
        }
        return true;
    }

    public SpendingService getSpendingService() {
        return spendingService;
    }

    public void setSpendingService(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

}
