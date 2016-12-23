package com.budget.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.budget.entity.CreateUser;
import com.budget.entity.User;
import com.budget.service.AuthenticationService;
import com.budget.service.UserService;

@Component
@Qualifier("signupValid")
public class SignupValidator implements Validator {
    
    @Autowired
    private AuthenticationService authService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)  {
        CreateUser user = (CreateUser) target;
        
        String username = user.getUsername();
        String password = user.getPassword();
        String passwordConfirm = user.getPasswordConfirm();
        
        if (!password.equals(passwordConfirm)) {
            errors.reject("passwordMatch",
                    "Your passwords do not match");
        }

        if (username == null || username.length() < 4) {
            errors.reject("username",
                    "username must be between 4 and 20 characters");
        }

        if (password == null || password.length() < 4) {
            errors.reject("password",
                    "password must be between 4 and 20 characters");
        }
        
        if (usernameExists(username)) {
            errors.reject("existingUsername",
                    "That username already exists");
        }
        
    }
    
    private boolean usernameExists(String username) {
        try {
            authService.loadUserByUsername(username);
            
        } catch (UsernameNotFoundException ex) {
            return false;
        }
        return true;
    }

}