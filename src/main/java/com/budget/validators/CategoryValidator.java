package com.budget.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.budget.entity.Category;
import com.budget.entity.User;
import com.budget.service.CategoryService;
import com.budget.utils.CurrentUserUtils;

@Component
@Qualifier("categoryValidator")
public class CategoryValidator implements Validator {
    
    @Autowired
    CategoryService categoryService;
    

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public void validate(Object arg, Errors errors) {
        Category category = (Category) arg;
        
        String name = category.getName();
        if (name == null || name.length() == 0) {
            errors.reject("invalidCategory",
                    "You must enter something for the category name");
        }
        
        if (categoryService.categoryExists(category)) {
            errors.reject("categoryExists",
                    "You already have that category");
        }

        
    }

}
