package com.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.dao.CategoryDao;
import com.budget.entity.Category;

@Service
public class CategoryService {
    
    @Autowired @Qualifier("MySql")
    CategoryDao categoryDao;

    public boolean categoryExists(Category category) {
        return categoryDao.categoryExists(category);
    }

}
