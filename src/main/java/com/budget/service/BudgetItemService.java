package com.budget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.dao.BudgetItemDao;
import com.budget.entity.BudgetItem;
import com.budget.entity.Category;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;

@Service
public class BudgetItemService {
    
    @Autowired @Qualifier("MySql")
    BudgetItemDao budgetDao;

    @Autowired
    CurrentUserUtils currentUserFinder;

    public boolean hasBudgetedForCategory(Category category) {
        return budgetDao.budgetForCategoryExists(category);
    }
    
    public void createBudgetItem(BudgetItem item) {
        User user = currentUserFinder.getCurrentUser();
        item.setUser_id(user.getId());
        
        budgetDao.insertBudgetItem(item);
    }

    public List<BudgetItem> getAllBudgetItems() {
        User user = currentUserFinder.getCurrentUser();

        return budgetDao.getAllBudgetItemsForUser(user);
    }
    

}
