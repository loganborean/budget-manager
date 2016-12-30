package com.budget.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.entity.BudgetItem;
import com.budget.service.BudgetItemService;

@Component
public class BudgetItemModificationValidator {

    @Autowired
    BudgetItemService budgetService;

    public boolean validModificationRequest(int budgetItemId) {
        return budgetService.budgetItemBelongsToCurrentUser(budgetItemId);
    }

    public boolean validAmount(BudgetItem budgetItem) {
        if (budgetItem == null) {
            return false;
        }
        return budgetItem.getAmount() <= 0.0001;
    }

}
