package com.budget.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.budget.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    
    @Test
	public void contextLoads() {
        UserService service = new UserService();
        User testUser = new User();
        testUser.setId(3423);
        testUser.setUsername("slkdf");
        testUser.setPassword("sldkf");
        assertFalse(service.isValidUser(testUser));
	}

}
