package com.budget.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.service.SpendingService;

@Component
public class ExpenseModificationValidator {

    @Autowired
    SpendingService spendingService;

    public boolean validModificationRequest(int id) {
        return spendingService.expenseBelongsToCurentUser(id);
    }
    

}
