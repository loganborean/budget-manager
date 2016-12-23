package com.budget.dao;

import com.budget.entity.User;

public interface UserDao {
    
    public boolean userExists(User user);

    User findByUsername(String username);

    public void insertUser(User user);

}
