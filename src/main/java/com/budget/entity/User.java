package com.budget.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    private int id;
    
    private String username;
    
    private String password;

    public int getId() { return id; }

    public void setId(int i) { this.id = i; }

    public String getUsername() { return username; }

    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { 
        this.password = password; 
    }
}
