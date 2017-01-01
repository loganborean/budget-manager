package com.budget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.dao.BudgetItemDao;
import com.budget.dao.CategoryDao;
import com.budget.entity.Category;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;

@Service
public class CategoryService {
    
    @Autowired @Qualifier("MySql")
    CategoryDao categoryDao;

    @Autowired
    CurrentUserUtils currentUserFinder;

    public boolean categoryExists(Category category) {
        return categoryDao.categoryExists(category);
    }
    
    public void createCategory(Category category) {
        User user = currentUserFinder.getCurrentUser();
        category.setUser_id(user.getId());

        categoryDao.insertCategory(category);
    }
    
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        User user = currentUserFinder.getCurrentUser();

        return categoryDao.getAllCategoriesForUser(user);
    }

    public List<Category> getCategoriesBudgetedFor() {
        User user = currentUserFinder.getCurrentUser();
        return categoryDao.getAllCategoriesBudgetedForUser(user);
    }

}
