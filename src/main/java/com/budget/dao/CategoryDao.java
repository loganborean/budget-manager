package com.budget.dao;

import java.util.List;

import com.budget.entity.Category;
import com.budget.entity.User;

public interface CategoryDao {
    
    boolean categoryExists(Category categoryToFind);

    void insertCategory(Category category);

    Category getCategoryById(int id);

    List<Category> getAllCategoriesForUser(User user);
}
