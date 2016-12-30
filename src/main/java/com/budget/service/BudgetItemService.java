package com.budget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.UiEntity.BudgetSummary;
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

    public boolean budgetItemBelongsToCurrentUser(Integer budgetItemId) {
        User user = currentUserFinder.getCurrentUser();
        BudgetItem item = new BudgetItem();
        item.setUser_id(user.getId());
        item.setId(budgetItemId);

        return budgetDao.budgetItemForUserExists(item);
    }

    public BudgetItem getBudgetItemById(int budgetItemId) {
        return budgetDao.getBudgetById(budgetItemId);
    }

    public void updateBudgetItem(BudgetItem item) {
        budgetDao.updateBudgetItemAmount(item);
        
    }

    public void deleteBudgetItemById(int budgetItemId) {
        budgetDao.deleteBudgetItemById(budgetItemId);
        
    }

    public BudgetSummary getBudgetSummary() {
        User user = currentUserFinder.getCurrentUser();
        BudgetSummary summary = new BudgetSummary();
        List<BudgetItem> allBudgetItems = budgetDao.getAllBudgetItemsForUser(user);
        summary.setBudgeted((int)getTotalAmountFromBudgetItems(allBudgetItems));
        // TODO add rest of budget summary
        return summary;
    }
    
    private double getTotalAmountFromBudgetItems(List<BudgetItem> items) {
        double total = 0;
        for (BudgetItem item : items) {
            total += item.getAmount();
        }
        return total;
    }
    

}
