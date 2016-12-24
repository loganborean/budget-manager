package com.budget.dao;

import com.budget.entity.Category;

public interface CategoryDao {
    
    boolean categoryExists(Category categoryToFind);

}
