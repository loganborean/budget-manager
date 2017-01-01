package com.budget.validators;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.budget.entity.Expense;

@Component
@Qualifier("expenseValidator")
public class ExpenseValidator implements Validator {
    

    @Override
    public boolean supports(Class<?> clazz) {
        return Expense.class.equals(clazz);
    }

    @Override
    public void validate(Object arg, Errors errors) {
        Expense expense = (Expense) arg;
        
        if (expense.getAmount() <= 0.0001 || expense.getAmount() >= 1000000000) {
            errors.reject("enterAmount",
                            "Invalid amount");
        }

        if (expense.getNote().length() > 100) {
            errors.reject("noteLength",
                            "Note is too long");
        }
        
    }

}
