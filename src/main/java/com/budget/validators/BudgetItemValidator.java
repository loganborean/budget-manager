package com.budget.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.budget.entity.BudgetItem;
import com.budget.service.BudgetItemService;

@Component
@Qualifier("budgetItemValidator")
public class BudgetItemValidator implements Validator {
    
    @Autowired
    BudgetItemService budgetService;

    @Override
    public boolean supports(Class<?> clazz) {
        return BudgetItem.class.equals(clazz);
    }

    @Override
    public void validate(Object arg, Errors errors) {
        BudgetItem budgetItem = (BudgetItem) arg;
        
        if (budgetService.hasBudgetedForCategory(budgetItem.getCategory())
                                                 ) {
            errors.reject("alreadyBudgeted",
                    "You have already budgeted for that category");
        }
        
        if (budgetItem.getAmount() <= 0.0001) {
            errors.reject("enterAmount",
                    "You must enter an amount");
        }
        
    }

}
