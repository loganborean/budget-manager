package com.budget.UiEntity;

public class SpendingSummary {
    
    private String progressBarClass;
    private double percentage;
    private double spent;
    
    public SpendingSummary(String progressBarClass, double percentage, double spent) {
        this.progressBarClass = progressBarClass;
        this.percentage = percentage;
        this.spent = spent;
    }

    public String getProgressBarClass() {
        return progressBarClass;
    }
    public void setProgressBarClass(String progressBarClass) {
        this.progressBarClass = progressBarClass;
    }
    public double getSpent() {
        return spent;
    }
    public void setSpent(double spent) {
        this.spent = spent;
    }
    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

}
