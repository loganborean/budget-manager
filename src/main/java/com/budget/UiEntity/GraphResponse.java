package com.budget.UiEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GraphResponse {
    private Map<String, Double> monthTotals;
    private SpendingStatistic mostSpent;
    private SpendingStatistic leastSpent;
    private int average;
    private double grandTotal;


    public Map<String, Double> getMonthTotals() {
        return monthTotals;
    }

    public void setMonthTotals(Map<String, Double> monthTotals) {
        this.monthTotals = monthTotals;
    }
    
    public void setMostSpent(int amount, String monthYear) {
        mostSpent = new SpendingStatistic(amount, monthYear);
    }

    public SpendingStatistic getMostSpent() {
        return this.mostSpent;
    }

    public SpendingStatistic getLeastSpent() {
        return this.leastSpent;
    }


    public void setLeastSpent(int amount, String monthYear) {
        leastSpent = new SpendingStatistic(amount, monthYear);
    }
    
    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    private class SpendingStatistic {
        private int amount;
        private String monthYear;
        
        SpendingStatistic(int amount, String monthYear) {
            this.amount = amount;
            this.monthYear = monthYear;
        }
        public int getAmount() {
            return amount;
        }
        public void setAmount(int amount) {
            this.amount = amount;
        }
        public String getMonthYear() {
            return monthYear;
        }
        public void setMonthYear(String monthYear) {
            this.monthYear = monthYear;
        }
    }

}
