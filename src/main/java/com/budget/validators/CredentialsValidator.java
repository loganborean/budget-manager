package com.budget.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.budget.entity.User;
import com.budget.service.UserService;

@Component
@Qualifier("credValid")
public class CredentialsValidator implements Validator {
    
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)  {
        User user = (User) target;
        if (!userService.isValidUser(user)) {
            errors.reject("invalidLogin",
                    "That Username or Password does not exist");
        }
        
        
    }

}
