package com.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.dao.UserDao;
import com.budget.entity.CreateUser;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;

@Service
public class UserService {
    
    @Autowired @Qualifier("MySql")
    private UserDao userDao;

    @Autowired
    CurrentUserUtils currentUserFinder;
    
    public boolean isValidUser(User user) {
        return userDao.userExists(user);
    }

    public void registerUser(User user) {
        userDao.insertUser(user);
        userDao.insertDefaultCategoriesForUser(
                currentUserFinder.getCurrentUserByUsername(user.getUsername()));
    }

}
