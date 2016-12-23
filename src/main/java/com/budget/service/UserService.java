package com.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.dao.UserDao;
import com.budget.entity.CreateUser;
import com.budget.entity.User;

@Service
public class UserService {
    
    @Autowired
    @Qualifier("MySql")
    private UserDao userDao;
    
    public boolean isValidUser(User user) {
        if (userDao.userExists(user)) {
            return true;
        }
        return false;
        
    }

    public void registerUser(User user) {
        userDao.insertUser(user);
        
    }

}
