package com.budget.dao;

import java.util.List;

import com.budget.entity.BudgetItem;
import com.budget.entity.Category;
import com.budget.entity.User;

public interface BudgetItemDao {
    
    boolean budgetForCategoryExists(Category category);

    void insertBudgetItem(BudgetItem item);

    List<BudgetItem> getAllBudgetItemsForUser(User user);


}
