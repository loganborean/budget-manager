package com.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.budget.dao.UserDao;
import com.budget.entity.CreateUser;
import com.budget.entity.User;
import com.budget.utils.CurrentUserUtils;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class UserService {
    
    @Autowired @Qualifier("MySql")
    private UserDao userDao;

    @Autowired
    CurrentUserUtils currentUserFinder;
    
    public boolean isValidUser(User user) {
        if (userDao == null) {
            System.out.println("alsdjfkdf");
        }
        return userDao.userExists(user);
    }

    public void registerUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.insertUser(user);
        userDao.insertDefaultCategoriesForUser(
                currentUserFinder.getCurrentUserByUsername(user.getUsername()));
    }

}
