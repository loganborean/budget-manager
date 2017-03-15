package com.budget.UiEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.budget.entity.Category;
import com.budget.entity.Expense;

public class MonthSpendingHistoryResponse {
    private List<Expense> expenses;
    private Map<String, Double> categoryAmountPair;

    public MonthSpendingHistoryResponse createResponse(List<Expense> specifiedMonthYearExpenses) {
        setExpenses(specifiedMonthYearExpenses);
        setCategoryAmountPair(this.makeCategoryAmountPair(specifiedMonthYearExpenses));
        return this;
    }

    private Map<String, Double> makeCategoryAmountPair(List<Expense> specifiedMonthYearExpenses) {
        Map<String, Double> categoryAmountMap = new HashMap<>();
        for (Expense expense : specifiedMonthYearExpenses) {

            String categoryName = expense.getCategory().getName();

            if (categoryAmountMap.containsKey(categoryName)) {
                double categoryTotal = categoryAmountMap.get(categoryName);
                categoryTotal += expense.getAmount();
                categoryAmountMap.put(categoryName, categoryTotal);

            } else {
                categoryAmountMap.put(categoryName, expense.getAmount());
            }
            
        }
        return categoryAmountMap;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Map<String, Double> getCategoryAmountPair() {
        return categoryAmountPair;
    }

    public void setCategoryAmountPair(Map<String, Double> categoryAmountPair) {
        this.categoryAmountPair = categoryAmountPair;
    }
    
}
