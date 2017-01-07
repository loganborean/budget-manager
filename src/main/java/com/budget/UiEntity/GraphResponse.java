package com.budget.UiEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GraphResponse {
    private Map<String, Double> monthTotals;

    public Map<String, Double> getMonthTotals() {
        return monthTotals;
    }

    public void setMonthTotals(Map<String, Double> monthTotals) {
        this.monthTotals = monthTotals;
    }

}
