package com.budget.UiEntity;

public class BudgetSummary {
    
    private int budgeted;
    private int spent;
    private int left;
    private int overBudget;

    public int getBudgeted() {
        return budgeted;
    }
    public void setBudgeted(int budgeted) {
        this.budgeted = budgeted;
    }
    public int getSpent() {
        return spent;
    }
    public void setSpent(int spent) {
        this.spent = spent;
    }
    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }
    public int getOverBudget() {
        return overBudget;
    }
    public void setOverBudget(int overBudget) {
        this.overBudget = overBudget;
    }

}
