package com.budget.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.entity.Category;
import com.budget.service.CategoryService;

@Component
public class TrendsDataRequestValidator {
    
    @Autowired
    CategoryService categoryService;

    public boolean validDataRequest(String categoryName, int dateFrom) {
        System.out.println(categoryName);
        System.out.println(dateFrom);
        if (dateFrom != 3 && dateFrom != 6 && dateFrom != 9 && dateFrom != 12) {
            return false;
        }
        
        if (!categoryName.equals("-1")) {
            Category category = new Category();
            category.setName(categoryName);
            return categoryService.categoryExists(category);
            
        }
        
        return true;
    }

}
